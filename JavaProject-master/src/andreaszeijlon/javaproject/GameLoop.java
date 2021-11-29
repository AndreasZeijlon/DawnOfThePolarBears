package andreaszeijlon.javaproject;


import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Andreas Zeijlon on 2015-03-21.
 */
public class GameLoop {

    private static final int ONE_BILLION = 1000000000;
    private static final int ONE_MILLION = 1000000;
    private Shop shop;
    private JFrame frame;
    private EnemySpawner spawner;
    private TowerHandler towerHandler;
    private BulletHandler bulletHandler;
    private Menu menu;
    private long targetFPS;
    private long optimalTime;
    private long lastLoopTime;
    /**
     *
     */
    public final static int TARGET_FPS = 60;

    private int lastFpsTime, fps;


    public GameLoop(Shop shop, JFrame frame, EnemySpawner spawner, TowerHandler towerHandler, BulletHandler bulletHandler, Menu menu) {
        this.shop = shop;
        this.frame = frame;
        this.spawner = spawner;
        this.towerHandler = towerHandler;
        this.bulletHandler = bulletHandler;
        this.menu = menu;

        this.targetFPS = TARGET_FPS;
        this.optimalTime = ONE_BILLION / targetFPS;
        this.lastFpsTime = 0;
        this.fps = 0;

        loop();
    }

    private void loop() {

        while (menu.isGameRunning()) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            lastFpsTime += (int) updateLength;
            fps++;

            if (lastFpsTime >= ONE_BILLION) {
                lastFpsTime = 0;
                fps = 0;
            }

            updateGame();

            frame.repaint();

            sleep();
        }
    }

    private void sleep(){
        try {
            Thread.sleep((lastLoopTime - System.nanoTime() + optimalTime) / ONE_MILLION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch(IllegalArgumentException ignored){}
    }

    private void updateGame() {

        if (menu.getState() == State.INGAME) {
            frame.pack();
            if(spawner.isFastForward()){
                optimalTime = ONE_BILLION / (targetFPS*2);
            }
            else{
                optimalTime = ONE_BILLION / (targetFPS);
            }
	    if(!spawner.isBetweenRounds()){
		spawner.spawnEnemies();

		spawner.checkEnemyFinished();
		spawner.moveEnemy();

		towerHandler.towerPhysic();

		bulletHandler.updateBullets();


		if (shop.getHealth() <= 0) {
                    String name = JOptionPane.showInputDialog(frame, "Input your name please.", "Highscore", JOptionPane.PLAIN_MESSAGE);
                    menu.setState(State.MENU);
		    menu.setIfLost(true);
                    shop.setHoldsItem(null);
                    HighScore hs = new HighScore(name, spawner.getLevel());
                    HighScoreList.addToHighScore(hs);
                    spawner.setArmored(new ArrayList<>());
                    spawner.setBasic(new ArrayList<>());
                    spawner.setBoss(new ArrayList<>());
                    spawner.setSpy(new ArrayList<>());
                    bulletHandler.setBullets(new ArrayList<>());
            }}
	    else bulletHandler.setBullets(new ArrayList<>());
	}

    }

}

