package net.sodiumstudio.dwmg.entities.handlers.hmag;

import java.util.HashSet;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.sodiumstudio.befriendmobs.entity.befriending.BefriendableAddHatredReason;
import net.sodiumstudio.befriendmobs.entity.capability.CBefriendableMob;
import net.sodiumstudio.nautils.EntityHelper;
import net.sodiumstudio.nautils.NbtHelper;

public class HandlerBanshee extends HandlerSkeletonGirl
{
	@Override
	public boolean additionalConditions(Player player, Mob mob)
	{
		return witherRoseCondition(mob) && player.hasEffect(MobEffects.WITHER);
	}
	
	@Override
	public HashSet<BefriendableAddHatredReason> getAddHatredReasons() {
		HashSet<BefriendableAddHatredReason> set = new HashSet<BefriendableAddHatredReason>();
		set.add(BefriendableAddHatredReason.ATTACKED);
		set.add(BefriendableAddHatredReason.ATTACKING);
		set.add(BefriendableAddHatredReason.HIT);
		return set;
	}
		
	@Override
	public int getHatredDurationTicks(BefriendableAddHatredReason reason)
	{
		switch (reason)
		{
		case ATTACKED:
			return 300 * 20;
		case ATTACKING:
			return 30 * 20; 
		case HIT:
			return 30 * 20;
		
		default:
			return 0;				
		}
	}
	
	@Override
	public void serverTick(Mob mob)
	{	
		this.forAllPlayersInProcess(mob, (p) -> {
			if (p != null && mob != null && p.distanceToSqr(mob) > 32 * 32)
				this.interrupt(p, mob, false);
		});
		if (this.isInProcess(mob))
		{
			mob.removeEffect(MobEffects.WITHER);
		}
		if (!witherRoseCondition(mob) && this.isInProcess(mob))
		{
			this.forAllPlayersInProcess(mob, player -> {
				this.addProgressValue(mob, player, -0.005);	// 0.1 per second
				if (this.getProgressValue(mob, player) <= 0)
				{
					interrupt(player, mob, false);
				}
			});
			EntityHelper.sendSmokeParticlesToLivingDefault(mob);
		}	
		Player lastGiver = getLastGiver(mob);
		if (lastGiver != null && lastGiver.hasEffect(MobEffects.WITHER) 
				&& mob.distanceToSqr(lastGiver) <= 32 * 32)
		{
			mob.setTarget(lastGiver);
		}
	}
	
	@Override
	public void onAttackProcessingPlayer(Mob mob, Player player, boolean damageGiven)
	{}
	
	// 8 wither roses in 15*15*15 range centered by mob
	public boolean witherRoseCondition(Mob mob)
	{
		BlockPos pos = mob.blockPosition();
		AABB area = new AABB(pos.offset(-7, -7, -7), pos.offset(7, 7, 7));
		return mob.level.getBlockStates(area).filter((b) -> b.is(Blocks.WITHER_ROSE)).count() >= 8;
	}
	
	@Override
	public void afterItemGiven(Player player, Mob mob, ItemStack item)
	{
		CBefriendableMob.getCap(mob).getNbt().putUUID("last_item_giver", player.getUUID());
	}
	
	@Nullable
	protected Player getLastGiver(Mob mob) 
	{
		CBefriendableMob cap = CBefriendableMob.getCap(mob);
		if (cap.getNbt().contains("last_item_giver", NbtHelper.TAG_INT_ARRAY_ID))
			return mob.level.getPlayerByUUID(cap.getNbt().getUUID("last_item_giver"));
		else return null;
	}
		
	
}

