package net.sodiumstudio.dwmg.befriendmobs.entity.befriending.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Mob;
import net.sodiumstudio.dwmg.befriendmobs.entity.capability.CBefriendableMob;
import net.sodiumstudio.dwmg.befriendmobs.registry.BefMobCapabilities;

public class BefriendableMobRegistry
{

	// A list to register all befriendable mobs in the whole game
	private static BefriendableMobRegistry REGISTRY = new BefriendableMobRegistry();
	
	private HashMap<UUID, Mob> mobMap = new HashMap<UUID, Mob>();
	
	// If the registry is just cleaned.
	// When cleaning set true, and when adding mob set false
	// For reducing iterating all entries in runtime as this registry is read many times on tick 
	private boolean isFreshlyCleaned = false;
	
	public static void put(Mob mob)
	{
		if (!mob.getCapability(BefMobCapabilities.CAP_BEFRIENDABLE_MOB).isPresent())
			throw new IllegalArgumentException("Mob put into BefriendableMobRegistry must have CBefriendableMob capability.");
		if (!mob.isAlive())
			return;
		REGISTRY.mobMap.put(mob.getUUID(), mob);
		REGISTRY.isFreshlyCleaned = false;
	}

	// No need to clean, just check the entry mob's validity
	@Nullable
	public static Mob get(UUID uuid)
	{
		if (!REGISTRY.mobMap.containsKey(uuid))
			return null;
		else if (!REGISTRY.mobMap.get(uuid).isAlive())
			return null;
		else return REGISTRY.mobMap.get(uuid);
	}
	
	// No need to clean, just check the entry mob's validity
	public static boolean contains(UUID uuid)
	{
		if (!REGISTRY.mobMap.containsKey(uuid))
			return false;
		else if (!REGISTRY.mobMap.get(uuid).isAlive())
			return false;
		else return true;
	}
	
	// Clean invalid references (not alive) of mobs in the registry
	public static void clean()
	{
		HashSet<UUID> toRemove = new HashSet<UUID>();
		for (UUID uuid: REGISTRY.mobMap.keySet())
		{
			if (REGISTRY.mobMap.get(uuid) == null || !REGISTRY.mobMap.get(uuid).isAlive())
			{
				toRemove.add(uuid);
			}
		}
		for (UUID uuid: toRemove)
		{
			REGISTRY.mobMap.remove(uuid);
		}
		REGISTRY.isFreshlyCleaned = true;
	}
	
	public static Set<UUID> allUUIDs()
	{
		if (!REGISTRY.isFreshlyCleaned)
			clean();
		return REGISTRY.mobMap.keySet();
	}
	
	public static Collection<Mob> allMobs()
	{
		if (!REGISTRY.isFreshlyCleaned)
			clean();
		return REGISTRY.mobMap.values();
	}
	
	public static HashSet<CBefriendableMob> allCaps()
	{
		if (!REGISTRY.isFreshlyCleaned)
			clean();
		HashSet<CBefriendableMob> set = new HashSet<CBefriendableMob>();
		for (Mob mob: allMobs())
		{
			set.add(CBefriendableMob.getCap(mob));
		}
		return set;
	}
}
