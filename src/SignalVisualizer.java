import javax.swing.*;
import java.awt.*;

public class SignalVisualizer extends JFrame {
    private RealTimeSignalPanel realTimeSignalPanel;
    private OfflineSignalPanel offlineSignalPanel;

    public SignalVisualizer() {
        setTitle("Phonocardiogram Signal Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        realTimeSignalPanel = new RealTimeSignalPanel();
        offlineSignalPanel = new OfflineSignalPanel();

        tabbedPane.addTab("Real-Time Signal", realTimeSignalPanel);
        tabbedPane.addTab("Offline Signal", offlineSignalPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
