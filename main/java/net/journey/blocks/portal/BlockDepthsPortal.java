package net.journey.blocks.portal;

import java.util.List;
import java.util.Random;

import net.journey.JITL;
import net.journey.JourneyTabs;
import net.journey.dimension.depths.TeleporterDepths;
import net.journey.enums.EnumParticlesClasses;
import net.journey.util.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.api.EnumMaterialTypes;
import net.slayer.api.block.BlockMod;

public class BlockDepthsPortal extends BlockMod {

	public BlockDepthsPortal(String name, String f) {
		super(EnumMaterialTypes.PORTAL, name, f, 1.0F);
		this.setTickRandomly(true);
		setCreativeTab(JourneyTabs.portalBlocks);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) { }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5345F, 1.0F);
	}

	@Override
	public boolean isPassable(IBlockAccess access, BlockPos pos) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
		if ((entity.getRidingEntity() == null) && ((entity instanceof EntityPlayerMP))) {
			EntityPlayerMP thePlayer = (EntityPlayerMP)entity;
			WorldServer worldserver = thePlayer.mcServer.getWorld(thePlayer.dimension);
			int dimensionID = Config.depths;
			if(thePlayer.timeUntilPortal > 0) 
				thePlayer.timeUntilPortal = 10;
			else if(thePlayer.dimension != dimensionID) {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, dimensionID, new TeleporterDepths(thePlayer.mcServer.getWorld(dimensionID)));
			} else {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, 0, new TeleporterDepths(thePlayer.mcServer.getWorld(0)));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
		double d0 = (float)pos.getX() + rand.nextFloat();
		double d1 = (float)pos.getY() + 0.8F;
		double d2 = (float)pos.getZ() + rand.nextFloat();
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d5 = 0.0D;
		JITL.proxy.spawnParticle(EnumParticlesClasses.DEPTHS, worldIn, d0, d1, d2, d3, d4, d5);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, d3, d4, d5, new int[0]);
	}
}