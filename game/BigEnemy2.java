package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.lang.Math;

public class BigEnemy2 extends GameObject {
    
    private Handler handler;
    private HUD hud;
    private GameObject player;
    private GameObject explosion;
    
    private int Health = 14;
    private int timer = 25;
    private int timer2 = 80;
    private int timer3 = 10;

    public BigEnemy2(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        velX = (float)-.75;
        velY = 0;
        
        player = handler.findObject(0, 0, ID.Player);
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        collision();
        firingRate();
        
        //remove object if hp is 0
        //create 4 enemies
        //destroys itself
        if(Health <= 0){
            hud.setScore(hud.getScore() + 100);
            
            handler.addAdjustedObject(x, y, ID.BasicEnemy, handler, 16, 16, -1, -1, hud);
            handler.addAdjustedObject(x, y+48, ID.BasicEnemy, handler, 16, 16, -1, 1, hud);
            handler.addAdjustedObject(x+48, y, ID.BasicEnemy, handler, 16, 16, 1, -1, hud);
            handler.addAdjustedObject(x+48, y+48, ID.BasicEnemy, handler, 16, 16, 1, 1, hud);
            
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.BigEnemy2);
            handler.removeObject(tempObject);
        }else if(x+64 >= player.getX() && x-64 <= player.getX()){
            
            if(timer3 == 10){
                timer3--;
                explosion = handler.addAdjustedObject(x-64, y-64, ID.BasicEnemy, handler, base*3, height*3, 0, 0, hud);
            }
            else if(timer3 > 0) timer3--;
            else {
                hud.setScore(hud.getScore() + 100);
                GameObject tempObject = handler.findObject((int)x, (int)y, ID.BigEnemy2);
                handler.removeObject(tempObject);
                handler.removeObject(explosion);
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
    
    private void firingRate(){
        if(timer > 0) timer--;
        else {
            handler.addAdjustedObject(x, y, ID.EnemyBullet, handler, 12, 12, -5, -1, hud);
            handler.addAdjustedObject(x, y+54, ID.EnemyBullet, handler, 12, 12, -5, 1, hud);
            timer = 25;
        }
        if(timer2 > 0) timer2--;
        else{
            handler.addAdjustedObject(x, y+8, ID.EnemyBullet, handler, 48, 48, -5, 0, hud);
            timer2 = 80;
        }
    }
    
    @Override
    public void render(Graphics g){
        
            g.setColor(Color.BLUE);
            g.fillRect((int)x,(int)y,base,height);
    }
}
