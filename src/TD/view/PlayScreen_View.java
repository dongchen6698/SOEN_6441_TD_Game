package TD.view;

import static TD.config.ConfigModel.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.Serializable;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import TD.config.ConfigModel;
import TD.controller.PlayScreen_Controller;
import TD.controller.Shop_Controller;
import TD.model.Creature_Model;
import TowerDefenceGame.*;

/**
  * This is GUI class of Play Screen Module.
 * @author peilin
 */
public class PlayScreen_View extends JPanel implements Runnable{

    public Thread gameLoop = new Thread(this);
    
    private static boolean isFirst = true;
    private static boolean isWon = false;
    private static int waveLap = ConfigModel.waveLap;
    public static boolean isWin = false;
    boolean rFlag =false;
    static PlayScreen_Controller psCont;
    private volatile boolean isRunning = true;
    
    public static Creature_Model[] Creatures = CreatureFactory.getCreature(waveLap);
    //  public static Creature_Model[] Creatures = new Creature_Model[ConfigModel.creaturesNo];
      Creature_View cView = new Creature_View();
      
    public static int winTime = 2000, winFrame =0;
    public Graphics w;
    
    /**
     * get graph function
     * @return
     */
    public Graphics getGraph(){
        return w;
    }
    
    /**
     * This method will initialize GUI components for Play Screen.
     * @param j the GamePlay object
     */
    public PlayScreen_View(GamePlay j){
        final KeyController eventSource = new KeyController();
 
        // create an observer
        final Shop_Controller responseHandler = new Shop_Controller();
 
        // subscribe the observer to the event source
        eventSource.addObserver(responseHandler);
        
        j.addMouseListener(eventSource);
        j.addMouseMotionListener(eventSource);

        ground_level[0] = new ImageIcon("resources/green_ground.png").getImage();
        ground_level[1] = new ImageIcon("resources/path.png").getImage();
 
        air_level[0] = new ImageIcon("resources/end_point.png").getImage();
        air_level[1] = new ImageIcon("resources/entry_point.png").getImage();
        air_level[3] = new ImageIcon("resources/tower_1.png").getImage();
        air_level[4] = new ImageIcon("resources/tower_2.png").getImage();
        air_level[5] = new ImageIcon("resources/tower_3.png").getImage();
        air_level[6] = new ImageIcon("resources/tower_4.png").getImage();
        
        tileset_res[0] = new ImageIcon("resources/cell.png").getImage();
        tileset_res[1] = new ImageIcon("resources/heart.png").getImage();
        tileset_res[2] = new ImageIcon("resources/coin_icon.png").getImage();
        tileset_mob[0] = new ImageIcon("resources/critter.png").getImage();
        fire[0] = new ImageIcon("resources/fire.gif").getImage();
        ice[0] = new ImageIcon("resources/ice.png").getImage();
        star[0] = new ImageIcon("resources/star.gif").getImage();
        
        happy[0] = new ImageIcon("resources/happy.gif").getImage();
        sad[0] = new ImageIcon("resources/sad.gif").getImage();
   
    }
    
    /**
     * Initializing creatures
     * @return successFlag
     */
    public boolean initCreatures(){
        //System.out.println("initCreatures");
    	LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "Initial the creature.");
   
