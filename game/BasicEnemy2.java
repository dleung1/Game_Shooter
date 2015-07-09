package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class BasicEnemy2 extends GameObject {
    
    private Handler handler;
    private HUD hud;
    
    private int Health = 5;
    private int timer = 35;
    
    public BasicEnemy2(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        velX = 0;
        velY = (float)1.5;
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
        
        if(y <= 0 || y >= Game.HEIGHT - 40) movementRate();
        
        //remove object if hp is 0
        if(Health <= 0){
            hud.setScore(hud.getScore() + 30);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.BasicEnemy2);
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
            handler.addAdjustedObject(x, y, ID.EnemyBullet, handler, 12, 12, -5, 0, hud);
            timer = 35;
        }
    }
    
    private void movementRate(){
            velY *= -1;
            velX = 0;
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.blue);
        g.fillRect((int)x,(int)y,base,height);
    }
}
