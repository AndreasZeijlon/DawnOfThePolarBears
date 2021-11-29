package andreaszeijlon.javaproject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Menu {
    private Grid grid;
    private Shop shop;
    private EnemySpawner spawner;
    private TowerHandler towerHandler;
    private BulletHandler bulletHandler;
    private JFrame frame;
    private Sound mainTheme = null;
    private boolean gameRunning;
    private boolean levelSelect;
    private boolean options;
    private boolean ifGamePaused;
    private boolean ifLost;
    private boolean leaderboard;
    private int mapSelected;

    private State state;

    private static final int MENU_TAB_WIDTH = 150;
    private static final int MENU_TAB_HEIGHT = 25;

    private static final int STARTGOLD = 15;
    private static final int HIGHSCORESIZE = 16;
    private static final int MAP_TAB_WIDTH = 70;
    private static final int MAP_TAB_HEIGHT = 30;

    private static final int FIRST_COLUMN_X = 360;
    private static final int FIRST_COLUMN_Y = 165;

    private static final int SECOND_COLUMN_X = 550;
    private static final int SECOND_COLUMN_Y = 195;

    private static final int MENU_GRID_X = 650;
    private static final int MENU_GRID_Y = 200;
    private static final int DIFFICULTY_Y = 350;
    private static final int MENU_GRID_TILE_SIZE = 12;





    public Menu() {
        state = State.MENU;
        gameRunning = true;
        levelSelect = false;
        ifGamePaused = false;
        mapSelected = 1;
        options = false;
        ifLost = false;
        leaderboard = false;
	mainTheme = new Sound("sounds/song.aiff");
        this.grid = new Grid(mapSelected);
        this.shop = new Shop(grid);
        this.spawner = new EnemySpawner(grid, shop);
        this.bulletHandler = new BulletHandler(grid, spawner);
        this.towerHandler = new TowerHandler(grid, shop, spawner, bulletHandler);
	final KeyHandler keyHandler = new KeyHandler(spawner, towerHandler, this);
        this.frame = new GameFrame(grid, shop, spawner, towerHandler, bulletHandler, this, keyHandler);
        GameLoop gameloop = new GameLoop(shop, frame, spawner, towerHandler, bulletHandler, this);
    }

    private Image menuImage = (Toolkit.getDefaultToolkit().getImage("images/DawnofthePolarBears.png"));
    private Image resumeGame = (Toolkit.getDefaultToolkit().getImage("images/ResumeGame.png"));
    private Image newGame = (Toolkit.getDefaultToolkit().getImage("images/NewGameT.png"));
    private Image leaderboardimg = (Toolkit.getDefaultToolkit().getImage("images/leaderboard.png"));
    private Image quit = (Toolkit.getDefaultToolkit().getImage("images/QuitGame.png"));
    private Image levelSelectImage = (Toolkit.getDefaultToolkit().getImage("images/LevelSelect.png"));
    private Image map1 = (Toolkit.getDefaultToolkit().getImage("images/Map1.png"));
    private Image map2 = (Toolkit.getDefaultToolkit().getImage("images/Map2.png"));
    private Image map3 = (Toolkit.getDefaultToolkit().getImage("images/Map3.png"));
    private Image map4 = (Toolkit.getDefaultToolkit().getImage("images/Map4.png"));
    private Image optionsImage = (Toolkit.getDefaultToolkit().getImage("images/Options.png"));
    private Image audio = (Toolkit.getDefaultToolkit().getImage("images/gameaudiooff.png"));
    private Image music = (Toolkit.getDefaultToolkit().getImage("images/gamemusicoff.png"));


    private Rectangle audioRect = new Rectangle(SECOND_COLUMN_X, SECOND_COLUMN_Y, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);
    private Rectangle musicRect = new Rectangle(SECOND_COLUMN_X, SECOND_COLUMN_Y + MENU_TAB_HEIGHT, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);


    private Rectangle resumeGameButton = new Rectangle();
    private Rectangle newGameButton = new Rectangle(FIRST_COLUMN_X, FIRST_COLUMN_Y+MAP_TAB_HEIGHT, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);
    private Rectangle selectLevel = new Rectangle(FIRST_COLUMN_X, FIRST_COLUMN_Y + MENU_TAB_HEIGHT*2, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);
    private Rectangle optionsButton = new Rectangle(FIRST_COLUMN_X, FIRST_COLUMN_Y + MENU_TAB_HEIGHT*3, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);
    private Rectangle leaderBoard = new Rectangle(FIRST_COLUMN_X, FIRST_COLUMN_Y + MENU_TAB_HEIGHT*4, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);
    private Rectangle quitGameButton = new Rectangle(FIRST_COLUMN_X, FIRST_COLUMN_Y + MENU_TAB_HEIGHT*5, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);

    private Rectangle rectMap1 = new Rectangle(SECOND_COLUMN_X-5, SECOND_COLUMN_Y, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
    private Rectangle rectMap2 = new Rectangle(SECOND_COLUMN_X-5, SECOND_COLUMN_Y+MAP_TAB_HEIGHT, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
    private Rectangle rectMap3 = new Rectangle(SECOND_COLUMN_X-5, SECOND_COLUMN_Y + MAP_TAB_HEIGHT*2, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
    private Rectangle rectMap4 = new Rectangle(SECOND_COLUMN_X-5, SECOND_COLUMN_Y+MAP_TAB_HEIGHT*3, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);


    public void menuEdit(Point p) {
            if (newGameButton.contains(p)) {
                newGame();
            } else if (resumeGameButton.contains(p)) {
                resumeGame();
            }else if (leaderBoard.contains(p)) {
                            leaderboard = true;
                levelSelect = false;
                options = false;
                        }
            else if (quitGameButton.contains(p)) {
                quitGame();
            } else if (selectLevel.contains(p)) {
                levelSelect = true;
                options = false;
                leaderboard = false;
            } else if (optionsButton.contains(p)) {
                options = true;
                levelSelect = false;
                leaderboard = false;
            } else if (levelSelect) {
                chooseMap(p);
            } else if (options) {
                options(p);
            }else{
                levelSelect = false;
                options = false;
                leaderboard = false;
            }
        }



    public void newGame() {
        if (!Sound.isNoMusic()) {
            if (!Sound.getClipPlaying()) {
                mainTheme.play();
                mainTheme.loop();
                Sound.setClipPlaying(true);
            }
        }
        spawner.setBetweenRounds(true);
        grid.setMapSize(mapSelected);
        shop.setHealth(33);
        shop.setGold(STARTGOLD+30);
        spawner.setEnemies(new ArrayList<>());
        spawner.setLevel();
        spawner.setArmoredEnemyCount(1);
        spawner.setSpyEnemyCount(1);
        towerHandler.setTowers(new ArrayList<>());
        bulletHandler.setBullets(new ArrayList<>());
	spawner.setBasicEnemyCount(1);
	spawner.setBossEnemyCount(1);
        levelSelect = false;
        options = false;
        spawner.setStarts(new ArrayList<>());
        state = State.INGAME;
                ifLost = false;
    }

    public void resumeGame() {
        state = State.INGAME;
        ifGamePaused = false;
        levelSelect = false;
        options = false;
        
    }

    public void quitGame() {
        levelSelect = false;
        options = false;
        int answer = JOptionPane
                .showConfirmDialog(null, "Are you sure you want to quit? ", "Confirm", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void chooseMap(Point p) {
        if (rectMap1.contains(p)) {
            mapSelected = 1;
        } else if (rectMap2.contains(p)) {
            mapSelected = 2;
        } else if (rectMap3.contains(p)) {
            mapSelected = 3;
        } else if (rectMap4.contains(p)) {
            mapSelected = 4;
        } else {
            levelSelect = false;
        }
    }

    public void options(Point p) {
        if (audioRect.contains(p)) {
            if (Sound.isNoGameAudio()) {
		Sound.setNoGameAudio(false);
            } else if (!Sound.isNoGameAudio()) {
                Sound.setNoGameAudio(true);
            }
        } else if (musicRect.contains(p)) {
            if (Sound.isNoMusic()) {
                if(!Sound.getClipPlaying()){
                    mainTheme.play();
                    mainTheme.loop();
                    Sound.setClipPlaying(true);
                }
                Sound.setNoMusic(false);
            } else if (!Sound.isNoMusic()) {
                mainTheme.stop();
                Sound.setClipPlaying(false);
                Sound.setNoMusic(true);
            }
        } else {
            options = false;
        }

    }

    public void escape() {
        state = State.MENU;
        ifGamePaused = true;
        frame.pack();
        resumeGameButton = new Rectangle(FIRST_COLUMN_X, FIRST_COLUMN_Y, MENU_TAB_WIDTH, MENU_TAB_HEIGHT);
    }

    public void draw(Graphics g2d) {
        g2d.drawImage(menuImage, 0, 0, null);
        g2d.drawImage(newGame, FIRST_COLUMN_X, FIRST_COLUMN_Y+MENU_TAB_HEIGHT, null);
        g2d.drawImage(levelSelectImage, FIRST_COLUMN_X, FIRST_COLUMN_Y+MENU_TAB_HEIGHT*2, null);
        g2d.drawImage(optionsImage, FIRST_COLUMN_X, FIRST_COLUMN_Y+MENU_TAB_HEIGHT*3, null);
        g2d.drawImage(leaderboardimg, FIRST_COLUMN_X, FIRST_COLUMN_Y+MENU_TAB_HEIGHT*4, null);
        g2d.drawImage(quit, FIRST_COLUMN_X, FIRST_COLUMN_Y+MENU_TAB_HEIGHT*5, null);


        if(leaderboard){
            g2d.setFont(new Font("courier new", Font.BOLD, 18));
            g2d.drawString("# : Name : Round", SECOND_COLUMN_X, SECOND_COLUMN_Y);
            g2d.setFont(new Font("courier new", Font.BOLD, HIGHSCORESIZE));
            for (int i = 0; i < HighScoreList.getINSTANCE().getHighScoreList().size(); i++) {
                g2d.drawString(i+1 + " : "+HighScoreList.showHighScore(i).getName() + " : " + HighScoreList.showHighScore(i).getHighScore(),
                               SECOND_COLUMN_X, SECOND_COLUMN_Y + HIGHSCORESIZE + (i * HIGHSCORESIZE));
        }}

        else if (levelSelect) {
            g2d.drawImage(map1, SECOND_COLUMN_X, SECOND_COLUMN_Y, null);
            g2d.drawImage(map2, SECOND_COLUMN_X, SECOND_COLUMN_Y+MAP_TAB_HEIGHT, null);
            g2d.drawImage(map3, SECOND_COLUMN_X, SECOND_COLUMN_Y+MAP_TAB_HEIGHT*2, null);
            g2d.drawImage(map4, SECOND_COLUMN_X, SECOND_COLUMN_Y+MAP_TAB_HEIGHT*3, null);
            drawGrid(g2d, Maps.getMap(mapSelected));
            g2d.setFont(new Font("courier new", Font.BOLD, 24));
            g2d.setColor(Color.black);
            switch(mapSelected){
                case 1:
                g2d.drawString("VERY EASY", MENU_GRID_X, DIFFICULTY_Y);
                g2d.drawRect(SECOND_COLUMN_X -5, SECOND_COLUMN_Y, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
                    break;
                case 2:
                g2d.drawString("EASY", MENU_GRID_X, DIFFICULTY_Y);
                g2d.drawRect(SECOND_COLUMN_X -5, SECOND_COLUMN_Y+MAP_TAB_HEIGHT, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
                    break;
                case 3:
                g2d.drawString("MEDIUM", MENU_GRID_X, DIFFICULTY_Y);
                g2d.drawRect(SECOND_COLUMN_X -5, SECOND_COLUMN_Y+MAP_TAB_HEIGHT*2, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
                    break;
                case 4:
                g2d.drawString("VERY HARD", MENU_GRID_X, DIFFICULTY_Y);
                g2d.drawRect(SECOND_COLUMN_X -5, SECOND_COLUMN_Y+MAP_TAB_HEIGHT*3, MAP_TAB_WIDTH, MAP_TAB_HEIGHT);
                    break;
            }
        }
        else if (options) {
            g2d.drawImage(audio, SECOND_COLUMN_X, SECOND_COLUMN_Y, null);
            g2d.drawImage(music, SECOND_COLUMN_X, SECOND_COLUMN_Y+MENU_TAB_HEIGHT, null);
            if (Sound.isNoGameAudio()) {
                g2d.drawRect(SECOND_COLUMN_X, SECOND_COLUMN_Y, MENU_TAB_WIDTH, MAP_TAB_HEIGHT);
            }
            if (Sound.isNoMusic()) {
                g2d.drawRect(SECOND_COLUMN_X, SECOND_COLUMN_Y+MENU_TAB_HEIGHT, MENU_TAB_WIDTH, MAP_TAB_HEIGHT);

            }
        }
        if (ifGamePaused) {
            if(!ifLost){
            g2d.drawImage(resumeGame, FIRST_COLUMN_X -10, FIRST_COLUMN_Y +10, null);
        }}}

    private void drawGrid(Graphics g2d, int[][] squares) {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                ImageIcon squareType = new ImageIcon(grid.checkSquareType(squares[i][j]).getScaledInstance(MENU_GRID_TILE_SIZE, MENU_GRID_TILE_SIZE, Image.SCALE_DEFAULT));
                g2d.drawImage(squareType.getImage(), MENU_GRID_X + j * MENU_GRID_TILE_SIZE, MENU_GRID_Y + i * MENU_GRID_TILE_SIZE, null);
            }
        }
    }


    public Sound getMainTheme() {
        return mainTheme;}

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setIfLost(final boolean ifLost) {
        this.ifLost = ifLost;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }
}