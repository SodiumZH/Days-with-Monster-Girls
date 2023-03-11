package net.sodiumstudio.befriendmobs.client.gui.screens;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sodiumstudio.befriendmobs.entitiy.IBefriendedMob;
import net.sodiumstudio.befriendmobs.inventory.AbstractInventoryMenuBefriended;
import net.sodiumstudio.befriendmobs.util.math.IntVec2;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractGuiBefriended extends AbstractContainerScreen<AbstractInventoryMenuBefriended> {

	public IBefriendedMob mob;
	protected float xMouse = 0;
	protected float yMouse = 0;


	public abstract ResourceLocation getTextureLocation();
	
	public AbstractGuiBefriended(AbstractInventoryMenuBefriended pMenu, Inventory pPlayerInventory,
			IBefriendedMob mob) {
		this(pMenu, pPlayerInventory, mob, true);
	}

	public AbstractGuiBefriended(AbstractInventoryMenuBefriended pMenu, Inventory pPlayerInventory,
			IBefriendedMob mob, boolean renderName)
	{
		super(pMenu, pPlayerInventory, renderName ? ((LivingEntity)mob).getDisplayName() : new TextComponent(""));
		this.mob = mob;
		this.passEvents = false;
	}

	// Background, mouse XY and tooltip are rendered here. Do not render them again in subclasses.
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		this.xMouse = (float) pMouseX;
		this.yMouse = (float) pMouseY;
		super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.renderTooltip(pPoseStack, pMouseX, pMouseY);
	}
	
	public void blit(PoseStack poseStack, IntVec2 xy, IntVec2 uvOffset, IntVec2 uvSize)
	{
		blit(poseStack, xy.x, xy.y, uvOffset.x, uvOffset.y, uvSize.x, uvSize.y);
	}
	
	public void addSlotBg(PoseStack poseStack, int slotIndex, IntVec2 xy, @Nullable IntVec2 uvEmpty, @Nullable IntVec2 uvFilled)
	{
		if (!menu.slots.get(slotIndex).hasItem() && uvEmpty != null)
			blit(poseStack, xy, uvEmpty, IntVec2.of(18));
		else if (menu.slots.get(slotIndex).hasItem() && uvFilled != null)
			blit(poseStack, xy, uvFilled, IntVec2.of(18));
	}

	public void addHealthInfo(PoseStack poseStack, IntVec2 position, int color)
	{
		int hp = (int) ((LivingEntity)mob).getHealth();
		int maxHp = (int) ((LivingEntity)mob).getMaxHealth();
		Component info = new TextComponent("HP: " + hp + " / " + maxHp);
		font.draw(poseStack, info, position.x, position.y, color);
	}
	
	public void addHealthInfo(PoseStack poseStack, IntVec2 position)
	{
		addHealthInfo(poseStack, position, 4210752);
	}
	
}
