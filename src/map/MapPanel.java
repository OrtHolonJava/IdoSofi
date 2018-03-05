package map;
import javax.swing.JPanel;

import characters.Animation;
import characters.GameCharacter;
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
	private final int _terrainTSRows = 4, _terrainTSWidth = 5, _objTSLength = 3, _charBoxWidth = 30, _charBoxHeight = 65;
	private Img _imgBackground;
	private Img[][] _terrainTileSet;
	private Img[] _objTileSet;
	private Map _map;
	private GameCharacter _playerChar;
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
	public MapPanel(int mapID, int rows, int cols)
	{
		this._imgBackground = new Img(String.format("images\\backgrounds\\bgMap%d.png", mapID), 0, 0, 1920, 1080);
		
		/**
		 * Initializing the instance of the map logic.
		 */
		this._map = new Map(rows, cols, String.format("MapFiles\\Map%d\\terrainXml.xml", mapID), String.format("MapFiles\\Map%d\\objXml.xml", mapID));

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
		this._playerChar = new GameCharacter(50, 100, _charBoxWidth, _charBoxHeight, 1);
		this._playerCam = new PlayerCamera(this._playerChar, 2f, this);
		this._keyListener = new PlayerKeyListener(this._playerChar);
		this.addKeyListener(this._keyListener);
		this.setOpaque(false);
		
		/**
		 * The game is now at a running state.
		 */
		this._isRunning = true;
		Animation.startUniversalTimer();
	}

	public boolean isRunning()
	{
		return this._isRunning;
	}

	/**
	 * Method: Checking if a character is collided with some terrain block.
	 */
	public void checkTerrainCollision()
	{
		int feetBlock = this._playerChar.getFeetBlock(this._map.getMapWidth());
		TerrainBlock tempBlock = this._map.getTerrainHashMap().get(feetBlock);
		if (tempBlock != null)
		{
			tempBlock.affectLivingObj(this._playerChar);
			return;
		}
		
		/**
		 * De-effecting the block's effect - 
		 */
		this._playerChar.setCollidedState(false);
	}
	
	public void checkObjCollision()
	{
		int charBlock = this._playerChar.getMidBlock(this._map.getMapWidth());
		ObjectBlock tempBlock = this._map.getObjHashMap().get(charBlock);
		if (tempBlock != null)
		{
			tempBlock.affectLivingObj(this._playerChar);
			return;
		}
		
		this._playerChar.stopClimbing();
	}
	
	/**
	 * Method: Sets the current logical state of the game.
	 */
	public void setLogic()
	{
		this._keyListener.processInput();
		this._playerChar.update();
		this.checkTerrainCollision();
		this.checkObjCollision();
		this._playerCam.setPosition();
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

		/**
		 * Testings -
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
		 * Drawing the characters -
		 */
		this._playerChar.drawCharacter(g);
	}

	/**
	 * Method: Draws the map onto the panel.
	 * 
	 * @param g
	 */
	public void drawMap(Graphics g)
	{
		/**
		 * Drawing the terrain blocks of the map (first layer of tiles)-
		 */
		for (TerrainBlock tb : _map.getTerrainHashMap().values())
		{
			this.drawTerrainBlock(g, tb);
		}

		/**
		 * Drawing the object blocks of the map (second layer of tiles)-
		 */
		for (ObjectBlock ob : _map.getObjHashMap().values())
		{
			this.drawObjBlock(g, ob);
		}
		
	}

	/**
	 * Method: Draws the given character onto the panel.
	 * 
	 * @param g
	 */
	public void drawCharacter(Graphics g, GameCharacter chara)
	{
		g.setColor(Color.CYAN);
		g.fillRect(chara.getObjBox().x, chara.getObjBox().y, chara.getObjBox().width, chara.getObjBox().height);
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
		this._terrainTileSet[tile / _terrainTSWidth][tile % _terrainTSWidth].setImgCords(tb._x, tb._y);
		this._terrainTileSet[tile / _terrainTSWidth][tile % _terrainTSWidth].drawImg(g);
	}

	/**
	 * Method: Draws the given block onto the panel, assuming its tile values
	 * refer to the object tileset -
	 * 
	 * @param g
	 */
	public void drawObjBlock(Graphics g, ObjectBlock ob)
	{
		int tile = ob.getTile() - 1;
		this._objTileSet[tile].setImgCords(ob._x, ob._y);
		this._objTileSet[tile].drawImg(g);
	}

	/**
	 * Method: Marks the logical blocks onto the panel.
	 */
	public void markBlocks(Graphics g)
	{
		g.setColor(Color.GREEN);
		for (TerrainBlock tb : _map.getTerrainHashMap().values())
		{
			g.drawRect(tb._x, tb._y, Map._blockSize, Map._blockSize);
		}

		g.setColor(Color.RED);
		for (ObjectBlock ob : _map.getObjHashMap().values())
		{
			g.drawRect(ob._x, ob._y, Map._blockSize, Map._blockSize);
		}
		
		TerrainBlock tempBlock = this._map.getTerrainHashMap().get(this._playerChar.getFeetBlock(this._map.getMapWidth()));
		if (tempBlock != null)
		{
			g.setColor(Color.BLUE);
			g.drawRect(tempBlock._x, tempBlock._y, Map._blockSize, Map._blockSize);
		}

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
