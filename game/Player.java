package game;

import static game.Game.HEIGHT;
import static game.Game.WIDTH;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import game.Audio.Sound;

public class Player extends GameObject {
    
    Random r = new Random();
    private Handler handler;
    private Sound sound;
    
    private int invincibilityTimer = 150;
    private int shootBullet;
    private int shootTimer = 30;
    private int specialTimer = 0, coolDown = 80;
    private static int npoints = 4;
    
    private boolean useSpecial = false; 
    
    protected boolean specialAttack = false;
    protected boolean twinGuns = false;
    protected boolean rapidFire = false;
    
    public Player(int x, int y, ID id, Handler handler, int base, int height, Sound sound){
        super(x,y,id,base,height);
        this.handler = handler;
        this.sound = sound;
        
    }
    public Rectangle getBounds(){
        return null;
    }
    public Polygon getBoundsPolygon(){
        int xpoints[] = {(int)x, (int)x, (int)x+15, (int)x};
        int ypoints[] = {(int)y, (int)y+20, (int)y+10, (int)y};
        return new Polygon(xpoints, ypoints, npoints);
    }
    public void tick(){
        x += velX*3/4;
        y += velY*3/4;
        
        x = Game.clamp(x, 0, Game.WIDTH - 16);
        y = Game.clamp(y, 0, Game.HEIGHT - 48);
        
        //respawn invincibility
        if(invincibilityTimer > 0) invincibilityTimer--;
        else collision();
        
        firingRate();
    }
    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.BasicEnemy 
                    || tempObject.getId() == ID.BasicEnemy2
                    || tempObject.getId() == ID.FastEnemy 
                    || tempObject.getId() == ID.SmartEnemy 
                    || tempObject.getId() == ID.SmartEnemy2 
                    || tempObject.getId() == ID.SmartEnemy3
                    || tempObject.getId() == ID.BigEnemy
                    || tempObject.getId() == ID.StrongEnemy
                    || tempObject.getId() == ID.EnemyBoss
                    || tempObject.getId() == ID.EnemyBoss2){
                if(getBoundsPolygon().intersects(tempObject.getBounds())){
                    //collision into enemy
                    HUD.HEALTH -= 1;
                    x = (WIDTH/2)-300;
                    y = (HEIGHT/2)-32;
                    invincibilityTimer = 150;
                }
            }else if(tempObject.getId() == ID.EnemyBullet){
                if(getBoundsPolygon().intersects((double)tempObject.x, (double)tempObject.y, tempObject.base, tempObject.height)){
                    //collision into enemy bullet
                    handler.removeObject(tempObject);
                    HUD.HEALTH -= 1;
                    x = (WIDTH/2)-300;
                    y = (HEIGHT/2)-32;
                    invincibilityTimer = 150;
                }
            }else if(tempObject.getId() == ID.SpecialBeam){
                if(getBoundsPolygon().intersects(tempObject.getBounds())){
                    sound.POWERUP_SPECIAL.play();
                    HUD.score = HUD.getScore() + 200;
                    specialAttack = true;
                    handler.removeObject(tempObject);
                }
            }else if(tempObject.getId() == ID.TwinGuns){
                if(getBoundsPolygon().intersects(tempObject.getBounds())){
                    sound.POWERUP.play();
                    HUD.score = HUD.getScore() + 200;
                    twinGuns = true;
                    handler.removeObject(tempObject);
                }
            }else if(tempObject.getId() == ID.RapidFire){
                if(getBoundsPolygon().intersects(tempObject.getBounds())){
                    sound.POWERUP.play();
                    HUD.score = HUD.getScore() + 200;
                    rapidFire = true;
                    handler.removeObject(tempObject);
                }
            }else if(tempObject.getId() == ID.ExtraLife){
                if(getBoundsPolygon().intersects(tempObject.getBounds())){
                    sound.POWERUP.play();
                    HUD.score = HUD.getScore() + 200;
                    HUD.HEALTH++;
                    handler.removeObject(tempObject);
                }
            }else if(tempObject.getId() == ID.Invincible){
                if(getBoundsPolygon().intersects(tempObject.getBounds())){
                    sound.POWERUP.play();
                    HUD.score = HUD.getScore() + 200;
                    invincibilityTimer = 1000;
                    handler.removeObject(tempObject);
                }
            }
        }
    }
    private void firingRate(){
        shootBullet = base;
        if(height > 0) useSpecial = true;
        
        if(shootTimer > 0) shootTimer--;
        if(specialTimer > 0) specialTimer--;
        
        if(shootBullet == 1 && shootTimer == 0){
            Sound.LASER.play();
            if(twinGuns == true){
                handler.addObject(new PlayerBullet((int)x+4, (int)y-10, ID.PlayerBullet, handler, 14, 14));
                handler.addObject(new PlayerBullet((int)x+4, (int)y+18, ID.PlayerBullet, handler, 14, 14));
            }
            
            if(rapidFire == true) shootTimer = 15;
            else shootTimer = 30;
            
            handler.addObject(new PlayerBullet((int)x +15, (int)y +9, ID.PlayerBullet, handler, 32, 4));
            
        }else if(specialAttack == true && specialTimer == 0 && useSpecial == true){
            if(coolDown <= 0) {
                useSpecial = false;
                specialTimer = 10000;
                coolDown = 80;                
            }else if(coolDown <= 80){
                if(coolDown == 80) sound.SPECIAL_LASER.play();
                if(coolDown%4 == 0)handler.addObject(new PlayerBullet((int)x +15, (int)y +5, ID.PlayerBullet, handler, 16, 16));
                else if(coolDown%2 == 0){
                    handler.addObject(new PlayerBullet((int)x +15, (int)y -4, ID.PlayerBullet, handler, 16, 16));
                    handler.addObject(new PlayerBullet((int)x +15, (int)y +12, ID.PlayerBullet, handler, 16, 16));
                }
                coolDown--;
            }
        }
    }
    public void render(Graphics g){
        int xpoints[] = {(int)x, (int)x, (int)x+15, (int)x};
        int ypoints[] = {(int)y, (int)y+20, (int)y+10, (int)y};
    
        if(invincibilityTimer %2 == 0)g.setColor(Color.white);
        else if(invincibilityTimer %11 == 0)g.setColor(Color.black);
        g.fillPolygon(xpoints, ypoints, npoints);
        g.drawPolygon(xpoints, ypoints, npoints);
        
        if(twinGuns == true){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect((int)x-4,(int)y-6,19,4);
            g.fillRect((int)x-4,(int)y+22,19,4);
        }
    }
}
