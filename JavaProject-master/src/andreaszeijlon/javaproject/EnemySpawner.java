package andreaszeijlon.javaproject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

/**
 * Created by Andreas Zeijlon on 2015-03-21.
 */
public class EnemySpawner {
    private static final int WAVE_BUTTON_SIZE = 150;
    private static final int WAVE_BUTTON_MARGIN = 20;
    private static final int SPAWNRATE = 75;


    private Grid grid;
    private Shop shop;
    private Sound bearDies = new Sound("sounds/beardeath.wav");



    private List<Enemy> enemies = new ArrayList<>();
    private List<Start> starts = new ArrayList<>();
    private List<Enemy> basic = new ArrayList<>();
    private List<Enemy> armored = new ArrayList<>();
    private List<Enemy> spy = new ArrayList<>();
    private List<Enemy> boss = new ArrayList<>();
    private int spawnTime = 0;
    private int level = 0;
    private int basicEnemyCount = 1;
    private int armoredEnemyCount = 1;
    private int spyEnemyCount = 1;
    private int bossEnemyCount = 1;


    private Image nextWaveImg = Toolkit.getDefaultToolkit().getImage("images/nextWaveImg.png")
	    .getScaledInstance(WAVE_BUTTON_SIZE, WAVE_BUTTON_SIZE, Image.SCALE_DEFAULT);
    private Image slowMode = Toolkit.getDefaultToolkit().getImage("images/slowMode.png")
	    .getScaledInstance(WAVE_BUTTON_SIZE, WAVE_BUTTON_SIZE, Image.SCALE_DEFAULT);
    private Image fastMode = Toolkit.getDefaultToolkit().getImage("images/fastMode.png")
	    .getScaledInstance(WAVE_BUTTON_SIZE, WAVE_BUTTON_SIZE, Image.SCALE_DEFAULT);


    private boolean betweenRounds;
    private boolean fastForward;

    private Rectangle nextRoundButton;


    public EnemySpawner(Grid grid, Shop shop) {
	this.grid = grid;
	this.shop = shop;
	this.betweenRounds = true;
	this.fastForward = false;
	this.nextRoundButton =
		new Rectangle(grid.getWidth() + WAVE_BUTTON_MARGIN, grid.getHeight() - WAVE_BUTTON_SIZE, WAVE_BUTTON_SIZE, WAVE_BUTTON_SIZE);
    }


    public void waveHandler() {
	if (level == 5) {
	    addEnemiesToSpawn(EnemyType.ARMORED, armoredEnemyCount, armored);
	    armoredEnemyCount += level;
	} else if (level == 10) {
	    addEnemiesToSpawn(EnemyType.SPY, spyEnemyCount, spy);
	    spyEnemyCount +=  level;
	} else if (level == 20) {
	    addEnemiesToSpawn(EnemyType.BOSS, bossEnemyCount, boss);
	    bossEnemyCount +=  1;
	}else if (level > 20) {
	    addEnemiesToSpawn(EnemyType.BASIC, basicEnemyCount, basic);
	    addEnemiesToSpawn(EnemyType.ARMORED, armoredEnemyCount, armored);
	    addEnemiesToSpawn(EnemyType.SPY, spyEnemyCount, spy);
	    addEnemiesToSpawn(EnemyType.BOSS, bossEnemyCount, boss);
	    basicEnemyCount += level*2;
	    armoredEnemyCount += level;
	    spyEnemyCount += level;
	    bossEnemyCount += 1;
		}
	else if (level > 10) {
	    addEnemiesToSpawn(EnemyType.BASIC, basicEnemyCount, basic);
	    addEnemiesToSpawn(EnemyType.ARMORED, armoredEnemyCount, armored);
	    addEnemiesToSpawn(EnemyType.SPY, spyEnemyCount, spy);
	    basicEnemyCount += level*2;
	    armoredEnemyCount += level;
	    spyEnemyCount +=  level;

	} else if (level > 5) {
	    addEnemiesToSpawn(EnemyType.ARMORED, armoredEnemyCount, armored);
	    addEnemiesToSpawn(EnemyType.BASIC, basicEnemyCount, basic);
	    basicEnemyCount += level*2;
	    armoredEnemyCount += level;
	} else {
	    addEnemiesToSpawn(EnemyType.BASIC, basicEnemyCount, basic);
	    basicEnemyCount += level*2;
	}}

