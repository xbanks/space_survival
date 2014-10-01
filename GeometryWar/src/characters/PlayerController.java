package characters;

import java.util.ArrayList;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class PlayerController implements ControllerListener {
	
	int time, shieldDelay;
	Player player = null;
	GameContainer container = null;
	boolean shield;
	
	public PlayerController(GameContainer gc, Player p){
		player = p;
		container = gc;
		time = shieldDelay = 0;
		shield = false;
	}
	
	public void update(Input input, int d, ArrayList<Enemy> enemies){
		
		float delta = d*.2f;
		
		if(input.isMouseButtonDown(0)){
			time += d;
			if(time >= player.getFireSpeed()){
				time = 0;
				player.fire(input.getMouseX(), input.getMouseY(), 3);
			}
		}
		
		if(shieldDelay > 1000){
			
			if(input.isMousePressed(1) && player.getShieldStatus() == false){
				shieldDelay = 0;
				player.setShieldStatus(true);
			}
		}
		
		System.out.println(shieldDelay);
		
		if(player.getShieldStatus()){
			player.updateShield(d);
		}
		else
			shieldDelay += d*.2;
		
		if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(0)){
			if(player.getX() > 0)
				player.moveX(-delta);
		}
		
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)|| input.isControllerRight(0)){
			if(player.getX() < container.getWidth())
				player.moveX(delta);
		}
		
		if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP) || input.isControllerUp(0)){
			if(player.getY() > 0)
				player.moveY(-delta);
		}
		
		if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN) || input.isControllerDown(0)){
			if(player.getY() < container.getHeight())
				player.moveY(delta);
		}
		
		player.updateProjectiles(d, enemies);
		
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
