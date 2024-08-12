package quill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;

public class Quill extends JFrame implements ActionListener {
    JTextArea area; // Text area for displaying and editing text
    String text; // Holds the text to be copied/pasted
    private Board board;  // Reference to the Board instance (whiteboard)
    private JFrame boardFrame; // Reference to the Board window

    Quill() {
        setTitle("Quill"); // Set the window title
        ImageIcon quillIcon = new ImageIcon(ClassLoader.getSystemResource("quill/npd_icon.jpeg"));
        Image icon = quillIcon.getImage();
        setIconImage(icon); // Set the window icon

        JMenuBar menubar = new JMenuBar(); // Create the menu bar
        menubar.setBackground(Color.lightGray); // Set the background color

        // Create and configure the "File" menu
        JMenu file = new JMenu("File");
        file.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem newfile = new JMenuItem("NEW");
        newfile.addActionListener(this); // Add action listener for the NEW menu item
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)); // Shortcut for NEW

        JMenuItem open = new JMenuItem("OPEN");
        open.addActionListener(this); // Add action listener for the OPEN menu item
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK)); // Shortcut for OPEN

        JMenuItem save = new JMenuItem("SAVE");
        save.addActionListener(this); // Add action listener for the SAVE menu item
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)); // Shortcut for SAVE

        JMenuItem print = new JMenuItem("PRINT");
        print.addActionListener(this); // Add action listener for the PRINT menu item
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK)); // Shortcut for PRINT

        JMenuItem exit = new JMenuItem("EXIT");
        exit.addActionListener(this); // Add action listener for the EXIT menu item
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK)); // Shortcut for EXIT

        // Add menu items to the "File" menu
        file.add(newfile);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        menubar.add(file); // Add "File" menu to the menu bar

        // Create and configure the "Edit" menu
        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this); // Add action listener for the CUT menu item
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK)); // Shortcut for CUT

        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(this); // Add action listener for the COPY menu item
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK)); // Shortcut for COPY

        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(this); // Add action listener for the PASTE menu item
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK)); // Shortcut for PASTE

        JMenuItem select_all = new JMenuItem("Select All");
        select_all.addActionListener(this); // Add action listener for the SELECT ALL menu item
        select_all.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK)); // Shortcut for SELECT ALL

        // Add menu items to the "Edit" menu
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(select_all);

        menubar.add(edit); // Add "Edit" menu to the menu bar

        // Create and configure the "Pen" menu
        JMenu penMenu = new JMenu("Pen");
        penMenu.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem choosePenColor = new JMenuItem("Choose Color");
        choosePenColor.addActionListener(this); // Add action listener for the CHOOSE COLOR menu item

        JMenuItem loadImage = new JMenuItem("Load Image");
        loadImage.addActionListener(this); // Add action listener for the LOAD IMAGE menu item

        JMenuItem resizeImage = new JMenuItem("Resize Image");
        resizeImage.addActionListener(this); // Add action listener for the RESIZE IMAGE menu item

        // Add menu items to the "Pen" menu
        penMenu.add(choosePenColor);
        penMenu.add(loadImage);
        penMenu.add(resizeImage);

        menubar.add(penMenu); // Add "Pen" menu to the menu bar

        // Create and configure the "Board" menu
        JMenu white_board = new JMenu("Board");
        white_board.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem boardMenuItem = new JMenuItem("Board");
        boardMenuItem.addActionListener(this); // Add action listener for the BOARD menu item
        white_board.add(boardMenuItem);

        menubar.add(white_board); // Add "Board" menu to the menu bar

        // Create and configure the "Help" menu
        JMenu helpmenubar = new JMenu("Help");
        helpmenubar.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem help = new JMenuItem("About");
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK)); // Shortcut for About

        helpmenubar.add(help);

        menubar.add(helpmenubar); // Add "Help" menu to the menu bar

        setJMenuBar(menubar); // Set the menu bar for the window

        // Create and configure the text area
        area = new JTextArea();
        area.setFont(new Font("COMIC SANS", Font.PLAIN, 18));
        area.setLineWrap(true); // Enable line wrapping
        area.setWrapStyleWord(true); // Wrap text at word boundaries

        JScrollPane pane = new JScrollPane(area); // Add scroll bars to the text area
        pane.setBorder(BorderFactory.createEmptyBorder()); // Remove border
        add(pane); // Add the scroll pane to the window
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window

        setVisible(true); // Make the window visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Handle various menu item actions
        if (ae.getActionCommand().equals("NEW")) {
            area.setText(""); // Clear the text area
        } else if (ae.getActionCommand().equals("OPEN")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only.txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int action = chooser.showOpenDialog(this);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = chooser.getSelectedFile();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader, null); // Read the file into the text area
            } catch (Exception e) {
                e.printStackTrace(); // Print stack trace if an error occurs
            }
        } else if (ae.getActionCommand().equals("SAVE")) {
            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("SAVE");

            int action = saveas.showSaveDialog(this);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File filename = new File(saveas.getSelectedFile() + ".txt");
            BufferedWriter outfile = null;

            try {
                outfile = new BufferedWriter(new FileWriter(filename));
                area.write(outfile); // Write the text area content to the file
            } catch (Exception e) {
                e.printStackTrace(); // Print stack trace if an error occurs
            }
        } else if (ae.getActionCommand().equals("PRINT")) {
            try {
                area.print(); // Print the content of the text area
            } catch (Exception e) {
                e.printStackTrace(); // Print stack trace if an error occurs
            }
        } else if (ae.getActionCommand().equals("EXIT")) {
            System.exit(0); // Exit the application
        } else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText(); // Store the selected text
        } else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText(); // Store and remove the selected text
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        } else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition()); // Insert the stored text at the caret position
        } else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll(); // Select all text in the text area
        } else if (ae.getActionCommand().equals("Choose Color")) {
            // Show a color chooser dialog and set the pen color
            Color selectedColor = JColorChooser.showDialog(this, "Choose Pen Color", Color.BLACK);
            if (selectedColor != null) {
                if (board != null) {
                    board.setPenColor(selectedColor); // Ensure this method is defined in Board
                }
            }
        } else if (ae.getActionCommand().equals("Load Image")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (board != null) {
                    board.setImage(selectedFile); // Load the image into the board
                }
            }
        } else if (ae.getActionCommand().equals("Resize Image")) {
            if (boardFrame != null && boardFrame.isVisible()) {
                // Prompt user for new image dimensions
                String widthStr = JOptionPane.showInputDialog(this, "Enter width:");
                String heightStr = JOptionPane.showInputDialog(this, "Enter height:");

                try {
                    int width = Integer.parseInt(widthStr);
                    int height = Integer.parseInt(heightStr);
                    if (board != null) {
                        board.setImageSize(width, height); // Resize the image on the board
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Open the board window first.");
            }
        } else if (ae.getActionCommand().equals("Board")) {
            if (boardFrame == null || !boardFrame.isVisible()) {
                // Create and show the board window if it's not already visible
                boardFrame = new JFrame("Whiteboard");
                boardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                board = new Board(); // Create a new Board instance
                boardFrame.add(board); // Add the board to the window
                boardFrame.pack();
                boardFrame.setVisible(true);
            } else {
                boardFrame.toFront(); // Bring the board window to the front if it's already open
            }
        } else if (ae.getActionCommand().equals("About")) {
            new About().setVisible(true); // Show the "About" dialog
        }
    }

    public static void main(String[] args) {
        new Quill(); // Create and show the main application window
    }
}
