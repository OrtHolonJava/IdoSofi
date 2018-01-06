package map;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import images.Img;

/**
 * The MapPanel Class - 
 */
public class MapPanel extends JPanel
{
	private final int _blockSize = 30, terrainTSRows = 4, terrainTSWidth = 5, objTSLength = 3;
	private int _rows, _columns;
	private Img _imgBackground;
	private Img[][] _terrainTileSet;
	private Img[] _objTileSet;
	private Map _map;
	
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
		 this._imgBackground = new Img(String.format("images\\backgrounds\\bgMap%d.png", mapID), 0, 0, 1280, 720);
		 
		 /**
		  * Initializing the terrain tileset matrix.
		  */
		 this._terrainTileSet = new Img[terrainTSRows][terrainTSWidth];
		 for (int i = 0; i < terrainTSRows; i++) 
		 {
			for (int j = 0; j < terrainTSWidth; j++) 
			{
				this._terrainTileSet[i][j] = new Img(String.format("images\\maps\\map%d\\terrainTiles\\%d.png", mapID, i * terrainTSWidth + j), 0, 0, this._blockSize, this._blockSize);
			}
		 }
		 
		/**
		 * Initializing the objects tileset matrix.
		 */
		this._objTileSet = new Img[objTSLength];
		for (int i = 0; i < objTSLength; i++) 
		{
			this._objTileSet[i] = new Img(String.format("images\\maps\\map%d\\objTiles\\%d.png", mapID, i), 0, 0, this._blockSize, this._blockSize);
		}
		
		/**
		 * Initializing the instance of the map logic.
		 */
		this._map = new Map(this._rows, this._columns, String.format("MapFiles\\Map%d\\terrainXml.xml", mapID), String.format("MapFiles\\Map%d\\objXml.xml", mapID));
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
		g.translate(-MouseInfo.getPointerInfo().getLocation().x, -MouseInfo.getPointerInfo().getLocation().y);
		Graphics2D g2d = (Graphics2D)g;
		g2d.scale(1, 1);
		
		/**
		 * Drawing the map - 
		 */
		drawMap(g);
	}
	
	/**
	 * Method: Draws the map onto the panel.
	 * @param g
	 */
	public void drawMap(Graphics g)
	{
		for (int i = 0; i < _rows; i++) 
		{
			for (int j = 0; j < _columns; j++) 
			{
				/**
				 * Drawing the first layer (the terrain) -
				 */
				if (this._map.getMapTerrainTiles()[i][j] != 0)
				{
					this._terrainTileSet[(_map.getMapTerrainTiles()[i][j] - 1) / terrainTSWidth][(_map.getMapTerrainTiles()[i][j] - 1) % terrainTSWidth].setImgCords(j * _blockSize, i * _blockSize);
					this._terrainTileSet[(_map.getMapTerrainTiles()[i][j] - 1) / terrainTSWidth][(_map.getMapTerrainTiles()[i][j] - 1) % terrainTSWidth].drawImg(g);
				}
				/**
				 * Drawing the second layer (the objects) -
				 */
				if (this._map.getMapObjTiles()[i][j] != 0)
				{
					this._objTileSet[_map.getMapObjTiles()[i][j] - 1].setImgCords(j * _blockSize, i * _blockSize);
					this._objTileSet[_map.getMapObjTiles()[i][j] - 1].drawImg(g);
				}
				
			}
		}
	}

}
