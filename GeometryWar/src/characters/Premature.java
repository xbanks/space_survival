package characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class Premature extends Enemy{

	
	public Premature(float x, float y, Player p) throws SlickException {
		super(x, y, p);
		shape = Shapes.Circle;
		myShape =  new Circle(xPos, yPos, 5f);
		speed = 2;
		color = Color.red;
		life = 20;
		// TODO Auto-generated constructor stub
	}
	
	public void update(int d){
		super.update(d);
		
		if(this.getShape().intersects(target.getShape())){
			target.updateLife(-15);
			this.life = 0;
		}
	}
	

}
