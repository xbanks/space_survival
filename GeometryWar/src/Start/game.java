package Start;


import java.io.IOException;

import misc.HighScoreIO;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import gameStates.*;

public class game extends StateBasedGame{

	public static HighScoreIO highScores = null;
	public static Music music = null;
	
	public game(String name) throws SlickException {
		super(name);
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param args
	 * @throws SlickException 
	 */

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		try {
			highScores = new HighScoreIO();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.addState(new StartState());
		this.addState(new PlayState());
		this.addState(new InstructionsState());
		this.addState(new OptionsState());
		this.addState(new Scoring());
		this.addState(new EndState());
		
	}

}
