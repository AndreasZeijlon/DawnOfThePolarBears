package andreaszeijlon.javaproject;


import java.awt.*;


/**
 * Created by Andreas Zeijlon on 2015-03-21.
 */
public class Shop {
    private int gold;
    private int health;
    private final static int SHOPBUTTON_SIZE = 80;
    private final static int MAGICSHOPCOORDONE =35;
    private final static int MAGICSHOPCOORDTWO =160;
    private final static int MAGICSHOPCOORDTHREE =140;
    private final static int MAGICSHOPMONEYINT = 95;
    private final static int BASICTOWERINFOHEIGHT =320;
    private final static int MAGICHEARTCOORD =60;
    private final static int STRINGSHOPHEIGHT =125;
    private final static int SHOP_MARGIN = 14;
    private final static int AUDIO_POS = 120;
    private final static int MUSIC_POS = 80;
    private final static int SOUND_POS_Y = 50;
    private final static int KEYHELPER_X = 343;
    private final static int KEYHELPER_START = 200;
    private final static int KEYHELPER_Y = 100;
    private final static int KEYHELPER_YDIFFERENS = 15;





    private Image button = Toolkit.getDefaultToolkit().getImage("images/shopButton60.png").getScaledInstance(SHOPBUTTON_SIZE,
													     SHOPBUTTON_SIZE,
													     Image.SCALE_DEFAULT);
    private Image buttonFocus = Toolkit.getDefaultToolkit().getImage("images/shopButtonFocus60.png").getScaledInstance(
	    SHOPBUTTON_SIZE, SHOPBUTTON_SIZE, Image.SCALE_DEFAULT);
    private Image coin = Toolkit.getDefaultToolkit().getImage("images/coin.png");
    private Image heart = Toolkit.getDefaultToolkit().getImage("images/heart.png");
    private Image basicTowerInfo = Toolkit.getDefaultToolkit().getImage("images/basicTowerInfo.png");
    private Image apTowerInfo = Toolkit.getDefaultToolkit().getImage("images/apTowerInfo.png");
    private Image scoutTowerInfo = Toolkit.getDefaultToolkit().getImage("images/scoutTowerInfo.png");
    private Image theX = Toolkit.getDefaultToolkit().getImage("images/X.png");
    private Image speaker = Toolkit.getDefaultToolkit().getImage("images/speaker.png");
    private Image note = Toolkit.getDefaultToolkit().getImage("images/note.png");




    private Rectangle[][] shopButtons = new Rectangle[2][2];
    private Image[][] shopImages = new Image[2][2];

    private Tower holdsItem;

    private Grid grid;

    public Shop(Grid grid) {
        this.grid = grid;
	Rectangle noteRec = new Rectangle(grid.getWidth() + MUSIC_POS, SOUND_POS_Y,30,30);
	Rectangle speakerRec = new Rectangle(grid.getWidth() + AUDIO_POS,SOUND_POS_Y,30,30);

        this.holdsItem = null;
        defineShopButtons();
        Image trashCan = Toolkit.getDefaultToolkit().getImage("images/trashCan60.png").getScaledInstance(SHOPBUTTON_SIZE, SHOPBUTTON_SIZE, Image.SCALE_DEFAULT);
        Image armorPiercingTowerImage = Toolkit.getDefaultToolkit().getImage("images/armorPiercingTower60.png").getScaledInstance(SHOPBUTTON_SIZE, SHOPBUTTON_SIZE, Image.SCALE_DEFAULT);
        Image basicTowerImage = Toolkit.getDefaultToolkit().getImage("images/basicTower60.png").getScaledInstance(SHOPBUTTON_SIZE, SHOPBUTTON_SIZE, Image.SCALE_DEFAULT);
	Image scoutTowerImage = Toolkit.getDefaultToolkit().getImage("images/scoutTower.png").getScaledInstance(SHOPBUTTON_SIZE, SHOPBUTTON_SIZE, Image.SCALE_DEFAULT);
        shopImages = new Image[][]{{basicTowerImage, armorPiercingTowerImage}, {scoutTowerImage, trashCan}};
    }


