package map;
import characters.GameCharacter;

/**
 * The ObjectBlock Class - 
 * Blocks that extend this class affect the characters in a unique way when collision occurs.
 * 
 * XML ID's:
 * 1-3 - Ladder blocks
 * ~More to be listed~
 */
public abstract class ObjectBlock extends Block
{
	public ObjectBlock(int tile, int x, int y)
	{
		super(tile, x, y);
	}
	
	/**
	 * Abstract Method - The effect a collision with the block causes. Each
	 * variation of block implements it in its own way.
	 * 
	 * @param obj
	 */
	public abstract void affectLivingObj(GameCharacter gc);
}
