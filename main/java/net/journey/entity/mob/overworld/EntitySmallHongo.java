package net.journey.entity.mob.overworld;

import net.journey.JourneySounds;
import net.journey.entity.MobStats;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.slayer.api.SlayerAPI;
import net.slayer.api.entity.EntityModMob;

public class EntitySmallHongo extends EntityModMob {

	public EntitySmallHongo(World par1World) {
		super(par1World);
		addAttackingAI();
		this.setSize(0.5F, 0.7F);
	}

	@Override
	public double setAttackDamage(MobStats s) {
		return MobStats.SmallHongoDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return MobStats.SmallHongoHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return JourneySounds.SMALL_HONGO;
	}

	@Override
	public SoundEvent setHurtSound() {
		return JourneySounds.SMALL_HONGO_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return JourneySounds.SMALL_HONGO_HURT;
	}

	@Override
	public boolean getCanSpawnHere() {
		return 
			   this.world.getBlockState(new BlockPos(this.posX, this.posY-1, this.posZ)).getBlock() == Blocks.GRASS || 
			   		this.world.getBlockState(new BlockPos(this.posX, this.posY-1, this.posZ)).getBlock() == Blocks.LEAVES || 
			   			this.world.getBlockState(new BlockPos(this.posX, this.posY-1, this.posZ)).getBlock() == Blocks.SAND || 
			   				this.world.getBlockState(new BlockPos(this.posX, this.posY-1, this.posZ)).getBlock() == Blocks.DIRT;
	}

	@Override
	public Item getItemDropped() {
		return SlayerAPI.toItem(Blocks.BROWN_MUSHROOM);
	}
}