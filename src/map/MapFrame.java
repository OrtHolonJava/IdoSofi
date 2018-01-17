package map;
import java.util.Timer;
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
		Timer gameTimer = new Timer();
		GameLoop gLoop = new GameLoop(this._mapPanel);
		add(this._mapPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //setSize(1280, 720);
	    setResizable(false);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
	    setVisible(true);
	    gameTimer.schedule(gLoop, 0, 1000 / 60); //new timer at 60 fps, the timing mechanism
	}
}
