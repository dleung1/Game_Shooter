package game;

import static game.Game.HEIGHT;
import static game.Game.WIDTH;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import game.Game.STATE;
import game.Audio.Sound;

public class Menu extends MouseAdapter{
    
    private Game game;
    private Handler handler;
    private Random r = new Random();
    private HUD hud;
    private Option option;
    private Sound sound;
    
    private int timer = 10;
    
    protected boolean pause = false;
    
    public Menu(Game game, Handler handler, HUD hud, Option option, Sound sound){
        this.game = game;
        this.hud = hud;
        this.handler = handler;
        this.option = option;
        this.sound = sound;
    }
    
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        
        if(game.gameState == STATE.Menu){
            //play button
            if(mouseOver(mx, my, 210, 150, 200, 64)){
                game.gameState = STATE.Game;
                handler.clearEnemys();
                handler.addObject(new Player((Game.WIDTH/2)-300, Game.HEIGHT/2-32, ID.Player, handler, 0, 0, sound));
                sound.BACKGROUND.loop();
            }

            //option button
            if(mouseOver(mx, my, 210, 250, 200, 64)){
                sound.OPTION.play();
                game.gameState = STATE.Menu;
                option.setOption();  
            }
             //quit button
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                System.exit(1);
            }
        }
      /*  
        //pause button
        if(game.gameState == STATE.Pause){
            /*while(true){
                if(pause == false){
                    game.gameState = STATE.Game;
                    return;
                } 
            }
        }*/
        //try again button for end
        if(game.gameState == STATE.End){
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                game.gameState = STATE.Game;
                hud.hudReset(hud);
                handler.clearEnemys();
                handler.addObject(new Player((Game.WIDTH/2)-300, Game.HEIGHT/2-32, ID.Player, handler, 0, 0, sound));
                Sound.BACKGROUND.loop();
            }
        }
        
        if(game.gameState == STATE.Win){
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                game.gameState = STATE.Game;
                hud.hudReset(hud);
                handler.clearEnemys();
                handler.addObject(new Player((Game.WIDTH/2)-300, Game.HEIGHT/2-32, ID.Player, handler, 0, 0, sound));
                sound.BACKGROUND.loop();
            }
        }
    }
    
    public void mouseReleased(MouseEvent e){
        
    }
    
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx >= x && mx < x + width){
            if(my >= y && my < y + height){
                return true;
            }
        }
        return false;
    }
    
    public void tick(){
    }
    
    public void render(Graphics g){
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        Font fnt3 = new Font("arial", 1, 20);
        Font fnt4 = new Font("arial", 1,15);
        
        if(game.gameState == STATE.Menu){
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 270, 190);

            g.setColor(Color.white);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Options", 250, 290);

            g.setColor(Color.white);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 270, 390);
        }else if(game.gameState == STATE.Pause){
            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("PAUSE", 240, 70);
        }else if(game.gameState == STATE.End){
            sound.BACKGROUND.stop();
            sound.BOSS_MIDDLE.stop();
            
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 180, 70);
            
            g.setFont(fnt3);
            g.drawString("You lost with a score of: " + hud.getScore(), 175, 200);
            
            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try Again", 245, 390);
        }else if(game.gameState == STATE.Win){
            sound.BACKGROUND.stop();
            sound.BOSS_MIDDLE.stop();
            
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("You Win", 205, 70);
            
            g.setFont(fnt3);
            g.drawString("You Won with a score of: " + hud.getScore(), 175, 200);
            
            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Play Again?", 228, 390);
        }
        
    }
    
}
