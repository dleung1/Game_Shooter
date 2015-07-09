package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class StrongEnemy extends GameObject {
    
    private Handler handler;
    private HUD hud;
    private GameObject player;
    private GameObject strongEnemy;
    
    private int Health = 12;
    private int timer = 275;
    private int small = 0;
    
    private boolean inRange = false;
    private boolean overLap = false;
    
    public StrongEnemy(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        player = handler.findObject(0, 0, ID.Player);
        strongEnemy = handler.findObject(x, y, ID.StrongEnemy);
        
        velX = 0;
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;

        collision();
        
        if(y < player.getY() + 52 && y > player.getY() - 8) inRange = true;
        if(timer > 0 && inRange == true){
                firingRate();
                timer--;
        }else{
            timer = 275;
            small = 0;
            inRange = false;
        }
        
        movementRate();
        
        //remove object if hp is 0
        if(Health <= 0){
            hud.setScore(hud.getScore() + 75);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.StrongEnemy);
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
            }else if(tempObject.getId() == ID.StrongEnemy && tempObject.getY() != y){
                if(getBounds().intersects(tempObject.getBounds()))overLap = true;
                else overLap = false;
            }
        }
    }
    
    private void firingRate(){
        //laser beam
            GameObject tempObject = null;
            if(timer >= 200) tempObject = handler.addAdjustedObject(x - 30,y,ID.EnemyBullet, handler,60,49,-10,0, hud);
            else if(timer > 175){
            small++;
            tempObject = handler.addAdjustedObject(x - 26,y+small,ID.EnemyBullet, handler,60,50 - small*2,-10,0, hud);
            }
    }
    
    private void movementRate(){
        float diffY = y - player.getY();
        float distance = (float) Math.sqrt((y-player.getY())*(y-player.getY()));
         
        if(overLap == true) velY *= -1;
        else if(timer == 275)velY = ((-1/distance) * diffY);
        else velY = 0;
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int)x,(int)y,base,height);
    }
}
