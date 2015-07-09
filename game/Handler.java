package game;

import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {
    LinkedList<GameObject> object = new LinkedList<GameObject>();

    public void tick(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            
            tempObject.tick();
        }
    }
    
    public void render(Graphics g){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            
            tempObject.render(g);
        }
    }
    
    public void clearEnemys(){
        object.clear();
    }
    
    public GameObject addObject(GameObject object){
        this.object.add(object);
        return object;
    }
    
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
    
    public GameObject findObject(int x, int y, ID id){
        for (GameObject tempObject : object) {
            if(x == 0 && y == 0 && id == ID.Player){
                return tempObject;
            }
            else if((int)tempObject.x == x && (int)tempObject.y == y && tempObject.id == id){
                return tempObject;
            }
        }
        return null;
    }
    
    public GameObject addAdjustedObject(float x, float y, ID id, Handler handler, int base, int height, int VelX, int VelY, HUD hud){
        GameObject tempObject = null;
        
        if(id == ID.EnemyBullet) tempObject = handler.addObject(new EnemyBullet((int)x, (int)y, id, handler, base, height));
        else if(id == ID.BasicEnemy) tempObject = handler.addObject(new BasicEnemy((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.BasicEnemy2) tempObject = handler.addObject(new BasicEnemy2((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.FastEnemy) tempObject = handler.addObject(new FastEnemy((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.BigEnemy) tempObject = handler.addObject(new BigEnemy((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.BigEnemy2) tempObject = handler.addObject(new BigEnemy2((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.SmartEnemy) tempObject = handler.addObject(new SmartEnemy((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.SmartEnemy2) tempObject = handler.addObject(new SmartEnemy2((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.SmartEnemy3) tempObject = handler.addObject(new SmartEnemy3((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.StrongEnemy) tempObject = handler.addObject(new StrongEnemy((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.SpecialBeam) tempObject = handler.addObject(new SpecialBeam((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.TwinGuns) tempObject = handler.addObject(new TwinGuns((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.RapidFire) tempObject = handler.addObject(new RapidFire((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.ExtraLife) tempObject = handler.addObject(new ExtraLife((int)x, (int)y, id, handler, base, height, hud));
        else if(id == ID.Invincible) tempObject = handler.addObject(new Invincible((int)x, (int)y, id, handler, base, height, hud));
        
        tempObject.setVelX(VelX);
        tempObject.setVelY(VelY);
        
        return tempObject;
    }
   

}
