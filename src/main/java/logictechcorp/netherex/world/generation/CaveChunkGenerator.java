/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.world.generation;

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.NetherChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.List;

public class CaveChunkGenerator extends NetherChunkGenerator
{
    public CaveChunkGenerator(World world, BiomeProvider biomeProvider, NetherGenSettings settings)
    {
        super(world, biomeProvider, settings);
    }

    @Override
    public void func_225550_a_(BiomeManager biomeManager, IChunk chunk, GenerationStage.Carving stage)
    {
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(this.getBiome(biomeManager, chunk.getPos().asBlockPos()));

        if(biomeData != BiomeData.EMPTY)
        {
            biomeData.carve(biomeManager, chunk, stage, this.seed, this.getSeaLevel());
        }
    }

    @Override
    public void decorate(WorldGenRegion region)
    {
        int chunkX = region.getMainChunkX();
        int chunkZ = region.getMainChunkZ();
        int posX = chunkX * 16;
        int posZ = chunkZ * 16;
        BlockPos pos = new BlockPos(posX, 0, posZ);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(this.getBiome(region.getBiomeManager(), pos.add(8, 8, 8)));
        SharedSeedRandom random = new SharedSeedRandom();
        long decorationSeed = random.setDecorationSeed(region.getSeed(), posX, posZ);

        if(biomeData != BiomeData.EMPTY)
        {
            for(GenerationStage.Decoration stage : GenerationStage.Decoration.values())
            {
                biomeData.decorate(stage, this, region, decorationSeed, random, pos);
            }
        }
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification classification, BlockPos pos)
    {
        if(classification == EntityClassification.MONSTER)
        {
            if(Feature.NETHER_BRIDGE.isPositionInsideStructure(this.world, pos))
            {
                return Feature.NETHER_BRIDGE.getSpawnList();
            }

            if(Feature.NETHER_BRIDGE.isPositionInStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICKS)
            {
                return Feature.NETHER_BRIDGE.getSpawnList();
            }
        }

        Biome biome = this.world.getBiome(pos);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(biome);
        List<Biome.SpawnListEntry> spawns = new ArrayList<>();

        if(biomeData != BiomeData.EMPTY && biomeData.useDefaultEntities())
        {
            spawns.addAll(biome.getSpawns(classification));
        }

        spawns.addAll(biomeData.getSpawns(classification));
        return spawns;
    }
}
