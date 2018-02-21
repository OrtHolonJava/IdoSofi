package map;
import java.nio.charset.MalformedInputException;

import javax.swing.JFrame;

/**
 * The MapFrame Class - 
 */
public class MapFrame extends JFrame implements MenuPanelListener
{
	private MenuPanel _menuPanel;
	private MapPanel _mapPanel;
	private GameLoop _gameLoop;
	
	/**
	 * The Constructor Method: 
	 * Initializes an instance of the class.
	 */
	public MapFrame(int mapID ,int rows, int cols)
	{
		this.setTitle("Link's Scrolls");
		this._menuPanel = new MenuPanel();
		this.add(_menuPanel);
		setFrameSettings();
	}
	
	public void startGame()
	{
	    _gameLoop.startGame();
	}
	
	/**
	 * Method: Setting the JFrame's settings -
	 */
	private void setFrameSettings()
	{
	    setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //setSize(1280, 720);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
	    setVisible(true);
	}
	
	
	
	@Override
	public void mapHasBeenChosen(int mapID)
	{
		// TODO Auto-generated method stub
		this._mapPanel = new MapPanel(mapID, 40, 69); // TEMP
		add(this._mapPanel);
		
		/**
		 * Initializing the game loop -
		 */
		_gameLoop = new GameLoop(this._mapPanel);
	}
}
