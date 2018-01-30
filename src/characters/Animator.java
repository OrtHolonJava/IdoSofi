package characters;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import images.Img;

/**
 * @author USER
 *
 */
public class Animator implements ActionListener
{
	private static Img sprites;
	
	private int _currFrame;
	private Img _currentImage;
	private int _timeBetweenFrames;
	private Timer _timer;
	
	public Animator()
	{
		
	}
	
	/**
	 * The ticking of the animation timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
	
}
