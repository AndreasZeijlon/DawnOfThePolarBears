package andreaszeijlon.javaproject;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameFrame extends JFrame {

    public GameFrame(Grid grid, Shop shop, EnemySpawner spawner, TowerHandler towerHandler, BulletHandler bulletHandler, Menu menu, KeyHandler keyHandler) throws HeadlessException {
        super("DAWN OF THE POLAR BEARS");
        GameComponent gameComponent = new GameComponent(grid, shop, spawner, towerHandler, bulletHandler, menu);
        this.setLayout(new BorderLayout());
        this.add(gameComponent, BorderLayout.CENTER);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.isResizable();

        this.addMouseListener(keyHandler);
        this.addMouseMotionListener(keyHandler);


    }
}