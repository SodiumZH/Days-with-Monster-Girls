package net.sodiumstudio.dwmg.registries;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.entity.NecroticReaperEntity;
import com.github.mechalopa.hmag.world.entity.projectile.MagicBulletEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sodiumstudio.dwmg.Dwmg;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedNecroticReaper;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedCreeperGirl;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedDrownedGirl;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedEnderExecutor;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedHornet;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedHuskGirl;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedSkeletonGirl;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedStrayGirl;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedWitherSkeletonGirl;
import net.sodiumstudio.dwmg.entities.hmag.EntityBefriendedZombieGirl;
import net.sodiumstudio.dwmg.entities.projectile.NecromancerMagicBulletEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DwmgEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Dwmg.MOD_ID);
	
	public static final RegistryObject<EntityType<EntityBefriendedZombieGirl>> HMAG_ZOMBIE_GIRL = 
			ENTITY_TYPES.register("hmag_zombie_girl", () -> EntityType.Builder.of(
			EntityBefriendedZombieGirl::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_zombie_girl").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedSkeletonGirl>> HMAG_SKELETON_GIRL = 
			ENTITY_TYPES.register("hmag_skeleton_girl", () -> EntityType.Builder.of(
			EntityBefriendedSkeletonGirl::new, MobCategory.CREATURE)
			.sized(0.6F, 1.99F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_skeleton_girl").toString()));

	public static final RegistryObject<EntityType<EntityBefriendedHuskGirl>> HMAG_HUSK_GIRL = 
			ENTITY_TYPES.register("hmag_husk_girl", () -> EntityType.Builder.of(
			EntityBefriendedHuskGirl::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_husk_girl").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedDrownedGirl>> HMAG_DROWNED_GIRL = 
			ENTITY_TYPES.register("hmag_drowned_girl", () -> EntityType.Builder.of(
			EntityBefriendedDrownedGirl::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_drowned_girl").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedCreeperGirl>> HMAG_CREEPER_GIRL = 
			ENTITY_TYPES.register("hmag_creeper_girl", () -> EntityType.Builder.of(
			EntityBefriendedCreeperGirl::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_creeper_girl").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedEnderExecutor>> HMAG_ENDER_EXECUTOR =
			ENTITY_TYPES.register("hmag_ender_executor", () -> EntityType.Builder.of(
			EntityBefriendedEnderExecutor::new, MobCategory.CREATURE)
			.sized(0.6F, 2.9F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_ender_executor").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedStrayGirl>> HMAG_STRAY_GIRL = 
			ENTITY_TYPES.register("hmag_stray_girl", () -> EntityType.Builder
			.of(EntityBefriendedStrayGirl::new, MobCategory.CREATURE)
			.immuneTo(Blocks.POWDER_SNOW)
			.sized(0.6F, 1.99F)			
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_stray_girl").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedWitherSkeletonGirl>> HMAG_WITHER_SKELETON_GIRL = 
			ENTITY_TYPES.register("hmag_wither_skeleton_girl", () -> EntityType.Builder
			.of(EntityBefriendedWitherSkeletonGirl::new, MobCategory.CREATURE)
			.fireImmune()
			.immuneTo(Blocks.WITHER_ROSE)
			.sized(0.7F, 2.4F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_wither_skeleton_girl").toString()));

	public static final RegistryObject<EntityType<EntityBefriendedHornet>> HMAG_HORNET = 
			ENTITY_TYPES.register("hmag_hornet", () -> EntityType.Builder
			.of(EntityBefriendedHornet::new, MobCategory.CREATURE)
			.sized(0.6F, 1.7F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_hornet").toString()));
	
	public static final RegistryObject<EntityType<EntityBefriendedNecroticReaper>> HMAG_NECROTIC_REAPER =
			ENTITY_TYPES.register("hmag_necrotic_reaper", () -> EntityType.Builder
			.of(EntityBefriendedNecroticReaper::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.setTrackingRange(8)
			.setUpdateInterval(3)
			.setShouldReceiveVelocityUpdates(false)
			.noSummon()
			.build(new ResourceLocation(Dwmg.MOD_ID, "hmag_necrotic_reaper").toString()));
	
	@SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(DwmgEntityTypes.HMAG_ZOMBIE_GIRL.get(), EntityBefriendedZombieGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_SKELETON_GIRL.get(), EntityBefriendedSkeletonGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_HUSK_GIRL.get(), EntityBefriendedHuskGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_DROWNED_GIRL.get(), EntityBefriendedDrownedGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_CREEPER_GIRL.get(), EntityBefriendedCreeperGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_ENDER_EXECUTOR.get(), EntityBefriendedEnderExecutor.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_STRAY_GIRL.get(), EntityBefriendedStrayGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_WITHER_SKELETON_GIRL.get(), EntityBefriendedWitherSkeletonGirl.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_HORNET.get(), EntityBefriendedHornet.createAttributes().build());
        event.put(DwmgEntityTypes.HMAG_NECROTIC_REAPER.get(), EntityBefriendedNecroticReaper.createAttributes().build());
	}
	
	// Projectiles
	
	public static final RegistryObject<EntityType<NecromancerMagicBulletEntity>> NECROMANCER_MAGIC_BULLET = 
			ENTITY_TYPES.register("necromancer_magic_bullet", () -> EntityType.Builder
			.<NecromancerMagicBulletEntity>of(NecromancerMagicBulletEntity::new, MobCategory.MISC)
			.sized(0.3215F, 0.3215F)
			.setTrackingRange(4)
			.setUpdateInterval(1)
			.setShouldReceiveVelocityUpdates(true)
			.setCustomClientFactory(NecromancerMagicBulletEntity::new)
			.build(new ResourceLocation(Dwmg.MOD_ID, "necromancer_magic_bullet").toString()));
	
	
}