    public void checkRoundFinished(){
	if(!betweenRounds){
	    if(enemies.isEmpty()){
		betweenRounds = true;
		fastForward = false;
		if (level <= 10) {
		    shop.setGold(shop.getGold() + 10 - (level-1));
		}
		else {
		    EnemyProperties.setBasicHealth(EnemyProperties.getBasicstarthealth()*2 + level * 5);
		    EnemyProperties.setArmoredHealth(EnemyProperties.getArmoredstarthealth()*2 + level * 5);
		    EnemyProperties.setSpyHealth(EnemyProperties.getSpystarthealth()*2 + level * 5);
		}
	    }
	}
    }

    public void checkNextWave(Point p) {
	if (shop.getHoldsItem() == null) {
		if (nextRoundButton.contains(p)) {
			nextWave();
		}
	    }
	}

public void nextWave(){
    if (betweenRounds) {
    			level++;
    			betweenRounds = false;
    			waveHandler();
    		    } else {
    			fastForward = !fastForward;
    		    }
}

    private void addEnemiesToSpawn(EnemyType type, int count, Collection<Enemy> list) {
	for (int i = 0; i < count ; i++) {
	    list.add(new EnemyProperties(type));}
    }

    public void spawnEnemies(){
	int spawnRate = SPAWNRATE;
	if(spawnTime>= spawnRate){
	    spawnEnemyType(basic);
	    spawnEnemyType(armored);
	    spawnEnemyType(spy);
	    spawnEnemyType(boss);
	    spawnTime = 0;
	}
	else{
	    spawnTime++;
	}
    }
    public void spawnEnemyType(List<Enemy> list){
	    if(!list.isEmpty()){
		Enemy enemy = list.get(0);
		enemies.add(enemy);
		for (int y = 0; y < grid.getSquares().length; y++) {
		    for (int x = 0; x < grid.getSquares()[y].length; x++) {
			if (grid.getSquares()[y][x] == Grid.START) {
			    Start start = new Start(x, y);
			    starts.add(start);
			}
		    }
		}
		Random random = new Random();
		int n = starts.size();
		int rnd = random.nextInt(n);
		enemy.setyC(starts.get(rnd).getY());
		enemy.setxC(starts.get(rnd).getX());
		enemy.setY(starts.get(rnd).getY() * GameComponent.TILE_SIZE);
		enemy.setX(starts.get(rnd).getX() * GameComponent.TILE_SIZE);


		defineHasWalked(enemy);
		decideDirection(enemy);
		enemy.setEnemyEllipse();
		list.remove(0);
	    }		spawnTime = 0;


    }


    private void defineHasWalked(Enemy enemy) {
	int height = grid.getSquares().length;
	int width = Grid.checkLargestRow(grid);
	enemy.setHasWalked(new int[height][width]);
    }

    private void decideDirection(Enemy enemy) {
	if (!collision(enemy, enemy.getyC(), enemy.getxC() + 1)) {
	    enemy.setDirection(Direction.RIGHT);
	} else if (!collision(enemy, enemy.getyC(), enemy.getxC() - 1)) {
	    enemy.setDirection(Direction.LEFT);
	}
	else if (!collision(enemy, enemy.getyC() + 1, enemy.getxC())) {
	    enemy.setDirection(Direction.DOWN);
	}
	 else if (!collision(enemy, enemy.getyC() - 1, enemy.getxC())) {
	    enemy.setDirection(Direction.UPWARD);
	}
    }

