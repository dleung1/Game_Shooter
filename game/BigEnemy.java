package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.lang.Math;

public class BigEnemy extends GameObject {
    
    private Handler handler;
    private HUD hud;
    
    private int Health = 10;
    private int timer = 20;
    private int timer2 = 60;

    public BigEnemy(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        velX = -1;
        velY = 0;
        
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
        
        if(x < 350) velX = 0;
        
        //remove object if hp is 0
        //create 4 enemies
        if(Health <= 0){
            hud.setScore(hud.getScore() + 60);
            
            handler.addAdjustedObject(x, y, ID.BasicEnemy, handler, 16, 16, -1, -1, hud);
            handler.addAdjustedObject(x, y+48, ID.BasicEnemy, handler, 16, 16, -1, 1, hud);
            handler.addAdjustedObject(x+48, y, ID.BasicEnemy, handler, 16, 16, 1, -1, hud);
            handler.addAdjustedObject(x+48, y+48, ID.BasicEnemy, handler, 16, 16, 1, 1, hud);
            
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.BigEnemy);
            handler.removeObject(tempObject);
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
            timer = 20;
        }
        if(timer2 > 0) timer2--;
        else{
            handler.addAdjustedObject(x, y+8, ID.EnemyBullet, handler, 48, 48, -5, 0, hud);
            timer2 = 60;
        }
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.YELLOW);
        g.fillRect((int)x,(int)y,base,height);
    }
}
