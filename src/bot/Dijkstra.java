package bot;
import java.util.LinkedList;

/**
 * The Dijkstra Class -
 * Used in order to calculate the shortest paths from pairs of blocks.
 */
public class Dijkstra
{
	private static Graph _graph;
	
	/**
	 * Method - Calculates and returns the shortest path from the given source to the given target, using Dijkstra's algorithm.
	 * @param source
	 * @param target
	 * @return
	 */
	public static LinkedList<Edge> getShortestPathTo(int source, int target)
	{
		_graph.resetMinDistances();
		
		int visitedVertexes = 0;
		LinkedList<Vertex> accessedVertexes = new LinkedList<Vertex>();
		Vertex ver = _graph.getVertex(source);
		ver.setMinDistance(0);
		while (visitedVertexes < _graph.getNumberOfVertexes())
		{
			ver.setVisited(true);
			visitedVertexes++;
			for (Edge edg : ver.getAdjacencies())
			{
				if (edg.getTarget().getMinDistance() == Double.POSITIVE_INFINITY)
				{
					accessedVertexes.add(edg.getTarget());
				}
				if (edg.getWeight().getActualWeight() + ver.getMinDistance() < edg.getTarget().getMinDistance())
				{
					edg.getTarget().setPrevious(ver);
					edg.getTarget().setMinDistance(edg.getWeight().getActualWeight() + ver.getMinDistance());	
				}
			}
			if (ver.getBlockID() == target) // If we found the shortest path to the target block, the algorithm can be stopped.
			{
				break;
			}
			ver = getMinDistanceVertex(accessedVertexes);
		}
		return getCurrentPath(_graph.getVertex(target)); // Returning the shortest path found.
	}
	
	private static Vertex getMinDistanceVertex(LinkedList<Vertex> list)
	{
		double min = Double.POSITIVE_INFINITY;
		Vertex minVertex = null;
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getMinDistance() < min && list.get(i).isVisited() == false)
			{
				min = list.get(i).getMinDistance();
				minVertex = list.get(i);
			}
		}
		return minVertex;
	}
	
	/**
	 * Method - Returns the current minimum path that leads to the given vertex from the origin vertex.
	 * @param ver
	 * @return
	 */
	private static LinkedList<Edge> getCurrentPath(Vertex ver)
	{
		LinkedList<Edge> path = new LinkedList<Edge>();
		Vertex targetVer = ver, tempVer = targetVer.getPrevious();
		while (tempVer != null)
		{
			for (Edge edg : tempVer.getAdjacencies())
			{
				if (edg.getTarget() == targetVer)
				{
					path.addFirst(edg);
					break;
				}
			}
			targetVer = tempVer;
			tempVer = targetVer.getPrevious();
		}
		return path;
	}
	
	/**
	 * Method - Sets the graph the class should work on.
	 * @param graph
	 */
	public static void setGraph(Graph graph)
	{
		_graph = graph;
	}
}
