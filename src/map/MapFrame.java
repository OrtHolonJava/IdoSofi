package map;
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
	public MapFrame(int mapID ,int rows, int cols)
	{
		this.setTitle("Link's Scrolls v0.1");
		this._mapPanel = new MapPanel(mapID ,rows, cols);
		add(this._mapPanel);
		
		/**
		 * Initializing the game loop -
		 */
		GameLoop gameLoop = new GameLoop(this._mapPanel);
		
		/**
		 * Setting the frame's settings -
		 */
	    setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //setSize(1280, 720);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
	    setVisible(true);
	    
	    /**
	     * Starting the game -
	     */
	    gameLoop.startGame();
	}
}
