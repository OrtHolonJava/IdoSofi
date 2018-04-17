package map;
import javax.swing.JPanel;

import animation.AnimationTimer;
import bot.BotBehaviour;
import characters.GameCharacter;
import characters.LivingObject;
import characters.PlayerCamera;
import characters.PlayerKeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import images.Img;

/**
 * The MapPanel Class -
 */
public class MapPanel extends JPanel
{
	public static final int _terrainTSRows = 4, _terrainTSWidth = 5, _objTSLength = 4;
	private Img _imgBackground;
	private Img[][] _terrainTileSet;
	private Img[] _objTileSet;
	private Map _map;
	private GameCharacter _playerChar, _enemyChar;
	private BotBehaviour _enemyBot;
	private PlayerCamera _playerCam;
	private PlayerKeyListener _keyListener;
	private boolean _isRunning;
	
	/**
	 * The Constructor Method - Initializes an instance of the MapPanel class
	 * and its attributes.
	 * 
	 * @param mapID
	 * @param rows
	 * @param cols
	 * @param bSize
	 */
	public MapPanel(int mapID)
	{
		this.setOpaque(false);
		
		this._imgBackground = new Img(String.format("images\\backgrounds\\bgMap%d.png", mapID), 0, 0, 1920, 1080);
		
		/**
		 * Initializing the instance of the map logic.
		 */
		this._map = new Map(String.format("MapFiles\\Map%d\\terrainXml.xml", mapID), String.format("MapFiles\\Map%d\\objXml.xml", mapID));

		/**
		 * Initializing the terrain tileset matrix.
		 */
		this._terrainTileSet = new Img[_terrainTSRows][_terrainTSWidth];
		for (int i = 0; i < _terrainTSRows; i++)
		{
			for (int j = 0; j < _terrainTSWidth; j++)
			{
				this._terrainTileSet[i][j] = new Img(String.format("images\\maps\\map%d\\terrainTiles\\%d.png", mapID, i * _terrainTSWidth + j), 0, 0, Map._blockSize, Map._blockSize);
			}
		}

		/**
		 * Initializing the objects tileset matrix.
		 */
		this._objTileSet = new Img[_objTSLength];
		for (int i = 0; i < _objTSLength; i++)
		{
			this._objTileSet[i] = new Img(String.format("images\\maps\\map%d\\objTiles\\%d.png", mapID, i), 0, 0, Map._blockSize, Map._blockSize);
		}

		/**
		 * Initializing character-related instances.
		 */
		this._playerChar = new GameCharacter(Map.blockIDToPoint(5767).x, Map.blockIDToPoint(5767).y - GameCharacter._charBoxHeight, GameCharacter._charBoxWidth, GameCharacter._charBoxHeight, 1);
		this._playerCam = new PlayerCamera(this._playerChar, 2f, this);
		this._keyListener = new PlayerKeyListener(this._playerChar);
		this.addKeyListener(this._keyListener);
		
		/**
		 * Initializing enemy-related instances.
		 */
		this._enemyChar = new GameCharacter(Map.blockIDToPoint(5767).x, Map.blockIDToPoint(5767).y - GameCharacter._charBoxHeight, GameCharacter._charBoxWidth, GameCharacter._charBoxHeight, 1);
		this._enemyBot = new BotBehaviour(this._enemyChar);
		this._playerChar.addListener(this._enemyBot);
		
		/**
		 * The game is now at a running state.
		 */
		this._isRunning = true;
		AnimationTimer.startUniversalTimer();
	}

	public boolean isRunning()
	{
		return this._isRunning;
	}

	/**
	 * Method: Checking if a living object is collided with some terrain block.
	 */
	public void checkTerrainCollision(LivingObject liveObj)
	{
		int feetBlock = liveObj.getFeetBlock();
		Block tempBlock = this._map.getBlockHashMap().get(new BlockMapKey(feetBlock, BlockType.Terrain));
		if (tempBlock != null)
		{
			tempBlock.affectLivingObj(liveObj);
			return;
		}
		
		/**
		 * De-effecting the floor block's effect - 
		 */
		liveObj.setCollidedState(false);
	}
	
	/**
	 * Method: Checking if a living object is collided with some object block.
	 */
	public void checkObjCollision(GameCharacter gameChar)
	{
		int charBlock = gameChar.getMidBlock();
		Block tempBlock = this._map.getBlockHashMap().get(new BlockMapKey(charBlock, BlockType.Object));
		if (tempBlock != null)
		{
			tempBlock.affectLivingObj(gameChar);
		}
	}
	
	/**
	 * Method: Sets the current logical state of the game.
	 */
	public void setLogic()
	{
		this._keyListener.processInput();
		this._enemyBot.update();
		this.updateGameCharacters();
		this._playerCam.setPosition();
	}
	
