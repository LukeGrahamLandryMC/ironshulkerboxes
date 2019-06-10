package com.progwml6.ironshulkerbox.common.tileentity;

import com.progwml6.ironshulkerbox.common.blocks.ShulkerBoxType;
import com.progwml6.ironshulkerbox.common.core.IronShulkerBoxBlocks;
import com.progwml6.ironshulkerbox.common.inventory.ShulkerBoxContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;

import javax.annotation.Nullable;

public class GoldShulkerBoxTileEntity extends IronShulkerBoxTileEntity
{
    public GoldShulkerBoxTileEntity()
    {
        this(null);
    }

    public GoldShulkerBoxTileEntity(@Nullable DyeColor colorIn)
    {
        super(ShulkerBoxTileEntityType.GOLD_SHULKER_BOX, colorIn, ShulkerBoxType.GOLD, IronShulkerBoxBlocks.goldShulkerBoxes);
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory)
    {
        return ShulkerBoxContainer.createGoldContainer(id, playerInventory, this);
    }
}
