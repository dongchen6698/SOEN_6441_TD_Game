package TD.config;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

/**
 * This is Default Value Model. To load Different images, tower properties, game levels.
 * 
 */
public class ConfigModel{   
    public static int money = 500;
    public static int total_earned = 150;
    public static int health = 6;
    public static int killed = 0;
    public static int total_killed = 0;
    public static int killsToWin = 5;
    public static int maxLevel = 3;
    public static boolean roundSlot = true;
    public static int creaturesNo = 5;
    public static int waveLap = 1;   
    public static int level = 1;    
    public static int gridX = 7;
    public static int gridY = 7;
    public static int cellPixels = 44;   
    public static int walkFrame = 0, walkSpeed = 20;   
    public static int spawnTime = 1000, spawnFrame = 0;
    public static Point mse = new Point(0, 0);    
    public static int groundGrass = 0;
    public static int groundRoad = 1;
    public static int groundWater = 11;   
    public static int airAir = -1;
    public static int airCave = 8;    
    public static int removeTower = 11;
    public static int[] airTowerLaser  = new int[]{3,4,5,6};
    public static int[] TowerPrice 	   = new int[]{10,20,30,40};
    public static int[] TowerLevel     = new int[]{1,1,1,1};
    public static int[] airTowerRanger = new int[]{88,132,88,88};
    public static int[] TowerFiringRate= new int[]{1,5,2,2};
    public static int[] Towerhealth    = new int[]{10,15,20,25};
    public static String[] TowerSpecial= new String[]{"Normal","Fire","Freeze","Splash"};
    public static int mobAir = -1;
    public static int mobGreeny = 0;    
    public static int[] deathReward = {10};    
    public static Image[] ground_level = new Image[100];
    public static Image[] air_level = new Image[100];    
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_mob = new Image[100];
    public static Image[] fire = new Image[1];
    public static Image[] ice = new Image[1];
    public static Image[] star = new Image[1];    
    public static Image[] happy = new Image[1];
    public static Image[] sad = new Image[1];    
    public static boolean gameOberFlag = false;
}
