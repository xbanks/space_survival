package gameStates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

import misc.HighScoreIO;

public class StartState extends BasicGameState{

	protected final Transition fade_out = new FadeOutTransition();
	protected final Transition fade_in = new FadeInTransition();
	
	private ParticleSystem particle_system = null;
	private ConfigurableEmitter bg_emitter = null;
	private ArrayList<Rectangle> options = null;
	private Rectangle highlight = null;
	private Color highlight_color = Color.transparent;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		SetupParticleSystem();
		CreateButtons(container);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException 
	{
		// TODO Auto-generated method stub
		particle_system.update(delta);
		
		Input input = container.getInput();
		
		highlight_color = Color.transparent;
		//	Toggles Highlight effect
		for(Rectangle r : options)
			if(r.contains(input.getMouseX(), input.getMouseY())){
				highlight.setLocation(r.getX()-3, r.getY()-3);
				highlight_color = Color.blue;
			}
		
		//	Changes state when a certain button is pressed	
		for(Rectangle r : options)
			if(input.isMouseButtonDown(0))
				if(r.contains(input.getMouseX(), input.getMouseY()))
				{
					if(options.indexOf(r)+1 == 1)
						sbg.getState(1).init(container, sbg);
					sbg.enterState(options.indexOf(r)+1, new FadeOutTransition(), new FadeInTransition());
				}
		
		//	{END OF FUNCTION}		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		particle_system.render();
		
		g.setColor(highlight_color);
		g.fill(highlight);

		//	Displays Option Buttons
		g.setColor(new Color(0,255,15,.5f));
		g.fill(options.get(0));
		g.fill(options.get(1));
		//	g.fill(options.get(2));
		
		//	Displays Text on Option Buttons
		g.setColor(Color.green);
		g.drawString("Start Game", options.get(0).getCenterX()-50, options.get(0).getCenterY()-10);
		g.drawString("Instructions", options.get(1).getCenterX()-50, options.get(1).getCenterY()-10);
		//	g.drawString("Options", options.get(2).getCenterX()-50, options.get(2).getCenterY()-10);
		
		
	}

	//	Setting up particle effects for start screen
	private void SetupParticleSystem() throws SlickException
	{
		Image Ptcl = new Image("res/images/particle.png", false);
		particle_system = new ParticleSystem(Ptcl, 1500);
		File xml = new File("res/particleFiles/flame.xml");
		try {
			bg_emitter = ParticleIO.loadEmitter(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		particle_system.addEmitter(bg_emitter);
		bg_emitter.setPosition(0, 0);
	}
	
	//	Creating Options Buttons
	private void CreateButtons(GameContainer container)
	{
		options = new ArrayList<Rectangle>();
		options.add(new Rectangle(container.getWidth()/2f-100, container.getHeight()/2f-200, 200,75));
		options.add(new Rectangle(container.getWidth()/2f-100, container.getHeight()/2f-100, 200,75));
		//	options.add(new Rectangle(container.getWidth()/2f-100, container.getHeight()/2f, 200,75));
		
		//	The Highlight effect for hovering over a button
		highlight = new Rectangle(container.getWidth()/2f-100, container.getHeight()/2f-200, 206 ,81);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
