package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class SmartEnemy2 extends GameObject {
    
    private Handler handler;
    private HUD hud;
    
    private int Health = 9;
    private int timer = 500;
    
    public SmartEnemy2(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        velX = 1;
        velY = 1;
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
       
        handler.addObject(new Trail(x,y,ID.Trail,Color.green,16,16,0.1f, handler));
        
        movementRate();
        collision();
        
        if(timer > 0) timer--;
        else{
            handler.addObject(new BasicEnemy((int)x, (int)y, ID.BasicEnemy, handler,16,16,hud));
            timer = 500;
        }
        
        //remove object if hp is 0
        if(Health <= 0){
            hud.setScore(hud.getScore() + 50);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.SmartEnemy2);
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
                    //HUD.HEALTH -= 2;
                    Health -= 1;
                }
            }
        }
    }
    
    public void movementRate(){
        if(y <= 0 || y >= Game.HEIGHT - 40) velY *= -1;
        if(x < 300 || x >= Game.WIDTH - 20) velX *= -1;
    }
    
    @Override
    public void render(Graphics g){
        
            g.setColor(Color.GREEN);
            g.fillRect((int)x,(int)y,base,height);
    }
}
