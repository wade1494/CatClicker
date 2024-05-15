import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClickerGame extends JFrame {
    private int score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }
    

    public ClickerGame() 
    {
        score = 0;

        JButton clickButton = new JButton(new ImageIcon("src\\download.jpg"));
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setPreferredSize(new Dimension(200, 200));
        clickButton.setLocation(500, 500);
        clickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if (toogle)
            {
                clickButton.setIcon(new ImageIcon("src\\images.jpg"));
                toogle = !toogle;
            }
            else
            {
                clickButton.setIcon(new ImageIcon("src\\download.jpg"));
                toogle = !toogle;
            }
            score++;
            updateScoreLabel();
            
            // Add the following code to return to download.jpg after a delay
            Timer timer = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                clickButton.setIcon(new ImageIcon("src\\download.jpg"));
                toogle = true;
                }
            });
            timer.setRepeats(false);
            timer.start();
            }
        });

        scoreLabel = new JLabel("Score: 0");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(clickButton);
        panel.add(scoreLabel);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Clicker Game");
        setSize(1000, 5000);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClickerGame();
            }
        });
    }
}