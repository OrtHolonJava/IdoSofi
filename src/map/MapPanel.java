package map;
import javax.swing.JPanel;

import characters.GameCharacter;
import characters.PlayerCamera;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import images.Img;

/**
 * The MapPanel Class - 
 */
public class MapPanel extends JPanel
{
	private final int _terrainTSRows = 4, _terrainTSWidth = 5, _objTSLength = 3, _charBoxWidth = 30, _charBoxHeight = 65;
	private int _rows, _columns;
	private Img _imgBackground;
	private Img[][] _terrainTileSet;
	private Img[] _objTileSet;
	private Map _map;
	private GameCharacter _playerChar;
	private PlayerCamera _playerCam;
	
	/**
	 * The Constructor Method - 
	 * Initializes an instance of the MapPanel class and its attributes.
	 * @param mapID
	 * @param rows
	 * @param cols
	 * @param bSize
	 */
	public MapPanel(int mapID ,int rows, int cols)
	{
		 this._rows = rows;
		 this._columns = cols;
		 this._imgBackground = new Img(String.format("images\\backgrounds\\bgMap%d.png", mapID), 0, 0, 1920, 1080);
		 
		/**
		 * Initializing the instance of the map logic.
		 */
		this._map = new Map(this._rows, this._columns, String.format("MapFiles\\Map%d\\terrainXml.xml", mapID), String.format("MapFiles\\Map%d\\objXml.xml", mapID));
		 
		 /**
		  * Initializing the terrain tileset matrix.
		  */
		 this._terrainTileSet = new Img[_terrainTSRows][_terrainTSWidth];
		 for (int i = 0; i < _terrainTSRows; i++) 
		 {
			for (int j = 0; j < _terrainTSWidth; j++) 
			{
				this._terrainTileSet[i][j] = new Img(String.format("images\\maps\\map%d\\terrainTiles\\%d.png", mapID, i * _terrainTSWidth + j), 0, 0, this._map.getBlockSize(), this._map.getBlockSize());
			}
		 }
		 
		/**
		 * Initializing the objects tileset matrix.
		 */
		this._objTileSet = new Img[_objTSLength];
		for (int i = 0; i < _objTSLength; i++) 
		{
			this._objTileSet[i] = new Img(String.format("images\\maps\\map%d\\objTiles\\%d.png", mapID, i), 0, 0, this._map.getBlockSize(), this._map.getBlockSize());
		}
		
		/**
		 * Initializing character-related instances.
		 */
		this._playerChar = new GameCharacter(50, 100, _charBoxWidth, _charBoxHeight);
		this._playerCam = new PlayerCamera(this._playerChar, 2f, this);
	}
	
	public void checkTerrainCollision()
	{
		Point charFeet = new Point(this._playerChar.getObjBox().x / this._map.getBlockSize(), (this._playerChar.getObjBox().y +  this._playerChar.getObjBox().height) / this._map.getBlockSize());
		if (this._map.getTerrainHashMap().get((int)(charFeet.getX() + charFeet.getY() * this._map.getMapWidth())) != null)
		{
			this._map.getTerrainHashMap().get((int)(charFeet.getX() + charFeet.getY() * this._map.getMapWidth())).affectLivingObj(this._playerChar);
		}
		else
		{
			this._playerChar.setGravity(true);
		}
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
		Graphics2D g2d = (Graphics2D)g;
		g2d.scale(this._playerCam.getScale(), this._playerCam.getScale());
		g.translate(this._playerCam.getX(), this._playerCam.getY());
		
		/**
		 * Drawing the map - 
		 */
		this.drawMap(g);
		this.markBlocks(g);
		
		/**
		 * Drawing the characters - 
		 */
		this.drawCharacter(g, this._playerChar);
		this._playerChar.setMovement();
		this._playerCam.setPosition();
	}
	
	/**
	 * Method: Draws the map onto the panel.
	 * @param g
	 */
	public void drawMap(Graphics g)
	{
		/**
		 * Drawing the terrain blocks of the map (first layer of tiles)-
		 */
		for (int key : _map.getTerrainHashMap().keySet()) 
		{
			this.drawTerrainBlock(g, key);
		}
		
		/**
		 * Drawing the object blocks of the map (second layer of tiles)-
		 */
		for (int key : _map.getObjHashMap().keySet()) 
		{
			this.drawObjBlock(g, key);
		}
	}
	
	/**
	 * Method: Draws the given character onto the panel.
	 * @param g
	 */
	public void drawCharacter(Graphics g, GameCharacter chara)
	{
		g.setColor(Color.pink);
		g.fillRect(chara.getObjBox().x, chara.getObjBox().y, chara.getObjBox().width, chara.getObjBox().height);
	}
	
	/**
	 * Method: Draws the given block onto the panel, assuming its tile values refer to the terrain tileset -
	 * @param g
	 */
	public void drawTerrainBlock(Graphics g, int blockKey)
	{
		int tile = _map.getTerrainHashMap().get(blockKey).getTile() - 1;		
		this._terrainTileSet[tile / _terrainTSWidth][tile % _terrainTSWidth].setImgCords((blockKey % _columns) * _map.getBlockSize(), (blockKey / _columns) * _map.getBlockSize());
		this._terrainTileSet[tile / _terrainTSWidth][tile % _terrainTSWidth].drawImg(g);
	}
	
	/**
	 * Method: Draws the given block onto the panel, assuming its tile values refer to the object tileset -
	 * @param g
	 */
	public void drawObjBlock(Graphics g, int blockKey)
	{
		int tile = _map.getObjHashMap().get(blockKey).getTile() - 1;		
		this._objTileSet[tile].setImgCords((blockKey % _columns) * _map.getBlockSize(), (blockKey / _columns) * _map.getBlockSize());
		this._objTileSet[tile].drawImg(g);
	}
	
	/**
	 * Method: Marks the logical blocks onto the panel.
	 */
	public void markBlocks(Graphics g)
	{
		Block tb;
		
		g.setColor(Color.GREEN);
		for (int key : _map.getTerrainHashMap().keySet()) 
		{
			tb = _map.getTerrainHashMap().get(key);
			g.drawRect(tb.getRectangle().x, tb.getRectangle().y, tb.getRectangle().width, tb.getRectangle().height);
		}
		
		g.setColor(Color.RED);
		for (int key : _map.getObjHashMap().keySet()) 
		{
			tb = _map.getObjHashMap().get(key);
			g.drawRect(tb.getRectangle().x, tb.getRectangle().y, tb.getRectangle().width, tb.getRectangle().height);
		}
	}

	public Map getMap()
	{
		return _map;
	}

}
