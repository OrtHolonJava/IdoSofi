package bot;
import java.awt.Point;
import java.util.HashMap;

import characters.GameCharacter;
import characters.Gravity;
import map.Block;
import map.BlockMapKey;
import map.BlockType;
import map.Map;

public class Weight
{
	private static final double[] _linkWeights = {(double)Map._blockSize / 4, (double)Map._blockSize / 3, (double)Map._blockSize / 3, (double)Map._blockSize / Gravity._maxGForce}; // The base weight for each direct link except falling.
	private double _weight;
	private DirectLinks _linkType;
	
	public Weight(DirectLinks linkType, double weight)
	{
		this._linkType = linkType;
		this._weight = weight;
	}
	
	public Weight(DirectLinks linkType)
	{
		this(linkType, _linkWeights[linkType.ordinal()]);
	}
	
	/**
	 * Method - Calculate the weight of a falling link.
	 * @param movementSpeedX
	 * @param movementSpeedY
	 * @return
	 */
	public static double calculateFallingWeight(int ver_Y_Distance)
	{
//		double weight = _linkWeights[DirectLinks.Walking.ordinal()];
//		for (int i = 1; i <= ver_Y_Distance; i++)
//		{
//			weight += Map._blockSize / ((i > Gravity._maxGForce) ? Gravity._maxGForce : i);
//		}
//		return weight;
		
		return _linkWeights[DirectLinks.Falling.ordinal()] * ver_Y_Distance +_linkWeights[DirectLinks.Walking.ordinal()];
	}
	
	public static double calculateJumpingWeight(int ver_X_Distance)
	{
		return _linkWeights[DirectLinks.Jumping.ordinal()] * ver_X_Distance;
	}
	
	public static boolean isJumpable(Graph graph, Vertex ver1, Vertex ver2, int jumpingHeight, int movementX)
	{
		/**
		 * Both block should be terrain type.
		 */
		if (ver1.getBlockType() != BlockType.Terrain || ver2.getBlockType() != BlockType.Terrain)
		{
			return false;
		}
		Point startingPoint = Map.blockIDToPoint(ver1.getBlockID());
		startingPoint.x += (movementX > 0) ? (Map._blockSize - GameCharacter._walkingSpeed) : GameCharacter._walkingSpeed; // Jumping from the edge of the block.
		int targetY = Map.blockIDToPoint(ver2.getBlockID()).y, movementY = -jumpingHeight;
		while (movementY < 0 || startingPoint.y < targetY)
		{
			startingPoint.y += movementY;
			startingPoint.x += movementX;
			
			if (graph.getVertex(Map.pointToBlockID(startingPoint)) != null && graph.getVertex(Map.pointToBlockID(startingPoint)).getBlockType() == BlockType.Terrain) // If a terrain block exists within the calculated path.
			{
				return Map.pointToBlockID(startingPoint) == ver2.getBlockID();
			}
			if (movementY < Gravity._maxGForce) movementY++;
		}
		return Map.pointToBlockID(startingPoint) == ver2.getBlockID();
	}
	
	public static boolean isFallable(Graph graph, Vertex ver1, Vertex ver2)
	{
		if (!ver1.isCorner())
		{
			return false;
		}
		int blockID = ver1.getBlockID();
		blockID += (ver1.isRightCorner()) ? 1 : -1 ;
		blockID += Map._sizeW;
		while (blockID < Map._sizeW * Map._sizeH - 1)
		{
			if (graph.getVertex(blockID) != null) // If a blocks exists within the calculated path.
			{
				return blockID == ver2.getBlockID();
			}
			blockID += Map._sizeW;
		}
		return false;
	}
	
	public double getActualWeight()
	{
		return this._weight;
	}

	public DirectLinks getLinkType()
	{
		return _linkType;
	}
	
    public String toString()
    {
    	return "[ Link Type: " + this._linkType.toString() + ", Actual Weight: " + this._weight + " ]"; 
    }
}