        if(psCont != null){
        		Creatures = CreatureFactory.getCreature(waveLap);
            for(int i=0;i<Creatures.length;i++){
                Creatures[i] = new Creature_Model(psCont.getCcModel(),psCont.getCcCont());
            }
            isFirst = false;
            
            return true;
        } else {
            //System.out.println("psCont not initialized");
            return false;
        }
    }
    
    /**
     * Adding new creatures for next level
     */
    public void newCreatures(){
        if((ConfigModel.level > ConfigModel.maxLevel) && health != 0){
            isWon = true;
            LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "win the game");
        }else{
            isWon = false;
        }
        if((ConfigModel.health > 0 && checkLiveCreatures()) && !isWon){
        		
        		//System.out.println(waveLap+"  "+ConfigModel.creaturesNo);
        		ConfigModel.waveLap++;
        		this.waveLap++;
        	if(this.waveLap == 2){  
        		LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "move to next wave");
        		ConfigModel.killed = 0;
                ConfigModel.level++;        
                tileset_mob[0] = new ImageIcon("resources/critter_2.png").getImage();
                Creatures = CreatureFactory.getCreature(waveLap);
                LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "create new creatures");
                //System.out.println(waveLap+"  "+ConfigModel.creaturesNo);
                ConfigModel.killsToWin = ConfigModel.creaturesNo;
                ConfigModel.walkSpeed = 15;
        	}
        	if(this.waveLap == 3){
        		LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "move to next wave");
                ConfigModel.killed = 0;
                ConfigModel.level++;
                tileset_mob[0] = new ImageIcon("resources/critter_3.png").getImage();
                Creatures = CreatureFactory.getCreature(waveLap);
                LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "create new creatures");
                //System.out.println(waveLap+"  "+ConfigModel.creaturesNo+"should be wave 3");
                ConfigModel.killsToWin = 15;
                ConfigModel.walkSpeed = 10;
        	}
            for(int i=0;i<Creatures.length;i++){
                Creatures[i] = new Creature_Model(psCont.getCcModel(),psCont.getCcCont());
            }
        }
    }
    
    /**
     * This method will start thread.
     * @return successFlag
     */
    public boolean startGame(){
        gameLoop.start();
        return true;
    }
    
    /**
     * Check Game over or not.
     * @return successFlag
     */
    public boolean isGameOver(){
        if(ConfigModel.health <= 0)
            return true;
        else 
            return false;
    }
    
    /**
     * Check user is won the game or not.
     * @return successFlag
     */
    public static boolean hasWon() {
        if((ConfigModel.level >= ConfigModel.maxLevel) && ConfigModel.killed == ConfigModel.killsToWin){
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * Check Creatures Life health
     * @return successFlag 
     */
    public static boolean checkLiveCreatures(){
        for(int i=0;i<Creatures.length;i++){
                if(Creatures[i].getHealth() != 0)
                    return false;
            }
        return true;
    }
    
    /**
     * This method will set          
     * @param cont the Play Screen Controller
     */
    public void setController(PlayScreen_Controller cont){
        psCont = cont;
        rFlag = true;
    }
    
    /**
     * This method will draw GUI Components.
     * @param g the Graphics
     */
    public void paintComponent(Graphics g){
        w=g;
        if(isFirst)
        {
            initCreatures();
        } else {
            newCreatures();
        }
        
        if(psCont !=null && !gameOberFlag){
            g.setColor(new Color(50,50,50));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.white);
            psCont.getccDraw(g);
            
            for(int i=0; i< Creatures.length;i++){
                if(Creatures[i].isInGame()){
                    cView.draw(Creatures[i], i, g);
                }
            }
            
            psCont.getshopDraw(g);
        }
        
        if(hasWon() || isWon){
            //System.out.println("Congratulations");
            Point Cp= GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
//            for(int i=0; i< Creatures.length;i++){
//                Creatures[i].setHealth(0);
//                Creatures[i].setInGame(false);
//            }
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(happy[0], Cp.x/2 - 60, Cp.y/2 - 120, 110, 100,  null);
            g.setColor(new Color(240, 20, 20));
            g.setFont(new Font("Courier New",Font.BOLD, 25));
            g.drawString("Congratulations", Cp.x/2 - 120, Cp.y/2);
            
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Courier New",Font.BOLD, 15));
            g.drawString("Level: "+(level-1), Cp.x/2 - 80, Cp.y/2 + 20);
            g.drawString("Killed: "+total_killed, Cp.x/2 - 80, Cp.y/2 + 40);
            g.drawString("Earned: "+money, Cp.x/2 - 80, Cp.y/2 + 60);
        }
        
        if(isGameOver()){
            if(!gameOberFlag)
            {
                total_earned = money;
                money = 0;
            }
            gameOberFlag = true;
            LogGenerator.addLogInfo("WAVE_"+Integer.toString(ConfigModel.waveLap), "Null", "game over");
            Point Cp= GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
            for(int i=0; i< Creatures.length;i++){
                Creatures[i].setHealth(0);
                Creatures[i].setInGame(false);
            }
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(sad[0], Cp.x/2 - 60, Cp.y/2 - 120, 110, 100,  null);
            g.setColor(new Color(240, 20, 20));
            g.setFont(new Font("Courier New",Font.BOLD, 25));
            g.drawString("Game Over", Cp.x/2 - 80, Cp.y/2);
            
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Courier New",Font.BOLD, 15));
            g.drawString("Level: "+(level-1), Cp.x/2 - 80, Cp.y/2 + 20);
            g.drawString("Killed: "+total_killed, Cp.x/2 - 80, Cp.y/2 + 40);
            g.drawString("Earned: "+total_earned, Cp.x/2 - 80, Cp.y/2 + 60);
        }
    }
    
    /**
     * Creatures Movement for current level
     */
    public int spawnTime = 1000, spawnFrame = 0;
    public boolean mobSpawner(){
        if(spawnFrame >= spawnTime){
            int i =0;
            for(i=0;i<Creatures.length;i++){
                if(!Creatures[i].isInGame() && Creatures[i].getHealth() == 44){
                    Creatures[i].setHealth(44 * ConfigModel.level);
                    Creatures[i].spawnCreature(i);
                    break;
                }
            }
            spawnFrame = 0;
        }else{
            spawnFrame += 1;
        }
        return true;
    }
    
    /**
     * This is run method for Thread.
     */
    @Override
    public void run() {
        while(isRunning){
            
            if(isFirst){
                initCreatures();
            }
            
            if(rFlag && !isFirst && !isWin){
                try {
                    psCont.getCcModel().physic(Creatures);
                } catch (ParseException ex) {
                }
                if(this.waveLap == 2){
                	this.spawnTime = 800;
                	//System.out.println(waveLap+"  "+this.spawnTime);
                	mobSpawner();
                }else if(this.waveLap == 3){
                	this.spawnTime = 400;
                	//System.out.println(waveLap+"  "+this.spawnTime);
                	mobSpawner();
                }else{
                	this.spawnTime = 1000;
                	//System.out.println(waveLap+"  "+this.spawnTime);
                	mobSpawner();
                }
                for(int i=0;i<Creatures.length;i++){
                    if(Creatures[i].isInGame()){
                        Creatures[i].physic();
                    }
                }
                repaint();
                try{
                    gameLoop.sleep(1);
                } catch (Exception e){
                    //System.out.println("Some Error");
                }
            } else {
                if(isWin){
                    if(winFrame >= winTime){
                        if(ConfigModel.level == ConfigModel.maxLevel){
                        }else{
                            ConfigModel.level +=1;
                            isWin = false;
                        }
                        winFrame =0;
                    }else {
                        winFrame +=1;
                    }
                }
            }
        }
    }
    
    /**
     * this function for stop the Thread.
     */
    public void killThread(){
    	this.isRunning = false;
    }
}

