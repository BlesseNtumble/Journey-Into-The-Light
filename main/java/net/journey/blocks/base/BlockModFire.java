package net.journey.blocks.base;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.journey.JourneyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.api.EnumMaterialTypes;
import net.slayer.api.block.BlockMod;

public class BlockModFire extends BlockMod {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
	private final Map<Block, Integer> encouragements = Maps.<Block, Integer>newIdentityHashMap();
	private final Map<Block, Integer> flammabilities = Maps.<Block, Integer>newIdentityHashMap();
	Material mat = new Material(blockMapColor);

	protected static final AxisAlignedBB AABB_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return this.getDefaultState();
	}

	public BlockModFire(String name, String finalN) {
		super(name, finalN);
		this.mat = Material.FIRE;
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		setLightLevel(1.0F);
		setUnlocalizedName(name);
		setCreativeTab(null);
	}

	@Override
	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}

	public static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(1F, 0.0F, 1F, 0F, 0.1F, 0F);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BLOCK_AABB;
	}

	public static void init() {
		JourneyBlocks.fire.setFireInfo(Blocks.PLANKS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.DOUBLE_WOODEN_SLAB, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.WOODEN_SLAB, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.OAK_FENCE_GATE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.SPRUCE_FENCE_GATE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.BIRCH_FENCE_GATE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.JUNGLE_FENCE_GATE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.ACACIA_FENCE_GATE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.OAK_FENCE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.SPRUCE_FENCE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.BIRCH_FENCE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.JUNGLE_FENCE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.DARK_OAK_FENCE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.ACACIA_FENCE, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.OAK_STAIRS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.BIRCH_STAIRS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.SPRUCE_STAIRS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.JUNGLE_STAIRS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.ACACIA_STAIRS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.DARK_OAK_STAIRS, 5, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.LOG, 5, 5);
		JourneyBlocks.fire.setFireInfo(Blocks.LOG2, 5, 5);
		JourneyBlocks.fire.setFireInfo(Blocks.LEAVES, 30, 60);
		JourneyBlocks.fire.setFireInfo(Blocks.LEAVES2, 30, 60);
		JourneyBlocks.fire.setFireInfo(Blocks.BOOKSHELF, 30, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.TNT, 15, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.TALLGRASS, 60, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.DOUBLE_PLANT, 60, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.YELLOW_FLOWER, 60, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.RED_FLOWER, 60, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.DEADBUSH, 60, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.WOOL, 30, 60);
		JourneyBlocks.fire.setFireInfo(Blocks.VINE, 15, 100);
		JourneyBlocks.fire.setFireInfo(Blocks.COAL_BLOCK, 5, 5);
		JourneyBlocks.fire.setFireInfo(Blocks.HAY_BLOCK, 60, 20);
		JourneyBlocks.fire.setFireInfo(Blocks.CARPET, 60, 20);
	}

	public void setFireInfo(Block blockIn, int encouragement, int flammability) {
		if (blockIn == Blocks.AIR)
			throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
		this.encouragements.put(blockIn, Integer.valueOf(encouragement));
		this.flammabilities.put(blockIn, Integer.valueOf(flammability));
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
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
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public int tickRate(World worldIn) {
		return 30;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.getGameRules().getBoolean("doFireTick")) {
			if (!worldIn.isAreaLoaded(pos, 2))
				return;
			if (!this.canPlaceBlockAt(worldIn, pos)) {
				worldIn.setBlockToAir(pos);
			}

			Block block = worldIn.getBlockState(pos.down()).getBlock();
			boolean flag = block.isFireSource(worldIn, pos.down(), EnumFacing.UP);

			int i = ((Integer) state.getValue(AGE)).intValue();

			if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos)
					&& rand.nextFloat() < 0.2F + (float) i * 0.03F) {
				worldIn.setBlockToAir(pos);
			} else {
				if (i < 15) {
					state = state.withProperty(AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
					worldIn.setBlockState(pos, state, 4);
				}

				worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));

				if (!flag) {
					if (!this.canNeighborCatchFire(worldIn, pos)) {
						if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)
								|| i > 3) {
							worldIn.setBlockToAir(pos);
						}

						return;
					}

					if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0) {
						worldIn.setBlockToAir(pos);
						return;
					}
				}

				boolean flag1 = worldIn.isBlockinHighHumidity(pos);
				int j = 0;

				if (flag1) {
					j = -50;
				}

				this.tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
				this.tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
				this.tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
				this.tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
				this.tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
				this.tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

				for (int k = -1; k <= 1; ++k) {
					for (int l = -1; l <= 1; ++l) {
						for (int i1 = -1; i1 <= 4; ++i1) {
							if (k != 0 || i1 != 0 || l != 0) {
								int j1 = 100;

								if (i1 > 1) {
									j1 += (i1 - 1) * 100;
								}

								BlockPos blockpos = pos.add(k, i1, l);
								int k1 = this.getNeighborEncouragement(worldIn, blockpos);

								if (k1 > 0) {
									int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);

									if (flag1) {
										l1 /= 2;
									}

									if (l1 > 0 && rand.nextInt(j1) <= l1
											&& (!worldIn.isRaining() || !this.canDie(worldIn, blockpos))) {
										int i2 = i + rand.nextInt(5) / 4;

										if (i2 > 15) {
											i2 = 15;
										}

										worldIn.setBlockState(blockpos, state.withProperty(AGE, Integer.valueOf(i2)),
												3);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean canDie(World worldIn, BlockPos pos) {
		return worldIn.isRainingAt(pos) || worldIn.isRainingAt(pos.west()) || worldIn.isRainingAt(pos.east())
				|| worldIn.isRainingAt(pos.north()) || worldIn.isRainingAt(pos.south());
	}

	@Override
	public boolean requiresUpdates() {
		return false;
	}

	@Deprecated
	public int getFlammability(Block blockIn) {
		Integer integer = this.flammabilities.get(blockIn);
		return integer == null ? 0 : integer.intValue();
	}

	@Deprecated
	public int getEncouragement(Block blockIn) {
		Integer integer = this.encouragements.get(blockIn);
		return integer == null ? 0 : integer.intValue();
	}

	@Deprecated
	private void catchOnFire(World worldIn, BlockPos pos, int chance, Random random, int age) {
		this.tryCatchFire(worldIn, pos, chance, random, age, EnumFacing.UP);
	}

	private void tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face) {
		int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);

		if (random.nextInt(chance) < i) {
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
				int j = age + random.nextInt(5) / 4;

				if (j > 15) {
					j = 15;
				}

				worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(j)), 3);
			} else {
				worldIn.setBlockToAir(pos);
			}

			if (iblockstate.getBlock() == Blocks.TNT) {
				Blocks.TNT.onBlockDestroyedByPlayer(worldIn, pos,
						iblockstate.withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
			}
		}
	}

	private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite())) {
				return true;
			}
		}

		return false;
	}

	private int getNeighborEncouragement(World worldIn, BlockPos pos) {
		if (!worldIn.isAirBlock(pos)) {
			return 0;
		} else {
			int i = 0;

			for (EnumFacing enumfacing : EnumFacing.values()) {
				i = Math.max(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getFireSpreadSpeed(worldIn,
						pos.offset(enumfacing), enumfacing.getOpposite()), i);
			}

			return i;
		}
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Deprecated
	public boolean canCatchFire(IBlockAccess worldIn, BlockPos pos) {
		return canCatchFire(worldIn, pos, EnumFacing.UP);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isTopSolid() || this.canNeighborCatchFire(worldIn, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.getBlockState(pos.down()).isTopSolid() && !this.canNeighborCatchFire(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState s) {
		if (!JourneyBlocks.eucaPortal.makePortal(world, pos)) {
			if (!world.getBlockState(pos.down()).isTopSolid())
				world.setBlockToAir(pos);
			else
				world.scheduleUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10));
		}
		if (!JourneyBlocks.boilPortal.makePortal(world, pos)) {
			if (!world.getBlockState(pos.down()).isTopSolid())
				world.setBlockToAir(pos);
			else
				world.scheduleUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10));
		}
		if (!JourneyBlocks.frozenPortal.makePortal(world, pos)) {
			if (!world.getBlockState(pos.down()).isTopSolid())
				world.setBlockToAir(pos);
			else
				world.scheduleUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10));
		}
		if (!JourneyBlocks.cloudiaPortal.makePortal(world, pos)) {
			if (!world.getBlockState(pos.down()).isTopSolid())
				world.setBlockToAir(pos);
			else
				world.scheduleUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10));
		}
		if (!JourneyBlocks.terraniaPortal.makePortal(world, pos)) {
			if (!world.getBlockState(pos.down()).isTopSolid())
				world.setBlockToAir(pos);
			else
				world.scheduleUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10));
		}
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.TNT;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(24) == 0) {
			worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
					(double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS,
					1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
		}

		if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)
				&& !JourneyBlocks.fire.canCatchFire(worldIn, pos.down(), EnumFacing.UP)) {
			if (JourneyBlocks.fire.canCatchFire(worldIn, pos.west(), EnumFacing.EAST)) {
				for (int j = 0; j < 2; ++j) {
					double d3 = (double) pos.getX() + rand.nextDouble() * 0.10000000149011612D;
					double d8 = (double) pos.getY() + rand.nextDouble();
					double d13 = (double) pos.getZ() + rand.nextDouble();
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
				}
			}

			if (JourneyBlocks.fire.canCatchFire(worldIn, pos.east(), EnumFacing.WEST)) {
				for (int k = 0; k < 2; ++k) {
					double d4 = (double) (pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
					double d9 = (double) pos.getY() + rand.nextDouble();
					double d14 = (double) pos.getZ() + rand.nextDouble();
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
				}
			}

			if (JourneyBlocks.fire.canCatchFire(worldIn, pos.north(), EnumFacing.SOUTH)) {
				for (int l = 0; l < 2; ++l) {
					double d5 = (double) pos.getX() + rand.nextDouble();
					double d10 = (double) pos.getY() + rand.nextDouble();
					double d15 = (double) pos.getZ() + rand.nextDouble() * 0.10000000149011612D;
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
				}
			}

			if (JourneyBlocks.fire.canCatchFire(worldIn, pos.south(), EnumFacing.NORTH)) {
				for (int i1 = 0; i1 < 2; ++i1) {
					double d6 = (double) pos.getX() + rand.nextDouble();
					double d11 = (double) pos.getY() + rand.nextDouble();
					double d16 = (double) (pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
				}
			}

			if (JourneyBlocks.fire.canCatchFire(worldIn, pos.up(), EnumFacing.DOWN)) {
				for (int j1 = 0; j1 < 2; ++j1) {
					double d7 = (double) pos.getX() + rand.nextDouble();
					double d12 = (double) (pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612D;
					double d17 = (double) pos.getZ() + rand.nextDouble();
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
				}
			}
		} else {
			for (int i = 0; i < 3; ++i) {
				double d0 = (double) pos.getX() + rand.nextDouble();
				double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
				double d2 = (double) pos.getZ() + rand.nextDouble();
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(AGE)).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE });
	}

	public boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return world.getBlockState(pos).getBlock().isFlammable(world, pos, face);
	}
}