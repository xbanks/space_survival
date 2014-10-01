package characters;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Projectile {

	private Shape myShape = null;
	private float xPos;
	private float yPos;
	private float angle;
	private float damage;
	private Character target = null;
	private Color fireColor = null;
	
	public Projectile(Character p, float x, float y, float a){
		xPos = x;
		yPos = y;
		damage = -5f;
		target = p;
		angle = (float) (((float) a + 90) * (Math.PI/180));
		myShape = new Circle(x,y,2f);
		
		fireColor = Color.green;
	}
	
	public Projectile(float x, float y, float a){
		xPos = x;
		yPos = y;
		damage = -5f;
		angle = (float) (((float) a + 90) * (Math.PI/180));
		myShape = new Circle(x,y,2f);
		
		fireColor = Color.green;
	}
	
	public Projectile(Character p, float x, float y, float tx, float ty){
		xPos = x;
		yPos = y;
		damage = -5f;
		target = p;
		angle = (float) (((float) Math.toDegrees(Math.atan2(xPos - tx, yPos - ty)) + 90) * (Math.PI/180));
		myShape = new Circle(x,y,2f);
		fireColor = Color.green;
	}
	
	public Projectile(Character p, float x, float y, float tx, float ty, float d){
		xPos = x;
		yPos = y;
		damage = -d;
		target = p;
		angle = (float) (((float) Math.toDegrees(Math.atan2(xPos - tx, yPos - ty)) + 90) * (Math.PI/180));
		myShape = new Circle(x,y,2f);
		fireColor = Color.green;
	}
	
	public Projectile(float x, float y, float tx, float ty){
		xPos = x;
		yPos = y;
		damage = -5f;
		angle = (float) (((float) Math.toDegrees(Math.atan2(xPos - tx, yPos - ty)) + 90) * (Math.PI/180));
		myShape = new Circle(x,y,2f);
		fireColor = Color.green;
	}
	
	public Projectile(float x, float y, float tx, float ty, float d){
		xPos = x;
		yPos = y;
		damage = -d;
		
		angle = (float) (((float) Math.toDegrees(Math.atan2(xPos - tx, yPos - ty)) + 90) * (Math.PI/180));
		
		System.out.println((float) Math.toDegrees(Math.atan2(xPos - tx, yPos - ty)));
		myShape = new Circle(x,y,2f);
		fireColor = Color.green;
	}
	
	public float getX(){
		return xPos;
	}
	
	public float getY(){
		return yPos;
	}
	
	public Shape getShape(){
		return myShape;
	}
	
	public void setColor(Color color){
		fireColor = color;
	}
	
	public void update(int d){
		
		float delta = d*.5f;
				
		float dx = (float) (delta*Math.cos(angle));
		float dy = (float) (delta*Math.sin(angle));
		
		
		xPos = xPos + dx;
		yPos = yPos - dy;
		myShape.setCenterX(xPos);
		myShape.setCenterY(yPos);
		
		if(myShape.intersects(target.getShape())){
			target.updateLife(damage);
		}
	}
	
	public void update(int d, ArrayList<Enemy> enemies){
		
		if(myShape != null){
			float delta = d*.5f;
					
			float dx = (float) (delta*Math.cos(angle));
			float dy = (float) (delta*Math.sin(angle));
			
			xPos = xPos + dx;
			yPos = yPos - dy;
			myShape.setCenterX(xPos);
			myShape.setCenterY(yPos);
			
			
			for(Character c : enemies){
				if(myShape != null && myShape.intersects(c.getShape())){
					c.updateLife(damage);
					myShape = null;
				}
			}
		}
	}
	
	public void render(Graphics g){
		g.setColor(fireColor);
		if(myShape != null)
			g.fill(myShape);
	}
	
	
}
