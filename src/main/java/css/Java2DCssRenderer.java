package css;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Java2DCssRenderer extends JFrame {

    public Java2DCssRenderer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // HTML content with basic CSS
        String htmlContent = "<html><head><style>body { color: red; font-size: 20px; }</style></head><body>Hello, World!</body></html>";

        // Create a BufferedImage to draw HTML content
        BufferedImage bufferedImage = renderHtml(htmlContent);

        // Create a JPanel to display the BufferedImage
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, this);
            }
        };

        // Add JPanel to the frame
        add(panel);

        // Set the frame visible
        setVisible(true);
    }

    private BufferedImage renderHtml(String htmlContent) {
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Set font and color
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.setColor(Color.RED);

        // Render HTML content
        String[] lines = htmlContent.split("\n");
        int y = 30; // Starting y-coordinate
        for (String line : lines) {
            g2d.drawString(line, 20, y);
            y += 30; // Increment y-coordinate for the next line
        }

        g2d.dispose();
        return bufferedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Java2DCssRenderer());
    }
}
