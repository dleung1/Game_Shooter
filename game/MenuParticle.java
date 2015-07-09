/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Random;
/**
 *
 * @author denise
 */
public class MenuParticle extends GameObject {
    
    private Handler handler;
    
    Random r = new Random();
    
    private Color col;
    
    public MenuParticle(int x, int y, ID id, Handler handler, int base, int height){
        super(x,y,id,base,height);
        
        this.handler = handler;
        
        velX = (r.nextInt(7- -7) + -5);
        velY = (r.nextInt(7- -7) + -5);
        if(velX == 0) velX = 1;
        if(velY == 0) velY = 1;
        
        col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        if(y <= 0 || y >= Game.HEIGHT - 48) velY *= -1;
        if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        
        handler.addObject(new Trail((int)x,(int)y,ID.Trail,col,16,16,0.1f, handler));
    }
    
    public void collision(){}
    
    @Override
    public void render(Graphics g){
        
        g.setColor(col);
        g.fillRect((int)x,(int)y,base,height);
    }
}
