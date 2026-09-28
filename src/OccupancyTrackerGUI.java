import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OccupancyTrackerGUI implements ActionListener {
    JButton enterButton;
    JButton exitButton;
    JButton resetButton;
    JLabel currentOccupancy;
    JLabel occupancyPercentage;
    JLabel levelOfOccupancy;

    Tracker tracker;

    public OccupancyTrackerGUI(Tracker tracker) {
        this.tracker = tracker;

        JFrame frame = new JFrame("Occupancy Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Occupancy Tracker");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(title);

        JLabel currentOccupancyText = new JLabel("Current Occupancy:");
        currentOccupancyText.setFont(new Font("Arial", Font.PLAIN, 22));
        currentOccupancyText.setHorizontalAlignment(JLabel.CENTER);

        currentOccupancy = new JLabel(String.valueOf(tracker.getOccupancyCounter()));
        currentOccupancy.setFont(new Font("Arial", Font.PLAIN, 20));
        currentOccupancy.setHorizontalAlignment(JLabel.CENTER);

        double percent = tracker.getOccupancyPercentage();
        String percentText = String.format("%.0f%%", percent);
        occupancyPercentage = new JLabel(percentText);
        occupancyPercentage.setFont(new Font("Arial", Font.PLAIN, 20));
        occupancyPercentage.setHorizontalAlignment(JLabel.CENTER);

        String levelOfOccupancyText = tracker.returnLevelOfOccupancy(percent);
        levelOfOccupancy = new JLabel(levelOfOccupancyText);
        levelOfOccupancy.setFont(new Font("Arial", Font.PLAIN, 20));
        levelOfOccupancy.setHorizontalAlignment(JLabel.CENTER);

        JPanel occupancyDetailsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        occupancyDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        occupancyDetailsPanel.add(currentOccupancyText);
        occupancyDetailsPanel.add(currentOccupancy);
        occupancyDetailsPanel.add(occupancyPercentage);
        occupancyDetailsPanel.add(levelOfOccupancy);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(occupancyDetailsPanel);

        enterButton = new JButton();
        enterButton.setText("Enter");
        enterButton.setPreferredSize(new Dimension(100, 40));
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20));
        enterButton.setHorizontalAlignment(JLabel.CENTER);
        enterButton.setFocusable(false);
        enterButton.addActionListener(this);

        exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

        resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resetButton.setHorizontalAlignment(JLabel.CENTER);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(enterButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(resetButton);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        OccupancyTrackerGUI occupancyTrackerGUI = new OccupancyTrackerGUI(new Tracker(10));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterButton) {
            tracker.enter();
        }

        if (e.getSource() == exitButton) {
            tracker.exit();
        }

        if (e.getSource() == resetButton) {
            tracker.resetCounter();
        }

        refreshDisplay();
    }

    private void refreshDisplay() {
        currentOccupancy.setText(String.valueOf(tracker.getOccupancyCounter()));

        double percent = tracker.getOccupancyPercentage();
        String percentText = String.format("%.0f%%", percent);
        occupancyPercentage.setText(percentText);

        String levelOfOccupancyText = tracker.returnLevelOfOccupancy(percent);
        levelOfOccupancy.setText(levelOfOccupancyText);
    }
}
