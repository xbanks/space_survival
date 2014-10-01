package misc;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class PowerUp {
	
	public static final int WEAPON_UPGRADE1 = 0;
	public static final int WEAPON_UPGRADE2 = 1;
	public static final int SHIELD = 3;
	public static final int EXPLOSION = 4;
	
	private int pType;
	private float x, y;
	private int angle, time;
	
	private Circle bound = null;
	
	public PowerUp(int pType, GameContainer container){
		Random random = new Random();
		this.pType = pType;
		x = random.nextInt(container.getWidth()-30);
		y = random.nextInt(container.getHeight()-30);
		Circle bound = new Circle(x, y, 30);
		bound.setCenterX(x);
		bound.setCenterY(y);
		time = 0;
		angle = 0;
	}
	
	public void update(int delta){
		
		time+=delta*.3;
		if(time >= 300){
			time = 0;
			angle++;
			if(angle > 360)
				angle = 0;
		}
		
	}
	
	public void render(Graphics g){
		
		switch(pType){
		
		case WEAPON_UPGRADE1:
			g.drawString("Fire Rate", x, y);
			break;
		case WEAPON_UPGRADE2:
			g.drawString("Spread", x, y);
			break;
		}
		
		//g.rotate(x, y, angle);
		
	}

}
