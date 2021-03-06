package com.jaquadro.minecraft.storagedrawers.item;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawersStandard;
import com.jaquadro.minecraft.storagedrawers.config.ConfigManager;
import com.jaquadro.minecraft.storagedrawers.core.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemDrawers extends ItemMultiTexture
{
    public ItemDrawers (Block block) {
        super(block, block, BlockWood.field_150096_a);
    }

    protected ItemDrawers (Block block, String[] names) {
        super(block, block, names);
    }

    @Override
    public boolean placeBlockAt (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
            return false;

        TileEntityDrawers tile = (TileEntityDrawers) world.getTileEntity(x, y, z);
        if (tile != null) {
            if (side > 1)
                tile.setDirection(side);

            BlockDrawers block = (BlockDrawers) field_150939_a;
            if (tile instanceof TileEntityDrawersStandard)
                ((TileEntityDrawersStandard)tile).setDrawerCount(block.drawerCount);

            tile.setDrawerCapacity(getCapacityForBlock(block));
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        list.add(StatCollector.translateToLocalFormatted("storageDrawers.drawers.description", getCapacityForBlock(block)));
    }

    protected int getCapacityForBlock (Block block) {
        ConfigManager config = StorageDrawers.config;
        int count = 0;

        if (!(block instanceof BlockDrawers))
            return 0;

        BlockDrawers drawer = (BlockDrawers)block;

        if (drawer.drawerCount == 1)
            count = config.getBlockBaseStorage("fulldrawers1");
        else if (drawer.drawerCount == 2 && !drawer.halfDepth)
            count = config.getBlockBaseStorage("fulldrawers2");
        else if (drawer.drawerCount == 4 && !drawer.halfDepth)
            count = config.getBlockBaseStorage("fulldrawers4");
        else if (drawer.drawerCount == 2 && drawer.halfDepth)
            count = config.getBlockBaseStorage("halfdrawers2");
        else if (drawer.drawerCount == 4 && drawer.halfDepth)
            count = config.getBlockBaseStorage("halfdrawers4");
        else if (drawer.drawerCount == 3)
            count = config.getBlockBaseStorage("compDrawers");

        return count;
    }
}
