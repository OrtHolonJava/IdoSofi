package map;
import java.awt.Rectangle;
import characters.LivingObject;

public class TerrainBlock extends Block
{
	public TerrainBlock(int x, int y, int size, int tile)
	{
		super(x, y, size, tile);
	}

	@Override
	public void affectLivingObj(LivingObject obj)
	{
				
	}
}
