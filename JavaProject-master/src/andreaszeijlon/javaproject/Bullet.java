package andreaszeijlon.javaproject;

import java.awt.*;

/**
 * This class define what a bullet gameobjectiv consist of and creates all setters and getters for
 */
public class Bullet implements GameObject {
    private double angle;
    private double x;
    private double y;
    private double bulletSpeed;
    private int damage;


    private Image image = null;

    private Rectangle bulletRect = null;

    public void setImage(Image image) {
        this.image = image;
    }

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getAngle() {
        return angle;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }


    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public Rectangle getBulletRect() {
        return bulletRect;
    }

    public void setBulletRect(Rectangle bulletRect) {
        this.bulletRect = bulletRect;
    }

    public Image getImage() {
        return image;
    }

    public int getDamage() {
        return damage;
    }
}
