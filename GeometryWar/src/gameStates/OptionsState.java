package gameStates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import misc.HighlightButton;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class OptionsState extends BasicGameState{

	private ArrayList<HighlightButton> buttons = null;
	private ParticleSystem pSystem = null;
	private ConfigurableEmitter bgEmitter = null;
	private HighlightButton back = null;
	
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		Image Ptcl = new Image("res/images/particle.png", false);
		pSystem = new ParticleSystem(Ptcl, 1500);
		File xml = new File("res/particleFiles/flame.xml");
		try {
			bgEmitter = ParticleIO.loadEmitter(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pSystem.addEmitter(bgEmitter);
		bgEmitter.setPosition(0, 0);

		buttons = new ArrayList<HighlightButton>();
		buttons.add(new HighlightButton(50,50, 200,75));
		
		back = new HighlightButton(20, container.getHeight() - 50, 100,25);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		pSystem.update(delta);
		Input input = container.getInput();
		for(HighlightButton hb : buttons){
			hb.update(input);
		}
		
		if(input.isMouseButtonDown(0)){
			if(back.contains(input.getMouseX(), input.getMouseY())){
				sbg.enterState(0, new FadeOutTransition(), new FadeInTransition());
			}
		}
		back.update(input);
	}

	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		pSystem.render();	//Render before everything else to create background particles
		for(HighlightButton hb : buttons){
			hb.fill(g);
		}
		
		back.fill(g);
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
