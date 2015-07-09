package game;

import java.util.Random;
import game.Game.STATE;
import game.Audio.Sound;

public class Spawn {
    
    private Handler handler;
    private HUD hud;
    private Game game;
    private Sound sound;
    
    private int pauseTimer = 100;
    
    private static Random r = new Random ();
    
    public Spawn(Handler handler, HUD hud, Game game, Sound sound){
        this.handler = handler;
        this.hud = hud;
        this.game = game;
        this.sound = sound;
    }
    
    public void tick(){
        
        if(noEnemies() == true){
            if(pauseTimer > 0) pauseTimer--;
            else{
                hud.setWave(hud.getWave() + 1);

                if(hud.getLevel() == 1){
                    if(hud.getWave() == 1){
                        handler.addObject(new BasicEnemy(600, 350, ID.BasicEnemy, handler,16,16,hud));
                        handler.addObject(new BasicEnemy(600, 200, ID.BasicEnemy, handler,16,16,hud));
                        handler.addObject(new BasicEnemy(600, 50, ID.BasicEnemy, handler,16,16,hud));
                        handler.addObject(new BasicEnemy(500, 275, ID.BasicEnemy, handler,16,16,hud));
                        handler.addObject(new BasicEnemy(500, 125, ID.BasicEnemy, handler,16,16,hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 2){
                        handler.addObject(new FastEnemy(550, 350, ID.FastEnemy, handler,16,16, hud));
                        handler.addAdjustedObject(475, 180, ID.FastEnemy, handler, 17, 17, 0, 0, hud);
                        handler.addObject(new FastEnemy(550, 100, ID.FastEnemy, handler,16,16, hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 3){
                        handler.addObject(new BigEnemy(600, 100, ID.BigEnemy, handler,64,64, hud));
                        handler.addObject(new BigEnemy(600, 310, ID.BigEnemy, handler,64,64, hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 4){
                        sound.BACKGROUND.stop();
                        handler.addObject(new EnemyBoss(800,(Game.HEIGHT/2)-70, ID.EnemyBoss, handler,128,128, hud, sound));
                        pauseTimer = 200;
                    }
                    else if(hud.getWave() == 5){
                        sound.BACKGROUND.loop();
                        hud.setLevel(hud.getLevel() + 1);
                        hud.setWave(0);
                    }
                }else if(hud.getLevel() == 2){
                    if(hud.getWave() == 1){
                        handler.addObject(new BasicEnemy2(300, 360, ID.BasicEnemy2, handler,16,16,hud));
                        handler.addObject(new BasicEnemy2(350, 185, ID.BasicEnemy2, handler,16,16,hud));
                        handler.addObject(new BasicEnemy2(400, 10, ID.BasicEnemy2, handler,16,16,hud));
                        handler.addObject(new BasicEnemy(600, 350, ID.BasicEnemy, handler,16,16,hud));
                        handler.addObject(new BasicEnemy(600, 50, ID.BasicEnemy, handler,16,16,hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 2){
                        randPowerUp();
                        handler.addObject(new BigEnemy2(600, 30, ID.BigEnemy2, handler,64,64, hud));
                        handler.addObject(new FastEnemy(550, 215, ID.FastEnemy, handler,16,16, hud));
                        handler.addObject(new BigEnemy2(600, 330, ID.BigEnemy2, handler,64,64, hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 3){
                        handler.addObject(new SmartEnemy2(600, 350, ID.SmartEnemy2, handler,16,16, hud));
                        handler.addObject(new SmartEnemy3(600, 150, ID.SmartEnemy3, handler,16,16, hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 4){
                        handler.addObject(new StrongEnemy(580, 350, ID.StrongEnemy, handler,48,48, hud));
                        handler.addObject(new StrongEnemy(580, 100, ID.StrongEnemy, handler,48,48, hud));
                        pauseTimer = 100;
                    }else if(hud.getWave() == 5){
                        randPowerUp();
                        for(int i = 0; i < r.nextInt(6); i++){
                            randEnemyGen();
                        }
                        pauseTimer = 100;
                    }else if(hud.getWave() == 6){
                        for(int i = 0; i < r.nextInt(7); i++){
                            randEnemyGen();
                        }
                        pauseTimer = 100;
                    }else if(hud.getWave() == 7){
                        for(int i = 0; i < r.nextInt(10); i++){
                            randEnemyGen();
                        }
                        pauseTimer = 100;
                    }else if(hud.getWave() == 8){
                        sound.BACKGROUND.stop();
                        handler.addObject(new EnemyBoss2(800,0, ID.EnemyBoss2, handler,128,450, hud, game, sound));
                        pauseTimer = 200;
                    }else if(hud.getWave() == 9){
                        hud.setLevel(hud.getLevel() + 1);
                        hud.setWave(0);
                    }
                }else if(hud.getLevel() == 3){
                    sound.WIN.play();
                    game.gameState = STATE.Win;
                }
            }
        }
    }
    
    public boolean noEnemies(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.BasicEnemy
                    || tempObject.getId() == ID.BasicEnemy2
                    || tempObject.getId() == ID.FastEnemy
                    || tempObject.getId() == ID.BigEnemy 
                    || tempObject.getId() == ID.BigEnemy2
                    || tempObject.getId() == ID.SmartEnemy 
                    || tempObject.getId() == ID.SmartEnemy2 
                    || tempObject.getId() == ID.SmartEnemy3 
                    || tempObject.getId() == ID.StrongEnemy
                    || tempObject.getId() == ID.EnemyBoss 
                    || tempObject.getId() == ID.EnemyBoss2 
                    || tempObject.getId() == ID.EnemyBullet
                    || game.gameState == STATE.Win){
                return false;
            }
        }
        return true;
    }
    
    public void randEnemyGen(){
        switch(r.nextInt(9)){
            case 0: handler.addObject(new BasicEnemy(game.randInt(300,560), game.randInt(10,360), ID.BasicEnemy, handler,16,16,hud));
                break;
            case 1: handler.addObject(new BasicEnemy2(game.randInt(300,560), game.randInt(10,360), ID.BasicEnemy2, handler,16,16,hud));
                break;
            case 2: handler.addObject(new FastEnemy(game.randInt(300,560), game.randInt(10,360), ID.FastEnemy, handler,16,16, hud));
                break;
            case 3: handler.addObject(new BigEnemy(game.randInt(300,560), game.randInt(10,360), ID.BigEnemy, handler,64,64, hud));
                break;
            case 4: handler.addObject(new BigEnemy2(game.randInt(300,560), game.randInt(10,360), ID.BigEnemy2, handler,64,64, hud));
                break;
            case 5: handler.addObject(new SmartEnemy(game.randInt(300,560), game.randInt(10,360), ID.SmartEnemy, handler,16,16,hud));
                break;
            case 6: handler.addObject(new SmartEnemy2(game.randInt(300,560), game.randInt(10,360), ID.SmartEnemy2, handler,16,16, hud));
                break;
            case 7: handler.addObject(new SmartEnemy3(game.randInt(300,560), game.randInt(10,360), ID.SmartEnemy3, handler,16,16, hud));
                break;
            case 8: handler.addObject(new StrongEnemy(game.randInt(300,560), game.randInt(10,360), ID.StrongEnemy, handler,48,48, hud));
                break;
        }
    }
    
    public void randPowerUp(){
        switch(r.nextInt(4)){
            case 0: handler.addObject(new TwinGuns(400, 250, ID.TwinGuns, handler, 0, 0, hud));
                break;
            case 1: handler.addObject(new RapidFire(400, 250, ID.RapidFire, handler, 0, 0, hud));
                break;
            case 2: handler.addObject(new ExtraLife(400, 250, ID.ExtraLife, handler, 0, 0, hud));
                break;
            case 3: handler.addObject(new Invincible(400, 250, ID.Invincible, handler, 0, 0, hud));
                break;
        }
    }
}
