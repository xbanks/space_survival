package gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class PauseHandler {
	
	StateBasedGame sbg = null;
	GameContainer container = null;
	Rectangle rect = null;
	Rectangle bg = null;
	
	public PauseHandler(StateBasedGame sbg, GameContainer container){
		this.sbg = sbg;
		this.container = container;
		
		float width = container.getWidth() * .25f;
		float height = container.getHeight() * .15f;
		
		float x = (container.getWidth() - width)/2f;
		float y = (container.getHeight() - height)/2f;
		
		rect = new Rectangle(x,y,width,height);
		
		width = container.getWidth() * .27f;
		height = container.getHeight() * .17f;
		
		x = (container.getWidth() - width)/2f;
		y = (container.getHeight() - height)/2f;
		
		bg = new Rectangle(x,y,width,height);
	}
	
	public void update(int delta){
		
	}
	
	public void render(Graphics g){
		
		
		g.setColor(new Color(0,0,255, .5f));
		g.fill(bg);
		g.setColor(new Color(0,255,15,.5f));
		g.fill(rect);
		g.setColor(Color.black);
		g.drawString("Paused", rect.getMinX()+5, rect.getMinY() + 5);
		g.drawString("[P] - Return to game", rect.getMinX()+5, rect.getMinY() + 25);
		g.drawString("[ESC] - Return to menu", rect.getMinX()+5, rect.getMinY() + 45);
		g.drawString("[T] - Toggle particles", rect.getMinX()+5, rect.getMinY() + 65);
		
	}

}
