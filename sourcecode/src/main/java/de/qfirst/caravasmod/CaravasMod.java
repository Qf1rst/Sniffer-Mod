package de.qfirst.caravasmod;

import de.qfirst.caravasmod.block.ModBlocks;
import de.qfirst.caravasmod.item.ArmorEffects;
import de.qfirst.caravasmod.item.ModItemGroups;
import de.qfirst.caravasmod.item.ModItems;
import de.qfirst.caravasmod.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.PlaceCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaravasMod implements ModInitializer {
	public static final String MOD_ID = "caravasmod";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("caravasmod");

	@Override
	public void onInitialize() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerWorld world : server.getWorlds()) {
				for (PlayerEntity player : world.getPlayers()) {
					ArmorEffects.applyEffects(player);
					//ArmorEffects.invisEffect();
				}
			}
		});

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}