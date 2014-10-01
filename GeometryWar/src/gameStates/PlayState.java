package gameStates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import misc.PowerUp;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Start.game;

import characters.Enemy;
import characters.Premature;
import characters.Player;
import characters.PlayerController;
import characters.ProjectileEnemy;
import characters.Shapes;
import characters.StrongEnemy;


public class PlayState extends BasicGameState implements MusicListener{

	private Player player = null;
	private PlayerController player_controls = null;
	
	private ParticleSystem particle_system = null;
	private ParticleSystem health_particles = null;
	private ConfigurableEmitter player_particles = null;
	
	private ArrayList<ConfigurableEmitter> health_emitters = null;
//	private ArrayList<ConfigurableEmitter> explosion_emitters = null;
	private ArrayList<Enemy> enemies = null;
	private ArrayList<Circle> health_drops = null;
	
	private File healthXML = null;
	private File exploXML = null;
	private File followXML = null;
	
	private Random random = null;
	
	public static int score;
	private int EnemyTime, HealthTime;
	
	private Rectangle bound = null;
	
	private int wave;
	private int enemies_spawned;
	//	enemy types
	private int prematures, fatties, shooters;

	private PowerUp p1 = null;
	
	private boolean isPaused, viewParticles = false;
	private PauseHandler pause_handler = null;
	
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		score = 0;
		wave = 0;
		EnemyTime = HealthTime = 0;
		enemies_spawned = 0;
		prematures = fatties = shooters = 0;
		
		health_drops = new ArrayList<Circle>();
		enemies = new ArrayList<Enemy>();
		
		//c = Controllers.getController(0);
		random = new Random();
		bound =  new Rectangle(0,0,1280, 960);
		
//		p1 = new PowerUp(PowerUp.WEAPON_UPGRADE1, container);
		pause_handler = new PauseHandler(sbg, container);
		
		isPaused = false;
		SetupPlayer(container);
		SetupParticles();
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {

		if(!isPaused){
//			p1.update(delta);
			Input input = container.getInput();
			
			if(input.isKeyPressed(Input.KEY_P))
				isPaused = true;
			
			for(Enemy e : enemies)
				e.update(delta);
			
			spawnEnemies(delta, container);
			spawnHealth(1, delta, container);
			
			player_particles.setPosition(player.getX(), player.getY(), false);
			player_particles.angularOffset.setValue(player.getAngle());
			System.out.println(player.getAngle());
			
			for(int i = health_drops.size()-1; i >= 0; i--){
				if(health_drops.get(i).intersects(player.getShape())){
					
					health_emitters.get(i).setEnabled(false);
//					explosion_emitters.get(i).setEnabled(true);
					System.out.println(health_drops.get(i).intersects(player.getShape()));
					
					particle_system.removeEmitter(health_emitters.get(i));
					health_emitters.remove(i);
//					explosion_emitters.remove(i);
					health_drops.remove(i);
					player.updateLife(50f);
				}
	
			}
			
			if(viewParticles)
				particle_system.update(delta);
			
			health_particles.update(delta);
			
			player_controls.update(input, delta, enemies);
			for(int i = enemies.size()-1; i >= 0; i--){
				if(enemies.get(i).isDead()){
					enemies.remove(i);
					score+=100;
				}
			}
			
			
			if(player.isDead()){
				sbg.enterState(5, new FadeOutTransition(), new FadeInTransition());
			}
		}
		else{
			Input pausedInput = container.getInput();
			
			if(pausedInput.isKeyPressed(Input.KEY_P))
				isPaused = false;
			else if(pausedInput.isKeyPressed(Input.KEY_E) || pausedInput.isKeyPressed(Input.KEY_ESCAPE))
					sbg.enterState(0, new FadeOutTransition(), new FadeInTransition());
			else if(pausedInput.isKeyPressed(Input.KEY_T))
				viewParticles = !viewParticles;
		}
	}
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.yellow);
		for(Circle c : health_drops){
			//g.draw(c);
		}
