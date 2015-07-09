package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import game.Game.STATE;

public class KeyInput extends KeyAdapter {
    private Handler handler;
    private Option option;
    private Menu menu;
    private Game game;
    
    private boolean[] keyDown = new boolean [7];
    
    public KeyInput(Handler handler, Option option, Menu menu, Game game){
        this.handler = handler;
        this.option = option;
        this.menu = menu;
        this.game = game;
        
        keyDown[0]=false;
        keyDown[1]=false;
        keyDown[2]=false;
        keyDown[3]=false;
        keyDown[4]=false;
        keyDown[5]=false;
        keyDown[6]=false;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        for(int i = 0; i <handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.Player){
                //key events for player 1
                
                if(key == option.getUP()) { tempObject.setVelY(-5); keyDown[0] = true; }
                if(key == option.getDOWN()) { tempObject.setVelY(5); keyDown[1] = true; }
                if(key == option.getRIGHT()) { tempObject.setVelX(5); keyDown[2] = true; }
                if(key == option.getLEFT()) { tempObject.setVelX(-5); keyDown[3] = true; }
                if(key == option.getATTACK()) { tempObject.setBase(1); keyDown[4] = true; }
                if(key == option.getSPECIAL()) { tempObject.setHeight(1); keyDown[5] = true; }
                if(key == option.getPAUSE()) { menu.pause = !menu.pause; keyDown[6] = true;}
            }
        }
        
        if(key == KeyEvent.VK_ESCAPE) System.exit(1);
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        for(int i = 0; i <handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.Player){
                //key events for player 1
                
                if(key == option.getUP()) keyDown[0] = false;
                if(key == option.getDOWN()) keyDown[1] = false;
                if(key == option.getRIGHT()) keyDown[2] = false;
                if(key == option.getLEFT()) keyDown[3] = false;
                if(key == option.getATTACK()) { tempObject.setBase(0); keyDown[4] = false; }
                if(key == option.getSPECIAL()) { tempObject.setHeight(0); keyDown[5] = false; } 
                if(key == option.getPAUSE()) {
                    if(menu.pause == true)game.gameState = STATE.Pause;
                    else if(menu.pause == false) game.gameState = STATE.Game;
                    keyDown[6] = false; 
                }
                
                //vertical movement
                if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
                //horizontal movement
                if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
            }
        }
    }
}
