import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OfflineSignalPanel extends JPanel implements ActionListener {
    private BufferedImage image;
    private Graphics2D g2d;
    private JRadioButton signal1Button, signal2Button, signal3Button;
    private JButton resetButton;
    private JLabel bpmLabel, peakCountLabel;
    private String selectedFile;
    private int[] samples;
    private int sampleRate;

    public OfflineSignalPanel() {
        setPreferredSize(new Dimension(800, 400));
        setLayout(new BorderLayout());

        image = new BufferedImage(800, 400, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();

        JPanel controlPanel = new JPanel();
        signal1Button = new JRadioButton("Christians Heart");
        signal2Button = new JRadioButton("Dong3 Heart");
        signal3Button = new JRadioButton("Dong3 HeartORG");
        ButtonGroup group = new ButtonGroup();
        group.add(signal1Button);
        group.add(signal2Button);
        group.add(signal3Button);

        resetButton = new JButton("Reset");
        bpmLabel = new JLabel("BPM: -");
        peakCountLabel = new JLabel("Peaks: -");

        signal1Button.setSelected(true);
        selectedFile = "resources/Christians_Heart.wav";

        signal1Button.addActionListener(this);
        signal2Button.addActionListener(this);
        signal3Button.addActionListener(this);
        resetButton.addActionListener(e -> resetSignal());

        controlPanel.add(signal1Button);
        controlPanel.add(signal2Button);
        controlPanel.add(signal3Button);
        controlPanel.add(resetButton);
        controlPanel.add(bpmLabel);
        controlPanel.add(peakCountLabel);

        add(controlPanel, BorderLayout.NORTH);
        add(new SignalDisplayPanel(), BorderLayout.CENTER);

        loadAndDrawSignal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signal1Button) {
            selectedFile = "resources/Christians_Heart.wav";
        } else if (e.getSource() == signal2Button) {
            selectedFile = "resources/Dong3_Heart.wav";
        } else if (e.getSource() == signal3Button) {
            selectedFile = "resources/Dong3_HeartORG.wav";
        }
        loadAndDrawSignal();
        bpmLabel.setText("BPM: -");
        peakCountLabel.setText("Peaks: -");
        calculateAndDisplayBPM(); 
        repaint();
    }

    private void loadAndDrawSignal() {
        File file = new File(selectedFile);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            sampleRate = (int) format.getSampleRate();
            byte[] bytes = audioInputStream.readAllBytes();
            samples = new int[bytes.length / 2];
            for (int i = 0; i < bytes.length; i += 2) {
                samples[i / 2] = (bytes[i] << 8) | (bytes[i + 1] & 0xFF);
            }
            drawWaveform(samples);
            calculateAndDisplayBPM();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private void drawWaveform(int[] samples) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLUE);
        int midY = image.getHeight() / 2;
        for (int i = 0; i < samples.length - 1; i++) {
            int x1 = i * image.getWidth() / samples.length;
            int x2 = (i + 1) * image.getWidth() / samples.length;
            int y1 = midY - (samples[i] * midY / 32768);
            int y2 = midY - (samples[i + 1] * midY / 32768);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private void resetSignal() {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        bpmLabel.setText("BPM: -");
        peakCountLabel.setText("Peaks: -");
        repaint();
    }

    private void calculateAndDisplayBPM() {
        int bpm = calculateBPM(samples, sampleRate);
        bpmLabel.setText("BPM: " + bpm);
    }

    private int calculateBPM(int[] samples, int sampleRate) {
        int minPeakDistance = sampleRate / 2; // Minimum distance between peaks (0.5 seconds)
        int peakCount = 0;
        int[] peakIndices = new int[samples.length];

        double mean = 0;
        for (int sample : samples) {
            mean += sample;
        }
        mean /= samples.length;

        double stdDev = 0;
        for (int sample : samples) {
            stdDev += Math.pow(sample - mean, 2);
        }
        stdDev = Math.sqrt(stdDev / samples.length);

        double threshold = mean + 1.5 * stdDev;

        for (int i = 1; i < samples.length - 1; i++) {
            if (samples[i] > threshold && samples[i] > samples[i - 1] && samples[i] > samples[i + 1]) {
                if (peakCount == 0 || (i - peakIndices[peakCount - 1] > minPeakDistance)) {
                    peakIndices[peakCount++] = i;
                }
            }
        }

        peakCountLabel.setText("Peaks: " + peakCount);

        if (peakCount < 2) {
            return 0;
        }

        double totalIntervals = 0;
        for (int i = 1; i < peakCount; i++) {
            totalIntervals += (peakIndices[i] - peakIndices[i - 1]) / (double) sampleRate;
        }

        double averageInterval = totalIntervals / (peakCount - 1);
        return (int) (60 / averageInterval); 
    }

    private class SignalDisplayPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Offline Signal Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new OfflineSignalPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
