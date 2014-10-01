package characters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Character {
	
	protected float xPos;
	protected float yPos;
	protected Shape myShape = null;
	protected Shapes shape = null;
	protected float life;
	protected float lifePercent;
	protected ArrayList<Projectile> projectiles;
	protected Color color = Color.white;
	protected Rectangle bound = null;
	protected boolean Dead = false;
	
	public Character(float x, float y) throws SlickException{
		
		xPos = x;
		yPos = y;
		
	}
	
	public Character(float x, float y, Shapes s) throws SlickException{
		
		xPos = x;
		yPos = y;
		shape = s;
		setShape(s);

	}
	
	public void setShape(Shapes s){
		shape = s;
		if(s == Shapes.Circle){
			myShape = new Circle(xPos, yPos, 5f);
		}
		else{
			myShape = new Rectangle(xPos, yPos, 10f, 10f);
		}
		
	}
	
	public Shape getShape(){
		return myShape;
	}
	
	public void updateLife(float d){
		life+=d;
		if(this instanceof Player && life >= 100)
			life = 100;
		else if(life <= 0)
			life = 0;
		
		
	}
	
	public void update(int delta){
		
	}
	
	public void render(){
	}
	
	public boolean isDead(){
		if(life <= 0){
			Dead = true;
		}
		return Dead;
	}
	
	public void setBounds(Rectangle r){
		bound = r;
	}
}
