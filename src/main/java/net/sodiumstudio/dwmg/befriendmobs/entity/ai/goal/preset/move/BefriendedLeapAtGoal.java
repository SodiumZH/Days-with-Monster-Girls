package net.sodiumstudio.dwmg.befriendmobs.entity.ai.goal.preset.move;

import java.util.EnumSet;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.sodiumstudio.befriendmobs.entity.IBefriendedMob;
import net.sodiumstudio.befriendmobs.entity.ai.goal.BefriendedMoveGoal;

/** Adjusted from HMaG-LeapAtTargetGoal2 */
public abstract class BefriendedLeapAtGoal extends BefriendedMoveGoal
{
	
	protected Optional<Vec3> targetPos = Optional.empty();
	protected Optional<Double> lookY = Optional.empty();
	protected final float yd;
	protected final float xzd;
	protected final float maxAttackDistance;
	protected final int chance;

	public void setTargetPos(@Nullable Entity target)
	{
		if (target == null)
		{
			removeTargetPos();
		}
		else
		{
			targetPos = Optional.of(target.position());
			lookY = Optional.of(target instanceof LivingEntity ? target.getEyeY() : (target.getBoundingBox().minY + target.getBoundingBox().maxY) / 2.0D);	// From LookControl#getWantedY
		}
	}
	
	public void setTargetPos(Vec3 target)
	{
		if (target == null)
		{
			removeTargetPos();
		}
		else
		{
			targetPos = Optional.of(target);
			lookY = Optional.of(target.y);
		}
	}
	
	public void removeTargetPos()
	{
		targetPos = Optional.empty();
		lookY = Optional.empty();
	}
	
	public BefriendedLeapAtGoal(IBefriendedMob mob, float yd)
	{
		this(mob, yd, 0.4F);
	}

	public BefriendedLeapAtGoal(IBefriendedMob mob, float yd, float xzd)
	{
		this(mob, yd, xzd, 4.0F, 5);
	}

	public BefriendedLeapAtGoal(IBefriendedMob mob, float yd, float xzd, float maxAttackDistance, int chance)
	{
		super(mob);
		this.yd = yd;
		this.xzd = xzd;
		this.maxAttackDistance = maxAttackDistance;
		this.chance = chance;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean checkCanUse()
	{
		if (this.mob.asMob().isVehicle())
		{
			return false;
		}
		else
		{
			//this.target = this.mob.asMob().getTarget();

			if (!this.targetPos.isPresent())
			{
				return false;
			}
			else
			{
				if (canLeap())
				{
					if (!this.mob.asMob().isOnGround())
					{
						return false;
					}
					else
					{
						return this.mob.asMob().getRandom().nextInt(reducedTickDelay(this.chance)) == 0;
					}
				}
				else
				{
					return false;
				}
			}
		}
	}

	@Override
	public boolean checkCanContinueToUse()
	{
		return !this.mob.asMob().isOnGround();
	}

	@Override
	public void start()
	{
		//this.mob.asMob().setAggressive(true);
		super.start();
		Vec3 vec3 = this.mob.asMob().getDeltaMovement();
		Vec3 vec31 = new Vec3(this.targetPos.get().x - this.mob.asMob().getX(), 0.0D, this.targetPos.get().z - this.mob.asMob().getZ());

		if (vec31.lengthSqr() > 1.0E-7D)
		{
			vec31 = vec31.normalize().scale(this.getXZD()).add(vec3.scale(0.2D));
		}

		this.mob.asMob().setDeltaMovement(vec31.x, this.getYD(), vec31.z);
	}

	@Override
	public void stop()
	{
		super.stop();
		//this.mob.asMob().setAggressive(false);
	}

	@Override
	public void tick()
	{
		if (this.targetPos.isPresent())
		{
			this.mob.asMob().getLookControl().setLookAt(this.targetPos.get().x, this.lookY.get(), this.targetPos.get().z, 30.0F, 30.0F);
		}

		super.tick();
	}

	protected boolean canLeap()
	{
		double d0 = this.mob.asMob().distanceToSqr(this.targetPos.get());
		return !(d0 < 4.0D) && !(d0 > this.getMaxAttackDistanceSqr());
	}
	
	protected final double getMaxAttackDistanceSqr()
	{
		return (double)this.getMaxAttackDistance() * (double)this.getMaxAttackDistance();
	}

	public float getMaxAttackDistance()
	{
		return this.maxAttackDistance;
	}

	public double getYD()
	{
		return this.yd;
	}

	public double getXZD()
	{
		return this.xzd;
	}

}
