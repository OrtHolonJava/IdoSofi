package bot;

import java.util.HashMap;

import characters.GameCharacter;
import map.Block;
import map.BlockMapKey;
import map.Map;

public class Graph
{
	private HashMap<Integer, Vertex> _graphHashMap;
	
	public Graph()
	{
		this._graphHashMap = new HashMap<Integer, Vertex>();
	}

	public void addToGraph(Vertex ver, int key)
	{
		if (this._graphHashMap.get(key) == null)
		{
			this._graphHashMap.put(key, ver);
		}
	}

	public Vertex getVertex(int key)
	{
		return this._graphHashMap.get(key);
	}
	
	public int getNumberOfVertexes()
	{
		return this._graphHashMap.values().size();
	}
	
	/**
	 * Used for debugging!
	 */
	public void printUnvisitedVertexes()
	{
		for (Vertex ver : this._graphHashMap.values())
		{
			if (ver.isVisited() == false)
			{
				System.out.println("Unvisited: " + ver.getBlockID());
			}
		}
	}
	
	/**
	 * Method - Linking the vertexes of the graph in accordance to the map's
	 * features.
	 */
	public void bulidEdges()
	{
		int verDistance;
		for (Vertex ver1 : _graphHashMap.values())
		{
			for (Vertex ver2 : _graphHashMap.values())
			{
				verDistance = ver1.compareTo(ver2);

				/**
				 * Linking ver1 with blocks that are walkable from it -
				 */
				if (Math.abs(verDistance) == 1)
				{
					ver1.addAdjacentVertex(ver2, new Weight(DirectLinks.Walking));
				}

				/**
				 * Linking ver1 with blocks that are climbable from it -
				 */
				else if (verDistance == Map._sizeW)
				{
					ver1.addAdjacentVertex(ver2, new Weight(DirectLinks.Climbing));
				}

				/**
				 * Linking ver1 with blocks that are fallable from it -
				 */
				else if (Weight.isFallable(this, ver1, ver2))
				{
					ver1.addAdjacentVertex(ver2, new Weight(DirectLinks.Falling, Weight.calculateFallingWeight(Math.abs(verDistance) / Map._sizeW)));
				}

				/**
				 * Linking ver1 with blocks that are jumpable from it -
				 */
				else if (Weight.isJumpable(this, ver1, ver2, GameCharacter._jumpingHeight, GameCharacter._flyingSpeed) || Weight.isJumpable(this, ver1, ver2, GameCharacter._jumpingHeight, -GameCharacter._flyingSpeed))
				{
					ver1.addAdjacentVertex(ver2, new Weight(DirectLinks.Jumping, Weight.calculateJumpingWeight(Math.abs(ver1.getBlockID() % Map._sizeW - ver2.getBlockID() % Map._sizeW))));
				}
			}
		}
	}
	
	/**
	 * Method - Sets the minimum distance of each vertex to infinity again (used before a re-calculation of a shortest path).
	 */
	public void resetMinDistances()
	{
		for (Vertex ver : _graphHashMap.values())
		{
			ver.setMinDistance(Double.POSITIVE_INFINITY);
			ver.setPrevious(null);
			ver.setVisited(false);
		}
	}
	
	public HashMap<Integer, Vertex> getHashmap()
	{
		return this._graphHashMap;
	}
}
