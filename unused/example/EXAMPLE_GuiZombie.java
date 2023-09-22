package net.sodiumstudio.befriendmobs.example;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sodiumstudio.befriendmobs.BefriendMobs;
import net.sodiumstudio.befriendmobs.client.gui.screens.BefriendedGuiScreen;
import net.sodiumstudio.befriendmobs.entity.befriended.IBefriendedMob;
import net.sodiumstudio.befriendmobs.inventory.BefriendedInventoryMenu;
import net.sodiumstudio.nautils.math.IntVec2;
import net.sodiumstudio.dwmg.dwmgcontent.Dwmg;

@OnlyIn(Dist.CLIENT)
public class EXAMPLE_GuiZombie extends BefriendedGuiScreen {
	
	@Override
	public ResourceLocation getTextureLocation() {
		return new ResourceLocation(BefriendMobs.MOD_ID,
			"textures/gui/container/example_befriended_mob_gui.png");
	}
	
	public EXAMPLE_GuiZombie(AbstractInventoryMenuBefriended pMenu, Inventory pPlayerInventory, IBefriendedMob mob) {
		super(pMenu, pPlayerInventory, mob, true);
		imageWidth = 200;
		imageHeight = 183;
		inventoryLabelY = imageHeight - 93;
	}

	@Override
	protected void init() {
		super.init();
		//this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, getTextureLocation());
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		IntVec2 v = IntVec2.of(i, j);
		// Main window
		
		this.blit(graphics, v.x, v.y, 0, 0, imageWidth, imageHeight);	
		// Armor slots
		v.add(7, 17);
		IntVec2 ve = IntVec2.of(imageWidth, 0);	// for empty
		IntVec2 vf = IntVec2.of(imageWidth + 36, 0);	//for filled
		for (int k = 0; k < 4; ++k)
		{
			this.addSlotBg(graphics, k, v, ve, vf);
			v.slotBelow();
			ve.slotBelow();
		}
		v.set(i, j).add(79, 17);
		ve.set(imageWidth + 18, 0);
		
		// Hand items and baubles
		int[] si = {6, 7, 5, 4};
		for(int k = 0; k < 4; ++k)
		{
			this.addSlotBg(graphics, si[k], v, ve, vf);
			v.slotBelow();
			ve.slotBelow();
		}
		
		// Info box
		this.blit(graphics, i + 99, j + 17, 0, imageHeight, 96, 72);
		addHealthInfo(graphics, IntVec2.of(i + 102, j + 20));
		InventoryScreen.renderEntityInInventory(i + 52, j + 70, 25, (float) (i + 52) - this.xMouse,
				(float) (j + 75 - 50) - this.yMouse, (LivingEntity)mob);		
	}

	@Override
	public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
	   super.render(graphics, pMouseX, pMouseY, pPartialTick);
	}

}

