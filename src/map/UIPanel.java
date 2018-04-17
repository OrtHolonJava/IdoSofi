package map;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import images.Img;

/**
 * The UIPanel Class -
 */
public class UIPanel extends JPanel implements MapPanelListener
{
	private static final Point _redBarOffset = new Point(123, 953);
	
	private Img _template, _hpBar, _manaBar;
	private float _hpBarLength, _manaBarLength;
	
	public UIPanel()
	{
		this.setLayout(null);
		this.setOpaque(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this._hpBarLength = this._manaBarLength = 1f;
		ImageIcon barIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images\\others\\redBar.png"));  
		
		this._template = new Img("images\\others\\ui.png", 0, 0, screenSize.width, screenSize.height);
		this._hpBar = new Img("images\\others\\redBar.png", _redBarOffset.x, _redBarOffset.y, barIcon.getIconWidth(), barIcon.getIconHeight());
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this._template.drawImg(g);
		this._hpBar.drawImg(g);
	}
}
