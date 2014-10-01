package gameStates;

import org.newdawn.slick.Input;

public class KeyHandler{


	private char[] name;
	private String reName;
	int place;
	
	public KeyHandler(){
		name = new char[3];
		name[0] = 'A';
		name[1] = 'A';
		name[2] = 'A';
		place = 0;
		reName = new String();
	}
	
	public String getName(Input input){
		
		if(input.isKeyPressed(Input.KEY_RIGHT))
			place++;
		else if(input.isKeyPressed(Input.KEY_LEFT))
			place--;
		
		if(place > 2)
			place = 2;
		
		if(place < 0)
			place = 0;
		
		if(input.isKeyPressed(Input.KEY_A)){name[place] = 'A';}
		else if(input.isKeyPressed(Input.KEY_B)){name[place] = 'B';}
		else if(input.isKeyPressed(Input.KEY_C)){name[place] = 'C';}
		else if(input.isKeyPressed(Input.KEY_D)){name[place] = 'D';}
		else if(input.isKeyPressed(Input.KEY_E)){name[place] = 'E';}
		else if(input.isKeyPressed(Input.KEY_F)){name[place] = 'F';}
		else if(input.isKeyPressed(Input.KEY_G)){name[place] = 'G';}
		else if(input.isKeyPressed(Input.KEY_H)){name[place] = 'H';}
		else if(input.isKeyPressed(Input.KEY_I)){name[place] = 'I';}
		else if(input.isKeyPressed(Input.KEY_J)){name[place] = 'J';}
		else if(input.isKeyPressed(Input.KEY_K)){name[place] = 'K';}
		else if(input.isKeyPressed(Input.KEY_L)){name[place] = 'L';}
		else if(input.isKeyPressed(Input.KEY_M)){name[place] = 'M';}
		else if(input.isKeyPressed(Input.KEY_N)){name[place] = 'N';}
		else if(input.isKeyPressed(Input.KEY_O)){name[place] = 'O';}
		else if(input.isKeyPressed(Input.KEY_P)){name[place] = 'P';}
		else if(input.isKeyPressed(Input.KEY_Q)){name[place] = 'Q';}
		else if(input.isKeyPressed(Input.KEY_R)){name[place] = 'R';}
		else if(input.isKeyPressed(Input.KEY_S)){name[place] = 'S';}
		else if(input.isKeyPressed(Input.KEY_T)){name[place] = 'T';}
		else if(input.isKeyPressed(Input.KEY_U)){name[place] = 'U';}
		else if(input.isKeyPressed(Input.KEY_V)){name[place] = 'V';}
		else if(input.isKeyPressed(Input.KEY_W)){name[place] = 'W';}
		else if(input.isKeyPressed(Input.KEY_X)){name[place] = 'X';}
		else if(input.isKeyPressed(Input.KEY_Y)){name[place] = 'Y';}
		else if(input.isKeyPressed(Input.KEY_Z)){name[place] = 'Z';}
		
		reName = new String(name);

		return reName;
	}

	

	
	
}
