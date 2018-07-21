package net.journey.dimension.nether;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderNetherJourney extends WorldProvider {   
	
    public void init() {
        this.biomeProvider = new BiomeProviderSingle(Biomes.HELL);
        this.doesWaterVaporize = true;
        this.nether = true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
    	
        return new Vec3d(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
    }
    
    @Override
    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderNether(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return false;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }

    @Override
    public WorldBorder createWorldBorder(){
        return new WorldBorder(){
            public double getCenterX(){
                return super.getCenterX() / 8.0D;
            }
            public double getCenterZ(){
                return super.getCenterZ() / 8.0D;
            }
        };
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionType.NETHER;
    }
}