    public void moveEnemy() {
	for (Enemy enemy : enemies) {
	    if (enemy.getDirection() == Direction.RIGHT) {
		enemy.setAngle(Math.PI / 2);
		enemy.setX(enemy.getX() + enemy.getSpeed());
	    } else if (enemy.getDirection() == Direction.LEFT) {
		enemy.setAngle(3 * Math.PI / 2);
		enemy.setX(enemy.getX() - enemy.getSpeed());
	    } else if (enemy.getDirection() == Direction.DOWN) {
		enemy.setAngle(Math.PI);
		enemy.setY(enemy.getY() + enemy.getSpeed());
	    } else if (enemy.getDirection() == Direction.UPWARD) {
		enemy.setAngle(0);
		enemy.setY(enemy.getY() - enemy.getSpeed());
	    }
	    enemy.setEnemyEllipse();
	    enemy.setEnemyWalk(enemy.getEnemyWalk() + enemy.getSpeed());
	    enemy.setPixelsWalked(enemy.getPixelsWalked() + enemy.getSpeed());

	    if (enemy.getEnemyWalk() >= GameComponent.TILE_SIZE) {
		enemy.getHasWalked()[enemy.getyC()][enemy.getxC()] = grid.getSquares()[enemy.getyC()][enemy.getxC()];

		if (enemy.getDirection() == Direction.RIGHT) {
		    enemy.setxC(enemy.getxC() + 1);
		    if (collision(enemy, enemy.getyC(), enemy.getxC() + 1)) {
			enemy.setHasRight(true);
		    }
		} else if (enemy.getDirection() == Direction.LEFT) {
		    enemy.setxC(enemy.getxC() - 1);
		    if (collision(enemy, enemy.getyC(), enemy.getxC() - 1)) {
			enemy.setHasLeft(true);
		    }
		} else if (enemy.getDirection() == Direction.DOWN) {
		    enemy.setyC(enemy.getyC() + 1);
		    if (collision(enemy, enemy.getyC() + 1, enemy.getxC())) {
			enemy.setHasDown(true);
		    }
		} else if (enemy.getDirection() == Direction.UPWARD) {
		    enemy.setyC(enemy.getyC() - 1);
		    if (collision(enemy, enemy.getyC() - 1, enemy.getxC())) {
			enemy.setHasUp(true);

		    }
		}
		changeDirection(enemy);
		enemy.setHasDown(false);
		enemy.setHasLeft(false);
		enemy.setHasUp(false);
		enemy.setHasRight(false);
		enemy.setEnemyWalk(0);
	    }
	}
    }

    private boolean collision(Enemy enemy, int y, int x) {
	try {
	    if (enemy.getHasWalked()[y][x] != Grid.PATH) {
		if (grid.getSquares()[y][x] == Grid.PATH || grid.getSquares()[y][x] == Grid.CROSSROAD ||
		    grid.getSquares()[y][x] == Grid.FINISH) {
		    return false;
		}
	    }
	} catch (RuntimeException ignored) {
	    return true;
	}
	return true;
    }


    private void changeDirection(Enemy enemy) {
	if (enemy.isHasRight() || enemy.isHasLeft()) {
	    if (!collision(enemy, enemy.getyC() + 1, enemy.getxC())) {
		enemy.setDirection(Direction.DOWN);
	    } else if (!collision(enemy, enemy.getyC() - 1, enemy.getxC())) {
		enemy.setDirection(Direction.UPWARD);
	    } else {
		enemy.setDirection(Direction.STILL);
	    }
	} else if (enemy.isHasDown() || enemy.isHasUp()) {
	    if (!collision(enemy, enemy.getyC(), enemy.getxC() + 1)) {
		enemy.setDirection(Direction.RIGHT);
	    } else if (!collision(enemy, enemy.getyC(), enemy.getxC() - 1)) {
		enemy.setDirection(Direction.LEFT);
	    } else {
		enemy.setDirection(Direction.STILL);
	    }
	}
    }

    public void checkEnemyFinished() {
	for (int i = 0; i < enemies.size(); i++) {
            int x = enemies.get(i).getxC();
            int y = enemies.get(i).getyC();
            if (enemies.get(i).getHp() <= 0) {
                shop.setGold(shop.getGold() + enemies.get(i).getGoldgain());
                enemies.remove(enemies.get(i));
		checkRoundFinished();
		if(!Sound.getNoGameAudio()){
                    bearDies.play();
		    }
            } else if (grid.getSquares()[y][x] == Grid.FINISH) {
                shop.setHealth(shop.getHealth() - enemies.get(i).getDamage());
                enemies.remove(enemies.get(i));
		checkRoundFinished();
            }
        }
    }

