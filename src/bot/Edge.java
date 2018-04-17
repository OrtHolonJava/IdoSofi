package bot;

public class Edge
{
	  private Vertex _target;
      private Weight _weight;
      
      public Edge(Vertex target, Weight weight)
      {
    	  this._target = target;
    	  this._weight = weight;
      }
      
      public Vertex getTarget()
      {
    	  return this._target;
      }

      public Weight getWeight()
      {
    	  return this._weight;
      }
      
      public String toString()
      {
    	  return "Target Vertex: " + this._target.getBlockID() + ", Weight: " + this._weight.toString() + " - "; 
      }
}
