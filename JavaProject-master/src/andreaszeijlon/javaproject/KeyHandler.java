package andreaszeijlon.javaproject;



import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class KeyHandler extends MouseAdapter {
    private static final int MENU_HEIGHT = 30;
    protected static Point motionPoint = null;

    private EnemySpawner spawner;
    private TowerHandler towerHandler;
    private andreaszeijlon.javaproject.Menu menu;


    public KeyHandler(final EnemySpawner spawner, final TowerHandler towerHandler, final Menu menu)
    {
	this.spawner = spawner;
        this.towerHandler = towerHandler;
        this.menu = menu;
    }


    @Override
    public void mousePressed(final MouseEvent e) {
        super.mousePressed(e);
        Point p = new Point(e.getX() - 8, e.getY() - MENU_HEIGHT);
        if (menu.getState() == State.MENU) {
            menu.menuEdit(p);
        }
        else  {
            spawner.checkNextWave(p);
            towerHandler.checkButtonClick(p);
            towerHandler.checkTowerTargeted(p);
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        super.mouseMoved(e);
        motionPoint = new Point(e.getX() - 8, e.getY() - MENU_HEIGHT);
    }


}
