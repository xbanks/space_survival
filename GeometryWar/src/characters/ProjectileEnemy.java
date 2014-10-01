package characters;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class ProjectileEnemy extends Enemy {

	private float time;
	private ArrayList<Projectile> projectiles;
	
	public ProjectileEnemy(float x, float y, Player p) throws SlickException {
		super(x, y, p);
		myShape = new Rectangle(xPos, yPos, 10f, 10f);
		time = 0f;
		projectiles = new ArrayList<Projectile>();
		color = Color.orange;
		life = 50;
	}
	
	public void update(int d){
		super.update(d);
		time+=d;
		
		if(time >= 500){
			Projectile p = new Projectile(target ,xPos, yPos, target.getX(), target.getY(), 5f);
			p.setColor(Color.red);
			projectiles.add(p);
			time = 0;
		}
		
		for(Projectile p : projectiles){
			p.update(d);
		}
		
		if(this.getShape().intersects(target.getShape())){
			target.updateLife(-25);
			this.life = 0;
		}
	}

	
	public void render(Graphics g){
		super.render(g);
		g.setColor(color);
		g.fill(myShape);
		for(Projectile p : projectiles){
			p.render(g);
		}
		for(int i = projectiles.size()-1; i >= 0; i--){
			if(!bound.contains(projectiles.get(i).getX(), projectiles.get(i).getY())){
				projectiles.remove(i);
			}
		}
		
	}
	

}
