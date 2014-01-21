package io.view;

import io.controller.IOController;
import io.model.Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IOPanel extends JPanel
{
	private IOController baseController;
	private JButton saveButton;
	private JButton loadButton;
	private JTextField titleField;
	private JTextField rankingField;
	private JTextArea rulesArea;
	private JLabel rulesLabel;
	private JLabel rankingLabel;
	private JLabel titleLabel;
	private JLabel gameCountLabel;
	private SpringLayout baseLayout;
	
	public IOPanel(IOController baseController)
	{
		
		this.baseController = baseController;
		
		saveButton = new JButton("Save the game stuff");
		loadButton = new JButton("load the game stuff");
		titleField = new JTextField(15);
		titleLabel = new JLabel("Game Title");
		rankingField = new JTextField(5);
		rankingLabel = new JLabel("Game Ranking");
		rulesArea = new JTextArea(5,20);
		rulesLabel = new JLabel("Game Rules");
		gameCountLabel = new JLabel("Current Game Count: ");
		baseLayout = new SpringLayout();
		
		
		
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	public void setupPanel()
	{
		this.setLayout(baseLayout);
		this.add(rankingField);
		this.add(rankingLabel);
		this.add(rulesArea);
		this.add(rulesLabel);
		this.add(saveButton);
		this.add(titleField);
		this.add(titleLabel);
		this.add(loadButton);
		this.add(gameCountLabel);
	}
	public void setupLayout()
	{
		setBackground(new Color(51, 0, 102));
		gameCountLabel.setForeground(Color.WHITE);
		titleLabel.setForeground(new Color(255, 255, 255));
		rulesLabel.setForeground(new Color(255, 255, 255));
		rankingLabel.setForeground(new Color(255, 255, 255));
		baseLayout.putConstraint(SpringLayout.WEST, rankingLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesArea, 6, SpringLayout.SOUTH, rulesLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rulesArea, 0, SpringLayout.WEST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.SOUTH, rulesArea, -12, SpringLayout.NORTH, saveButton);
		baseLayout.putConstraint(SpringLayout.EAST, rulesArea, -200, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesLabel, 6, SpringLayout.SOUTH, rankingLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rulesLabel, 0, SpringLayout.WEST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingLabel, 3, SpringLayout.NORTH, rankingField);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingField, 4, SpringLayout.SOUTH, titleField);
		baseLayout.putConstraint(SpringLayout.WEST, rankingField, 0, SpringLayout.WEST, titleField);
		baseLayout.putConstraint(SpringLayout.WEST, titleField, 35, SpringLayout.EAST, titleLabel);
		baseLayout.putConstraint(SpringLayout.WEST, gameCountLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, titleLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, titleField, -3, SpringLayout.NORTH, titleLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, titleLabel, 31, SpringLayout.SOUTH, gameCountLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, gameCountLabel, 10, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, loadButton, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, saveButton, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, saveButton, -6, SpringLayout.NORTH, loadButton);
		baseLayout.putConstraint(SpringLayout.SOUTH, loadButton, -10, SpringLayout.SOUTH, this);
		
	}
	
	public void setupListeners()
	{
		/**
		 * when performed a save file.txt appears in the IO project folder.
		 */
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.makeGameFromInput(titleField.getText(), rankingField.getText(), rulesArea.getText());
				if(tempGame != null)
				{
					baseController.saveGameInformation(tempGame);
					gameCountLabel.setText("Current Game Count: " + baseController.getProjectGames().size());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Try again with a valid number");
				}
			}
		});
		/**
		 * when performed, it loads the save file.txt into the program.
		 */
		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.readGameInformation();
				if(tempGame != null)
				{
					titleField.setText(tempGame.getGameTitle());
					rankingField.setText(Integer.toString(tempGame.getFunRanking()));
					String temp = "";
					for(String currentRule : tempGame.getGameRules() )
					{
						temp+= currentRule +"\n";
					}
					rulesArea.setText(temp);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "check the save file make sure it is in order.");
				}
			}
		});
	}
}
