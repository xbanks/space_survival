package characters;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Enemy extends Character {
	
	protected Player target = null;
	protected int speed = 1;
	
	protected File explodeXML = null;
	protected File followXML = null;
	
	protected ConfigurableEmitter follow = null;
	protected ConfigurableEmitter explode = null;
	
	protected int pulse;
	protected boolean change;
	
	protected float startX, startY;

	protected Polygon poly;
	public Enemy(float x, float y, Player p) throws SlickException{
		super(x,y);
		target = p;
		life = 50;
		
		startX = x;
		startY = y;
		
		poly = new Polygon();
		explodeXML = new File("res/particleFiles/explosion.xml");
		followXML = new File("res/particleFiles/enemyFollower.xml");
		
		try{
			follow = ParticleIO.loadEmitter(followXML);
			explode = ParticleIO.loadEmitter(explodeXML);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		follow.setEnabled(true);
		explode.setEnabled(false);
		
		pulse = 0;
		change = false;
	}
	
	public Enemy(float x, float y, Player p ,Shapes s) throws SlickException{
		super(x,y,s);
		target = p;
		
		startX = x;
		startY = y;
		
		poly = new Polygon();
		explodeXML = new File("res/particleFiles/explosion.xml");
		followXML = new File("res/particleFiles/enemyFollower.xml");
		
		try{
			follow = ParticleIO.loadEmitter(followXML);
			explode = ParticleIO.loadEmitter(explodeXML);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		follow.setEnabled(true);
		explode.setEnabled(false);
		
		pulse = 0;
		change = false;
	}
	
	public void update(int d){
		super.update(d);
		if(!this.isDead()){
			float delta = d*.1f;
			float angle = (float) (((float) Math.toDegrees(Math.atan2(xPos - target.getX(), yPos - target.getY())) + 90) * (Math.PI/180));
		
			float dx = (float) (delta*speed*Math.cos(angle));
			float dy = (float) (delta*speed*Math.sin(angle));
		
			xPos = xPos + dx;
			yPos = yPos - dy;
			myShape.setCenterX(xPos);
			myShape.setCenterY(yPos);
			follow.setPosition(xPos, yPos, false);
			explode.setPosition(xPos, yPos, false);
		}
		
		if(change){
			if(myShape instanceof Circle){
				((Circle) myShape).setRadius(((Circle) myShape).getRadius()+.1f);
			}
			
			if(myShape instanceof Rectangle){
				((Rectangle) myShape).setHeight(myShape.getHeight()+.1f);
				((Rectangle) myShape).setWidth(myShape.getWidth()+.1f);
			}
			
			pulse++;
		}
		else{
			if(myShape instanceof Circle){
				((Circle) myShape).setRadius(((Circle) myShape).getRadius()-.1f);
			}
			
			if(myShape instanceof Rectangle){
				((Rectangle) myShape).setHeight(myShape.getHeight()-.1f);
				((Rectangle) myShape).setWidth(myShape.getWidth()-.1f);
			}
			
			pulse--;
		}
		
		if(pulse >= 5)
			change = false;
		if(pulse <= 0)
			change = true;
		poly.addPoint(xPos, yPos);
	}
	
	public void render(Graphics g){
		super.render();
		if(!this.isDead()){
			g.setColor(color);
			g.fill(myShape);
		}
		
		//g.drawLine(startX, startY, xPos, yPos);
	}
	
	public float getX(){
		return xPos;
	}
	
	public float getY(){
		return yPos;
	}
	
	public void addEmitters(ParticleSystem system){
		
		system.addEmitter(follow);
		system.addEmitter(explode);
		
	}
	
	public boolean isDead(){
		boolean re = super.isDead();
		if(re){
			follow.setEnabled(false);
			explode.setEnabled(true);
		}
		return re;
	}
	
	public void changeEffect(){
		follow.setEnabled(false);
		explode.setEnabled(true);
	}

}
