package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.Graphics;

public class HighScoreIO {

	private File hsFile = null;
	private Scanner fileScanner = null;
	private ArrayList<Integer> scoreList;
	private ArrayList<String> nameList;
	
	public HighScoreIO() throws IOException{
		
		hsFile = new File("res/dat files/highScores.dat");
		if(!hsFile.exists()){
			hsFile.createNewFile();
			createNewScoreFile();
		}
		
		fileScanner = new Scanner(hsFile);
		
		scoreList = new ArrayList<Integer>();
		nameList = new ArrayList<String>();
		readScoreFile();
		setLists();
	}
	
	private void createNewScoreFile() throws IOException{
		
		hsFile.createNewFile();
		FileWriter fw = new FileWriter(hsFile);
		
		for(int i = 0; i < 10; i++){
			fw.write("NAME - *** > HIGHSCORE = 0000\n");
		}
		fw.close();
		
	}
	
	public void readScoreFile(){
		Pattern format = Pattern.compile("NAME - (\\w|\\*){3} > HIGHSCORE = \\d+");
		Matcher match;
		int slotsCorrect = 0;
		
		//Checks if file has correct formatting
		while(fileScanner.hasNext()){
			
			String line = fileScanner.nextLine();
			match = format.matcher(line);
			while(match.find()){
				if(match.group().trim().length() > 0){
				}
				slotsCorrect+=1;
			}
		}
		
		
		if(slotsCorrect != 10){
			System.out.println("Formatted Incorrectly - recreating file");
			try {
				createNewScoreFile();
                //	readScoreFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	// Formats the score list
	private void setLists(){
		
		Pattern namePattern = Pattern.compile(" (\\w|\\*){3} ");
		Pattern scorePattern = Pattern.compile(" \\d+$");
		Matcher match;
		Scanner fs = null;
		
		try {
			fs = new Scanner(hsFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while(fs.hasNext()){
			
			String line = fs.nextLine();
			
			match = namePattern.matcher(line);
			while(match.find()){
				if(match.group().length() != 0){
					nameList.add(match.group().trim());
				}
			}
			
			match = scorePattern.matcher(line);
			while(match.find() && scoreList.size() < 10){
				if(match.group().length() != 0){
					scoreList.add( Integer.parseInt(match.group().trim()) );
				}
			}
			
		
		}
		
    //  System.out.println(scoreList);
	}
	
	public ArrayList<String> printScores(){
		ArrayList<String> reString = new ArrayList<String>();
		
    //	System.out.println("ScoreList Size : " + scoreList.size());
    //  System.out.println(scoreList);
    //  System.out.println("NameList Size : " + nameList.size());

		if((!scoreList.isEmpty() && !nameList.isEmpty()) && scoreList.size() == nameList.size()){
			
    //	System.out.println("High Scores:\n");
			
			for(int i = 0; i < 10; i++){
				reString.add(nameList.get(i) + " - " + scoreList.get(i));
				System.out.println(nameList.get(i) + " - " + scoreList.get(i));
			}
			
		}
		else System.out.println("NO MEGUSTA");
		
		 System.out.println(reString.size());
		return reString;
		
	}
	
	public void checkScore(int score, String name) throws IOException{
		boolean listChanged = false;
		int i = 0;
		
		if((!scoreList.isEmpty() && !nameList.isEmpty()) && scoreList.size() == nameList.size()){
			
			while(!listChanged && i < 10){
				if(score > scoreList.get(i)){
					addScore(i, score, name);
					listChanged = true;
				}
				i++;
			}
		}
		
		
	}
	
	private void addScore(int position, int score, String name) throws IOException{
		
		for(int i = 9; i >= position; i--){
			Integer element = scoreList.get(i);
			scoreList.add(null);
			scoreList.set(i+1, element);
			
			String element2 = nameList.get(i);
			nameList.add(null);
			nameList.set(i+1, element2);
		}
		
		scoreList.set(position, score);
		nameList.set(position, name);
		while(scoreList.size() > 10){
			scoreList.remove(10);
			nameList.remove(10);
		}
		
		
		
		FileWriter fw = new FileWriter(hsFile);

		for(int i = 0; i < 10; i++){
			fw.write("NAME - " + nameList.get(i) + " > HIGHSCORE = " + scoreList.get(i) + "\n");
		}
		fw.close();
		
		
	}
	
	
}
