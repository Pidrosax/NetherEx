/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.Settings;
import nex.api.IEnhancedNetherBiome;
import nex.init.ModBlocks;
import nex.world.gen.feature.WorldGenFrost;
import nex.world.gen.feature.WorldGenMinableMeta;

import java.util.Random;

public class BiomeHoarFrost extends BiomeNetherEx implements IEnhancedNetherBiome
{
    private WorldGenerator frost = new WorldGenFrost();
    private WorldGenerator rimeOre = new WorldGenMinableMeta(ModBlocks.RIME_ORE.getDefaultState(), 3, ModBlocks.NETHERRACK.getStateFromMeta(1));

    public BiomeHoarFrost()
    {
        super(new BiomeProperties("Hoar Frost"));

        topBlock = ModBlocks.RIME_ICE.getDefaultState();
        fillerBlock = ModBlocks.NETHERRACK.getStateFromMeta(1);

        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        quartzOreBlock = ModBlocks.QUARTZ_ORE.getStateFromMeta(1);

        settingCategory = Settings.CATEGORY_BIOME_HOAR_FROST;

        setNameAndRegister("hoar_frost", Settings.biomeWeight(settingCategory));
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        super.decorate(world, rand, pos);

        if(Settings.generateFrost)
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.frostRarity; i++)
                {
                    frost.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.frostRarity; i++)
                {
                    frost.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.frostRarity; i++)
                {
                    frost.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
                }
            }
        }

        if(Settings.generateRimeOre)
        {
            if(Settings.generateTallNether)
            {
                for(int i = 0; i < Settings.rimeOreRarity; i++)
                {
                    rimeOre.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 64, rand.nextInt(16) + 8));
                }

                for(int i = 0; i < Settings.rimeOreRarity; i++)
                {
                    rimeOre.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 148, rand.nextInt(16) + 8));
                }
            }
            else
            {
                for(int i = 0; i < Settings.rimeOreRarity; i++)
                {
                    rimeOre.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(84) + 32, rand.nextInt(16) + 8));
                }
            }
        }
    }

    @Override
    public IBlockState getOceanBlock()
    {
        return Blocks.MAGMA.getDefaultState();
    }
}