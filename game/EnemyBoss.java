package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import game.Audio.Sound;

public class EnemyBoss extends GameObject {
    
    private Handler handler;
    private HUD hud;
    Random r = new Random();
    private GameObject player;
    private Sound sound;
    
    private int timer = 146;
    private int timer2 = 1450;
    private int small = 0;
    
    private int Health = 80;
    
    private boolean random = r.nextBoolean();
    
    public EnemyBoss(int x, int y, ID id, Handler handler, int base, int height, HUD hud, Sound sound){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        this.sound = sound;
        
        velX = -2;
        velY = 0;
        
        player = handler.findObject(0, 0, ID.Player);
        
        sound.BOSS_INTRO.play();
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        if(timer == 1)Sound.BOSS_MIDDLE.loop();
        
        if(timer <= 0) velX = 0;
        else timer --;
        
        if(timer == 0 && timer2 > 0){
            timer2--;
        
            if(timer2 > 1430) {} //pause 20 ticks
            else if(timer2 > 950) phase1();
            else if(timer2 > 550 && timer2 <= 950) phase2();
            else if(timer2 > 0 && timer2 <= 500) phase3();

        }
        else{
            timer2 = 1450;
            small = 0;
            random = r.nextBoolean();
        }

        collision();
        
        //remove object if hp is 0
        if(Health <= 0){
            sound.BOSS_MIDDLE.stop();
            sound.BOSS_END.play();
            hud.setScore(hud.getScore() + 4000);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.EnemyBoss);
            handler.removeObject(tempObject);
            handler.addObject(new SpecialBeam(400, 250, ID.SpecialBeam, handler, 0, 0, hud));
        }
    }
    
    public void phase1(){
        //movement rate
        if(velY == 0) velY = 2;
        if(y <= 0 || y >= Game.HEIGHT - 156) velY *= -1;
        
        //firing rate
        if(timer2%20 == 0 ){
            
            handler.addAdjustedObject(x,y,ID.EnemyBullet, handler,14,14,-5,-2, hud);
            handler.addAdjustedObject(x,y+10,ID.EnemyBullet, handler,14,14,-4,-1, hud);
            handler.addAdjustedObject(x,y+100,ID.EnemyBullet, handler,14,14,-4,1, hud);
            handler.addAdjustedObject(x, y + 110, ID.EnemyBullet, handler, 14, 14, -5, 2, hud);
            
        }
        if(timer2%40 == 0) handler.addAdjustedObject(x,y,ID.EnemyBullet, handler,48,48,-5,0, hud);
        
    }
    
    public void phase2(){
        //movement rate
        velY = 0;
        
        //firing rate
        if(timer2%20 == 0){
            
            handler.addAdjustedObject(x,y,ID.EnemyBullet, handler,14,14,-5,-2, hud);
            handler.addAdjustedObject(x,y+1,ID.EnemyBullet, handler,12,12,-6,0, hud);
            handler.addAdjustedObject(x,y+10,ID.EnemyBullet, handler,14,14,-4,-1, hud);
            handler.addAdjustedObject(x,y+100,ID.EnemyBullet, handler,14,14,-4,1, hud);
            handler.addAdjustedObject(x,y+109,ID.EnemyBullet, handler,12,12,-6,0, hud);
            handler.addAdjustedObject(x, y + 110, ID.EnemyBullet, handler, 14, 14, -5, 2, hud);
            
        }
        if(timer2%60 == 0) handler.addAdjustedObject(x,y+38,ID.EnemyBullet, handler,52,52,-5,0, hud);
        else if(timer2%12 == 0) handler.addAdjustedObject(x,y+60,ID.EnemyBullet, handler,14,14,-5,0, hud);
    }
    
    public void phase3(){
        if(random == true) {
            //laser beam
            GameObject tempObject = null;
            if(timer2 >= 359) tempObject = handler.addAdjustedObject(x - 23,y,ID.EnemyBullet, handler,60,129,-10,0, hud);
            else if(timer2 < 359){
            small++;
            tempObject = handler.addAdjustedObject(x - 20,y+small/2,ID.EnemyBullet, handler,60,129 - small,-10,0, hud);
            }
        }
        else{
            //ram attack
            if(timer2 == 500){
                velX = 4;
                velY = 4;
            }
            if(timer2 > 400){
                velX *= -1;
                velY *= -1;
            }
            else if(timer2 > 370){
                float diffX = x - player.getX();
                float diffY = y - player.getY();
                float distance = (float) Math.sqrt( (x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));

                velX = ((-12/distance) * diffX);
                velY = ((-12/distance) * diffY);
            }
            else if(timer2 > 345){

                if(x <= 10 || x >= Game.WIDTH) velX  = 0;
                else velX = -12;
                velY = 0;
            }
            else if(timer2 > 0){
                float diffX = x - 508;
                float diffY = y - ((Game.HEIGHT/2)-70);
                float distance = (float) Math.sqrt( (x-508)*(x-508) + (y-((Game.HEIGHT/2)-70))*(y-((Game.HEIGHT/2)-70)));
                
                if(x >= 508){ velX  = 0; velY = 0;}
                else { velX = ((-4/distance) * diffX); velY = ((-4/distance) * diffY); }
                
            }
        }
        if(timer2 == 200){
            for(int i = 0; i < 129; i += 128){
                handler.addObject(new SmartEnemy((int)x+i, (int)y, ID.SmartEnemy, handler,16,16, hud));
                handler.addObject(new SmartEnemy((int)x+i, (int)y+128, ID.SmartEnemy, handler,16,16, hud));
            }
        }
      
    }
    
    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.PlayerBullet){
                if(getBounds().intersects((double)tempObject.x, (double)tempObject.y, tempObject.base, tempObject.height)){
                    //collision into enemy bullet
                    handler.removeObject(tempObject);
                    Health -= 1;
                }
            }
        }
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.red);
        g.fillRect((int)x,(int)y,base,height);
    }
}
