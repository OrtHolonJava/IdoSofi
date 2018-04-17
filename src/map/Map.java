package map;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import bot.Dijkstra;
import bot.Graph;
import bot.Vertex;

/**
 * The Map Class -
 * Instances represent the logical aspect of each map in the game.
 */
public class Map
{
	public static final int _blockSize = 30, _sizeW = 80, _sizeH = 80;
	private static final int _ladderTiles = 3, _spikeTile = 4;
	
	private HashMap<BlockMapKey, Block> _blockHashMap;
	private ScrollPortal _playerPortal, _enemyPortal; // The scroll portals of the map.
	
	/**
	 * Spawn areas. Integers represent block ID's. 
	 */
	private int _playerCharacterSpawnPoint;
	private LinkedList<Integer> _enemyCharacterSpawnPoints;
	private LinkedList<Integer> _monsterSpawnPoints;
	private Graph _mapGraph;
	
	private int _counter = 0; // Used when scanning XML file.
	
	/**
	 * The Constructor Method - Initializes an instance of the Map class. Sets the
	 * map's terrain and objects matrix in accordance to the given XML files.
	 * 
	 * @param size
	 * @param sizeW
	 * @param terrainXmlFileName
	 * @param objXmlFileName
	 */
	public Map(String terrainXmlFileName, String objXmlFileName)
	{
		this._blockHashMap = new HashMap<BlockMapKey, Block>();
		
		/**
		 * Initializing graph -
		 */
		this._mapGraph = new Graph();
		Dijkstra.setGraph(_mapGraph);
		
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
		
		/**
		 * Graph and map are now built. Time to form the edges - 
		 */
		this._mapGraph.bulidEdges();
	}

	/**
	 * Method: Receives the XML file's node list and sets the map's matrix in
	 * accordance to its data.
	 * 
	 * @param nodeList
	 */
	private void readNode(NodeList nodeList, BlockType mode)
	{
		int tileNo;
		Point blockPoint;
		
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
						tileNo = Integer.parseInt(node.getNodeValue());
						blockPoint = blockIDToPoint(_counter);
						if (tileNo != 0)
						{
							if (mode == BlockType.Terrain)
							{
								this._blockHashMap.put(new BlockMapKey(_counter, mode), new TerrainBlock(tileNo, blockPoint.x, blockPoint.y, tileNo <= MapPanel._terrainTSWidth));
								if (tileNo <= MapPanel._terrainTSWidth) // If the block is a floor block, it has to be added to the graph.
								{
									this._mapGraph.addToGraph(new Vertex(_counter, tileNo, tileNo == 1 || tileNo == MapPanel._terrainTSWidth, mode), _counter);
								}
							}
							else if (mode == BlockType.Object) // Block type - object block.
							{
								if (tileNo <= _ladderTiles) // The current object block code represents a LADDER block
								{
									this._blockHashMap.put(new BlockMapKey(_counter, mode), new LadderBlock(tileNo, blockPoint.x, blockPoint.y, tileNo > 1));
									this._mapGraph.addToGraph(new Vertex(_counter, tileNo, false, mode), _counter); // Every ladder block is also a graph vertex.
								}
								else if (tileNo == _spikeTile)
								{
									this._blockHashMap.put(new BlockMapKey(_counter, mode), new SpikeBlock(tileNo, blockPoint.x, blockPoint.y));
								}
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
	
	public static int pointToBlockID(Point p)
	{
		return p.x / _blockSize + p.y / _blockSize * _sizeW;
	}
	
	public static Point blockIDToPoint(int id)
	{
		return new Point(id % _sizeW * _blockSize, id / _sizeW * _blockSize);
	}
	
	/**
	 * Getters and Setters:
	 */
	public HashMap<BlockMapKey, Block> getBlockHashMap()
	{
		return this._blockHashMap;
	}
	
	public ScrollPortal getPlayerPortal()
	{
		return _playerPortal;
	}

	public void setPlayerPortal(ScrollPortal playerPortal)
	{
		_playerPortal = playerPortal;
	}

	public ScrollPortal getEnemyPortal()
	{
		return _enemyPortal;
	}

	public void setEnemyPortal(ScrollPortal enemyPortal)
	{
		_enemyPortal = enemyPortal;
	}
	
	public Graph getGraph()
	{
		return this._mapGraph;
	}

	public int getPlayerCharacterSpawnPoint()
	{
		return _playerCharacterSpawnPoint;
	}

	public LinkedList<Integer> getEnemyCharacterSpawnPoints()
	{
		return _enemyCharacterSpawnPoints;
	}

	public LinkedList<Integer> getMonsterSpawnPoints()
	{
		return _monsterSpawnPoints;
	}
}
