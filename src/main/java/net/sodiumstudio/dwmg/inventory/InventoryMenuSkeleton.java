package net.sodiumstudio.dwmg.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.sodiumstudio.befriendmobs.BefriendMobs;
import net.sodiumstudio.befriendmobs.client.gui.screens.BefriendedGuiScreen;
import net.sodiumstudio.befriendmobs.entity.IBefriendedMob;
import net.sodiumstudio.befriendmobs.inventory.BefriendedInventoryMenu;
import net.sodiumstudio.befriendmobs.item.baublesystem.BaubleHandler;
import net.sodiumstudio.befriendmobs.util.TagHelper;
import net.sodiumstudio.befriendmobs.util.math.IntVec2;
import net.sodiumstudio.dwmg.client.gui.screens.GuiBowSecWeaponOneBauble;

public class InventoryMenuSkeleton extends InventoryMenuPreset0
{

	public InventoryMenuSkeleton(int containerId, Inventory playerInventory, Container container,
			IBefriendedMob mob)
	{
		super(containerId, playerInventory, container, mob);
	}

	@Override
	protected void addMenuSlots() {
		this.addArmorSlot(0, leftRowPos(), HEAD, null);
		this.addArmorSlot(1, leftRowPos().slotBelow(), CHEST, null);
		this.addArmorSlot(2, leftRowPos().slotBelow(2), LEGS, null);
		this.addArmorSlot(3, leftRowPos().slotBelow(3), FEET, null);
		this.addGeneralSlot(4, rightRowPos().slotBelow(3), null);
		this.addGeneralSlot(5, rightRowPos().slotBelow(2), null);
		this.addBaubleSlot(6, rightRowPos(), "0");
		this.addGeneralSlot(7, rightRowPos().slotBelow(), null);
		this.addGeneralSlot(8, rightRowPos().slotLeft().addX(-3), (stack) -> stack.is(Items.ARROW));
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		int[] order = {8, 9, 0, 1, 2, 3, 4, 6, 5};
		return this.quickMovePreset(order.length, player, index, order);
	}
	
	@Override
	public BefriendedGuiScreen makeGui() {
		return new GuiBowSecWeaponOneBauble(this, playerInventory, mob);
	}

	@Override
	protected IntVec2 getPlayerInventoryPosition() {
		return IntVec2.valueOf(32, 101);
	}

	@Override
	public void removed(Player pPlayer) {
		super.removed(pPlayer);
		this.container.stopOpen(pPlayer);
	}
}