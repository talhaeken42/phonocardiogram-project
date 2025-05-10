import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RealTimeSignalPanel extends JPanel implements Runnable {
    private static final int BUFFER_SIZE = 4096;
    private TargetDataLine line;
    private Thread thread;
    private BufferedImage image;
    private Graphics2D g2d;

    public RealTimeSignalPanel() {
        setPreferredSize(new Dimension(800, 400));
        image = new BufferedImage(800, 400, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        startCapture();
    }

    private void startCapture() {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            thread = new Thread(this);
            thread.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[BUFFER_SIZE];
        while (thread != null) {
            int bytesRead = line.read(buffer, 0, buffer.length);
            if (bytesRead> 0) {
                int[] samples = new int[bytesRead / 2];
                for (int i = 0; i < bytesRead; i += 2) {
                    samples[i / 2] = (buffer[i] << 8) | (buffer[i + 1] & 0xFF);
                }
                drawWaveform(samples);
                repaint();
            }
        }
    }

    private void drawWaveform(int[] samples) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.GREEN);
        int midY = image.getHeight() / 2;
        for (int i = 0; i < samples.length - 1; i++) {
            int x1 = i * image.getWidth() / samples.length;
            int x2 = (i + 1) * image.getWidth() / samples.length;
            int y1 = midY - (samples[i] * midY / 32768);
            int y2 = midY - (samples[i + 1] * midY / 32768);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
