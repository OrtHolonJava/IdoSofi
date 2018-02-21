package map;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import images.Img;

/**
 * The MenuPanel Class -
 */
public class MenuPanel extends JPanel
{
	private Img _background, _logo;
	private List<MenuPanelListener> _listeners;
	private final int _yLogoOffset = 0;
	
	public MenuPanel()
	{
		int logoW = 0, logoH = 0;
		ImageIcon logoIcon;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		logoIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images\\others\\logo.png"));  
		logoW = logoIcon.getIconWidth();
		logoH = logoIcon.getIconHeight();
		this._background = new Img("images\\backgrounds\\bgMenu.png", 0, 0, screenSize.width, screenSize.height);
		this._logo = new Img("images\\others\\logo.png", (screenSize.width / 2) - (logoW / 2), _yLogoOffset, logoW, logoH);
		
		this._listeners = new ArrayList<MenuPanelListener>();
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				super.mousePressed(arg0);
				for (int i = 0; i < _listeners.size(); i++)
				{
					_listeners.get(i).mapHasBeenChosen(1);
				}
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
