package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Polygon;

public class TwinGuns extends GameObject {
    
    private Handler handler;
    private HUD hud;
    
    private int npoints = 6;
    private int TTL = 1500;
    
    public TwinGuns(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;

        velX = -3;
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x-13, (int)y-1, 26, 28);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        movementRate();
        
        if(TTL > 0) TTL--;
        else{
            GameObject tempObject = handler.findObject((int)x, (int)y, id);
            handler.removeObject(tempObject);
        }
    }
    
    public void movementRate(){
        if(y < 10 || y >= Game.HEIGHT - 50){
            if(velX == 0)velX += 1;
            velY *= -1;
        }
        if(x < 10 || x >= Game.WIDTH - 20){
            if(velY == 0)velY += 1;
            velX *= -1;
        }
    }
    
    public void collision(){
    }
     
    @Override
    public void render(Graphics g){
        int xpoints[] = {(int)x, (int)x-7, (int)x+11, (int)x-11, (int)x+7, (int)x};
        int ypoints[] = {(int)y, (int)y+25, (int)y+10, (int)y+10, (int)y+25, (int)y};
        
        g.setColor(Color.RED);
        
        g.fillPolygon(xpoints, ypoints, npoints);
        g.drawPolygon(xpoints, ypoints, npoints);
        
        Graphics2D g2d = (Graphics2D)g;
        Ellipse2D.Double circle = new Ellipse2D.Double((double)x-12.85, (double)y-1, 26, 28);
        g2d.draw(circle);
    }
}
