package map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

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
		FixedTSGameLoop fixedGLoop = new FixedTSGameLoop(this._mapPanel);
		Thread gLoopThread = new Thread(fixedGLoop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    //setSize(1280, 720);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
	    setVisible(true);
	    //gLoopThread.start();
	    Timer t = new Timer(1000/60, new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				_mapPanel.setLogic();
			}
		});
	    t.start();
	    
	    Timer t1 = new Timer(1000/180, new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				_mapPanel.repaint();
			}
		});
	    t1.start();
	}
}
