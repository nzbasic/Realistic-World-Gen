package rwg.surface;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import rwg.util.CellNoise;
import rwg.util.CliffCalculator;
import rwg.util.NoiseGenerator;

public class SurfaceGrassCanyon extends SurfaceBase {

    private byte claycolor;

    public SurfaceGrassCanyon(Block top, Block fill, byte b) {
        super(top, fill);
        claycolor = b;
    }

    @Override
    public void paintTerrain(Block[] blocks, byte[] metadata, int i, int j, int x, int y, int depth, World world,
            Random rand, NoiseGenerator perlin, CellNoise cell, float[] noise, float river, BiomeGenBase[] base) {
        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.3f ? true : false;

        for (int k = 255; k > -1; k--) {
            Block b = blocks[(y * 16 + x) * 256 + k];
            if (b == Blocks.air) {
                depth = -1;
            } else if (b == Blocks.stone) {
                depth++;

                if (depth > -1 && depth < 12) {
                    if (cliff) {
                        blocks[(y * 16 + x) * 256 + k] = Blocks.stained_hardened_clay;
                        metadata[(y * 16 + x) * 256 + k] = claycolor;
                    } else {
                        if (depth > 4) {
                            blocks[(y * 16 + x) * 256 + k] = Blocks.stained_hardened_clay;
                            metadata[(y * 16 + x) * 256 + k] = claycolor;
                        } else {
                            if (depth == 0) {
                                blocks[(y * 16 + x) * 256 + k] = topBlock;
                            } else {
                                blocks[(y * 16 + x) * 256 + k] = fillerBlock;
                            }
                        }
                    }
                } else if (k > 63) {
                    blocks[(y * 16 + x) * 256 + k] = Blocks.stained_hardened_clay;
                    metadata[(y * 16 + x) * 256 + k] = claycolor;
                }
            }
        }
    }
}
