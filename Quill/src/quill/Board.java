package quill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Board extends JPanel {
    private BufferedImage image; // Holds the image to be displayed
    private int imageWidth = 800; // Default width of the image
    private int imageHeight = 600; // Default height of the image
    private boolean drawing = false; // Tracks whether the user is currently drawing
    private Graphics2D g2d; // Graphics context for drawing on the image
    private BufferedImage drawingLayer; // Layer for drawing on top of the image

    // Constructor initializes the panel
    public Board() {
        // Set the preferred size of the panel
        setPreferredSize(new Dimension(imageWidth, imageHeight));
        // Make the background of the panel transparent
        setOpaque(false);

        // Mouse listener for handling mouse press and release events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Start drawing if an image is loaded
                if (image != null) {
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Stop drawing when the mouse is released
                drawing = false;
            }
        });

        // Mouse motion listener for handling mouse dragging events
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Draw on the drawing layer if drawing is active and an image is loaded
                if (drawing && image != null) {
                    if (drawingLayer != null) {
                        // Set the drawing color (e.g., black) and draw a line at the current mouse position
                        g2d.setColor(Color.BLACK); // You can change this to use the selected pen color
                        g2d.drawLine(e.getPoint().x, e.getPoint().y, e.getPoint().x, e.getPoint().y);
                        repaint(); // Request a repaint to show the drawing
                    }
                }
            }
        });
    }

    // Method to load an image from a file
    public void setImage(File file) {
        try {
            // Read the image from the file
            image = javax.imageio.ImageIO.read(file);
            imageWidth = image.getWidth(); // Update the image width
            imageHeight = image.getHeight(); // Update the image height
            setPreferredSize(new Dimension(imageWidth, imageHeight)); // Set the preferred size of the panel

            // Initialize the drawing layer
            drawingLayer = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            g2d = drawingLayer.createGraphics(); // Create a graphics context for the drawing layer
            g2d.setComposite(AlphaComposite.SrcOver); // Set the compositing mode for drawing
            g2d.setColor(Color.BLACK); // Set the default drawing color
            g2d.setStroke(new BasicStroke(2)); // Set the thickness of the drawing stroke

            // Request a repaint to display the loaded image
            repaint();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an error occurs while reading the image
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting
        if (image != null) {
            // Draw the image on the panel
            g.drawImage(image, 0, 0, imageWidth, imageHeight, this);
            if (drawingLayer != null) {
                // Draw the drawing layer on top of the image
                g.drawImage(drawingLayer, 0, 0, this);
            }
        }
    }
}
