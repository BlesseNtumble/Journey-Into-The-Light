package net.journey.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.api.EnumMaterialTypes;
import net.slayer.api.block.BlockMod;
import net.slayer.api.block.BlockModVine;

public class BlockCaveVine extends BlockMod {

    protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 1.0D, 0.699999988079071D);
    
	public BlockCaveVine(String name, String f) {
		super(EnumMaterialTypes.PLANT, name, f, 2);
		setLightLevel(0.6F);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BUSH_AABB;
    }

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }
	
	private boolean canBlockStay(World world, BlockPos pos) {
		return canPlaceBelow(world, pos.up());
	}

	public static boolean canPlaceBelow(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block blockAbove = state.getBlock();

		return state.getMaterial() == Material.ROCK || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.PLANTS;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		this.checkAndDropBlock(world, pos);
	}

	@Override
	@Deprecated
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		this.checkAndDropBlock(world, pos);
	}
	
	private void checkAndDropBlock(World world, BlockPos pos) {
		if (!this.canBlockStay(world, pos)) {
			world.destroyBlock(pos, true);
		}
	}
	
	@Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
    
	@Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos){return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos)  {
		return 1000;
	}
}