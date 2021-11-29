package andreaszeijlon.javaproject;

import java.awt.*;

/**
 * Created by Andreas Zeijlon on 2015-04-22.
 */
public class BulletProperties extends Bullet{

    public BulletProperties(Tower tower, BulletType type) {
        decideBullet(type, tower);
        setX(tower.getX()+(float)GameComponent.TILE_SIZE/2);
        setY(tower.getY()+(float)GameComponent.TILE_SIZE/2);
    }

    public void decideBullet(BulletType type, Tower tower) {
        switch (type) {
            case NORMAL:
                this.setBulletSpeed(tower.getBulletSpeed());
                this.setDamage(tower.getDamage());
                this.setImage(Toolkit.getDefaultToolkit().getImage("images/normalBullet.png"));
                break;
            case ARMORPIERING:
                this.setBulletSpeed(tower.getBulletSpeed());
                this.setDamage(tower.getDamage());
                this.setImage(Toolkit.getDefaultToolkit().getImage("images/apBullet.png"));
                break;
	    case SCOUT:
            this.setBulletSpeed(tower.getBulletSpeed());
            this.setDamage(tower.getDamage());
            this.setImage(Toolkit.getDefaultToolkit().getImage("images/scoutBullet.png"));
                break;
        }
    }
}
