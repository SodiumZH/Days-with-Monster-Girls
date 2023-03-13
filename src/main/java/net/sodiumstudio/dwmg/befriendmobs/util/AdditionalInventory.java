package net.sodiumstudio.dwmg.befriendmobs.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.sodiumstudio.dwmg.befriendmobs.entitiy.IBefriendedMob;

public class AdditionalInventory extends SimpleContainer
{

	// For BefriendedMob only, owner ref
	@Nullable
	protected IBefriendedMob owner = null;
	
	protected void updateOwner()
	{
		if (owner != null)
			owner.updateFromInventory();
	}
	
	public AdditionalInventory(int size)
	{
		super(size);
		if (size < 0)
			throw new NegativeArraySizeException();
	}
	
	public AdditionalInventory(int size, @Nonnull IBefriendedMob owner)
	{
		super(size);
		this.owner = owner;
		this.addListener(owner);
	}
	
	@Deprecated
	public void set(ItemStack stack, int index)
	{	
		this.setItem(index, stack);
	}
	
	@Override
	public void setItem(int index, ItemStack stack)
	{
		if (index < 0 || index >= getContainerSize())
			throw new IndexOutOfBoundsException();
		super.setItem(index, stack);
		if (owner != null)
			owner.updateFromInventory();
	}
	
	@Deprecated // Use getItem()
	public ItemStack get(int index)
	{
		return this.getItem(index);
	}
	
	@Override
	public ItemStack getItem(int index)
	{
		if (index < 0 || index >= getContainerSize())
			throw new IndexOutOfBoundsException();
		if (owner != null)
			owner.updateFromInventory();
		return super.getItem(index);
	}
	
	@Override
	public int getContainerSize()
	{
		if (owner != null)
			owner.updateFromInventory();
		return super.getContainerSize();
	}
	
	public void saveToTag(CompoundTag parent, String key)
	{
		CompoundTag tag = new CompoundTag();
		tag.put("size", IntTag.valueOf(this.getContainerSize()));
		for (int i = 0; i < this.getContainerSize(); ++i) 
		{
			NbtHelper.saveItemStack(this.getItem(i), tag, Integer.toString(i));
		}
		parent.put(key, tag);
	}
	
	public void readFromTag(CompoundTag tag)
	{
		if (!tag.contains("size"))
			throw new IllegalArgumentException("AdditionalInventory: reading from illegal tag.");
		if (tag.getInt("size") != this.getContainerSize())
			throw new IllegalStateException("AdditionalInventory reading from NBT: size not matching.");
		
		for (int i = 0; i < getContainerSize(); ++i)
		{
			if (tag.contains(Integer.toString(i)))
				this.setItem(i, NbtHelper.readItemStack(tag, Integer.toString(i)));
			else this.setItem(i, ItemStack.EMPTY);
		}
	}
	
	public static AdditionalInventory makeFromTag(CompoundTag tag) 
	{
		AdditionalInventory inv = new AdditionalInventory(tag.getInt("size"));
		inv.readFromTag(tag);
		return inv;
	}
	
	@Deprecated
	public AdditionalInventory toContainer() {
		return this;
	}
	
	// Make a new InventoryTag from container.
	public static AdditionalInventory makeFromContainer(SimpleContainer container)
	{
		AdditionalInventory inv = new AdditionalInventory(container.getContainerSize());
		for (int i = 0; i < inv.getContainerSize(); ++i)
		{
			inv.setItem(i, container.getItem(i));
		}
		return inv;	
	}
	
	// Set the tag content from a container.
	// Note: the tag size and container size must match.
	@Deprecated // Directly refer to this instead
	public void setFromContainer(SimpleContainer container)
	{
		if (getContainerSize() != container.getContainerSize())
			throw new IllegalArgumentException("Size not matching.");
		for (int i = 0; i < getContainerSize(); ++i)
		{
			setItem(i, container.getItem(i));
		}
	}
	
	public void swapItem(int position_1, int position_2)
	{
		ItemStack stack1 = this.getItem(position_1);
		this.setItem(position_1, this.getItem(position_2));
		this.setItem(position_2, stack1);
	}
	
	public boolean consumeItem(int position)
	{
		ItemStack stack = this.getItem(position);
		if (stack.isEmpty())
			return false;
		else
		{
			if (stack.getCount() == 1)
				this.setItem(position, ItemStack.EMPTY);
			else
			{
				stack.setCount(stack.getCount() - 1);
				this.setItem(position, stack);
			}
			return true;			
		}		
	}
}
