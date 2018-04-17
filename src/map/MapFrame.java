package map;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * The MapFrame Class - 
 */
public class MapFrame extends JFrame implements MenuPanelListener
{
	private MenuPanel _menuPanel;
	private MapPanel _mapPanel;
	private UIPanel _uiPanel;
	private GameLoop _gameLoop;
	
	/**
	 * The Constructor Method: 
	 * Initializes an instance of the class.
	 */
	public MapFrame()
	{
		this.setTitle("Link's Scrolls");
		
		this._menuPanel = new MenuPanel();
		this._menuPanel.addListener(this);
		this.add(_menuPanel);
		setFrameSettings();
	}
	
	private void startGame()
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
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setUndecorated(true);
	    setVisible(true);
	}
	
	private void removeMainMenu()
	{
		this.getContentPane().remove(this._menuPanel);
	}
	
	@Override
	public void mapHasBeenChosen(int mapID)
	{
		removeMainMenu();
		this._mapPanel = new MapPanel(mapID); // TEMP
		add(this._mapPanel);
		this._mapPanel.requestFocusInWindow();
		revalidate();
		
		/**
		 * Setting up the Game UI -
		 */
		this._uiPanel = new UIPanel();
		this.setGlassPane(this._uiPanel);
		this.getGlassPane().setVisible(true);
		
		_gameLoop = new GameLoop(this._mapPanel);
		startGame();
	}
}
