/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.lang.Math;
import java.util.Random;
import game.Audio.Sound;

public class Game extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1550691097;
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    
    private static Random r;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private Option option;
    private Sound sound;

    public enum STATE {
        Menu,
        Options,
        Game,
        Pause,
        End,
        Win
    };
    
    public static STATE gameState = STATE.Menu;
    
    public Game(){
        handler = new Handler();
        hud = new HUD();
        sound = new Sound();
        option = new Option(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_SPACE, KeyEvent.VK_M, KeyEvent.VK_P);
        menu = new Menu(this, handler, hud, option, sound);
        
        this.addKeyListener(new KeyInput(handler, option, menu, this));
        this.addMouseListener(menu);
        
        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);
        
        spawner = new Spawn(handler,hud,this,sound);
        r = new Random();
        
        if(gameState == STATE.Game)handler.addObject(new Player((WIDTH/2)-300, HEIGHT/2-32, ID.Player, handler, 0, 0, sound));
        else{
            for(int i = 0; i < 10; i++){
                handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.MenuParticle, handler, 16, 16));
            }
        }
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        sound.START.play();
    }
    
    public synchronized void stop(){
        try{
            sound.BACKGROUND.stop();
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick(){
        handler.tick();
        if(gameState == STATE.Game){
           hud.tick();
           spawner.tick(); 
           
           if(HUD.HEALTH < 0){
               HUD.HEALTH = 3;
               gameState = STATE.End;
               handler.object.clear();
               for(int i = 0; i < 10; i++){
                    handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.MenuParticle, handler, 16, 16));
               }
               sound.END.play();
           }
           
        }else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Win || gameState == STATE.Pause){
            menu.tick();
        }
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH, HEIGHT);
        
        handler.render(g);
        
        if(gameState == STATE.Game){
            hud.render(g);
        }else if(gameState == STATE.Menu || gameState == STATE.Options || gameState == STATE.End || gameState == STATE.Win || gameState == STATE.Pause){
            menu.render(g);
        }
        
        g.dispose();
        bs.show();
    }
    
    public static float clamp(float var, float min, float max){
        if(var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        else
            return var;
    }
    
    public static int randInt(int min, int max) {
        return r.nextInt((max - min) + 1) + min;
    }
    
    public static void main(String[] args) {
        new Game();
    }
    
}

