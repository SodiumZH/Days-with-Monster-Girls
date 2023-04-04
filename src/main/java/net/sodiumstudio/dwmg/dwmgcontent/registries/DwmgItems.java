package net.sodiumstudio.dwmg.dwmgcontent.registries;

import net.minecraft.world.item.ChorusFruitItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sodiumstudio.dwmg.dwmgcontent.Dwmg;
import net.sodiumstudio.dwmg.dwmgcontent.DwmgTab;

public class DwmgItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Dwmg.MOD_ID);
	
	public static final CreativeModeTab TAB = DwmgTab.TAB;
	// General register function for items
	
	// Register basic item with properties, not supporting item subclasses
	public static RegistryObject<Item> regItem(String name, Item.Properties properties)
	{
		return ITEMS.register(name, ()->new Item(properties.tab(TAB))); //Demos only
	}
	
	// Register basic item using default properties
	public static RegistryObject<Item> regItemDefault(String name)
	{
		return regItem(name, new Item.Properties());
	}

	
	/************************************/
	/* Item Registering, with constants */ 
	/************************************/	
	
	
	// Crafting intermediates
	public static final RegistryObject<Item> DEATH_CRYSTAL = regItem("death_crystal", new Item.Properties().rarity(Rarity.UNCOMMON));
	public static final RegistryObject<Item> DEATH_CRYSTAL_POWDER = regItemDefault("death_crystal_powder");
	public static final RegistryObject<Item> SOUL_FLOUR = regItemDefault("soul_flour");
	public static final RegistryObject<Item> SOUL_CLOTH = regItemDefault("soul_cloth");
	public static final RegistryObject<Item> ENDER_FRUIT_JAM = regItem("ender_fruit_jam", new Item.Properties().rarity(Rarity.RARE));
	// Foods
	public static final RegistryObject<Item> SOUL_CAKE_SLICE = regItem("soul_cake_slice", new Item.Properties().food(DwmgFoodProperties.SOUL_CAKE_SLICE).rarity(Rarity.UNCOMMON));
	public static final RegistryObject<Item> ENDERBERRY = ITEMS.register("enderberry", () -> new ChorusFruitItem(new Item.Properties().tab(TAB).food(DwmgFoodProperties.ENDERBERRY).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> ENDER_PIE = ITEMS.register("ender_pie", () -> new Item(new Item.Properties().tab(TAB).food(DwmgFoodProperties.ENDER_PIE).rarity(Rarity.RARE)));

	
	/* Item register end */
	
	public static void register(IEventBus eventBus){
	    ITEMS.register(eventBus);
	}
}