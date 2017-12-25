package map;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * The MapFrame Class - 
 */
public class MapFrame extends JFrame
{
	private MapPanel _mapPanel;
	
	/**
	 * The Constructor Method: 
	 * Initializes an instance of the class.
	 */
	public MapFrame(int mapID ,int rows, int cols, int bSize)
	{
		this.setTitle("Link's Scrolls v0.1");
		this._mapPanel = new MapPanel(mapID ,rows, cols, bSize);
		add(this._mapPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(1280, 720);
	    setVisible(true);
	    setResizable(false);
	    inGame();
	}
	
	public void inGame()
	{
		while (true)
		{
			this._mapPanel.repaint();
		}
	}
}
