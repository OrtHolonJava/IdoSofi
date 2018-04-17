package bot;
import java.util.ArrayList;

import map.BlockType;
import map.MapPanel;

public class Vertex implements Comparable<Vertex>
{
	private ArrayList<Edge> _adjacencies;
	private double _minDistance = Double.POSITIVE_INFINITY;
	private Vertex _previous;
	private boolean _visited;
	private int _blockID;
	private boolean _isRightCorner, _isLeftCorner;
	private BlockType _blockType;
	
	public Vertex(int id, int tileID, boolean isCorner, BlockType bType)
	{
		this._adjacencies = new ArrayList<Edge>();
		this._blockID = id;
		this._blockType = bType;
		this._visited = false;
		if (isCorner)
		{
			this._isLeftCorner = tileID == 1;
			this._isRightCorner = tileID == MapPanel._terrainTSWidth;
		}
		else
		{
			this._isLeftCorner = this._isRightCorner = false;
		}
	}
	
	public int getBlockID()
	{
		return this._blockID;
	}

	public BlockType getBlockType()
	{
		return _blockType;
	}

	public ArrayList<Edge> getAdjacencies()
	{
		return this._adjacencies;
	}

	public void addAdjacentVertex(Vertex vertex, Weight weight)
	{
		this._adjacencies.add(new Edge(vertex, weight));
	}

	public double getMinDistance()
	{
		return this._minDistance;
	}

	public void setMinDistance(double minDistance)
	{
		this._minDistance = minDistance;
	}

	public Vertex getPrevious()
	{
		return this._previous;
	}

	public void setPrevious(Vertex previous)
	{
		this._previous = previous;
	}

	public boolean isRightCorner()
	{
		return _isRightCorner;
	}

	public boolean isLeftCorner()
	{
		return _isLeftCorner;
	}
	
	public boolean isCorner()
	{
		return isLeftCorner() || isRightCorner();
	}
	
	public void setVisited(boolean vis)
	{
		this._visited = vis;
	}
	
	public boolean isVisited()
	{
		return this._visited;
	}
	
	@Override
	public int compareTo(Vertex vertex)
	{
		return this._blockID - vertex._blockID;
	}	
}
