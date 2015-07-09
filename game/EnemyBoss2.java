package game;

import game.Audio.Sound;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class EnemyBoss2 extends GameObject {
    
    private Handler handler;
    private HUD hud;
    private Sound sound;
    private Game game;
    private GameObject player;
    private GameObject explosion = null;
    Ellipse2D.Double circle = new Ellipse2D.Double(507, 200, 48, 48);
    private static Random r = new Random ();
    
    private int Health = 160;
    
    private int timer = 146;
    private int timer2 = 3700;
    private int damageTimer = 0;
    private int meteor = 0;
    private int counter = 1;
    
    private boolean shiftSide = true;
    private boolean vulnerable = false;
    private boolean phase3_1 = r.nextBoolean();
    private boolean phase3_2 = r.nextBoolean();
    private boolean phase5_1 = r.nextBoolean();
    
    public EnemyBoss2(int x, int y, ID id, Handler handler, int base, int height, HUD hud, Game game, Sound sound){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        this.game = game;
        this.sound = sound;
        
        player = handler.findObject(0, 0, ID.Player);
        
        velX = -2;
        velY = 0;
        
        sound.BOSS_INTRO.play();
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    private Ellipse2D getCircleBounds(){
        return new Ellipse2D.Double(507, 200, 48, 48);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;

        if(damageTimer > 0) damageTimer--;
        
        if(timer == 1)sound.BOSS_MIDDLE.loop();
        
        if(timer <= 0) velX = 0;
        else timer --;
        
        if(timer == 0 && timer2 > 0){
            timer2--;
        
            if(timer2 > 3660) {} //pause 40 ticks
            else if(timer2 > 3000 && timer2 < 3660) phase1(); //700 ticks
            else if(timer2 > 2400 && timer2 <= 2800) phase2(); //400 ticks
            else if(timer2 > 1700 && timer2 <= 2200) phase3(); //500 ticks
            else if(timer2 > 800 && timer2 <= 1700) phase4(); //700 ticks 
            else if(timer2 > 0 && timer2 <= 700) phase5(); //500 ticks

        }
        else{
            timer2 = 3700;
            shiftSide = r.nextBoolean();
            phase3_1 = r.nextBoolean();
            phase3_2 = r.nextBoolean();
            meteor = 0;
            phase5_1 = r.nextBoolean();
            vulnerable = false;
        }
        
        collision();
        
        //remove object if hp is 0
        if(Health <= 0){
            sound.BOSS_MIDDLE.stop();
            sound.BOSS_END.play();
            hud.setScore(hud.getScore() + 10000);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.EnemyBoss2);
            handler.removeObject(explosion);
            handler.removeObject(tempObject);
        }
        
    }
    
    public void phase1(){
        //firing rate
        if(timer2%30 == 0 ){
            if(shiftSide == true){
                handler.addAdjustedObject(x,y+5,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x,y+75,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x,y+145,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x, y+215, ID.EnemyBullet, handler, 12, 12, -4, 0, hud);
                handler.addAdjustedObject(x,y+285,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x,y+355,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x,y+425,ID.EnemyBullet, handler,12,12,-4,0, hud);
                
                shiftSide = false;
            }else{
                handler.addAdjustedObject(x,y+30,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x, y+100, ID.EnemyBullet, handler, 12, 12, -4, 0, hud);
                handler.addAdjustedObject(x,y+170,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x,y+240,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x,y+310,ID.EnemyBullet, handler,12,12,-4,0, hud);
                handler.addAdjustedObject(x, y+380, ID.EnemyBullet, handler, 12, 12, -4, 0, hud);
                handler.addAdjustedObject(x, y+450, ID.EnemyBullet, handler, 12, 12, -4, 0, hud);
                
                shiftSide = true;
            }
            
        }
    }
    
    public void phase2(){
        //firing rate
        if(timer2%40 == 0){
            handler.addAdjustedObject(x,y+5,ID.EnemyBullet, handler,14,14,-2,0, hud);
            handler.addAdjustedObject(x,y+75,ID.EnemyBullet, handler,14,14,-2,0, hud);
            handler.addAdjustedObject(x,y+145,ID.EnemyBullet, handler,14,14,-2,0, hud);
            handler.addAdjustedObject(x, y+215, ID.EnemyBullet, handler, 14, 14, -2, 0, hud);
            handler.addAdjustedObject(x,y+285,ID.EnemyBullet, handler,14,14,-2,0, hud);
            handler.addAdjustedObject(x,y+355,ID.EnemyBullet, handler,14,14,-2,0, hud);
            handler.addAdjustedObject(x,y+425,ID.EnemyBullet, handler,14,14,-2,0, hud);
        }
        if(timer2 < 2500){
            if(timer2%40 == 0 && shiftSide == true){
                handler.addAdjustedObject(x,y+275,ID.EnemyBullet, handler,14,14,-3,-1, hud);
                handler.addAdjustedObject(x,y+345,ID.EnemyBullet, handler,14,14,-3,-1, hud);
                handler.addAdjustedObject(x,y+415,ID.EnemyBullet, handler,14,14,-3,-1, hud);
                handler.addAdjustedObject(x,y+265,ID.EnemyBullet, handler,14,14,-3,-2, hud);
                handler.addAdjustedObject(x,y+335,ID.EnemyBullet, handler,14,14,-3,-2, hud);
                handler.addAdjustedObject(x,y+405,ID.EnemyBullet, handler,14,14,-3,-2, hud);
                handler.addAdjustedObject(x,y+255,ID.EnemyBullet, handler,14,14,-3,-3, hud);
                handler.addAdjustedObject(x,y+325,ID.EnemyBullet, handler,14,14,-3,-3, hud);
                handler.addAdjustedObject(x,y+395,ID.EnemyBullet, handler,14,14,-3,-3, hud);
                handler.addAdjustedObject(x,y+245,ID.EnemyBullet, handler,14,14,-2,-3, hud);
                handler.addAdjustedObject(x,y+315,ID.EnemyBullet, handler,14,14,-2,-3, hud);
                handler.addAdjustedObject(x,y+385,ID.EnemyBullet, handler,14,14,-2,-3, hud);
                handler.addAdjustedObject(x,y+235,ID.EnemyBullet, handler,14,14,-1,-3, hud);
                handler.addAdjustedObject(x,y+305,ID.EnemyBullet, handler,14,14,-1,-3, hud);
                handler.addAdjustedObject(x,y+375,ID.EnemyBullet, handler,14,14,-1,-3, hud);
            }else if(timer2%40 == 0 && shiftSide == false){
                handler.addAdjustedObject(x,y+15,ID.EnemyBullet, handler,14,14,-3,1, hud);
                handler.addAdjustedObject(x,y+85,ID.EnemyBullet, handler,14,14,-3,1, hud);
                handler.addAdjustedObject(x,y+155,ID.EnemyBullet, handler,14,14,-3,1, hud);
                handler.addAdjustedObject(x,y+25,ID.EnemyBullet, handler,14,14,-3,2, hud);
                handler.addAdjustedObject(x,y+95,ID.EnemyBullet, handler,14,14,-3,2, hud);
                handler.addAdjustedObject(x,y+165,ID.EnemyBullet, handler,14,14,-3,2, hud);
                handler.addAdjustedObject(x,y+35,ID.EnemyBullet, handler,14,14,-3,3, hud);
                handler.addAdjustedObject(x,y+105,ID.EnemyBullet, handler,14,14,-3,3, hud);
                handler.addAdjustedObject(x,y+175,ID.EnemyBullet, handler,14,14,-3,3, hud);
                handler.addAdjustedObject(x,y+45,ID.EnemyBullet, handler,14,14,-2,3, hud);
                handler.addAdjustedObject(x,y+115,ID.EnemyBullet, handler,14,14,-2,3, hud);
                handler.addAdjustedObject(x,y+185,ID.EnemyBullet, handler,14,14,-2,3, hud);
                handler.addAdjustedObject(x,y+55,ID.EnemyBullet, handler,14,14,-1,3, hud);
                handler.addAdjustedObject(x,y+125,ID.EnemyBullet, handler,14,14,-1,3, hud);
                handler.addAdjustedObject(x,y+195,ID.EnemyBullet, handler,14,14,-1,3, hud);
            }
        }
    }
    
    public void phase3(){
        if(phase3_2 == true){
            vulnerable = true;
            if(timer2 == 2100) handler.addObject(new BigEnemy2(507, 200, ID.BigEnemy2, handler,64,64, hud));
        }
        if(phase3_1 == true){
            if(timer2%15 == 0){
                if(meteor == 6){
                    handler.addAdjustedObject(1,1,ID.EnemyBullet, handler,64,64,0,4, hud);
                }else if(meteor < 6) handler.addAdjustedObject(400-(meteor*75),1,ID.EnemyBullet, handler,64,64,1,4, hud);
                else {}
                meteor++;
            }
        }else{
            if(timer2%20 == 0 ){
                if(shiftSide == true){
                    handler.addAdjustedObject(5,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(80,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(155,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(230,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(305,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(380,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(455,1,ID.EnemyBullet, handler,4,32,0,6, hud);

                    shiftSide = false;
                }else{
                    handler.addAdjustedObject(43,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(118,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(193,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(268,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(343,1,ID.EnemyBullet, handler,4,32,0,6, hud);
                    handler.addAdjustedObject(418,1,ID.EnemyBullet, handler,4,32,0,6, hud);

                    shiftSide = true;
                }
            }
        }
    }
    
    public void phase4(){
        vulnerable = false;
        
        if(timer2 == 1700){
            counter = (int)game.clamp((float)counter, 2, 4);
            for(int i = 0; i < counter; i++){
                handler.addAdjustedObject(502,game.randInt(25,345),ID.BasicEnemy, handler,16,16,-1,1, hud);
                if(counter == 4 && i == 3)handler.addObject(new SmartEnemy3(560, game.randInt(25,345), ID.SmartEnemy3, handler,16,16, hud));
            }
            counter++;
        }
    }
    
    public void phase5(){
        vulnerable = true;
        
        if(phase5_1 == true && player.getVelX() == 0)player.setVelX(1);
        else if(phase5_1 == false && player.getVelX() == 0)player.setVelX(-1);
        
        if(timer2 == 700 || timer2 == 350 || timer2 == 100){
            if(explosion != null) handler.removeObject(explosion);
            switch(r.nextInt(6)){
                case 1: explosion = handler.addAdjustedObject(250,230,ID.EnemyBullet, handler,4,4,0,0, hud);
                    break;
                case 2: explosion = handler.addAdjustedObject(105,110,ID.EnemyBullet, handler,4,4,0,0, hud);
                    break;
                case 3: explosion = handler.addAdjustedObject(355,110,ID.EnemyBullet, handler,4,4,0,0, hud);
                    break;
                case 4: explosion = handler.addAdjustedObject(365,330,ID.EnemyBullet, handler,4,4,0,0, hud);
                    break;
                case 5: explosion = handler.addAdjustedObject(115,320,ID.EnemyBullet, handler,4,4,0,0, hud);
                    break;
            }
        }
        
        if(timer2 < 700 && timer2 > 500){
            if(timer2%2 == 0){
                explosion.setX((int)explosion.getX() - 1);
                explosion.setY((int)explosion.getY() - 1);
            }
            explosion.setBase(explosion.getBase() + 1);
            explosion.setHeight(explosion.getHeight() + 1);
        }else if(timer2 < 350 && timer2 > 250){
            explosion.setX((int)explosion.getX() - 1);
            explosion.setY((int)explosion.getY() - 1);
            explosion.setBase(explosion.getBase() + 2);
            explosion.setHeight(explosion.getHeight() + 2);
        }else if(timer2 < 100 && timer2 > 50){
            explosion.setX((int)explosion.getX() - 2);
            explosion.setY((int)explosion.getY() - 2);
            explosion.setBase(explosion.getBase() + 4);
            explosion.setHeight(explosion.getHeight() + 4);
        }else if(timer2 == 50) handler.removeObject(explosion);
    }
    
    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.PlayerBullet){
                if(getCircleBounds().intersects((double)tempObject.x, (double)tempObject.y, tempObject.base, tempObject.height)){
                    if(vulnerable == true){
                     handler.removeObject(tempObject);
                        Health -= 1;
                        damageTimer = 100;   
                    }
                }
            }
        }
    }
    
    @Override
    public void render(Graphics g){
        
        if(damageTimer%2 == 0){
            g.setColor(Color.red);
            g.fillRect((int)x,(int)y,base,height);
        }else if(damageTimer%3 == 0){
            g.setColor(Color.black);
            g.fillRect((int)x,(int)y,base,height);
        }
        
        Graphics2D g2d = (Graphics2D)g;
        if(vulnerable == true){
            g2d.setColor(Color.GREEN);
            g2d.fill(circle);
            g2d.draw(circle);
        }else if(timer2 == 3499){
            g2d.setColor(Color.red);
            g2d.fill(circle);
            g2d.draw(circle);
        }
        
    }
}
