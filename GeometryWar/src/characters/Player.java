package characters;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Player extends Character{

	private float lifePercent;
	private ArrayList<Projectile> projectiles;
	private Circle shield = null;
	private float radius = 0;
	private boolean shieldStatus = false;
	private float preX, preY;
	private int shieldNumber = 3;
	private final Color player_color = Color.white;
	private int fire_speed = 50;
	
	public Player(float x, float y) throws SlickException{
		super(x,y);
		life = 100f;
		projectiles = new ArrayList<Projectile>();
		color = player_color;
	}
	
	public Player(float x, float y, Shapes s) throws SlickException{
		super(x,y,s);
		life = 100f;
		projectiles = new ArrayList<Projectile>();
		color = player_color;
	}
	
	public void moveY(float d) {
		preY = yPos;
		yPos += d;
	}
	
	public void moveX(float d){
		preX = xPos;
		xPos += d;
	}
	
	public float getX(){
		return this.myShape.getCenterX();
	}
	
	public float getY(){
		return this.myShape.getCenterY();
	}
	
	public void fire(float x, float y, int sprayNum){
		if(sprayNum == 1){
			Projectile p = new Projectile(xPos, yPos, x, y, 10f);
			p.setColor(Color.yellow);
			projectiles.add(p);
		}
		else{
			
			float angle = (float) Math.toDegrees(Math.atan2(xPos - x, yPos - y));
			for(int i = -sprayNum/2; i <= sprayNum/2; i++){
			
				Projectile p = new Projectile(xPos, yPos, angle + (i*3));
				p.setColor(Color.yellow);
				projectiles.add(p);
			}
		}
		
		
	}
	
	public float getAngle(){
		return (float) Math.toDegrees(Math.atan2(xPos - preX, yPos - preY));
	}
	
	public void explode(){
		for(int i = 1; i < 37; i++){
			Projectile p = new Projectile(xPos, yPos, i*10);
			p.setColor(Color.magenta);
			projectiles.add(p);

		}
	}
	
	public void updateShield(int delta){
		if(shield == null){
			shield = new Circle(xPos, yPos, radius);
		}
		else if(shield != null && shield.getRadius() <= bound.getWidth()/2){
			shield.setCenterX(xPos);
			shield.setCenterY(yPos);
			radius+=delta*.3f;
			if(radius >= 50){
				radius = 50;
			}
			shield.setRadius(radius);
			
		}
		else{
			shield = null;
			radius = 0;
			shieldStatus = false;
		}
		
		
	}
	
	public void setShieldStatus(boolean stat){
		shieldStatus = stat;
	}
	
	public boolean getShieldStatus(){
		return shieldStatus;
	}
	
	public void addShield(){
		shieldNumber++;
	}
	
	public void update(int delta){
		super.update(delta);
	}
	
	public void updateProjectiles(int d, ArrayList<Enemy> enemies){
		for(Projectile p : projectiles){
			p.update(d, enemies);
		}
		
		for(Enemy e: enemies){
			if(shield != null && shield.intersects(e.getShape())){
				e.updateLife(-50f);
				shieldNumber--;
				if(shieldNumber <= 0){
					shieldStatus = false;
					shield = null;
					radius = 0;
					shieldNumber = 3;
				}
			}
		}
	}
	
	public void render(Graphics g){
		
		for(Projectile p : projectiles){
			p.render(g);
		}
		
		for(int i = projectiles.size()-1; i >= 0; i--){
			if(!bound.contains(projectiles.get(i).getX(), projectiles.get(i).getY())){
				projectiles.remove(i);
			}
		}
		
		if(shieldStatus){
			g.setColor(Color.green);
			g.draw(shield);
		}
		
		myShape.setCenterX(xPos);
		myShape.setCenterY(yPos);
		g.setColor(color);
		g.fill(myShape);
		g.setColor(Color.yellow);
		g.drawRect(bound.getWidth()-125, 15, 100, 25);
		lifePercent = (life/100)*100f;
//		g.drawString("Shields: " + shieldNumber, 15, 100);
		g.fillRect(bound.getWidth()-125, 15, lifePercent, 25);
		
	}
	
	public int getFireSpeed()
	{
		return fire_speed;
	}
}
