package map;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
	
	// Each cell value represents the tile image that should be drawn at its area (graphical purpose).
	// Each node represents an 'actual' block in the map (logical purpose).
	private Block[][] _mapMatrix;
	
	private final int _blockSize = 30;
	
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
		this._mapMatrix = new Block[size][sizeW];
		this._size = sizeW;
		
		/**
		 * Reading the map's XML file and extracting its data to the matrices.
		 */
		try 
		{
			File terrainXml = new File(terrainXmlFileName), objXml = new File(objXmlFileName);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(terrainXml);

			if (doc.hasChildNodes()) 
			{
				readNode(doc.getChildNodes(), this._mapMatrix, BlockType.Terrain);
			}
			
			doc = docBuilder.parse(objXml);
			this._counter = 0;
			if (doc.hasChildNodes()) 
			{
				readNode(doc.getChildNodes(), this._mapMatrix, BlockType.Ladder);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	
	}
	
	/**
	 * Method: Returns the size of each block
	 */
	public int getBlockSize()
	{
		return this._blockSize;
	}
	
	/**
	 * Method: Returns the map's blocks matrix.
	 */
	public Block[][] getMapMatrix() 
	{
		return this._mapMatrix;
	}
	
	
	/**
	 * Method: Receives the XML file's node list and sets the map's matrix in accordance to its data.
	 * @param nodeList
	 */
	private void readNode(NodeList nodeList, Block[][] map, BlockType mode) 
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
						if (Integer.parseInt(node.getNodeValue()) != 0)
						{
							if (map[_counter/_size][_counter%_size] == null)
							{
								map[_counter/_size][_counter%_size] = new Block(_counter % _size * _blockSize, _counter / _size * _blockSize, _blockSize, Integer.parseInt(node.getNodeValue()), mode);
							}
							else
							{
								map[_counter/_size][_counter%_size].addTile(Integer.parseInt(node.getNodeValue()));
								map[_counter/_size][_counter%_size].setType(mode);
							}
						}
						_counter++;
					}
				}

				if (tempNode.hasChildNodes()) 
				{
					readNode(tempNode.getChildNodes(), map, mode);
				}
			}
		}
	}

}
