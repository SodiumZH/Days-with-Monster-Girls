package com.sodium.dwmg.entities.ai.goals.target;

import java.util.EnumSet;

import com.sodium.dwmg.entities.IBefriendedMob;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class BefriendedOwnerHurtByTargetGoal extends BefriendedTargetGoal {

	private LivingEntity ownerLastHurtBy;
	private int timestamp;

	public BefriendedOwnerHurtByTargetGoal(IBefriendedMob inMob) {
		super(inMob, false);
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		allowedStates.add(WANDER);
		allowedStates.add(FOLLOW);
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean canUse() {
		if (isDisabled())
			return false;
		LivingEntity livingentity = mob.getOwner();
		if (livingentity == null) {
			return false;
		} else {
			this.ownerLastHurtBy = livingentity.getLastHurtByMob();
			int i = livingentity.getLastHurtByMobTimestamp();
			if (i == this.timestamp)
				return false;
			else if (!this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT))
				return false;
			else if (!mob.wantsToAttack(this.ownerLastHurtBy, livingentity))
				return false;
			else return true;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void start() {
		getMob().setTarget(this.ownerLastHurtBy);
		LivingEntity livingentity = mob.getOwner();
		if (livingentity != null) {
			this.timestamp = livingentity.getLastHurtByMobTimestamp();
		}

		super.start();
	}

}
