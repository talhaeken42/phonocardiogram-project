import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignalVisualizer visualizer = new SignalVisualizer();
            visualizer.setVisible(true);
        });
    }
}
