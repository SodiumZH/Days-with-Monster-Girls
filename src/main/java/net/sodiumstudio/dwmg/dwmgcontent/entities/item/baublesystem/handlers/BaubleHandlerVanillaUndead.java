package net.sodiumstudio.dwmg.dwmgcontent.entities.item.baublesystem.handlers;

import java.util.HashSet;

import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sodiumstudio.dwmg.befriendmobs.item.baublesystem.IBaubleHolder;
import net.sodiumstudio.dwmg.dwmgcontent.registries.DwmgItems;

public class BaubleHandlerVanillaUndead extends BaubleHandlerGeneral
{
	
	@Override
	public HashSet<Item> getItemsAccepted() {
		HashSet<Item> set = super.getItemsAccepted();
		set.add(DwmgItems.SOUL_AMULET.get());
		return set;
	}

	@Override
	public void applyBaubleEffect(ItemStack bauble, IBaubleHolder owner) {
		super.applyBaubleEffect(bauble, owner);
		if (bauble.is(DwmgItems.SOUL_AMULET.get()))
		{
			owner.addBaubleModifier(Attributes.MAX_HEALTH, 10.0d, Operation.ADDITION);
			owner.addBaubleModifier(Attributes.ATTACK_DAMAGE, 3.0d, Operation.ADDITION);
		}
	}
}
