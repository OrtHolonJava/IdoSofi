package map;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import images.Img;

/**
 * The MenuPanel Class -
 */
public class MenuPanel extends JPanel
{
	private Img _background, _logo;
	private JButton _startButton, _exitButton;
	private List<MenuPanelListener> _listeners;
	private final int _yLogoOffset = 0, _gapBetweenButtons = 120; // Final integers for visual purposes.
	private int _yButtonsOffset = 450; // 200 = Initial value, goes up with each button that is added to the panel.
	
	/**
	 * The Constructor Method -
	 */
	public MenuPanel()
	{
		int logoW = 0, logoH = 0;
		ImageIcon logoIcon, startIcon, exitIcon;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(null);
		
		/**
		 * Initializing the background and logo images -
		 */
		logoIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images\\others\\logo.png"));  
		logoW = logoIcon.getIconWidth();
		logoH = logoIcon.getIconHeight();
		this._background = new Img("images\\backgrounds\\bgMenu.png", 0, 0, screenSize.width, screenSize.height);
		this._logo = new Img("images\\others\\logo.png", (screenSize.width / 2) - (logoW / 2), _yLogoOffset, logoW, logoH);
		
		/**
		 * Initializing menu buttons -
		 */
		startIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images\\buttons\\startButton.png")); 
		exitIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images\\buttons\\exitButton.png")); 
		this._startButton = new JButton();
		this._exitButton = new JButton();
		this.addButtonToMenu(this._startButton, startIcon, screenSize.width);
		this.addButtonToMenu(this._exitButton, exitIcon, screenSize.width);
		this.adjustButtonActions();
		
		this._listeners = new ArrayList<MenuPanelListener>();
	}
	
	private void addButtonToMenu(JButton button, ImageIcon image, int screenWidth)
	{
		button.setBounds(screenWidth / 2 - image.getIconWidth() / 2, this._yButtonsOffset, image.getIconWidth(), image.getIconHeight());
		button.setIcon(image);
		button.setMargin(new Insets(0, 0, 0, 0));
		this.add(button);
		this._yButtonsOffset += this._gapBetweenButtons;
	}
	
	/**
	 * Method - Adjusts the action listeners of each and every menu button.
	 */
	private void adjustButtonActions()
	{
		/**
		 * Setting the action listener of the start button -
		 */
		this._startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (int i = 0; i < _listeners.size(); i++)
				{
					_listeners.get(i).mapHasBeenChosen(1); // 1 map is currently available (TEMP)
				}
			}
		});
		
		/**
		 * Setting the action listener of the exit button -
		 */
		this._exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	public void addListener(MenuPanelListener lis)
	{
		this._listeners.add(lis);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		/**
		 * Drawing the panel's images-
		 */
		this._background.drawImg(g);
		this._logo.drawImg(g);
	}
}
