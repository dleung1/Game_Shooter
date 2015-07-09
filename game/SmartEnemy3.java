package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class SmartEnemy3 extends GameObject {
    
    private Handler handler;
    private HUD hud;
    private GameObject enemy;
    
    private int Health = 10;
    
    public SmartEnemy3(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        handler.addObject(new Trail(x,y,ID.Trail,Color.DARK_GRAY,16,16,0.1f, handler));
        
        findEnemy();
        firingRate();
        movementRate();
        collision();
        
        //remove object if hp is 0
        if(Health <= 0){
            hud.setScore(hud.getScore() + 100);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.SmartEnemy3);
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
    
    public void firingRate(){
        if(enemy == null){}
        else if((enemy.x < x+8 && enemy.x > x-8) && (enemy.y < y+8 && enemy.y > y-8)){
            if(enemy.id == ID.BasicEnemy){
                handler.removeObject(enemy);
                handler.addObject(new BasicEnemy2((int)x,(int)y,ID.BasicEnemy2,handler,16,16,hud));
            }else if(enemy.id == ID.BigEnemy){
                handler.removeObject(enemy);
                handler.addObject(new BigEnemy2((int)x,(int)y,ID.BigEnemy2,handler,64,64,hud));
            }else if(enemy.id == ID.SmartEnemy){
                handler.removeObject(enemy);
                handler.addObject(new SmartEnemy2((int)x,(int)y,ID.SmartEnemy2,handler,16,16,hud));
            }
        }
    }
    
    public void movementRate(){
        
        if(enemy == null){
            velX = 0;
            velY = 0;
        }else{
            float diffX = x - enemy.getX();
            float diffY = y - enemy.getY();
            float distance = (float) Math.sqrt( (x-enemy.getX())*(x-enemy.getX()) + (y-enemy.getY())*(y-enemy.getY()));

            velX = ((-1/distance) * diffX);
            velY = ((-1/distance) * diffY);
        }
    }
    
    private void findEnemy(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.id == ID.BasicEnemy
                    || tempObject.id == ID.BigEnemy
                    || tempObject.id == ID.SmartEnemy) 
            {
                enemy = tempObject;
                return;
            }
        }
        enemy = null;
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)x,(int)y,base,height);
    }
}
