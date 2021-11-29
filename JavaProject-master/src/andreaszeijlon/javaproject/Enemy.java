package andreaszeijlon.javaproject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Andreas Zeijlon on 2015-03-21.
 */
public class Enemy implements GameObject {

    /**
     * Constant for the length of enemies health bar.
     */
    public static final int HP_BAR_LENGTH = 60;
    private int hp;
    private double originalHp;
    private double healthBarHp = GameComponent.TILE_SIZE;
    private int speed;
    private int goldgain;
    private int damage;
    private double angle;
    private int x;
    private int y;
    private EnemyType type = null;
    private final static int ELLIPSE_SIZE_CORRECTION = 20;

    private Direction direction = Direction.STILL;
    private int enemyWalk = 0;
    private int pixelsWalked = 0;
    private int yC = 0;
    private int xC = 0;

    private boolean hasUp = false;
    private boolean hasDown = false;
    private boolean hasLeft = false;
    private boolean hasRight = false;

    private Image image = null;

    private int[][] hasWalked = null;

    private Ellipse2D enemyEllipse = null;


    public Shape getEnemyEllipse() {
        return enemyEllipse;
    }

    public void setEnemyEllipse() {
        switch (this.direction) {
            case RIGHT:
            case LEFT:
                this.enemyEllipse = new Ellipse2D.Float(x, y + 10, GameComponent.TILE_SIZE, GameComponent.TILE_SIZE - ELLIPSE_SIZE_CORRECTION);
                break;
            case DOWN:
            case UPWARD:
                this.enemyEllipse = new Ellipse2D.Float(x + 10, y, GameComponent.TILE_SIZE- ELLIPSE_SIZE_CORRECTION, GameComponent.TILE_SIZE);
                break;
            case STILL:
                break;
        }
    }

    public EnemyType getType() {
        return type;
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setGoldgain(int goldgain) {
        this.goldgain = goldgain;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setOriginalHp(double originalHp) {
        this.originalHp = originalHp;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getGoldgain() {
        return goldgain;
    }

    public int getDamage() {
        return damage;
    }

    public void setHp(final int hp) {
        this.hp += hp;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public int getEnemyWalk() {
        return enemyWalk;
    }

    public void setEnemyWalk(final int enemyWalk) {
        this.enemyWalk = enemyWalk;
    }

    public int getyC() {
        return yC;
    }

    public void setyC(final int yC) {
        this.yC = yC;
    }

    public int getxC() {
        return xC;
    }

    public void setxC(final int xC) {
        this.xC = xC;
    }

    public boolean isHasUp() {
        return hasUp;
    }

    public void setHasUp(final boolean hasUp) {
        this.hasUp = hasUp;
    }

    public boolean isHasDown() {
        return hasDown;
    }

    public void setHasDown(final boolean hasDown) {
        this.hasDown = hasDown;
    }

    public boolean isHasLeft() {
        return hasLeft;
    }

    public void setHasLeft(final boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    public boolean isHasRight() {
        return hasRight;
    }

    public void setHasRight(final boolean hasRight) {
        this.hasRight = hasRight;
    }

    public int[][] getHasWalked() {
        return hasWalked;
    }

    public void setHasWalked(final int[][] hasWalked) {
        this.hasWalked = hasWalked;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    public void setHealthBarHp(int damage) {
        double x = damage/originalHp;
        this.healthBarHp = healthBarHp - (HP_BAR_LENGTH *x);
    }

    public double getHealthBarHp() {
        return healthBarHp;
    }

    public int getPixelsWalked() {
        return pixelsWalked;
    }

    public void setPixelsWalked(int pixelsWalked) {
        this.pixelsWalked = pixelsWalked;
    }
}


