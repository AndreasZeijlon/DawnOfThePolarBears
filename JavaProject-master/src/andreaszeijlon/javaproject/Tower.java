package andreaszeijlon.javaproject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

    public class Tower implements GameObject{

        private int upgradeRangeCost;
        private int upgradeASCost;
        private int upgradeDamageCost;
        private int upgrades;

        private int damage;
        private int bulletSpeed;
        private int cost;
        private int radius;
        private int reloadTime;
        private int reloadTick;
        private int sell;
        private TowerType type;

        private Enemy targetEnemy = null;

        private Image image = null;

        private Ellipse2D range = null;

        private double angle;

        private boolean targeted;

        private Rectangle rectangle = null;


        private int x;
        private int y;

        public void setRange() {
            this.range = new Ellipse2D.Float(x - (float)(radius / 2) + ((float)GameComponent.TILE_SIZE / 2), y - (float)(radius / 2) + ((float)GameComponent.TILE_SIZE / 2), radius, radius);

        }

        public void setType(final TowerType type) {
            this.type = type;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public void setReloadTime(int reloadTime) {
            this.reloadTime = reloadTime;
        }

        public Image getImage() {
            return image;
        }

        public int getCost() {
            return cost;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(final int x) {
            this.x = x;
        }

        public void setY(final int y) {
            this.y = y;
        }

        public int getRadius() {
            return radius;
        }

        public int getReloadTime() {
            return reloadTime;
        }

        public Shape getRange() {
            return range;
        }

        public Enemy getTargetEnemy() {
            return targetEnemy;
        }

        public void setTargetEnemy(Enemy targetEnemy) {
            this.targetEnemy = targetEnemy;
        }

        public int getReloadTick() {
            return reloadTick;
        }

        public void setReloadTick(int reloadTick) {
            this.reloadTick = reloadTick;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public double getAngle() {
            return angle;
        }

        public TowerType getType() {
            return type;
        }

        public boolean isTargeted() {
            return targeted;
        }

        public void setTargeted(boolean targeted) {
            this.targeted = targeted;
        }

        public Rectangle getRectangle() {
            return rectangle;
        }

        public void setRectangle(Rectangle rectangle) {
            this.rectangle = rectangle;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public void setSell(int sell) {
            this.sell = sell;
        }

        public int getSell() {
            return sell;
        }

        public int getUpgradeRangeCost(){
            return upgradeRangeCost;
        }

        public int getUpgradeASCost(){
            return upgradeASCost;
        }

        public int getUpgradeDamageCost(){
            return upgradeDamageCost;
        }

        public void setUpgradeRangeCost(int upgradeRangeCost) {
            this.upgradeRangeCost = upgradeRangeCost;
        }

        public void setUpgradeDamageCost(int upgradeDamageCost) {
            this.upgradeDamageCost = upgradeDamageCost;
        }
        public void setUpgradeASCost(int upgradeASCost) {
            this.upgradeASCost = upgradeASCost;
        }

        public int getDamage(){
            return damage;
        }

        public int getBulletSpeed(){
            return bulletSpeed;
        }

        public void setDamage(int damage) {
            this.damage = damage;
        }
        public void setBulletSpeed(int bulletSpeed) {
            this.bulletSpeed = bulletSpeed;
        }

        public void setUpgrades(int upgrades){
            this.upgrades = upgrades;
        }

        public int getUpgrades(){
            return upgrades;
    }}


