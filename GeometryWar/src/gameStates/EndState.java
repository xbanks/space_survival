package gameStates;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import misc.HighScoreIO;
import misc.HighlightButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Start.game;
import gameStates.PlayState;


public class EndState extends BasicGameState{

	private HighlightButton back = null;
	private String name = null;
	private KeyHandler keys = null;
	private boolean done;
	private int timeElapsed;
	
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		back = new HighlightButton(20, container.getHeight() - 50, 125,25);
		name = new String();
		keys = new KeyHandler();
		done = false;
		timeElapsed = 0;
	}


	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();

		timeElapsed += delta*.5f;
		System.out.println("TE: " + timeElapsed);
		if(timeElapsed >= 1000){
			name = keys.getName(input);
			if(input.isKeyPressed(Input.KEY_ENTER) && !done){
				try {
					game.highScores.checkScore(PlayState.score, name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				done = true;
			}
		}
		if(input.isMouseButtonDown(0) && done){
			if(back.contains(input.getMouseX(), input.getMouseY())){
				done = false;
				sbg.getState(0).init(container, sbg);
				sbg.enterState(0, new FadeOutTransition(), new FadeInTransition());
			}
		}
		
		back.update(input);
	}
	

	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
//		g.setColor(Color.white);
//		Font font = new Font("Times New Roman", Font.ITALIC, 50);
//		UnicodeFont uFont = new UnicodeFont(font, font.getSize(), font.isItalic(), font.isPlain());
//		g.setFont(uFont);
		
		
		g.drawString("Game Over\nYour Score: " + PlayState.score, container.getWidth()/2, container.getHeight()/2);
		ArrayList<String> scores = new ArrayList<String>();
		//System.out.println(game.highScores.printScores());
		scores.addAll(game.highScores.printScores());
		
		g.setColor(Color.green);
		
		g.drawString("High Scores: ", 100, 40);
		for(int i = 0; i<10; i++ ){
			g.drawString(scores.get(i), 100, 70+(20*i));
		}
		
		g.drawString(name, 15, container.getHeight() - 150);
		
		if(done){
			back.fill(g);
			g.setColor(Color.green);
			g.drawString("Back to Start", back.getMinX()+4, back.getMinY()+2);
		}
	}


	public int getID() {
		// TODO Auto-generated method stub
		return 5;
	}

}