	/**
	 * Method: Sets the current logical state of both game characters.
	 */
	private void updateGameCharacters()
	{
		this.updateGameCharacter(this._playerChar);
		this.updateGameCharacter(this._enemyChar);
	}
	
	private void updateGameCharacter(GameCharacter gc)
	{
		this.checkTerrainCollision(gc);
		this.checkObjCollision(gc);
		gc.update();
	}
	
	/**
	 * Method: Draws everything onto the panel.
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		/**
		 * Drawing background -
		 */
		this._imgBackground.drawImg(g);
		
		this.printFPS(g);
		
		/**
		 * Activating the game camera -
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(this._playerCam.getScale(), this._playerCam.getScale());
		g2d.translate(this._playerCam.getX(), this._playerCam.getY());
		
		/**
		 * Drawing the map -
		 */
		this.drawMap(g);
		//this.markBlocks(g); // (Debug mode) 
		
		/**
		 * Drawing scroll portals -
		 */
		//this._playerPortal.drawPortal(g);
		//this._enemyPortal.drawPortal(g);
		
		/**
		 * Drawing the characters -
		 */
		this._playerChar.drawObj(g);
		this._enemyChar.drawObj(g);
		
		/**
		 * Manually collecting garbage -
		 */
		g.dispose();
	}

	/**
	 * Method: Draws the map onto the panel.
	 * 
	 * @param g
	 */
	public void drawMap(Graphics g)
	{	
		for (Block bl : _map.getBlockHashMap().values())
		{
			if (bl instanceof TerrainBlock)
			{
				this.drawTerrainBlock(g, (TerrainBlock)bl);
			}
			else
			{
				this.drawObjBlock(g, bl);	
			}
		}	
	}

	/**
	 * Method: Draws the given character onto the panel as a filled rectangle.
	 * @param g
	 */
	public void drawCharacter(Graphics g, GameCharacter gameChar)
	{
		g.setColor(Color.CYAN);
		g.fillRect(gameChar.getObjBox().x, gameChar.getObjBox().y, gameChar.getObjBox().width, gameChar.getObjBox().height);
	}

	/**
	 * Method: Draws the given block onto the panel, assuming its tile values
	 * refer to the terrain tileset -
	 * 
	 * @param g
	 */
	public void drawTerrainBlock(Graphics g, TerrainBlock tb)
	{
		int tile = tb.getTile() - 1;
		this._terrainTileSet[tile / _terrainTSWidth][tile % _terrainTSWidth].setImgCords(tb.getX(), tb.getY());
		this._terrainTileSet[tile / _terrainTSWidth][tile % _terrainTSWidth].drawImg(g);
	}

	/**
	 * Method: Draws the given block onto the panel, assuming its tile values
	 * refer to the object tileset -
	 * 
	 * @param g
	 */
	public void drawObjBlock(Graphics g, Block ob)
	{
		this._objTileSet[ob.getTile() - 1].setImgCords(ob.getX(), ob.getY());
		this._objTileSet[ob.getTile() - 1].drawImg(g);
	}

	/**
	 * Method: Marks the logical blocks onto the panel.
	 */
	public void markBlocks(Graphics g)
	{
		for (Block bl : _map.getBlockHashMap().values())
		{
			if (bl instanceof TerrainBlock)
			{
				g.setColor(Color.GREEN);
			}
			else if (bl instanceof LadderBlock)
			{
				g.setColor(Color.RED);
			}
			else if (bl instanceof SpikeBlock)
			{
				g.setColor(Color.BLACK);
			}
			g.drawRect(bl.getX(), bl.getY(), Map._blockSize, Map._blockSize);
		}
		
		Block tempBlock = this._map.getBlockHashMap().get(new BlockMapKey(this._playerChar.getMidBlock(), BlockType.Terrain));
		if (tempBlock != null)
		{
			g.setColor(Color.BLUE);
			g.drawRect(tempBlock.getX(), tempBlock.getY(), Map._blockSize, Map._blockSize);
		}
		
		/**
		 * Coloring a certain block (DEBUG MODE)
		 */
//		g.setColor(Color.MAGENTA);
//		tempBlock = this._map.getBlockHashMap().get(new BlockMapKey(1068, BlockType.Terrain));
//		g.drawRect(tempBlock.getX(), tempBlock.getY(), Map._blockSize, Map._blockSize);
	}
	
	private void printFPS(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.drawString("FPS: " + GameLoop.getCurrentFPS(), 50, 50);
		g.drawString("Last Feet Block: " + this._playerChar.getLastFeetBlock(), 50, 70);
	}
	
	public GameCharacter getMainChar()
	{
		return this._playerChar;
	}
	
	public Map getMap()
	{
		return this._map;
	}	
}
