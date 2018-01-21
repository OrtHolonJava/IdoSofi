package map;
import javax.swing.Timer;
import javax.swing.JFrame;

/**
 * The MapFrame Class - 
 */
public class MapFrame extends JFrame
{
	private MapPanel _mapPanel;
	private final int _delay = 1000 / 60;
	
	/**
	 * The Constructor Method: 
	 * Initializes an instance of the class.
	 */
	public MapFrame(int mapID ,int rows, int cols)
	{
		this.setTitle("Link's Scrolls v0.1");
		this._mapPanel = new MapPanel(mapID ,rows, cols);
		add(this._mapPanel);
		FixedTSGameLoop fixedGLoop = new FixedTSGameLoop(this._mapPanel);
		Thread gLoopThread = new Thread(fixedGLoop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //setSize(1280, 720);
	    setResizable(false);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
	    setVisible(true);
	    gLoopThread.start();
	}
}
