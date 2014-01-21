package io.controller;

import io.model.Game;
import io.view.IOFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
/**
 * controller class for the IO project.
 * @author itho1735
 *@version 1.3 20/12/2013 Documentation added, fixed error on getProjectGames 
 */
public class IOController
{
	//declaration section
	
	/**
	 * reference to the GUI Frame for the project.
	 */
	private IOFrame appFrame;
	/**
	 * all games that can be retrieved from the save file.
	 */
	private ArrayList<Game> projectGames;
	
	public IOController()
	{
		projectGames = new ArrayList<Game>();
	}
	
	public ArrayList<Game> getProjectGames()
	{
		return projectGames;
	}

	public void setProjectGames(ArrayList<Game> projectGames)
	{
		this.projectGames = projectGames;
	}

	public void start()
	{
		appFrame = new IOFrame(this);
	}
	
	/**
	 * reads the current game's game information
	 * uses try catch statement
	 * @return
	 */
	public Game readGameInformation()
	{
		String fileName = "Save file.txt"; //without a path it will look to the directory of the project!
		File currentSaveFile = new File(fileName);
		Scanner fileReader;
		Game currentGame = null;
		int gameRanking =0;
		String gameTitle = "";
		ArrayList<String> gameRules = new ArrayList<String>();
		
		/**
		 * Major Scanner methods!!
		 * .next()
		 * .nextLine()
		 * .nextInt()
		 * .nextBoolean()
		 * .nextDouble()
		 * 
		 */
		try
		{
			fileReader = new Scanner(currentSaveFile);
			gameTitle = fileReader.nextLine();
			gameRanking = fileReader.nextInt();
			while(fileReader.hasNext())
			{
				gameRules.add(fileReader.nextLine());
			}
			currentGame = new Game(gameRules, gameRanking, gameTitle);
			fileReader.close();
		}
		catch(FileNotFoundException currentFileDoesNotExist)
		{
			JOptionPane.showMessageDialog(appFrame, currentFileDoesNotExist.getMessage());
		}
		return currentGame;
	}
	
	/**
	 * unlike the readGameInformation method, this method reads ALL the game information
	 * uses try catch statement
	 * @return
	 */
	public String readAllGameInformation()
	{
		String fileContents = "";
		String fileName = "save file.txt";
		File currentSaveFile = new File(fileName);
		Scanner fileReader;

		try
		{
			fileReader = new Scanner(currentSaveFile);
			while(fileReader.hasNext())
			{
				fileContents += fileReader.nextLine();
			}
			fileReader.close();
		}
		catch(FileNotFoundException fileDoesNotExist)
		{
			JOptionPane.showMessageDialog(appFrame, fileDoesNotExist.getMessage());
		}
		return fileContents;
		
	}
	
	private void convertTextToGames(String currentInfo)
	{
		
		String [] gameChunks = currentInfo.split(";");
		for(String currentBlock : gameChunks)
		{
			int currentIndex = currentBlock.indexOf("\n");
			String title = currentBlock.substring(0, currentIndex); 
			int nextIndex = currentBlock.indexOf("\n" , currentIndex);
			String ranking = currentBlock.substring(currentIndex+1, nextIndex);
			String rules = currentBlock.substring(nextIndex+1);
			Game currentGame = makeGameFromInput(title, ranking, rules);
			projectGames.add(currentGame);
		}
	}
	
	public Game pickRandomGameFromSaveFile()
	{
		Game currentGame = null;
		
		String allInfo = readAllGameInformation();
		
		return currentGame;
	}
	
	public Game makeGameFromInput(String gameTitle, String gameRanking, String gameRules)
	{
		Game currentGame = new Game();
		currentGame.setGameTitle(gameTitle);
		
		if(checkNumberFormat(gameRanking))
		{
			currentGame.setFunRanking(Integer.parseInt(gameRanking));
		}
		else
		{
			return null;
		}
		
		String[] temp = gameRules.split("\n");
		ArrayList<String> tempRules = new ArrayList<String>();
		
		for(String tempWord : temp)
		{
			tempRules.add(tempWord);
		}
		currentGame.setGameRules(tempRules);
		return currentGame;
	}
	
	/**
	 * checks to make sure that numbers are being used in the rankingField
	 * uses try catch statement
	 * @param toBeParsed
	 * @return
	 */
	private boolean checkNumberFormat(String toBeParsed)
	{
		boolean isNumber = false;
		
		try
		{
			int valid = Integer.parseInt(toBeParsed);
			isNumber = true;
		}
		catch(NumberFormatException error)
		{
			JOptionPane.showMessageDialog(appFrame, "Please try again with an actual number.");
		}
		return isNumber;
	}
	
	/**
	 * Saves a Game object to the drive and seperates each Game object with a semicolon
	 * @param currentGame
	 */
	public void saveGameInformation(Game currentGame)
	{
		PrintWriter gameWriter;
		String saveFile = "save file.txt";
		
		try
		{
			gameWriter = new PrintWriter(saveFile); // creates the save file.
			
			gameWriter.append(currentGame.getGameTitle() + "\n");
			gameWriter.append(currentGame.getFunRanking() + "\n");
			for(int count = 0; count < currentGame.getGameRules().size(); count++)
			{
				gameWriter.append(currentGame.getGameRules().get(count) + "\n");
			}
			gameWriter.append(";" + "\n"); 
			
			gameWriter.close(); // required to prevent corruption of data and maintain security of the file.
		}
		catch(FileNotFoundException noFileExists)
		{
			JOptionPane.showMessageDialog(appFrame, "could not create the save file. :(");
			JOptionPane.showMessageDialog(appFrame, noFileExists.getMessage());
		}
	}
	
	
}
