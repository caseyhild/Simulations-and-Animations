import javax.swing.*;

public class SoccerFreekick extends JFrame {
    public SoccerFreekick() {
        setTitle("Soccer Freekick");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SoccerFreekick::new);
    }
}
