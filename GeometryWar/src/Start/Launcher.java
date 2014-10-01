package Start;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import misc.Console;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;


public class Launcher 
{

	public static void main(String[] args) throws SlickException 
	{
		// TODO Auto-generated method stub

		CanvasGameContainer container = new CanvasGameContainer(new game("Geometry Wars vX"));

		container.setName("Geo");
		container.isDisplayable();
		container.getContainer().setAlwaysRender(true);
		container.getContainer().setTargetFrameRate(60);
		
		double ratio = .75;
		int pwidth = (int) (container.getContainer().getScreenWidth()*.70);
		int pheight = (int) (pwidth*ratio);

		Dimension prefferedSize = new Dimension(pwidth, pheight);
		Dimension finalSize = null;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		if(screenSize.getWidth() < prefferedSize.getWidth()){
			int width = (int) screenSize.getWidth();
			int height = (int) (screenSize.getWidth()*prefferedSize.getHeight()/prefferedSize.width);
			finalSize = new Dimension(width, height);
		}
		else
			finalSize = prefferedSize;
		
		Console c = new Console();

		System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add("Center" ,container);
		//frame.add(BorderLayout.SOUTH,c);
		frame.setLocation(50, 50);
		frame.setSize(finalSize);
		frame.setVisible(true);
		container.start();
	}
	
}
