import sun.awt.windows.WingDings;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame{
    GamePanel gamePanel;

    public GameFrame(){
        setSize(800, 800);
        setResizable(true);
        gamePanel = new GamePanel();
        setTitle("Komeiji Koishi Graze Simulator. IR15-1");
        add(gamePanel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = getWidth();
                int newHeight = getHeight();
                int delta = newWidth - newHeight;
                if(newWidth > newHeight){
                    gamePanel.SideBlackRect.setSize(delta/2 , delta/2);
                    gamePanel.BottomBlackRect.setSize(0, 0);
                }else if(newWidth < newHeight){
                    gamePanel.SideBlackRect.setSize(0, 0);
                    gamePanel.BottomBlackRect.setSize(-delta, (newHeight - newWidth)/2);
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        System.setProperty("sun.java2d.opengl", "true");
        gamePanel.play();

    }



    public static void main(String[] args) {
        new GameFrame();
    }
}
