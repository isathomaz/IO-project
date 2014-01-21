package io.view;

import io.controller.IOController;

import javax.swing.JFrame;

/**
 * IOFrame class
 * @author itho1735
 *@version 1.2 20/12/2013 documentation added
 */
public class IOFrame extends JFrame
{
	/**
	 * IOPanel for the frame
	 */
	private IOPanel basePanel;
	
	/**
	 * constructor for the base controller
	 * @param baseController
	 */
	public IOFrame(IOController baseController)
	{
		basePanel = new IOPanel(baseController);
		
		setupFrame();
	}
	/**
	 * sets up frame size and loads the content panel.
	 */
	private void setupFrame()
	{
		this.setContentPane(basePanel);
		this.setTitle("IO Project");
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
