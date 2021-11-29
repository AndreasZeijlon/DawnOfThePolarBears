package andreaszeijlon.javaproject;

import java.awt.*;

/**
 * Created by Andreas Zeijlon on 2015-04-22.
 */
public class EnemyProperties extends Enemy{
    private static final int BASICSTARTHEALTH = 50;
    private static final int ARMOREDSTARTHEALTH = 200;
    private static final int SPYSTARTHEALTH = 60;


    private static int basicHealth = BASICSTARTHEALTH;
    private static final int BASIC_GOLD_GAIN = 1;
    private static final int BASIC_DAMAGE = 2;


    private static int armoredHealth = ARMOREDSTARTHEALTH;
    private static final int ARMORED_GOLD_GAIN = 5;
    private static final int ARMORED_DAMAGE = 5;

    private static int spyHealth = SPYSTARTHEALTH;
    private static final int SPY_GOLD_GAIN = 5;
    private static final int SPY_DAMAGE = 10;

    private static final int BOSS_HEALTH = 10000;
    private static final int BOSS_SPEED = 1;
    private static final int BOSS_GOLD_GAIN = 100;
    private static final int BOSS_DAMAGE = 100;

    public EnemyProperties(EnemyType type) {
        this.setType(type);
        decideEnemy(type);
    }

    public void decideEnemy(EnemyType type) {
        switch (type) {
            case BASIC:
                this.setOriginalHp(basicHealth);
                this.setHp(basicHealth);
		final int basicSpeed = 2;
		this.setSpeed(basicSpeed);
                this.setGoldgain(BASIC_GOLD_GAIN);
                this.setDamage(BASIC_DAMAGE);
                this.setImage(Toolkit.getDefaultToolkit().getImage("images/PolarBearNormal.gif"));
                break;
            case ARMORED:
                this.setOriginalHp(armoredHealth);
                this.setHp(armoredHealth);
		final int armoredSpeed = 1;
                this.setSpeed(armoredSpeed);
                this.setGoldgain(ARMORED_GOLD_GAIN);
                this.setDamage(ARMORED_DAMAGE);
                this.setImage(Toolkit.getDefaultToolkit().getImage("images/ArmoredPolarBearNormal60.gif"));
                break;
            case SPY:
                this.setOriginalHp(spyHealth);
                this.setHp(spyHealth);
		final int spySpeed = 3;
                this.setSpeed(spySpeed);
                this.setGoldgain(SPY_GOLD_GAIN);
                this.setDamage(SPY_DAMAGE);
                this.setImage(Toolkit.getDefaultToolkit().getImage("images/SpyPolarBearFast.gif"));
                break;
	    case BOSS:
            this.setOriginalHp(BOSS_HEALTH);
            this.setHp(BOSS_HEALTH);
            this.setSpeed(BOSS_SPEED);
            this.setGoldgain(BOSS_GOLD_GAIN);
            this.setDamage(BOSS_DAMAGE);
            this.setImage(Toolkit.getDefaultToolkit().getImage("images/bossEnemy.gif"));
		    break;
        }
    }

    public static void setBasicHealth(final int health) {
        basicHealth = health;
    }

    public static void setArmoredHealth(final int health) {
        armoredHealth = health;
    }

    public static void setSpyHealth(final int health) {
        spyHealth = health;
    }

    public static int getBasicstarthealth() {
	return BASICSTARTHEALTH;
    }

    public static int getArmoredstarthealth() {
	return ARMOREDSTARTHEALTH;
    }

    public static int getSpystarthealth() {
	return SPYSTARTHEALTH;
    }
}
