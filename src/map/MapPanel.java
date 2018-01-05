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
	private int _rows, _columns, _blockSize;
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
	public MapPanel(int mapID ,int rows, int cols, int bSize)
	{
		 this._rows = rows;
		 this._columns = cols;
		 this._blockSize = bSize;
		 this._imgBackground = new Img(String.format("images\\backgrounds\\bgMap%d.png", mapID), 0, 0, 1280, 720);
		 
		 /**
		  * Initializing the terrain tileset matrix.
		  */
		 this._terrainTileSet = new Img[4][5];
		 for (int i = 0; i < _terrainTileSet.length; i++) 
		 {
			for (int j = 0; j < _terrainTileSet[0].length; j++) 
			{
				this._terrainTileSet[i][j] = new Img(String.format("images\\map%d\\terrainTiles\\%d.png", mapID, i * 5 + j), 0, 0, this._blockSize, this._blockSize);
			}
		 }
		 
		/**
		 * Initializing the objects tileset matrix.
		 */
		this._objTileSet = new Img[3];
		for (int i = 0; i < _objTileSet.length; i++) 
		{
			this._objTileSet[i] = new Img(String.format("images\\map%d\\objTiles\\%d.png", mapID, i), 0, 0, this._blockSize, this._blockSize);
		}
		
		this._map = new Map(this._rows, this._columns, String.format("MapFiles\\Map%d\\terrainXml.xml", mapID), String.format("MapFiles\\Map%d\\objXml.xml", mapID));
	}
	
	/**
	 * Method: Draws the map onto the screen.
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
		 * Testings~
		 */
		g.translate(-MouseInfo.getPointerInfo().getLocation().x, -MouseInfo.getPointerInfo().getLocation().y);
		Graphics2D g2d = (Graphics2D)g;
		g2d.scale(1, 1);
		/**
		 * ~
		 */
		for (int i = 0; i < _rows; i++) 
		{
			for (int j = 0; j < _columns; j++) 
			{
				/**
				 * Drawing the first layer (the terrain) -
				 */
				if (this._map.getMapTerrain()[i][j] != 0)
				{
					this._terrainTileSet[(_map.getMapTerrain()[i][j] - 1) / 5][(_map.getMapTerrain()[i][j] - 1) % 5].setImgCords(j * _blockSize, i * _blockSize);
					this._terrainTileSet[(_map.getMapTerrain()[i][j] - 1) / 5][(_map.getMapTerrain()[i][j] - 1) % 5].drawImg(g);
				}
				/**
				 * Drawing the second layer (the objects) -
				 */
				if (this._map.getMapObj()[i][j] != 0)
				{
					this._objTileSet[_map.getMapObj()[i][j] - 1].setImgCords(j * _blockSize, i * _blockSize);
					this._objTileSet[_map.getMapObj()[i][j] - 1].drawImg(g);
				}
				
			}
		}
	}

}
