package tits;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

//import entity.Player;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3; 

    public final int tileSize = originalTileSize * scale; //scales the tiles so that it now looks like 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels
     
    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;
    
    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    
    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];  

    //GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    
    
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set size of this class(JPanel)
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //if set true, all drawing from this component will be done in an offscreen painting buffer
        // in short enabling the above line of code can improve game's rendering performance

        this.addKeyListener(keyH);
        this.setFocusable(true);// with this, Gamepanel can be "focused" to receive key input
    }

    public void setupGame() {
    	
    	aSetter.setObject();
    	aSetter.setNPC();
//    	playMusic(0);
    	gameState = titleState;
    }
    
    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override

    //sleep gameloop
    // public void run() {
    //     // google delta game loop method for another game loop


    //     double drawInterval = 1000000000/FPS; //0.01666 sec
    //     double nextDrawTime = System.nanoTime() + drawInterval;
    //     // long timer = 0;
    //     // int drawCount = 0;

    //     while(gameThread!= null){
            
    //         long currentTime = System.nanoTime(); // returns current value of running JVM's high resolution time source, in nanosecond
    //         update();
    //         repaint();

    //         try {
    //             double remainingTime = nextDrawTime - System.nanoTime();
    //             remainingTime = remainingTime/1000000;

    //             if(remainingTime < 0 ){
    //                 remainingTime = 0;
    //             }

    //             Thread.sleep((long) remainingTime);//sleep accepts input in milliseconds

    //             nextDrawTime += drawInterval;
    //         } 

    //         catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
        
    //}
    
    //delta gameloop
    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();   

            delta += (currentTime - lastTime) / drawInterval;
             
            lastTime = currentTime;

            if(delta>1){
                update();
                repaint();
                delta--; 
            }
        }
    }

    public void update(){
    	
    	if(gameState == playState) {
            //PLAYER
    		player.update();
    		
    		//NPC
    		for(int i = 0; i < npc.length; i++) {
    			if(npc[i] != null) {
    				npc[i].update();
    			}
    		}
    	}
    	if(gameState == pauseState) {
    		
    	}
    }

    public void paintComponent(java.awt.Graphics g){//built in method****Graphics class has many functions to draw objects on the screen    
        
    	super.paintComponent(g);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;// Graphics2D class extends the Graphics class to provide more sophesticated control over geometry, color management, and text layout
        
        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
        	drawStart = System.nanoTime();	
        }
        
        //TITLE SCREEN
        if(gameState == titleState) {
        	ui.draw(g2);
        	
        }
        
        //OTHERS
        else {
        	 
        	//TILE
            tileM.draw(g2);
            
            //OBJECT
            for(int i = 0; i < obj.length; i++) {
            	if(obj[i] != null) {
            		obj[i].draw(g2, this);
            	}
            }
            
            //NPC
            for(int i = 0; i < npc.length; i++) {
            	if(npc[i] != null) {
            		npc[i].draw(g2);
            	}
            }
            
            //PLAYER
            player.draw(g2);
            
            //UI
            ui.draw(g2);	
        }
        
        //DEBUG
        if(keyH.checkDrawTime == true) {
        	long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart; 
            g2.setColor(Color.white);
            g2.drawString("Draw Time:" + passed, 10, 400);
            System.out.println("Draw time: "+passed);
        }
        
        g2.dispose();//dispose of this graphics context and release any system resources that it is using
    }

    public void playMusic(int i) {
    	
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	
    	music.stop();
    }
    
    public void playSE(int i) {
    	
    	se.setFile(i);
    	se.play();
    	}
    }
    