//		p1.render(g);
		if(viewParticles)
			particle_system.render();
		
		health_particles.render();
		player.render(g);
		
		for(Enemy e : enemies){
			e.render(g);
		}
		
		g.setColor(Color.white);
		g.drawString("Enemiy Count: " + enemies.size(), 10, 50);
		g.drawString("Score: " + score, 10, 75);
		g.drawString("Wave: " + wave, 10, 100);
		
		if(isPaused){
			pause_handler.render(g);
		}
	}
	
	private void SetupPlayer(GameContainer container) throws SlickException
	{
		player = new Player(container.getWidth()/2f, container.getHeight()/2f, Shapes.Circle);
		player.setBounds(new Rectangle(0,0,container.getWidth(), container.getHeight()));
		player_controls = new PlayerController(container, player);
	}

	private void SetupParticles() throws SlickException
	{
		Image particle_image = new Image("res/images/particle.png", false);
		Image health_image = new Image("res/images/particle_plus.png");
		
		healthXML = new File("res/particleFiles/health.xml");
		exploXML = new File("res/particleFiles/explosion.xml");
		followXML = new File("res/particleFiles/follower.xml");
		
		particle_system = new ParticleSystem(particle_image, 1500);
		health_particles = new ParticleSystem(health_image, 1500);
		particle_system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		health_particles.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		
		try {
			player_particles = ParticleIO.loadEmitter(followXML);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		particle_system.addEmitter(player_particles);
		
		health_emitters = new ArrayList<ConfigurableEmitter>();
//		explosion_emitters = new ArrayList<ConfigurableEmitter>();
	}
	
	private void spawnEnemies(int delta, GameContainer container) throws SlickException{
		
		EnemyTime+=delta;
		
		int feNum = wave*3;
		int peNum = wave;
		int seNum = wave;
		
		int en = feNum + peNum;
		
		if(EnemyTime > 2500){
			if(enemies.size() <= 15 && enemies_spawned < en){
				
				if(prematures < feNum){
					spawnPremas(1, container);
					prematures++;
					enemies_spawned++;
				}
				
				if(fatties < peNum){
					spawnShooters(1, container);
					fatties++;
					enemies_spawned++;
				}
				
//				if(sS < peNum){
//					spawnSE(1, container);
//					sS++;
//				}
			}
			else if(enemies_spawned >= en && enemies.isEmpty()){
				prematures = 0;
				fatties = 0;
				shooters = 0;
				wave++;
				enemies_spawned = 0;
				EnemyTime = 0;
			}
			else{
				
			}
		}
		
	}
	
	private void spawnPremas(int numSpawning, GameContainer container) throws SlickException{
		
		Premature prema;
		int xLoc, yLoc;
		
		for(int i = 0; i < numSpawning; i++){
			
			int loc = random.nextInt(4);
			
			switch(loc){
			
			
			case 0:
				yLoc = random.nextInt(container.getHeight());
				prema = new Premature(-50, yLoc, player);
				prema.setShape(Shapes.Circle);
				prema.addEmitters(particle_system);
				enemies.add(prema);
				break;
			case 1:
				xLoc = random.nextInt(container.getWidth());
				prema = new Premature(xLoc, -50, player);
				prema.setShape(Shapes.Circle);
				prema.addEmitters(particle_system);
				enemies.add(prema);
				break;
			case 2:
				yLoc = random.nextInt(container.getHeight());
				prema = new Premature(container.getWidth()+50, yLoc, player);
				prema.setShape(Shapes.Circle);
				prema.addEmitters(particle_system);
				enemies.add(prema);
				break;
			case 3:
				xLoc = random.nextInt(container.getWidth());
				prema = new Premature(xLoc, container.getHeight()+50, player);
				prema.setShape(Shapes.Circle);
				prema.addEmitters(particle_system);
				enemies.add(prema);
				break;
			
			
			}
			
		}
	}
	
	private void spawnShooters(int numSpawning, GameContainer container) throws SlickException{
		
		ProjectileEnemy pe;
		int xLoc, yLoc;
		
		for(int i = 0; i < numSpawning; i++){
			
			int loc = random.nextInt(4);
			
			switch(loc){
			
			
			case 0:
				yLoc = random.nextInt(container.getHeight());
				pe = new ProjectileEnemy(-50, yLoc, player);
				pe.setShape(Shapes.Circle);
				pe.addEmitters(particle_system);
				pe.setBounds(bound);
				enemies.add(pe);
				break;
			case 1:
				xLoc = random.nextInt(container.getWidth());
				pe = new ProjectileEnemy(xLoc, -50, player);
				pe.setShape(Shapes.Circle);
				pe.addEmitters(particle_system);
				enemies.add(pe);
				pe.setBounds(bound);
				break;
			case 2:
				yLoc = random.nextInt(container.getHeight());
				pe = new ProjectileEnemy(container.getWidth()+50, yLoc, player);
				pe.setShape(Shapes.Circle);
				pe.addEmitters(particle_system);
				enemies.add(pe);
				pe.setBounds(bound);
				break;
			case 3:
				xLoc = random.nextInt(container.getWidth());
				pe = new ProjectileEnemy(xLoc, container.getHeight()+50, player);
				pe.setShape(Shapes.Circle);
				pe.addEmitters(particle_system);
				enemies.add(pe);
				pe.setBounds(bound);
				break;
			
			
			}
			
		}
	}
	
	private void spawnFatties(int numSpawning, GameContainer container) throws SlickException{
		
		StrongEnemy se;
		int xLoc, yLoc;
		
		for(int i = 0; i < numSpawning; i++){
			
			int loc = random.nextInt(4);
			
			switch(loc){
			
			
			case 0:
				yLoc = random.nextInt(container.getHeight());
				se = new StrongEnemy(-50, yLoc, player);
				se.setShape(Shapes.Rectangle);
				se.addEmitters(particle_system);
				se.setBounds(bound);
				enemies.add(se);
				break;
			case 1:
				xLoc = random.nextInt(container.getWidth());
				se = new StrongEnemy(xLoc, -50, player);
				se.setShape(Shapes.Rectangle);
				se.addEmitters(particle_system);
				enemies.add(se);
				se.setBounds(bound);
				break;
			case 2:
				yLoc = random.nextInt(container.getHeight());
				se = new StrongEnemy(container.getWidth()+50, yLoc, player);
				se.setShape(Shapes.Rectangle);
				se.addEmitters(particle_system);
				enemies.add(se);
				se.setBounds(bound);
				break;
			case 3:
				xLoc = random.nextInt(container.getWidth());
				se = new StrongEnemy(xLoc, container.getHeight()+50, player);
				se.setShape(Shapes.Rectangle);
				se.addEmitters(particle_system);
				enemies.add(se);
				se.setBounds(bound);
				break;
			
			
			}
			
		}
	}
	
	private void spawnHealth(int level, int delta, GameContainer container){
		
		if(health_drops.isEmpty())
			HealthTime+=delta;
		
		if(HealthTime >= 5000){
			HealthTime = 0;
			try {
				int x = random.nextInt(container.getWidth());
				int y = random.nextInt(container.getHeight());
				
				health_drops.add(new Circle(x,y, 30));
				
				health_emitters.add(ParticleIO.loadEmitter(healthXML));
				health_emitters.get(health_emitters.size()-1).setPosition(x, y, false);
				
//				explosion_emitters.add(ParticleIO.loadEmitter(exploXML));
//				explosion_emitters.get(explosion_emitters.size()-1).setPosition(x, y, false);
//				explosion_emitters.get(explosion_emitters.size()-1).setEnabled(false);
				
				health_particles.addEmitter(health_emitters.get(health_emitters.size()-1));
//				health_particles.addEmitter(explosion_emitters.get(explosion_emitters.size()-1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void musicEnded(Music arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void musicSwapped(Music first, Music second) {
		// TODO Auto-generated method stub
		
	}

}
