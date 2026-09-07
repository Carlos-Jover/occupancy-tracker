import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OccupancyTrackerGUI implements ActionListener {
    static JButton enterButton;
    static JButton exitButton;
    static JLabel currentOccupancy;
    static JLabel occupancyPercentage;

    static Tracker tracker = new Tracker(10);

    static double percent = tracker.getOccupancyPercentage();
    static String percentText = String.format("%.0f%%", percent);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Occupancy Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);

        JLabel title = new JLabel("Occupancy Tracker");
        title.setBounds(100, 10, 300, 40);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createLineBorder(Color.green));

        JLabel currentOccupancyText = new JLabel("Current Occupancy:");
        currentOccupancyText.setBounds(140, 80, 200, 30);
        currentOccupancyText.setFont(new Font("Arial", Font.PLAIN, 20));
        currentOccupancyText.setHorizontalAlignment(JLabel.CENTER);
        currentOccupancyText.setBorder(BorderFactory.createLineBorder(Color.green));

        currentOccupancy = new JLabel(String.valueOf(tracker.getOccupancyCounter()));
        currentOccupancy.setBounds(200, 115, 70, 30);
        currentOccupancy.setFont(new Font("Arial", Font.PLAIN, 20));
        currentOccupancy.setHorizontalAlignment(JLabel.CENTER);
        currentOccupancy.setBorder(BorderFactory.createLineBorder(Color.green));

        occupancyPercentage = new JLabel(percentText);
        occupancyPercentage.setBounds(200, 230, 70, 30);
        occupancyPercentage.setFont(new Font("Arial", Font.PLAIN, 20));
        occupancyPercentage.setHorizontalAlignment(JLabel.CENTER);
        occupancyPercentage.setBorder(BorderFactory.createLineBorder(Color.green));

        enterButton = new JButton();
        enterButton.setText("Enter");
        enterButton.setBounds(80, 320, 110, 60);
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20));
        enterButton.setHorizontalAlignment(JLabel.CENTER);
        enterButton.setFocusable(false);
        enterButton.addActionListener(new OccupancyTrackerGUI());

        exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setBounds(300, 320, 110, 60);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        exitButton.setFocusable(false);
        exitButton.addActionListener(new OccupancyTrackerGUI());

        frame.add(title);
        frame.add(currentOccupancyText);
        frame.add(currentOccupancy);
        frame.add(occupancyPercentage);
        frame.add(enterButton);
        frame.add(exitButton);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterButton) {
            tracker.enter();
            currentOccupancy.setText(String.valueOf(tracker.getOccupancyCounter()));

            percent = tracker.getOccupancyPercentage();
            percentText = String.format("%.0f%%", percent);
            occupancyPercentage.setText(percentText);
        }

        if (e.getSource() == exitButton) {
            tracker.exit();
            currentOccupancy.setText(String.valueOf(tracker.getOccupancyCounter()));

            percent = tracker.getOccupancyPercentage();
            percentText = String.format("%.0f%%", percent);
            occupancyPercentage.setText(percentText);
        }
    }
}
