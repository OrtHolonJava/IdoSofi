package map;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Map Class -
 */
public class Map 
{
	private int _size;
	private int _counter = 0;
	private int[][] _mapTerrainTiles, _mapObjTiles; // Each cell value represents the tile image that should be drawn at its area.
	
	/**
	 * The Constructor Method -
	 * Initializes an instance of the Map class.
	 * Sets the map's terrain and objects matrix in accordance to the given XML files.
	 * @param size
	 * @param sizeW
	 * @param terrainXmlFileName
	 * @param objXmlFileName
	 */
	public Map(int size, int sizeW, String terrainXmlFileName, String objXmlFileName)
	{
		this._mapTerrainTiles = new int[size][sizeW];
		this._mapObjTiles = new int[size][sizeW];
		this._size = sizeW;

		try 
		{
			File terrainXml = new File(terrainXmlFileName), objXml = new File(objXmlFileName);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(terrainXml);

			if (doc.hasChildNodes()) 
			{
				readNode(doc.getChildNodes(), this._mapTerrainTiles);
			}
			
			doc = docBuilder.parse(objXml);
			this._counter = 0;
			if (doc.hasChildNodes()) 
			{
				readNode(doc.getChildNodes(), this._mapObjTiles);
			}
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method: Returns the map's terrain tiles matrix.
	 */
	public int[][] getMapTerrainTiles() 
	{
		return this._mapTerrainTiles;
	}
	
	/**
	 * Method: Returns the map's object tiles matrix.
	 */
	public int[][] getMapObjTiles() 
	{
		return this._mapObjTiles;
	}
	
	/**
	 * Method: Receives the XML file's node list and sets the map's matrix in accordance to its data.
	 * @param nodeList
	 */
	private void readNode(NodeList nodeList, int[][] map) 
	{
		for (int count = 0; count < nodeList.getLength(); count++) 
		{
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				if (tempNode.hasAttributes()) 
				{
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) 
					{
						Node node = nodeMap.item(i);
						map[_counter/_size][_counter%_size] = Integer.parseInt(node.getNodeValue()); 
						_counter++;
					}
				}

				if (tempNode.hasChildNodes()) 
				{
					readNode(tempNode.getChildNodes(), map);
				}
			}
		}
	}

}
