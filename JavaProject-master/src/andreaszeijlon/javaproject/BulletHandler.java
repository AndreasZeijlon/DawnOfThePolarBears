package andreaszeijlon.javaproject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Zeijlon on 2015-04-01.
 */
public class BulletHandler {

    private static final int RECT_SIZE = 20;
    private Grid grid;
    private EnemySpawner spawner;
    private List<Bullet> bullets = new ArrayList<>();



    public BulletHandler(Grid grid, EnemySpawner spawner) {
        this.grid = grid;
        this.spawner = spawner;

    }

    private void defineBullets(Bullet bullet) {
        bullet.setBulletRect(new Rectangle(bullet.getX(), bullet.getY(), RECT_SIZE, RECT_SIZE));
    }

    public void shootEnemy(GameObject enemy, Tower tower) {
        Bullet bullet = decideBullet(tower);
        assert bullet != null;
        bullet.setAngle(Math.atan2((enemy.getY() + (float)GameComponent.TILE_SIZE/2) - (bullet.getY()+(float)RECT_SIZE/2),
                                   (enemy.getX() + (float)GameComponent.TILE_SIZE/2) - (bullet.getX()+(float)RECT_SIZE/2)));
        bullets.add(bullet);
    }

    private Bullet decideBullet(Tower tower) {
        TowerType type = tower.getType();
        switch (type) {
            case BASIC:
                return new BulletProperties(tower,BulletType.NORMAL);
            case ARMORPIERCING:
                return new BulletProperties(tower, BulletType.ARMORPIERING);
            case SCOUT:
                return new BulletProperties(tower, BulletType.SCOUT);
            default:
                return null;
        }
    }

    public void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            defineBullets(bullets.get(i));
            bullets.get(i).setX(bullets.get(i).getX() + bullets.get(i).getBulletSpeed() * Math.cos(bullets.get(i).getAngle()));
            bullets.get(i).setY(bullets.get(i).getY() + bullets.get(i).getBulletSpeed() * Math.sin(bullets.get(i).getAngle()));
            if (!grid.getGridSize().contains(bullets.get(i).getX(), bullets.get(i).getY())) {
                bullets.remove(i);
            }
            for (int j = 0; j < spawner.getEnemies().size(); j++) {
                try {
                    if (spawner.getEnemies().get(j).getEnemyEllipse().intersects(bullets.get(i).getBulletRect())) {
                        spawner.getEnemies().get(j).setHp(-bullets.get(i).getDamage());
                        spawner.getEnemies().get(j).setHealthBarHp(bullets.get(i).getDamage());
                        bullets.remove(i);
                    }
                } catch (RuntimeException ignored) {
                }
            }
        }
    }


    public void draw(Graphics2D g) {
        for (Bullet bullet : bullets) {
            AffineTransform at = new AffineTransform();
            AffineTransform old = g.getTransform();
            at.rotate(bullet.getAngle() + Math.PI / 2, bullet.getX() + 10,
                    bullet.getY() + 10);
            g.transform(at);
            g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), null);
            g.setTransform(old);

        }
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }
}
