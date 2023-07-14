package net.sodiumstudio.dwmg.events;

import java.util.UUID;


import com.github.mechalopa.hmag.registry.ModItems;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sodiumstudio.befriendmobs.events.ServerEntityTickEvent;
import net.sodiumstudio.befriendmobs.item.ItemMobRespawner;
import net.sodiumstudio.befriendmobs.item.MobRespawnerInstance;
import net.sodiumstudio.befriendmobs.item.MobRespawnerStartRespawnEvent;
import net.sodiumstudio.befriendmobs.item.RespawnerConstructEvent;
import net.sodiumstudio.befriendmobs.item.capability.CItemStackMonitor;
import net.sodiumstudio.nautils.InfoHelper;
import net.sodiumstudio.nautils.NbtHelper;
import net.sodiumstudio.dwmg.Dwmg;
import net.sodiumstudio.dwmg.item.IWithDuration;
import net.sodiumstudio.dwmg.item.ItemEvilMagnet;
import net.sodiumstudio.dwmg.registries.DwmgItems;


@Mod.EventBusSubscriber(modid = Dwmg.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DwmgItemEvents
{
	@SubscribeEvent
	public static void beforeRespawnerConstruct(RespawnerConstructEvent.Before event)
	{
	}
	
	@SubscribeEvent
	public static void afterRespawnerConstruct(RespawnerConstructEvent.After event)
	{
		CompoundTag mobNbt = event.getRespawner().getMobNbt();
		
		// Remove fire
		mobNbt.putShort("Fire", (short) 0);
		
		// Update item name from mob name
		if (mobNbt.contains("CustomName", NbtHelper.TAG_STRING_ID)
				&& mobNbt.contains("dwmg:befriended_owner"))
		{
			String name = Component.Serializer.fromJson(mobNbt.getString("CustomName")).getString();
			MutableComponent nameComp = new TextComponent(name);
			nameComp.setStyle(nameComp.getStyle().withItalic(true));
			MutableComponent comp = 
					new TranslatableComponent("item.befriendmobs.mob_respawner")
					.append(" - ");
			comp.setStyle(comp.getStyle().withItalic(false));
			comp.append(nameComp);
			event.getRespawner().get().setHoverName(comp);
		}
		else 
		{
			MutableComponent comp = 
					new TranslatableComponent("item.befriendmobs.mob_respawner")
					.append(" - ")
					.append(event.getRespawner().getType().getDescription());
			comp.setStyle(comp.getStyle().withItalic(false));
			event.getRespawner().get().setHoverName(comp);
		}
	}
	
	@SubscribeEvent
	public static void onItemEntityPickUp(EntityItemPickupEvent event)
	{
		if (event.getItem().getItem().getItem() instanceof ItemMobRespawner)
		{
			MobRespawnerInstance ins = MobRespawnerInstance.create(event.getItem().getItem());
				// If the mob isn't a dwmg befriended mob, it should not have this uuid
				if (ins.getMobNbt().hasUUID("dwmg:befriended_owner")
					 && !ins.getMobNbt().getUUID("dwmg:befriended_owner").equals(event.getEntity().getUUID()))
				{
					event.setCanceled(true);
				}
		}
		// Clear already picked mobs when player picking up
		if (event.getItem().getItem().getTag() != null && event.getItem().getItem().getTag().contains("already_picked_befriendable_mobs"))
		{
			event.getItem().getItem().removeTagKey("already_picked_befriendable_mobs");
		}
	}
	
	@SubscribeEvent
	public static void onMobRespawnerStartRespawn(MobRespawnerStartRespawnEvent event)
	{
		if (event.getRespawner().getMobNbt().hasUUID("dwmg:befriended_owner") 
				&& !event.getRespawner().getMobNbt().getUUID("dwmg:befriended_owner").equals(event.getPlayer().getUUID()))
		{
			event.setCanceled(true);
		}
	}
	
	protected static final UUID SHARPNESS_MODIFIER_UUID = UUID.fromString("9c12b503-63c0-43e6-bd30-d7aae9818c99");
	
	/**
	 * Add sharpness atk modifier to befriended mobs
	 */
	@SubscribeEvent
	public static void onBefriendedMainHandItemChange(CItemStackMonitor.ChangeEvent event)
	{
		event.living.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(SHARPNESS_MODIFIER_UUID);
		int lv = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, event.to);
		if (lv > 0)
		{
			event.living.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(
					SHARPNESS_MODIFIER_UUID, "sharpness_modifier", 0.5d + 0.5d * (double) lv, AttributeModifier.Operation.ADDITION));
		}		
	}
	
	@SubscribeEvent
	public static void onAnvilChange(AnvilUpdateEvent event)
	{
		if (event.getLeft().getItem() instanceof IWithDuration wd)
		{
			// Necromancer's Wand
			if (event.getLeft().is(DwmgItems.NECROMANCER_WAND.get()) 
					&& event.getRight().is(DwmgItems.DEATH_CRYSTAL_POWDER.get())
					&& wd.canRepair(event.getLeft()))
			{
				ItemStack out = event.getLeft().copy();
				event.setCost(1);
				event.setMaterialCost(1);
				wd.repair(out, 32);
				event.setOutput(out);
			}
			// Evil Magnet fixing
			if (event.getLeft().is(DwmgItems.EVIL_MAGNET.get())
					&& wd.canRepair(event.getLeft())
					&& event.getRight().is(ModItems.EVIL_CRYSTAL.get()))
			{
				ItemStack out = event.getLeft().copy();
				event.setCost(1);
				event.setMaterialCost(1);
				wd.repair(out, 8);
				event.setOutput(out);
			}
		}
	}

	@SubscribeEvent
	public static void onServerItemEntityTick(ServerEntityTickEvent.PreWorldTick event)
	{
		if (event.getEntity() instanceof ItemEntity ie)
		{
			if (ie.getItem().getTag() != null 
					&& ie.getItem().getTag().contains("already_picked_befriendable_mobs", NbtHelper.TAG_COMPOUND_ID))
			{
				for (String key: ie.getItem().getTag().getCompound("already_picked_befriendable_mobs").getAllKeys())
				{
					if (ie.getItem().getTag().getCompound("already_picked_befriendable_mobs").getInt(key) > 0)
					{
						ie.getItem().getTag().getCompound("already_picked_befriendable_mobs").putInt(key, 
								ie.getItem().getTag().getCompound("already_picked_befriendable_mobs").getInt(key) - 1);
					}
					if (ie.getItem().getTag().getCompound("already_picked_befriendable_mobs").getInt(key) == 0)
					{
						ie.getItem().getTag().getCompound("already_picked_befriendable_mobs").remove(key);
					}
				}
			}
		}
	}
}
