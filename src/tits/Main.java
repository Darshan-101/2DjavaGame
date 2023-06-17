package tits;
import javax.swing.JFrame;

import tits.GamePanel;

public class Main{

    public  static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//this lets window properly close when user clicks x button
        window.setResizable(false);
        window.setTitle("Poggers the game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //causes window to be sized to fit the preferred size and layouts of it's subcomponents(GamePanel here)
        
        window.setLocationRelativeTo(null);//not specify the location of the window i.e. window will be displayed at the center of the screen
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
