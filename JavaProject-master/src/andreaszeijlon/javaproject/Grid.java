package andreaszeijlon.javaproject;

import java.awt.*;


public class Grid
{
    protected final static int GRASS = 0;
       protected final static int PATH = 1;
       protected final static int TOWER = 2;
       protected final static int START = 3;
       protected final static int FINISH = 4;
       protected final static int CROSSROAD = 5;



       private int width;
       private int height;

       private int[][] squares;
       private Rectangle[][] rectangles;

       private Rectangle gridSize;

       public Grid(int n) {
           this.squares = Maps.getMap(n);
           this.width = checkLargestRow(this) * GameComponent.TILE_SIZE;
           this.height = squares.length * GameComponent.TILE_SIZE;

           rectangles = new Rectangle[squares.length][checkLargestRow(this)];
           defineRectangles();
           gridSize = new Rectangle(0, 0, checkLargestRow(this) * GameComponent.TILE_SIZE, squares.length * GameComponent.TILE_SIZE);
       }

       public int[][] getSquares() {
           return squares;
       }


       public void defineRectangles() {
           for (int i = 0; i < squares.length; i++) {
               for (int j = 0; j < squares[i].length; j++) {
                   rectangles[i][j] = new Rectangle(j * GameComponent.TILE_SIZE, i * GameComponent.TILE_SIZE, GameComponent.TILE_SIZE, GameComponent.TILE_SIZE);
               }
           }
       }

       public void draw(Graphics g2d) {
           for (int i = 0; i < squares.length; i++) {
               for (int j = 0; j < squares[i].length; j++) {
                   Image img = checkSquareType(squares[i][j]);
                   g2d.drawImage(img, j * GameComponent.TILE_SIZE, i * GameComponent.TILE_SIZE, null);
               }
           }
       }

       public Image checkSquareType(int squaretype) {
           switch (squaretype) {
               case GRASS:
                   return Toolkit.getDefaultToolkit().getImage("images/ice.png");
               case PATH:
                   return Toolkit.getDefaultToolkit().getImage("images/icepath.png");
               case TOWER:
                   return Toolkit.getDefaultToolkit().getImage("images/towerBase.png");
               case START:
                   return Toolkit.getDefaultToolkit().getImage("images/start.png");
               case FINISH:
                   return Toolkit.getDefaultToolkit().getImage("images/finish60.png");
               case CROSSROAD:
                   return Toolkit.getDefaultToolkit().getImage("images/icepath.png");
               default:
                   throw new IllegalArgumentException("Invalid input squaretype");
           }

       }

       public static int checkLargestRow(Grid grid) {
           int largestRow = 0;
           for (int i = 0; i < grid.squares.length; i++) {
               if (grid.squares[i].length > largestRow) {
                   largestRow = grid.squares[i].length;
               }
           }
           return largestRow;
       }

       public Rectangle[][] getRectangles() {
           return rectangles;
       }

       public Shape getGridSize() {
           return gridSize;
       }

       public int getWidth() {
           return width;
       }

       public int getHeight() {
           return height;
       }

       public void setMapSize(int n)
       {
           squares = Maps.getMap(n);
       }
   /*
       public Image getGrassImg() {
           return grassImg;
       }

       public Image getPathImg() {
           return pathImg;
       }

       public Image getCrossRoadImg() {
           return crossRoadImg;
       }

       public Image getTowerImg() {
           return towerImg;
       }

       public Image getStartImg() {
           return startImg;
       }

       public Image getFinishImg() {
           return finishImg;
       }*/

       // --Commented out by Inspection START (2015-04-21 23:57):
   //    public void setSquares(int[][] squares) {
   //        this.squares = squares;
   //    }
   // --Commented out by Inspection STOP (2015-04-21 23:57)
}
