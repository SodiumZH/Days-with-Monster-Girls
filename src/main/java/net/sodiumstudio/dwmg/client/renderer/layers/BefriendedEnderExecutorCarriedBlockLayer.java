package net.sodiumstudio.dwmg.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.sodiumstudio.dwmg.client.model.BefriendedEnderExecutorModel;
import net.sodiumstudio.dwmg.entities.hmag.HmagEnderExecutorEntity;

@OnlyIn(Dist.CLIENT)
public class BefriendedEnderExecutorCarriedBlockLayer extends RenderLayer<HmagEnderExecutorEntity, BefriendedEnderExecutorModel<HmagEnderExecutorEntity>>
{
	public BefriendedEnderExecutorCarriedBlockLayer(RenderLayerParent<HmagEnderExecutorEntity, BefriendedEnderExecutorModel<HmagEnderExecutorEntity>> renderLayerParent)
	{
		super(renderLayerParent);
	}

	@SuppressWarnings("resource")
	@Override
	public void render(PoseStack pose, MultiBufferSource buffer, int packedLight, HmagEnderExecutorEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		BlockState blockstate = entity.getCarriedBlock();

		if (blockstate != null)
		{
			pose.pushPose();
			this.getParentModel().translateToHand(entity.getMainArm() == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT, pose);
			pose.translate(0.0D, 0.875D, -0.05D);
			float f = (float)entity.tickCount + partialTicks;
			pose.mulPose(Axis.XP.rotationDegrees(Mth.cos(f * 0.33F + 0.2F) * 6.0F));
			pose.mulPose(Axis.YP.rotationDegrees((f * 6.0F + 15.0F) % 360.0F));
			pose.translate(0.1875D, 0.1875D, 0.1875D);
			float f1 = 0.375F;
			pose.scale(-f1, -f1, f1);
			pose.mulPose(Axis.YP.rotationDegrees(90.0F));
			Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockstate, pose, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, (RenderType)null);
			pose.popPose();
		}
	}
}