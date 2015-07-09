/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.geom.Ellipse2D;
/**
 *
 * @author denise
 */
public class PlayerBullet extends GameObject {
    
    private Handler handler;
    Random r = new Random();
    
    public PlayerBullet(int x, int y, ID id, Handler handler, int base, int height){
        super(x,y,id,base,height);
        
        this.handler = handler;
        
        velX = 5;
        velY = 0;
        
    }
    
    public Rectangle getBounds(){
        return null;
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        //if(y <= 0 || y >= Game.HEIGHT - 48) velY *= -1;
        //if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        
        if(y >= Game.HEIGHT || y <= 0 || x >= Game.WIDTH || x <= 0) handler.removeObject(this);

    }
    
    public void collision(){}
    
    @Override
    public void render(Graphics g){
        
        if(base == 14 && height == 14) g.setColor(Color.YELLOW);
        else g.setColor(Color.MAGENTA);
        Graphics2D g2d = (Graphics2D)g;
        Ellipse2D.Double circle = new Ellipse2D.Double((double)x, (double)y, base, height);
        g2d.fill(circle);
    }
}
