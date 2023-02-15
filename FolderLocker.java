package com.mycompany.FolderLocker;

import java.awt.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.*;

class FolderLocker {
    static String filename = "PasswordDirectory.txt";

    JFrame f = new JFrame();
    JLabel l1 = new JLabel("New Password      ");
    JLabel l2 = new JLabel("Confirm Password");
    JLabel l3 = new JLabel("Enter Password  ");
    JLabel l4 = new JLabel();
    JLabel l5 = new JLabel();
    JPasswordField p1 = new JPasswordField(20);
    JPasswordField p2 = new JPasswordField(20);
    JPasswordField p3 = new JPasswordField(20);
    JButton b1 = new JButton("Lock");
    JButton b2 = new JButton("Unlock");

    public FolderLocker(String s, int width, int height) {
        f.setTitle(s); // Setting up the title for the JFrame window
        f.setLayout(new FlowLayout()); // Setting up default Layout
        f.setVisible(true); // Visibility
        f.setSize(width, height); // Set size of the JFrame window
        f.setResizable(false); // Fixed size JFrame window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void storePassword(String dir, String password) {
        try {
            File file = new File(filename);
            FileWriter fr = new FileWriter(file, true);
            fr.write(dir + "@" + password + "\n");
            fr.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String extractPassword(String dir) {
        try {
            File file = new File(filename);
            Scanner Reader = new Scanner(file);

            while (Reader.hasNextLine()) {
                String[] data = Reader.nextLine().split("@");

                if (dir.equals(data[0])) {
                    return data[1];
                }
            }
            Reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return null;
    }

    public void locker(boolean locked) {
        /* If password field is altered, the border will be black */
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                colorBorder();
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                colorBorder();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                colorBorder();
            }

            private void colorBorder() {
                p1.setBorder(new JTextField().getBorder());
                p2.setBorder(new JTextField().getBorder());
                p3.setBorder(new JTextField().getBorder());
            }
        };

        String dir = "D:\\University\\3-1 Term\\Software Development Projects\\AppLocker";
        p1.getDocument().addDocumentListener(documentListener);
        p2.getDocument().addDocumentListener(documentListener);
        p3.getDocument().addDocumentListener(documentListener);

        // FolderLocker.extractPassword(dir);

        if (!locked) { // Show options to lock
            f.add(l5);
            f.add(l1);
            f.add(p1);
            f.add(l2);
            f.add(p2);
            f.add(b1);
            f.add(l4);

            l5.setText(dir);

            b1.addActionListener((e) -> {
                String val1 = String.valueOf(p1.getPassword());
                String val2 = String.valueOf(p2.getPassword());

                if (val1.length() == 0) {
                    l4.setText("All Fields Must Be Filled");
                    p1.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else if (val2.length() == 0) {
                    l4.setText("All Fields Must Be Filled");
                    p2.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else if (val1.equals(val2)) {
                    FolderLocker.storePassword(dir, val1);
                    l4.setText("Password Stored Successfully");
                } else {
                    l4.setText("Password Doesn't Match");
                }
            });
        } else { // Show options to unlock
            f.add(l5);
            f.add(l3);
            f.add(p3);
            f.add(b2);
            f.add(l4);

            l5.setText(dir);
            b2.addActionListener((e) -> {
                if (FolderLocker.extractPassword(dir).equals(String.valueOf(p3.getPassword()))) {
                    l4.setText("Password Matched");
                } else {
                    l4.setText("Password Doesn't Match");
                }
            });
        }
    }
}
 class Folder{

   public static void main(String[] args) {
        FolderLocker folderlocker = new FolderLocker("App Locker", 400, 180);
        folderlocker.locker(false);
    }
    
}
