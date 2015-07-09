package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class SmartEnemy extends GameObject {
    
    private Handler handler;
    private HUD hud;
    private GameObject player;
    
    private int Health = 3;
    
    public SmartEnemy(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        player = handler.findObject(0, 0, ID.Player);
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
        
        //remove object if hp is 0
        if(Health <= 0){
            hud.setScore(hud.getScore() + 10);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.SmartEnemy);
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
        float diffX = x - player.getX();
        float diffY = y - player.getY();
        float distance = (float) Math.sqrt( (x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
        
        velX = ((-1/distance) * diffX);
        velY = ((-1/distance) * diffY);
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.green);
        g.fillRect((int)x,(int)y,base,height);
    }
}
