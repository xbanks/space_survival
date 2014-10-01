package characters;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class StrongEnemy extends Enemy{
	
	private Shape blastArea = null;
	private ArrayList<Projectile> projectiles;
	private int time;
	
	
	public StrongEnemy(float x, float y, Player p) throws SlickException {
		super(x, y, p);
		myShape = new Rectangle(xPos, yPos, 20f, 20f);
		color = Color.green;
		life = 200;
		blastArea = new Circle(xPos, yPos, 300);
		time = 0;
		projectiles = new ArrayList<Projectile>();
		
	}
	
	public void update(int d){
		super.update(d);

		
		blastArea.setCenterX(xPos);
		blastArea.setCenterY(yPos);
		
		if(blastArea.intersects(target.getShape())){
			time += d;
			if(time >= 1000){
				time = 0;
				for(int i = 1; i < 37; i++){
					Projectile p = new Projectile(target ,xPos, yPos, i*10);
					p.setColor(Color.yellow);
					projectiles.add(p);

				}
			}
		}
		
		for(Projectile p: projectiles){
			p.update(d);
		}
	}

	
	public void render(Graphics g){
		for(Projectile p : projectiles){
			p.render(g);
		}
		
		Color boundColor = new Color(255,48,48);
		g.setColor(Color.red);
		g.fill(myShape);
		g.setColor(boundColor);
		//g.draw(blastArea);
		
	}
	
}
