package quill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {
    private Color penColor = Color.BLACK;
    private boolean drawing = false;
    private int prevX, prevY;

     public Board() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e)
            {
                drawing = true;
                prevX = e.getX();
                prevY = e.getY();
            }
            
            
            @Override
            public void mouseReleased(MouseEvent e) {
                drawing = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
          @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing) {
                    int x = e.getX();
                    int y = e.getY();
                    drawLine(prevX, prevY, x, y);
                    prevX = x;
                    prevY = y;
                }
            }
        });
        
        
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        Graphics g = getGraphics();
        g.setColor(penColor);
        g.drawLine(x1, y1, x2, y2);
        g.dispose();
    }

    public void clear() {
        Graphics g = getGraphics();
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
    }

    public void setPenColor(Color color) {
        penColor = color;
    }
public static void hatit()
{
 JFrame frame = new JFrame("Whiteboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board();
        frame.add(board, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> board.clear());
        controlPanel.add(clearButton);

        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose Pen Color", board.penColor);
            if (selectedColor != null) {
                board.setPenColor(selectedColor);
            }
        });
        controlPanel.add(colorButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
}
}
