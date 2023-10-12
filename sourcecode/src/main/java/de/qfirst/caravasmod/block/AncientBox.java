package de.qfirst.caravasmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AncientBox extends Block {
    public AncientBox(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d blockCenter = Vec3d.ofCenter(pos);
            double radius = 10.0;

            // Erzeuge eine Partikelkugel
            for (double r = 0; r <= radius; r += 1.0) {
                for (double theta = 0; theta <= Math.PI; theta += Math.PI / 10) {
                    for (double phi = 0; phi <= 2 * Math.PI; phi += Math.PI / 10) {
                        double x = (r/2) * Math.sin(theta) * Math.cos(phi);
                        double y = (r/2) * Math.sin(theta) * Math.sin(phi) + 2;
                        double z = (r/2) * Math.cos(theta);
                        serverWorld.spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockCenter.x + x, blockCenter.y + y, blockCenter.z + z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
            placeStructure(serverWorld, pos);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public static void placeStructure(ServerWorld world, BlockPos pos) {
        // Erstelle ein Array von BlockStates, das die Palette darstellt
        BlockState[] palette = new BlockState[] {
                Blocks.SPRUCE_WOOD.getDefaultState(),
                Blocks.SPRUCE_PLANKS.getDefaultState(),
                Blocks.SPRUCE_LOG.getDefaultState(),
                Blocks.SPRUCE_FENCE_GATE.getDefaultState(),
                Blocks.AIR.getDefaultState(),
                Blocks.SPRUCE_DOOR.getDefaultState(),
                Blocks.GLASS.getDefaultState()
        };

        // Definiere die Blöcke in der Struktur
        int[][][] blocks = new int[][][] {
                {
                        {0, 0, 0},
                        {2, 1, 2},
                        {2, 6, 2},
                        {2, 1, 2},
                        {0, 0, 0}
                },
                {
                        {0, 0, 0},
                        {1, 4, 3},
                        {6, 4, 3},
                        {1, 4, 1},
                        {0, 0, 0}
                },
                {
                        {0, 0, 0},
                        {2, 1, 2},
                        {2, 6, 2},
                        {2, 1, 2},
                        {0, 0, 0}
                }
        };

        //{0, 0, 0},
        //{2, 3, 2},
        //{2, 3, 2},
        //{2, 1, 2},
        //{0, 0, 0}
        // Setze die Blöcke in der Welt
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    // Hole den BlockState aus der Palette
                    BlockState state = palette[blocks[x][y][z]];

                    // Setze den BlockState in der Welt an der angegebenen Position
                    world.setBlockState(pos.add(x, y, z), state);
                }
            }
        }
    }


}
