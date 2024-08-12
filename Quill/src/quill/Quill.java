package quill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;

public class Quill extends JFrame implements ActionListener {
    JTextArea area;
    String text;
    private Board board;  // Reference to the Board instance
    private JFrame boardFrame; // Reference to the Board window

    Quill() {
        setTitle("Quill");
        ImageIcon quillIcon = new ImageIcon(ClassLoader.getSystemResource("quill/npd_icon.jpeg"));
        Image icon = quillIcon.getImage();
        setIconImage(icon);

        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.lightGray);

        JMenu file = new JMenu("File");
        file.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem newfile = new JMenuItem("NEW");
        newfile.addActionListener(this);
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        JMenuItem open = new JMenuItem("OPEN");
        open.addActionListener(this);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        JMenuItem save = new JMenuItem("SAVE");
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem print = new JMenuItem("PRINT");
        print.addActionListener(this);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem exit = new JMenuItem("EXIT");
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        file.add(newfile);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        menubar.add(file);

        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem select_all = new JMenuItem("Select All");
        select_all.addActionListener(this);
        select_all.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(select_all);

        menubar.add(edit);

        JMenu penMenu = new JMenu("Pen");
        penMenu.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem choosePenColor = new JMenuItem("Choose Color");
        choosePenColor.addActionListener(this);

        JMenuItem loadImage = new JMenuItem("Load Image");
        loadImage.addActionListener(this);

        JMenuItem resizeImage = new JMenuItem("Resize Image");
        resizeImage.addActionListener(this);

        penMenu.add(choosePenColor);
        penMenu.add(loadImage);
        penMenu.add(resizeImage);

        menubar.add(penMenu);

        JMenu white_board = new JMenu("Board");
        white_board.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem boardMenuItem = new JMenuItem("Board");
        boardMenuItem.addActionListener(this);
        white_board.add(boardMenuItem);

        menubar.add(white_board);

        JMenu helpmenubar = new JMenu("Help");
        helpmenubar.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem help = new JMenuItem("About");
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        helpmenubar.add(help);

        menubar.add(helpmenubar);

        setJMenuBar(menubar);

        area = new JTextArea();
        area.setFont(new Font("COMIC SANS", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("NEW")) {
            area.setText("");
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
                area.read(reader, null);
            } catch (Exception e) {
                e.printStackTrace();
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
                area.write(outfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("PRINT")) {
            try {
                area.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("EXIT")) {
            System.exit(0);
        } else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText();
        } else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        } else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition());
        } else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll();
        } else if (ae.getActionCommand().equals("Choose Color")) {
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
                    board.setImage(selectedFile);
                }
            }
        } else if (ae.getActionCommand().equals("Resize Image")) {
            if (boardFrame != null && boardFrame.isVisible()) {
                String widthStr = JOptionPane.showInputDialog(this, "Enter width:");
                String heightStr = JOptionPane.showInputDialog(this, "Enter height:");

                try {
                    int width = Integer.parseInt(widthStr);
                    int height = Integer.parseInt(heightStr);
                    if (board != null) {
                        board.setImageSize(width, height); // Ensure this method is defined in Board
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Open the board window first.");
            }
        } else if (ae.getActionCommand().equals("Board")) {
            if (boardFrame == null || !boardFrame.isVisible()) {
                boardFrame = new JFrame("Whiteboard");
                boardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                board = new Board();
                boardFrame.add(board);
                boardFrame.pack();
                boardFrame.setVisible(true);
            } else {
                boardFrame.toFront(); // Bring the board window to the front if it's already open
            }
        } else if (ae.getActionCommand().equals("About")) {
            new About().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Quill();
    }
}
