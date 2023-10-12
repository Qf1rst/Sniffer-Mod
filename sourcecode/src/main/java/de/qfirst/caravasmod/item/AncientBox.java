package de.qfirst.caravasmod.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import de.qfirst.caravasmod.CaravasMod;
import de.qfirst.caravasmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesList;
import net.minecraft.structure.StructureStart;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.SwampHutStructure;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AncientBox extends Block {
    public AncientBox(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == ModItems.SNIFFER_KEY) {
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
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
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
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    // Hole den BlockState aus der Palette
                    BlockState state = palette[blocks[x][y][z]];

                    // Setze den BlockState in der Welt an der angegebenen Position
                    world.setBlockState(pos.add(x - 1, y -1, z - 1), state);
                }
            }
        }

        // Erstelle eine Rüstungsständer-Entität
        ArmorStandEntity armorStand = new ArmorStandEntity(EntityType.ARMOR_STAND,
                world);
        armorStand.refreshPositionAndAngles(pos.getX() + 0.5,
                pos.getY() + blocks[0].length + - 4,
                pos.getZ() + 0.5,
                armorStand.getYaw(),
                armorStand.getPitch());

        // Setze die Rüstung des Rüstungsständers
        armorStand.equipStack(EquipmentSlot.FEET,
                new ItemStack(ModItems.SNIFFER_BOOTS));
        armorStand.equipStack(EquipmentSlot.LEGS,
                new ItemStack(ModItems.SNIFFER_LEGGINGS));
        armorStand.equipStack(EquipmentSlot.CHEST,
                new ItemStack(ModItems.SNIFFER_CHESTPLATE));
        armorStand.equipStack(EquipmentSlot.HEAD,
                new ItemStack(ModItems.SNIFFER_HELMET));

        // Füge die Rüstungsständer-Entität zur Welt hinzu
        world.spawnEntity(armorStand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.caravasmod.ancient_box.tooltip"));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
