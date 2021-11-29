package andreaszeijlon.javaproject;

import java.awt.*;

/**
 * Created by Andreas Zeijlon on 2015-04-22.
 */
public class TowerProperties extends Tower{

    private static final Image BASIC_TOWER_IMAGE = Toolkit.getDefaultToolkit().getImage("images/basicTower60.png");
    private static final int BASIC_TOWER_DAMAGE = 10;
    private static final int BASIC_TOWER_BULLETSPEED = 6;
    private static final int BASIC_TOWER_COST = 10;
    private static final int BASIC_TOWER_SELL = 5;
    private static final int BASIC_TOWER_RADIUS = 200;
    private static final int BASIC_TOWER_RELOADTIME = 50;

    private static final Image AP_TOWER_IMAGE = Toolkit.getDefaultToolkit().getImage("images/armorPiercingTower60.png");
    private static final int AP_TOWER_DAMAGE = 10;
    private static final int AP_TOWER_BULLETSPEED = 10;
    private static final int AP_TOWER_COST = 20;
    private static final int AP_TOWER_SELL = 10;
    private static final int AP_TOWER_RADIUS = 300;
    private static final int AP_TOWER_RELOADTIME = 60;

    private static final Image SCOUT_TOWER_IMAGE = Toolkit.getDefaultToolkit().getImage("images/scoutTower.png");
    private static final int SCOUT_TOWER_DAMAGE = 10;
    private static final int SCOUT_TOWER_BULLETSPEED = 10;
    private static final int SCOUT_TOWER_COST = 40;
    private static final int SCOUT_TOWER_SELL = 20;
    private static final int SCOUT_TOWER_RADIUS = 500;
    private static final int SCOUT_TOWER_RELOADTIME = 40;

    public TowerProperties(TowerType type) {
        decideStats(type);
        setType(type);
        setTargeted(true);
        setUpgradeRangeCost(5);
        setUpgradeASCost(5);
        setUpgradeDamageCost(5);
        setUpgrades(0);
    }

    public void decideStats(TowerType type) {
        switch (type) {
            case BASIC:
                this.setImage(BASIC_TOWER_IMAGE);
                this.setDamage(BASIC_TOWER_DAMAGE);
                this.setBulletSpeed(BASIC_TOWER_BULLETSPEED);
                this.setCost(BASIC_TOWER_COST);
                this.setSell(BASIC_TOWER_SELL);
                this.setRadius(BASIC_TOWER_RADIUS);
                this.setReloadTime(BASIC_TOWER_RELOADTIME); //less is bettter
                this.setReloadTick(this.getReloadTime());
                break;
            case ARMORPIERCING:
                this.setImage(AP_TOWER_IMAGE);
                this.setDamage(AP_TOWER_DAMAGE);
                this.setBulletSpeed(AP_TOWER_BULLETSPEED);
                this.setCost(AP_TOWER_COST);
                this.setSell(AP_TOWER_SELL);
                this.setRadius(AP_TOWER_RADIUS);
                this.setReloadTime(AP_TOWER_RELOADTIME); //less is bettter
                this.setReloadTick(this.getReloadTime());
                break;
            case SCOUT:
                this.setImage(SCOUT_TOWER_IMAGE);
                this.setDamage(SCOUT_TOWER_DAMAGE);
                this.setBulletSpeed(SCOUT_TOWER_BULLETSPEED);
                this.setCost(SCOUT_TOWER_COST);
                this.setSell(SCOUT_TOWER_SELL);
                this.setRadius(SCOUT_TOWER_RADIUS);
                this.setReloadTime(SCOUT_TOWER_RELOADTIME); //less is bettter
                this.setReloadTick(this.getReloadTime());
                break;
        }
    }
}