    public void defineShopButtons() {
        for (int i = 0; i < shopButtons.length; i++) {
            for (int j = 0; j < shopButtons[i].length; j++) {
                shopButtons[i][j] =
                        new Rectangle(grid.getWidth() + SHOP_MARGIN + j * (SHOPBUTTON_SIZE+10), i * (SHOPBUTTON_SIZE + 10) + MAGICSHOPCOORDTHREE, SHOPBUTTON_SIZE,
                                SHOPBUTTON_SIZE);

        }
	}
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Courier New", Font.BOLD, MAGICSHOPCOORDONE-1));
        g.drawString("SHOP", grid.getWidth() + SHOP_MARGIN + MAGICSHOPCOORDONE+10, STRINGSHOPHEIGHT);
	g.drawImage(note, grid.getWidth() + MUSIC_POS, SOUND_POS_Y,null);
	g.drawImage(speaker, grid.getWidth() + AUDIO_POS,SOUND_POS_Y,null);
			if(Sound.isNoMusic()){
			    g.drawImage(theX,grid.getWidth() + MUSIC_POS,SOUND_POS_Y,null);
			}
			if(Sound.isNoGameAudio()){
				    g.drawImage(theX,grid.getWidth() + AUDIO_POS,SOUND_POS_Y,null);
				}


        // Code below draws the Shop buttons on the screen.

        for (int y = 0; y < shopButtons.length; y++) {
            for (int x = 0; x < shopButtons[y].length; x++) {
                if (shopButtons[y][x].contains(KeyHandler.motionPoint)) {
                    g.drawImage(buttonFocus, grid.getWidth() + SHOP_MARGIN + x *(SHOPBUTTON_SIZE + 10), y * (SHOPBUTTON_SIZE + 10) + MAGICSHOPCOORDTHREE, null);

                } else {

                    g.drawImage(button, grid.getWidth() + SHOP_MARGIN + x *(SHOPBUTTON_SIZE + 10), y * (SHOPBUTTON_SIZE + 10) + MAGICSHOPCOORDTHREE, null);
                }
                g.drawImage(shopImages[y][x], grid.getWidth() + SHOP_MARGIN + x *(SHOPBUTTON_SIZE + 10), y * (SHOPBUTTON_SIZE + 10) + MAGICSHOPCOORDTHREE, null);

            }
        }


        if (shopButtons[0][0].contains(KeyHandler.motionPoint)) {
            g.drawImage(basicTowerInfo, grid.getWidth()+SHOP_MARGIN, BASICTOWERINFOHEIGHT, null);
        } else if (shopButtons[0][1].contains(KeyHandler.motionPoint)) {
            g.drawImage(apTowerInfo, grid.getWidth()+SHOP_MARGIN, BASICTOWERINFOHEIGHT, null);
        } else if (shopButtons[1][0].contains(KeyHandler.motionPoint)) {
	    g.drawImage(scoutTowerInfo, grid.getWidth()+SHOP_MARGIN, BASICTOWERINFOHEIGHT, null);
	}



        g.setFont(new Font("Courier New", Font.BOLD, SHOP_MARGIN + 4));
        g.setColor(Color.yellow);
        g.drawString("$" + 10, grid.getWidth() + SHOP_MARGIN + 10, MAGICSHOPCOORDTWO);
        g.drawString("$" + 10 * 2, grid.getWidth() + SHOP_MARGIN + MAGICSHOPMONEYINT, MAGICSHOPCOORDTWO);
        g.drawString("$" + 10*4, grid.getWidth() +SHOP_MARGIN + 10, MAGICSHOPCOORDTWO + SHOPBUTTON_SIZE + 10);

        g.drawString("KeyCommands: Buy a BasicTower = D", KEYHELPER_START,grid.getHeight()+KEYHELPER_Y);
        g.drawString("Buy a Armorpiercingtower = A ",KEYHELPER_X,grid.getHeight()+KEYHELPER_Y+KEYHELPER_YDIFFERENS);
        g.drawString("Buy a ScoutTower = S",KEYHELPER_X,grid.getHeight()+KEYHELPER_Y+KEYHELPER_YDIFFERENS*2);
        g.drawString("NextWave/FastForward = Space",KEYHELPER_X,grid.getHeight()+KEYHELPER_Y+KEYHELPER_YDIFFERENS*3);
        g.drawString("Pause/Go back to menu = ESC.",KEYHELPER_X,grid.getHeight()+KEYHELPER_Y+KEYHELPER_YDIFFERENS*4);
        //Code below draws health and gold on the screen.
        g.setColor(Color.black);
        g.drawImage(coin, grid.getWidth() + SHOP_MARGIN, SHOP_MARGIN+1, null);
        g.setFont(new Font("courier new", Font.BOLD, SHOP_MARGIN));
        g.drawString(String.valueOf(gold), grid.getWidth() + SHOP_MARGIN + MAGICSHOPCOORDONE, MAGICSHOPCOORDONE);
        g.drawImage(heart, grid.getWidth() + SHOP_MARGIN, MAGICHEARTCOORD, null);
        g.drawString(String.valueOf(health), grid.getWidth() + SHOP_MARGIN + MAGICSHOPCOORDONE, SHOPBUTTON_SIZE);

        g.setFont(new Font("Courier new", Font.PLAIN, 10+2));


    }

    public Rectangle[][] getShopButtons() {
        return shopButtons;
    }

    public Tower getHoldsItem() {
        return holdsItem;
    }

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return health;
    }

    public void setGold(final int gold) {
        this.gold = gold;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public void setHoldsItem(final Tower holdsItem) {
        this.holdsItem = holdsItem;
    }


}
