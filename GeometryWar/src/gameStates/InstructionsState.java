package gameStates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

import gameStates.*;

public class InstructionsState extends BasicGameState{
	
	private final Color universalColor = new Color(0,255,15,.5f);
	private File instructionsFile;
	private ArrayList<String> lines;
	private int time;
	private int stop;
	int numLines;
	private ParticleSystem pSystem = null;
	private ConfigurableEmitter bgEmitter = null;
	private Rectangle back = null;
	private Rectangle highlight = null;
	private Color hiLightColor = Color.transparent;
	
	@Override
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

		time = 0;
		stop = 1;
		instructionsFile = new File("res/dat files/instructions.dat");
		Scanner in = null;
		try {
			in = new Scanner(instructionsFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		numLines = in.nextInt();
		
		lines = new ArrayList<String>();
		for(int i = 0; i < numLines; i++){
			lines.add(in.nextLine());
		}
		
		back = new Rectangle(20, container.getHeight() - 50, 100,25);
		highlight = new Rectangle(17, container.getHeight() - 53, 106 ,31);
	}


	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = container.getInput();
		
		time+=delta;
		if(time >= 50){
			stop++;
			time = 0;
		}
		
		hiLightColor = Color.transparent;
		if(back.contains(input.getMouseX(), input.getMouseY())){
			hiLightColor = Color.blue;
		}
		
		if(input.isMouseButtonDown(0)){
			if(back.contains(input.getMouseX(), input.getMouseY())){
				sbg.enterState(0, new FadeOutTransition(), new FadeInTransition());
			}
		}
		
		pSystem.update(delta);
	}
	
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		pSystem.render();
		g.setColor(hiLightColor);
		g.fill(highlight);
		
		g.setColor(universalColor);
		g.fill(back);
		
		g.setColor(Color.green);
		for(int i = 0; i < stop && i < numLines; i++ ){
			g.drawString(lines.get(i), 100, 50+(20*i));
		}
		
		g.drawString("Back", back.getCenterX()-22, back.getCenterY()-10);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
