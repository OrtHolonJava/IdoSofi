package map;

import javax.swing.SwingUtilities;

public class GameLoop implements Runnable
{
	private Thread _gameThread;
	private MapPanel _gamePanel;
	private final double _updateCap = 1.0 / 60.0;
	private int _fps;
	
	public GameLoop(MapPanel panel)
	{
		this._gamePanel = panel;
	}
	
	public void startGame()
	{
		this._gameThread = new Thread(this);
		this._gameThread.start();
	}
	
	@Override
	public void run()
	{
		boolean shouldRender = false;
		double firstTime = 0,
				lastTime = System.nanoTime() / 1000000000.0,
				passedTime = 0,
				unprocessedTime = 0,
				frameTime = 0;
		
		int frames = 0;
		
		while (this._gamePanel.isRunning())
		{
			shouldRender = false;
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while (unprocessedTime >= _updateCap)
			{
				unprocessedTime -= _updateCap;
				shouldRender = true;
				this.tick();
				
				if (frameTime >= 1.0)
				{
					frameTime = 0;
					_fps = frames;
					frames = 0;
					System.out.println("FPS: " + _fps);
				}
			}
			
			if (shouldRender)
			{
				render();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void render()
	{
		SwingUtilities.invokeLater(() -> _gamePanel.repaint());
	}
	
	private void tick()
	{
		SwingUtilities.invokeLater(() -> _gamePanel.setLogic());
	}
}
