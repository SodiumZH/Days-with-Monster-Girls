package net.sodiumstudio.dwmg.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.sodiumstudio.befriendmobs.BefriendMobs;
import net.sodiumstudio.befriendmobs.client.gui.screens.BefriendedGuiScreen;
import net.sodiumstudio.befriendmobs.entity.IBefriendedMob;
import net.sodiumstudio.befriendmobs.inventory.BefriendedInventoryMenu;
import net.sodiumstudio.befriendmobs.item.baublesystem.BaubleHandler;
import net.sodiumstudio.befriendmobs.item.baublesystem.IBaubleHolder;
import net.sodiumstudio.befriendmobs.util.TagHelper;
import net.sodiumstudio.befriendmobs.util.math.IntVec2;
import net.sodiumstudio.dwmg.client.gui.screens.GuiEquipmentTwoBaubles;

public class InventoryMenuEquipmentTwoBaubles extends InventoryMenuPreset0{

	public InventoryMenuEquipmentTwoBaubles(int containerId, Inventory playerInventory, Container container,
			IBefriendedMob mob) {
		super(containerId, playerInventory, container, mob);
	}

	@Override
	public BefriendedGuiScreen makeGui() {
		return new GuiEquipmentTwoBaubles(this, playerInventory, mob);
	}

	@Override
	protected void addMenuSlots()
	{
		this.addArmorSlot(0, leftRowPos(), HEAD, null);
		this.addArmorSlot(1, leftRowPos().slotBelow(), CHEST, null);
		this.addArmorSlot(2, leftRowPos().slotBelow(2), LEGS, null);
		this.addArmorSlot(3, leftRowPos().slotBelow(3), FEET, null);
		this.addGeneralSlot(4, rightRowPos().slotBelow(3), null);
		this.addGeneralSlot(5, rightRowPos().slotBelow(2), null);
		this.addBaubleSlot(6, rightRowPos(), "0");
		this.addBaubleSlot(7, rightRowPos(), "1");
	}
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		int[] order = {6, 7, 0, 1, 2, 3, 4, 5};
		return this.quickMovePreset(order.length, player, index, order);
	}
	
	@Override
	protected IntVec2 getPlayerInventoryPosition()
	{
		return IntVec2.valueOf(32, 101);
	}

	@Override
	public void removed(Player pPlayer) {
		super.removed(pPlayer);
	}
}
