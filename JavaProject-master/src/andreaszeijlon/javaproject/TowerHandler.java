package andreaszeijlon.javaproject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class TowerHandler
{

    private List<Tower> towers = new ArrayList<>();
    private final static int UPGRADEWIDTHCOORD = 200;
    private final static int STATUPGRADEWIDTHCOORD = 100;
    private final static int UPGRADEHEIGHTCORRECTIONCOORD = 30;
    private final static int MAX_UPGRADES = 15;
    private final static int UPGRADEPICSIZE = 50;
    private Image attackSpeed = Toolkit.getDefaultToolkit().getImage("images/speed.png");
    private Image stats = Toolkit.getDefaultToolkit().getImage("images/stats.png");
    private Image statsASBoost = Toolkit.getDefaultToolkit().getImage("images/statsASBoost.png");
    private Image statsARBoost = Toolkit.getDefaultToolkit().getImage("images/statsARBoost.png");
    private Image statsDBoost = Toolkit.getDefaultToolkit().getImage("images/statsDBoost.png");
    private Image damage = Toolkit.getDefaultToolkit().getImage("images/damage.png");
    private Image attackRange = Toolkit.getDefaultToolkit().getImage("images/range.png");
    private Image sell = Toolkit.getDefaultToolkit().getImage("images/sell.png");



    private Grid grid;
    private Shop shop;
    private EnemySpawner spawner;
    private BulletHandler bulletHandler;

    private Tower buildTower = null;
    private Rectangle upgradeTowerRange = null;
    private Rectangle upgradeTowerDamage = null;
    private Rectangle upgradeTowerAS = null;


    private Rectangle sellTower = null;

    private Collection<Enemy> enemiesWithinRange = new ArrayList<>();

    public TowerHandler(Grid grid, Shop shop, EnemySpawner spawner, BulletHandler bulletHandler) {
        this.grid = grid;
        this.shop = shop;
        this.spawner = spawner;
        this.bulletHandler = bulletHandler;
    }
    public void buyBasic(){
        Tower tower = new TowerProperties(TowerType.BASIC);
                        if (shop.getGold() >= tower.getCost()) {
                            shop.setHoldsItem(tower);
                            buildTower = tower;
                        } else {
                            shop.setHoldsItem(null);
                        }
    }
    public void buyArmorpiercing(){
            Tower tower = new TowerProperties(TowerType.ARMORPIERCING);
                            if (shop.getGold() >= tower.getCost()) {
                                shop.setHoldsItem(tower);
                                buildTower = tower;
                            } else {
                                shop.setHoldsItem(null);
                            }
        }
    public void buyScout(){
            Tower tower = new TowerProperties(TowerType.SCOUT);
                            if (shop.getGold() >= tower.getCost()) {
                                shop.setHoldsItem(tower);
                                buildTower = tower;
                            } else {
                                shop.setHoldsItem(null);
                            }
        }
    public void checkButtonClick(Point p) {
            if (shop.getShopButtons()[0][0].contains(p)) {
               buyBasic();
            } else if (shop.getShopButtons()[0][1].contains(p)) {
		buyArmorpiercing();
            } else if (shop.getShopButtons()[1][0].contains(p)) {
                buyScout();
            } else if (shop.getShopButtons()[1][1].contains(p)) {
                shop.setHoldsItem(null);
            }

            if (buildTower != null) {
                buildTower(buildTower, p);
            }
        }



    public void buildTower(Tower tower, Point p) {
            for (int i = 0; i < grid.getRectangles().length; i++) {
                for (int j = 0; j < grid.getRectangles()[i].length; j++) {
                    try {
                        if (grid.getRectangles()[i][j].contains(p))
                            if (grid.getSquares()[i][j] == Grid.GRASS) {
                                towers.add(tower);
                                grid.getSquares()[i][j] = Grid.TOWER;
                                tower.setX(j * GameComponent.TILE_SIZE);
                                tower.setY(i * GameComponent.TILE_SIZE);
                                tower.setRange();
                                tower.setRectangle(new Rectangle(tower.getX(), tower.getY(), tower.getImage().getWidth(null), tower.getImage().getHeight(null)));
                                shop.setGold(shop.getGold() - tower.getCost());
                                shop.setHoldsItem(null);
                                buildTower = null;
                            }
                    } catch (RuntimeException ignored) {

                    }
                }
            }

        }


    public void checkTowerTargeted(Point p) {
        for (int i = 0; i < towers.size(); i++) {
                if (towers.get(i).getRectangle().contains(p)) {
                    towers.get(i).setTargeted(true);
                    upgradeTowerAS = new Rectangle(STATUPGRADEWIDTHCOORD*2, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, UPGRADEPICSIZE, UPGRADEPICSIZE);
                    upgradeTowerRange = new Rectangle(STATUPGRADEWIDTHCOORD*3, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, UPGRADEPICSIZE, UPGRADEPICSIZE);
                    upgradeTowerDamage = new Rectangle(STATUPGRADEWIDTHCOORD*4, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, UPGRADEPICSIZE, UPGRADEPICSIZE);
                    sellTower = new Rectangle(STATUPGRADEWIDTHCOORD*5, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, UPGRADEPICSIZE, UPGRADEPICSIZE);
                } else if (towers.get(i).isTargeted()) {
                    if (upgradeTowerAS.contains(p)) {
                            upgradeAS(towers.get(i));

                    } else if (upgradeTowerRange.contains(p)) {
                            upgradeRange(towers.get(i));

                    } else if (upgradeTowerDamage.contains(p)) {
                            upgradeDamage(towers.get(i));


                    } else if (sellTower.contains(p)) {
                            sellTower(towers.get(i));


                    } else {
                        towers.get(i).setTargeted(false);
                    }
                }
            }
        }




    public void towerPhysic() {
	for (int i = 0; i < towers.size(); i++) {

	    if (spawner.isBetweenRounds()) {
		towers.get(i).setReloadTick(towers.get(i).getReloadTime());
	    }
	    checkEnemyWithinReach(towers.get(i));
	    if (!enemiesWithinRange.isEmpty()) {
		towers.get(i).setTargetEnemy(spawner.checkEnemyWalked(enemiesWithinRange));
		enemiesWithinRange = new ArrayList<>();
		towers.get(i).setAngle(
			Math.atan2(towers.get(i).getTargetEnemy().getY() - towers.get(i).getY(), towers.get(i).getTargetEnemy().getX() - towers.get(i).getX()));

		if (towers.get(i).getReloadTick() >= towers.get(i).getReloadTime()) {
		    bulletHandler.shootEnemy(towers.get(i).getTargetEnemy(), towers.get(i));
		    towers.get(i).setReloadTick(0);
		} else {
		    towers.get(i).setReloadTick(towers.get(i).getReloadTick() + 1);
		}
	    }
	}
    }

    public void checkEnemyWithinReach(Tower tower) {
	for (int i = 0; i < spawner.getEnemies().size(); i++) {
	    if (testIntersection(tower.getRange(), spawner.getEnemies().get(i).getEnemyEllipse())) {
		if (spawner.getEnemies().get(i).getType() == EnemyType.SPY) {
		    if (tower.getType() == TowerType.SCOUT) {
			enemiesWithinRange.add(spawner.getEnemies().get(i));
		    }
		} else {
		    enemiesWithinRange.add(spawner.getEnemies().get(i));
		}
	    }

	}
    }

    public boolean testIntersection(Shape shapeA, Shape shapeB) {
	Area areaA = new Area(shapeA);
	areaA.intersect(new Area(shapeB));
	return !areaA.isEmpty();
    }

    public void upgradeDamage(Tower tower) {
	if (shop.getGold() >= tower.getUpgradeDamageCost()) {
	    if (tower.getUpgrades() < MAX_UPGRADES) {
		shop.setGold(shop.getGold() - tower.getUpgradeDamageCost());
		tower.setUpgradeDamageCost(tower.getUpgradeDamageCost() * 2);
		tower.setDamage(tower.getDamage() + 5);
		tower.setUpgrades(tower.getUpgrades() + 1);
	    }
	}
    }

    public void upgradeAS(Tower tower) {
	if (shop.getGold() >= tower.getUpgradeASCost()) {
	    if (tower.getUpgrades() < MAX_UPGRADES) {
		shop.setGold(shop.getGold() - tower.getUpgradeASCost());
		tower.setUpgradeASCost(tower.getUpgradeASCost() * 2);
		tower.setReloadTime(tower.getReloadTime() - 5);
		tower.setBulletSpeed(tower.getBulletSpeed() + 1);
		tower.setUpgrades(tower.getUpgrades() + 1);

	    }
	}
    }

    public void upgradeRange(Tower tower) {
	if (shop.getGold() >= tower.getUpgradeRangeCost()) {
	    if (tower.getUpgrades() < MAX_UPGRADES) {
		shop.setGold(shop.getGold() - tower.getUpgradeRangeCost());
		tower.setUpgradeRangeCost(tower.getUpgradeRangeCost() * 2);
		tower.setRadius((int) (tower.getRadius() * 1.2));
		tower.setRange();
		tower.setUpgrades(tower.getUpgrades() + 1);
	    }
	}
    }

    public void sellTower(Tower tower) {
	shop.setGold(shop.getGold() + tower.getSell());
	towers.remove(tower);
	grid.getSquares()[tower.getY() / GameComponent.TILE_SIZE][tower.getX() / GameComponent.TILE_SIZE] = Grid.GRASS;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        for (Tower tower : towers) {
            AffineTransform at = new AffineTransform();
            AffineTransform old = g.getTransform();
            at.rotate(tower.getAngle(), tower.getX() + (float) tower.getImage().getWidth(null) / 2,
                    tower.getY() + (float) tower.getImage().getHeight(null) / 2);
            g.transform(at);
            g.drawImage(tower.getImage(), tower.getX(), tower.getY(), null);
            g.setTransform(old);
            if (tower.isTargeted()) {
                g.setColor(Color.black);
                g.setFont(new Font("courier new", Font.BOLD, 18));
                g.drawImage(stats, 0, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, null);
                if(upgradeTowerAS.contains(KeyHandler.motionPoint)){
                    g.drawImage(statsASBoost, 0, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD,null);
                }
		else if(upgradeTowerRange.contains(KeyHandler.motionPoint)){
                    g.drawImage(statsARBoost, 0, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD,null);
                }
		else if(upgradeTowerDamage.contains(KeyHandler.motionPoint)){
                    g.drawImage(statsDBoost, 0, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD,null);
                }

                g.drawImage(attackSpeed,STATUPGRADEWIDTHCOORD*2, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD,null);
                g.drawImage(attackRange, STATUPGRADEWIDTHCOORD * 3 , grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, null);
                g.drawImage(damage, STATUPGRADEWIDTHCOORD * 4, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, null);
                g.drawImage(sell,STATUPGRADEWIDTHCOORD * 5, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD, null);
                g.drawOval(tower.getX() - (tower.getRadius() / 2) + (GameComponent.TILE_SIZE / 2),
                           tower.getY() - (tower.getRadius() / 2) + (GameComponent.TILE_SIZE / 2), tower.getRadius(),
                           tower.getRadius());

                g.drawString("Upgrades: "+tower.getUpgrades()+"/"+MAX_UPGRADES, UPGRADEWIDTHCOORD * 3, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD + 20);

                g.setFont(new Font("courier new", Font.BOLD, 16));
                g.setColor(Color.black);

                g.drawString("$" + tower.getUpgradeASCost(),  UPGRADEWIDTHCOORD, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD + 10);

                g.drawString("$" + tower.getUpgradeRangeCost(), STATUPGRADEWIDTHCOORD*3, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD + 10);

                g.drawString("$" + tower.getUpgradeDamageCost(), STATUPGRADEWIDTHCOORD *4, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD + 10);

                g.drawString("$" + tower.getSell(), STATUPGRADEWIDTHCOORD * 5, grid.getHeight() + UPGRADEHEIGHTCORRECTIONCOORD + 10);


            }
        }
        Double dX = KeyHandler.motionPoint.getX();
        Double dY = KeyHandler.motionPoint.getY();
        int x = dX.intValue();
        int y = dY.intValue();
        if (shop.getHoldsItem() != null) {
            g.drawImage(shop.getHoldsItem().getImage(), x - GameComponent.TILE_SIZE / 2, y - GameComponent.TILE_SIZE / 2, null);
            g.drawOval(x - (shop.getHoldsItem().getRadius() / 2), y - (shop.getHoldsItem().getRadius() / 2),
                    shop.getHoldsItem().getRadius(), shop.getHoldsItem().getRadius());
        }


    }

    public void setTowers(List<Tower> towers) {
	this.towers = towers;
    }
}

