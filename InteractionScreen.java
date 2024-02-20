import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InteractionScreen extends JFrame {
    private JButton likeButton;
    private JButton commentButton;

    public InteractionScreen() {
        setTitle("Content Interaction");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        likeButton = new JButton("Like");
        commentButton = new JButton("Comment");

        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform like action logic here
                JOptionPane.showMessageDialog(null, "Liked!");
            }
        });

        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform comment action logic here
                JOptionPane.showMessageDialog(null, "Commented!");
            }
        });

        panel.add(likeButton);
        panel.add(commentButton);

        add(panel);
        setVisible(true);
    }
}
