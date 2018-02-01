package map;

import java.io.File;
import java.util.HashMap;
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
	public static final int _blockSize = 30;
	private int _sizeW, _sizeH;
	private int _counter = 0;

	// Each cell value represents the tile image that should be drawn at its
	// area (graphical purpose).
	// Each node represents an 'actual' block in the map (logical purpose).
	private HashMap<Integer, TerrainBlock> _terrainHashMap;
	private HashMap<Integer, ObjectBlock> _objHashMap;

	/**
	 * The Constructor Method - Initializes an instance of the Map class. Sets
	 * the map's terrain and objects matrix in accordance to the given XML
	 * files.
	 * 
	 * @param size
	 * @param sizeW
	 * @param terrainXmlFileName
	 * @param objXmlFileName
	 */
	public Map(int size, int sizeW, String terrainXmlFileName, String objXmlFileName)
	{
		// this._mapMatrix = new Block[size][sizeW];
		this._terrainHashMap = new HashMap<Integer, TerrainBlock>();
		this._objHashMap = new HashMap<Integer, ObjectBlock>();
		this._sizeW = sizeW;
		this._sizeH = size;

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
				readNode(doc.getChildNodes(), BlockType.Terrain);
			}

			doc = docBuilder.parse(objXml);
			this._counter = 0;
			if (doc.hasChildNodes())
			{
				readNode(doc.getChildNodes(), BlockType.Object);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Method: Receives the XML file's node list and sets the map's matrix in
	 * accordance to its data.
	 * 
	 * @param nodeList
	 */
	private void readNode(NodeList nodeList, BlockType mode)
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
							if (mode == BlockType.Terrain)
							{
								this._terrainHashMap.put(_counter, new TerrainBlock(Integer.parseInt(node.getNodeValue()), Integer.parseInt(node.getNodeValue()) <= 5));
							}
							else
							{
								this._objHashMap.put(_counter, new ObjectBlock(Integer.parseInt(node.getNodeValue())));
							}
						}
						this._counter++;
					}
				}

				if (tempNode.hasChildNodes())
				{
					readNode(tempNode.getChildNodes(), mode);
				}
			}
		}
	}

	/**
	 * Getters and Setters:
	 */
	public HashMap<Integer, TerrainBlock> getTerrainHashMap()
	{
		return _terrainHashMap;
	}

	public void setTerrainHashMap(HashMap<Integer, TerrainBlock> terrainHashMap)
	{
		_terrainHashMap = terrainHashMap;
	}

	public HashMap<Integer, ObjectBlock> getObjHashMap()
	{
		return _objHashMap;
	}

	public void setObjHashMap(HashMap<Integer, ObjectBlock> objHashMap)
	{
		_objHashMap = objHashMap;
	}

	public int getMapWidth()
	{
		return this._sizeW;
	}

	public int getMapHeight()
	{
		return this._sizeH;
	}
}
