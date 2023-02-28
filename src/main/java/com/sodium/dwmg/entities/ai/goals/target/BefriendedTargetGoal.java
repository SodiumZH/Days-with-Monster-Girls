package com.sodium.dwmg.entities.ai.goals.target;

import java.util.HashSet;

import com.sodium.dwmg.entities.IBefriendedMob;
import com.sodium.dwmg.entities.ai.BefriendedAIState;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

public abstract class BefriendedTargetGoal extends TargetGoal {
	
	// for simplification
	protected static final BefriendedAIState WAIT = BefriendedAIState.WAIT;
	protected static final BefriendedAIState FOLLOW = BefriendedAIState.FOLLOW;
	protected static final BefriendedAIState WANDER = BefriendedAIState.WANDER;
	
	protected IBefriendedMob mob = null;
	protected HashSet<BefriendedAIState> allowedStates = new HashSet<BefriendedAIState>();
	protected boolean isBlocked = false;
	
	public BefriendedTargetGoal(Mob pMob, boolean pMustSee) {
		super(pMob, pMustSee);
		mob = (IBefriendedMob)pMob;
	}

	public BefriendedTargetGoal(IBefriendedMob pMob, boolean pMustSee) {
		super((Mob)pMob, pMustSee);
		mob = pMob;
	}
	
	public boolean isStateAllowed()
	{
		return allowedStates.contains(mob.getAIState());
	}
	
	public void allowAllStates()
	{
		for (BefriendedAIState state : BefriendedAIState.values())
			allowedStates.add(state);
	}
	
	// Disable this goal
	public void blockGoal()
	{
		isBlocked = true;
	}
	
	public void resumeGoal()
	{
		isBlocked = false;
	}
	
	public boolean isDisabled()
	{
		return isBlocked || !allowedStates.contains(mob.getAIState());
	}
	
	public LivingEntity getLiving()
	{
		return (LivingEntity)mob;
	}
	
	public Mob getMob()
	{
		return (Mob)mob;
	}
	
	public PathfinderMob getPathfinder()
	{
		return (PathfinderMob)mob;
	}
		
	@Override
	public boolean canUse() {
		return false;
	}

}