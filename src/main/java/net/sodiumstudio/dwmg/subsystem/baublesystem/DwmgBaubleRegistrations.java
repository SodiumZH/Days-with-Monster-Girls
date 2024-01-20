package net.sodiumstudio.dwmg.subsystem.baublesystem;

import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.sodiumstudio.befriendmobs.entity.befriended.IBefriendedMob;
import net.sodiumstudio.befriendmobs.subsystems.baublesystem.BaubleEquippingCondition;
import net.sodiumstudio.befriendmobs.subsystems.baublesystem.RegisterBaubleEquippableMobsEvent;
import net.sodiumstudio.befriendmobs.subsystems.baublesystem.RegisterBaublesEvent;
import net.sodiumstudio.dwmg.Dwmg;
import net.sodiumstudio.dwmg.entities.hmag.HmagCrimsonSlaughtererEntity;
import net.sodiumstudio.dwmg.entities.hmag.HmagZombieGirlEntity;
import net.sodiumstudio.dwmg.registries.DwmgItems;
import net.sodiumstudio.dwmg.subsystem.baublesystem.baubles.InsomniaFruitBaubleBehavior;

@EventBusSubscriber(modid = Dwmg.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DwmgBaubleRegistrations
{
	
	@SubscribeEvent
	public static void dwmgBaubleAdditionalRegistration(DwmgBaubleAdditionalRegistry.RegisterEvent event)
	{
		event.register(DwmgItems.SOUL_AMULET.get());
		event.register(DwmgItems.SOUL_AMULET_II.get());
		event.register(DwmgItems.RESISTANCE_AMULET.get());
		event.register(DwmgItems.RESISTANCE_AMULET_II.get());
		event.register(DwmgItems.COURAGE_AMULET.get());
		event.register(DwmgItems.COURAGE_AMULET_II.get());
		event.register(DwmgItems.HEALING_JADE.get());
		event.register(DwmgItems.LIFE_JADE.get());
		event.register(DwmgItems.LIFE_JADE_II.get());
		event.register(DwmgItems.AQUA_JADE.get());
		event.register(DwmgItems.POISONOUS_THORN.get());
	}
	
	private static Function<Mob, ItemStack> accessMobAdditionalInventory(int position)
	{
		return mob -> {
			if (mob instanceof IBefriendedMob bm)
				return bm.getAdditionalInventory().getItem(position);
			else throw new IllegalArgumentException("Input mob isn't IBefriendedMob.");
		};
	}
	
	@SubscribeEvent
	public static void baubleEquippableRegistration(RegisterBaubleEquippableMobsEvent event)
	{
		registerWithContinuousSlotSequence(event, HmagZombieGirlEntity.class, 6, 8);
		registerWithContinuousSlotSequence(event, HmagCrimsonSlaughtererEntity.class, 0, 4);
	}
	
	@SubscribeEvent
	public static void baubleRegistration(RegisterBaublesEvent event)
	{
		event.register(DwmgItems.SOUL_AMULET.get());
		event.register(DwmgItems.SOUL_AMULET_II.get());
		event.register(new InsomniaFruitBaubleBehavior(new ResourceLocation("dwmg:insomnia_fruit"), BaubleEquippingCondition.always()));
		event.register(DwmgItems.RESISTANCE_AMULET.get());
		event.register(DwmgItems.RESISTANCE_AMULET_II.get());
		event.register(DwmgItems.COURAGE_AMULET.get());
		event.register(DwmgItems.COURAGE_AMULET_II.get());
		event.register(DwmgItems.HEALING_JADE.get());
		event.register(DwmgItems.LIFE_JADE.get());
		event.register(DwmgItems.LIFE_JADE_II.get());
		event.register(DwmgItems.AQUA_JADE.get());
		event.register(DwmgItems.POISONOUS_THORN.get());
		
	}
	
	// ========= Utils ==============
	/**
	 * Register a befriended mob with a series of continuous bauble slots in the additional inventory.
	 * for example, using index range (3, 6) will register the additional inventory slot 3, 4, 5 with slot name "0", "1", "2" respectively. 
	 */
	public static RegisterBaubleEquippableMobsEvent.SlotRegisterer registerWithContinuousSlotSequence(RegisterBaubleEquippableMobsEvent event, 
			Class<? extends Mob> clazz, int minIndex, int maxIndexExclude)
	{
		RegisterBaubleEquippableMobsEvent.SlotRegisterer reg = event.register(clazz);
		for (int i = 0; i < maxIndexExclude - minIndex; ++i)
		{
			reg.addSlot(Integer.toString(i), accessMobAdditionalInventory(minIndex + i));
		}
		return reg;
	}
	
	
}
