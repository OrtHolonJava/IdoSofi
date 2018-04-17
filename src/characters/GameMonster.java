package characters;

import java.util.LinkedList;

/**
 * The GameMonster class -
 * Small monsters, cannot climb, damage the player by intersecting it.
 */
public class GameMonster extends LivingObject
{
	private static LinkedList<GameMonster> _monsterList = new LinkedList<GameMonster>();
	
	public LinkedList<GameMonster> getMonsterList()
	{
		return _monsterList;
	}
	
	public GameMonster(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setGravity(boolean val)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyGravity()
	{
		if (this._isCollided)
		{
			this._movementY = 0;
			this._isGravityApplied = false;
		}
		else
		{
			this._isGravityApplied = true;
			if (this._movementY < Gravity._maxGForce)
			{
				this._movementY += 1;
			}
			this.setCurrState(MonsterState.Falling);
		}
		
	}
	
	public MonsterState getCurrState()
	{
		return MonsterState.values()[this._currState];
	}
	
	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void setCollidedState(boolean val)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void walkLeft()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void walkRight()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopWalking()
	{
		// TODO Auto-generated method stub
		
	}

}
