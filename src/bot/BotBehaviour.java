package bot;
import java.util.LinkedList;
import characters.GameCharacter;
import characters.GameCharacterListener;
import characters.Updateable;
import map.Map;

/**
 * The BotBehaviour Class -
 * The logical mind that goes behind every game character which is not controlled by the player.
 */
public class BotBehaviour implements Updateable, GameCharacterListener
{
	private GameCharacter _char;
	private BotState _currentState;
	private LinkedList<Edge> _currentPath;
	private boolean _toTheLeft;
	private int _nextBlock;
	
	/**
	 * The Constructor Method - Initializes an instance in accordance to the given parameters.
	 * @param gc
	 * @param graph
	 */
	public BotBehaviour(GameCharacter gc)
	{
		this._char = gc;
		this._currentState = BotState.Idle;
	}
	
	private void setNextTargetBlock(int blockID)
	{
		this._nextBlock = blockID;
		this._toTheLeft = this._char.getFeetPoint().x > Map.blockIDToPoint(this._nextBlock).x;
	}
	
	@Override
	public void update()
	{
		switch (this._currentState)
		{
			case Reach:
						
				if (this._char.getFeetBlock() == this._nextBlock) // Reached closest target.
				{
					if (this._currentPath.size() == 1) // Final target block has been reached! (last edge was the final one)
					{
						this._char.stopWalking(); // (TEMP) - Should be fully deactivated
						this._currentState = BotState.Idle;
					}
					/**
					 * De-activating certain states (releasing virtual keys) -
					 */
					if (this._currentPath.get(0).getWeight().getLinkType() != DirectLinks.Climbing && this._char.isWillingToClimb()) // Last edge involved climbing that has to be de-activated.
					{
						this._char.stopClimbing();
						this._char.setWillingToClimb(false);
					}
					this._currentPath.removeFirst();
					if (this._currentPath.isEmpty() == false)
					{
						this.setNextTargetBlock(this._currentPath.get(0).getTarget().getBlockID());	
					}
				}
				else // Still did not reach the next block.
				{	
					if (this._currentPath.get(0).getWeight().getLinkType() == DirectLinks.Walking)
					{
						if (this._toTheLeft) // Next block is to the character's left.
						{
							this._char.walkLeft();
						}
						else
						{
							this._char.walkRight();
						}
					}
					else if (this._currentPath.get(0).getWeight().getLinkType() == DirectLinks.Climbing)
					{
						this._char.setWillingToClimb(true);
					}
					else if (this._currentPath.get(0).getWeight().getLinkType() == DirectLinks.Jumping)
					{
						if (this._toTheLeft) // Next block is to the character's left.
						{
							if (this._char.isCollided())
							{
								if (this._char.getFeetPoint().x <= Map.blockIDToPoint(this._char.getFeetBlock()).x + GameCharacter._walkingSpeed) // Within optimal jumping range (to the left)
								{
									this._char.setFeetX(Map.blockIDToPoint(this._char.getFeetBlock()).x + GameCharacter._walkingSpeed); // Fixing the positioning of the character before it jumps.
									this._char.jump();
								}
							}
							this._char.walkLeft();
						}
						else
						{
							if (this._char.isCollided())
							{
								if (this._char.getFeetPoint().x >= Map.blockIDToPoint(this._char.getFeetBlock()).x + Map._blockSize - GameCharacter._walkingSpeed) // Within jumping range (to the right)
								{
									this._char.setFeetX(Map.blockIDToPoint(this._char.getFeetBlock()).x + Map._blockSize - GameCharacter._walkingSpeed); // Fixing the positioning of the character before it jumps.
									this._char.jump();
								}
							}
							this._char.walkRight();
						}
					}
					else if (this._currentPath.get(0).getWeight().getLinkType() == DirectLinks.Falling)
					{
						if (this._char.isCollided())
						{
							if (this._toTheLeft) // Next block is to the character's left.
							{
								this._char.walkLeft();
							}
							else
							{
								this._char.walkRight();
							}
						}
						else // Character is mid-air, no walking is needed.
						{
							this._char.stopWalking();
						}
					}
				}
				break;
			
			case Idle:
				//**//
				break;
		}
	}

	public BotState getCurrentState()
	{
		return _currentState;
	}

	public void setCurrentState(BotState currentState)
	{
		_currentState = currentState;
	}

	public LinkedList<Edge> getCurrentPath()
	{
		return _currentPath;
	}

	public void setCurrentPath(LinkedList<Edge> currentPath)
	{
		_currentPath = currentPath;
	}

	private boolean canCalculatePath()
	{
		return this._char.isCollided() || this._char.isWillingToClimb(); // more conditions to come
	}
	
	@Override
	public void playerMoved(GameCharacter gc)
	{
		if (this._char.getLastFeetBlock() != gc.getLastFeetBlock() && this.canCalculatePath())
		{
			if (this._currentState == BotState.Idle || this._currentState == BotState.Reach && this._currentPath.get(this._currentPath.size() - 1).getTarget().getBlockID() != gc.getLastFeetBlock())
			{
				this._currentPath = Dijkstra.getShortestPathTo(this._char.getLastFeetBlock(), gc.getLastFeetBlock());
				this.setNextTargetBlock(this._currentPath.get(0).getTarget().getBlockID());
				if (this._currentState == BotState.Idle)
				{
					this._currentState = BotState.Reach;
				}
			}
		}		
	}	
}
