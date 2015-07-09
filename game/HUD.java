package game;

import java.awt.Graphics;
import java.awt.Color;

public class HUD {
    
    public static float HEALTH = 3;
    
    public static int score = 0;
    private static int npoints = 4;
    
    //private float greenValue = 255;
    private int level = 1;
    private int wave = 0;
    
    public void tick(){
        //greenValue = (int) Game.clamp(greenValue, 0, 255);
        
        //greenValue = HEALTH*2;
        
    }
    
    public void render(Graphics g){
        /*g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, (int)greenValue, 0));
        g.fillRect(15, 15, (int)HEALTH * 2, 32);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 32);*/
        for(int i = 0; i < HEALTH; i++){
            int xpoints[] = {25+(i*25), 25+(i*25), 40+(i*25), 25+(i*25)};
            int ypoints[] = {20, 40, 30, 20};

            g.setColor(Color.white);
            g.fillPolygon(xpoints, ypoints, npoints);
            g.drawPolygon(xpoints, ypoints, npoints);
        }
        
        g.setColor(Color.white);
        g.drawString("Score: " + score, 500, 20);
        g.drawString("Level: " + level, 425, 20);
        
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public static int getScore(){
        return score;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int level){
        this.level = level;
    }
    
    public int getWave(){
        return wave;
    }
    
    public void setWave(int wave){
        this.wave = wave;
    }
    
    public void hudReset(HUD hud){
        hud.setLevel(1);
        hud.setScore(0);
        hud.setWave(0);
    }
}
