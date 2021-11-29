package andreaszeijlon.javaproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class GameComponent extends JComponent {
    private static final int MENU_WIDTH = 819;
    private static final int MENU_HEIGHT = 460;
    private Grid grid;
    private Shop shop;
    private EnemySpawner spawner;
    private TowerHandler towerHandler;
    private BulletHandler bulletHandler;
    private andreaszeijlon.javaproject.Menu menu;


    public final static int TILE_SIZE = 60;
    private final static int SHOP_SIZE_X = 200;
    private final static int INFO_BOX = 175;


    public GameComponent(Grid grid, Shop shop, EnemySpawner spawner, TowerHandler towerHandler, BulletHandler bulletHandler, Menu menu) {
        this.grid = grid;
        this.shop = shop;
        this.spawner = spawner;
        this.towerHandler = towerHandler;
        this.bulletHandler = bulletHandler;
        this.menu = menu;
        this.addKeyBindings();

    }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        int row = Grid.checkLargestRow(grid);
        if (menu.getState() == State.MENU) {
            return new Dimension(MENU_WIDTH, MENU_HEIGHT);
        } else {
            return new Dimension(row * TILE_SIZE + SHOP_SIZE_X, grid.getSquares().length * TILE_SIZE + INFO_BOX);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        if (menu.getState() == State.MENU) {
	    menu.draw(g2d);
        }  else {
            grid.draw(g2d);
            shop.draw(g2d);
            spawner.draw(g2d);
            towerHandler.draw(g2d);
            bulletHandler.draw(g2d);
        }
    }


    private void addKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);

        Action exitGame = new EscapeAction();
        getActionMap().put("exitGame", exitGame);
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "exitGame");

        Action buyBasic = new buyBasicAction();
        getActionMap().put("buyBasic", buyBasic);
        inputMap.put(KeyStroke.getKeyStroke("D"), "buyBasic");

        Action buyArmored = new buyArmoredAction();
        getActionMap().put("buyArmored", buyArmored);
        inputMap.put(KeyStroke.getKeyStroke("A"), "buyArmored");

        Action buyScout = new buyScoutAction();
        getActionMap().put("buyScout", buyScout);
        inputMap.put(KeyStroke.getKeyStroke("S"), "buyScout");

        Action nextWave= new nextWaveAction();
        getActionMap().put("nextWave", nextWave);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "nextWave");

       }
    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            menu.escape();
        }
    }
    private class nextWaveAction extends AbstractAction {
           public void actionPerformed(ActionEvent e) {
	       spawner.nextWave();
	   }
    }
    private class buyBasicAction extends AbstractAction {
           public void actionPerformed(ActionEvent e) {
		towerHandler.buyBasic();
           }
       }
    private class buyArmoredAction extends AbstractAction {
               public void actionPerformed(ActionEvent e) {
		   towerHandler.buyArmorpiercing();
               }
           }
    private class buyScoutAction extends AbstractAction {
               public void actionPerformed(ActionEvent e) {
		   towerHandler.buyScout();
               }
           }




}