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
import java.lang.Math;
/**
 *
 * @author denise
 */
public class FastEnemy extends GameObject {
    
    private Handler handler;
    private HUD hud;
    
    private int Health = 3;
    private int timer = 30;
    private int timer2 = 5;
    private float radius = 2;
    
    private double angle = 1.0;
    
    public FastEnemy(int x, int y, ID id, Handler handler, int base, int height, HUD hud){
        super(x,y,id,base,height);
        
        this.handler = handler;
        this.hud = hud;
        
        velX = 0;
        velY = 0;
        
    }
    
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,base,height);
    }
    
    @Override
    public void tick(){
        x += velX;
        y += velY;
        
        handler.addObject(new Trail((int)x,(int)y,ID.Trail,Color.CYAN,16,16,0.01f, handler));
        
        collision();
        firingRate();
        movementRate();
        
        //remove object if hp is 0
        if(Health <= 0){
            hud.setScore(hud.getScore() + 35);
            GameObject tempObject = handler.findObject((int)x, (int)y, ID.FastEnemy);
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
        if(timer%2 == 1 || (timer > 0 && timer < 25)) timer--;
        else {
            handler.addAdjustedObject(x, y, ID.EnemyBullet, handler, 12, 12, -7, 0, hud);
            if(timer == 0) timer = 29;
            else timer --;
        }
    }
    
    private void movementRate(){
        if(timer2 > 0) timer2 --;
        else {
            
            if(base == 17 && height == 17){
                velX = -(float)Math.sin(angle)*radius;
                velY = -(float)Math.cos(angle)*radius;
            }else{
                velX = (float)Math.sin(angle)*radius;
                velY = (float)Math.cos(angle)*radius;
            }
            
            timer2 = 5;
            
            angle+=.4;
            if(angle > 360) angle = 1; 
        }
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.CYAN);
        g.fillRect((int)x,(int)y,base,height);
    }
}
