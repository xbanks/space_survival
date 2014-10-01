package misc;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;


public class HighlightButton extends Rectangle{
	
	
	private Rectangle outer;
	private Color inColor, bgColor;
	
	
	public HighlightButton(float x, float y, float width, float height) {
		super(x, y, width, height);
		outer = new Rectangle(x-3,y-3,width+6,height+6);
		
		bgColor = Color.transparent;
		inColor = new Color(0,255,15,.5f);
	}
	
	public void update(Input input){
		if(super.contains(input.getMouseX(), input.getMouseY())){
			bgColor = Color.blue;
		}
		else
			bgColor = Color.transparent;
	}
	
	public void setString(String str){
		
	}
	
	public void fill(Graphics g){
		g.setColor(bgColor);
		g.fill(outer);
		
		g.setColor(inColor);
		g.fill(this);
		
	}
	
	public boolean contains(float xp, float yp){
		return super.contains(xp, yp);
	}
	
	


}
