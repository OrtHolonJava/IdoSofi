package map;

import characters.LivingObject;

public class SpikeBlock extends Block
{
	private static final int _knockbackX = 15;
	
	public SpikeBlock(int tile, int x, int y)
	{
		super(tile, x, y);
	}

	@Override
	public void affectLivingObj(LivingObject obj)
	{
		obj.setSlide((obj.isRight()) ?  -_knockbackX : _knockbackX);
	}
}