    public Enemy checkEnemyWalked(Iterable<Enemy> list) {
	int walked = 0;
	Enemy enemy = null;
	for (Enemy aList : list) {
	    if (aList.getPixelsWalked() > walked) {
		walked = aList.getPixelsWalked();
		enemy = aList;
	    }
	}
	return enemy;
    }

    public void draw(Graphics2D g) {
	for (int i = enemies.size() - 1; i >= 0; i--) {
	    AffineTransform at = new AffineTransform();
	    AffineTransform old = g.getTransform();
	    at.rotate(enemies.get(i).getAngle(), enemies.get(i).getX() + (float) enemies.get(i).getImage().getWidth(null) / 2,
		      enemies.get(i).getY() + (float) enemies.get(i).getImage().getHeight(null) / 2);
	    g.transform(at);
	    g.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), enemies.get(i).getY(), null);
	    g.setTransform(old);

	    g.setColor(Color.red);
	    int healthSpace = 3;
	    int healthHeight = 6;
	    g.fillRect(enemies.get(i).getX(), enemies.get(i).getY() - (healthSpace + healthHeight), Enemy.HP_BAR_LENGTH,
		       healthHeight);

	    g.setColor(Color.green);
	    g.fillRect(enemies.get(i).getX(), enemies.get(i).getY() - (healthSpace + healthHeight),
		       (int) enemies.get(i).getHealthBarHp(), healthHeight);

	    g.setColor(Color.black);
	    g.drawRect(enemies.get(i).getX(), enemies.get(i).getY() - (healthSpace + healthHeight), Enemy.HP_BAR_LENGTH - 1,
		       healthHeight - 1);
	}
	g.setColor(Color.red);
	g.setFont(new Font("courier new", Font.BOLD, 32));
	if (level >= 1) {
	    g.drawString("ROUND " + level, grid.getWidth() + WAVE_BUTTON_MARGIN, grid.getHeight() + 30);
	}

	//Draw next round button
	if (betweenRounds) {
	    g.drawImage(nextWaveImg, grid.getWidth() + WAVE_BUTTON_MARGIN, grid.getHeight() - WAVE_BUTTON_SIZE, null);
	} else if (fastForward) {
	    g.drawImage(fastMode, grid.getWidth() + WAVE_BUTTON_MARGIN, grid.getHeight() - WAVE_BUTTON_SIZE, null);
	} else {
	    g.drawImage(slowMode, grid.getWidth() + WAVE_BUTTON_MARGIN, grid.getHeight() - WAVE_BUTTON_SIZE, null);
	}
    }

    public boolean isBetweenRounds() {
	return betweenRounds;
    }

    public List<Enemy> getEnemies() {
	return enemies;
    }

    public void setLevel() {
	this.level = 0;
    }

    public void setArmoredEnemyCount(int armoredEnemyCount) {
	this.armoredEnemyCount = armoredEnemyCount;
    }

    public void setSpyEnemyCount(final int spyEnemyCount) {
	this.spyEnemyCount = spyEnemyCount;
    }

    public void setBasicEnemyCount(int enemyCount) {
        this.basicEnemyCount = enemyCount;
    } public void setEnemies(List<Enemy> enemies) {
    	this.enemies = enemies;
        }

    public boolean isFastForward() {
	return fastForward;
    }

    public int getLevel() {
	return level;
    }


    public void setBossEnemyCount(final int bossEnemyCount) {
	this.bossEnemyCount = bossEnemyCount;
    }

    public void setBetweenRounds(final boolean betweenRounds) {
	this.betweenRounds = betweenRounds;
    }

    public void setBasic(final List<Enemy> basic) {
	this.basic = basic;
    }

    public void setArmored(final List<Enemy> armored) {
	this.armored = armored;
    }

    public void setSpy(final List<Enemy> spy) {
	this.spy = spy;
    }

    public void setBoss(final List<Enemy> boss) {
	this.boss = boss;
    }

    public void setStarts(final List<Start> starts) {
	this.starts = starts;
    }

    public void setFastForward(final boolean fastForward) {
	this.fastForward = fastForward;
    }

}
