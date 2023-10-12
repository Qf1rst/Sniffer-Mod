package de.qfirst.caravasmod.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.lang.reflect.Array;
import java.util.Random;

public class ModLootTableModifiers {
    public static final Identifier SNIFFER_ID = new Identifier("minecraft", "gameplay/sniffer_digging");
    public static void addItem(Item item, float chance, float min, float max,  Identifier id, LootTable.Builder tableBuilder){
        if(SNIFFER_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(chance))
                    .with(ItemEntry.builder(item))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)).build());
            tableBuilder.pool(poolBuilder.build());
        }
    }



    public static void modifyLootTables(){
        Random rnd = new Random();
        int num = rnd.nextInt(3);
        Item item = Items.DIAMOND;
        float minDrop = 0;
        float maxDrop = 0;

        switch (num){
            case 0:
                item = Items.GOLDEN_CARROT;
                minDrop = 1.0f;
                maxDrop = 8.0f;
            break;
            case 1:
                item = Items.FIREWORK_ROCKET;
                minDrop = 8.0f;
                maxDrop = 16.0f;
                break;
            case 2:
                item = Items.ENCHANTED_GOLDEN_APPLE;
                minDrop = 1.0f;
                maxDrop = 1.0f;
                break;
        }

        Item finalItem = item;
        float finalMinDrop = minDrop;
        float finalMaxDrop = maxDrop;
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(SNIFFER_ID.equals(id)){
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.0f))
                        .with(ItemEntry.builder(finalItem))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(finalMinDrop, finalMaxDrop)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            //addItem(Items.GOLDEN_CARROT,0.05f, 1.0f,8.0f,  id, tableBuilder);
            //addItem(Items.FIREWORK_ROCKET,0.05f, 8.0f,16.0f,  id, tableBuilder);
            //addItem(Items.ENCHANTED_GOLDEN_APPLE,0.05f, 1.0f,1.0f,  id, tableBuilder);
            //addItem(Items.GOLDEN_APPLE,0.05f, 1.0f,3.0f,  id, tableBuilder);
            //addItem(Items.DIAMOND,0.05f, 1.0f,3.0f,  id, tableBuilder);
            //addItem(Items.DIAMOND_BLOCK,0.05f, 1.0f,2.0f,  id, tableBuilder);
            //addItem(Items.EMERALD,0.05f, 1.0f,3.0f,  id, tableBuilder);
            //addItem(Items.EMERALD_BLOCK,0.05f, 1.0f,2.0f,  id, tableBuilder);
            //addItem(Items.NETHERITE_INGOT,0.05f, 1.0f,3.0f,  id, tableBuilder);
            //addItem(Items.ANCIENT_DEBRIS,0.05f, 1.0f,3.0f,  id, tableBuilder);
            //addItem(Items.NETHERITE_BLOCK,0.05f, 1.0f,2.0f,  id, tableBuilder);
            //addItem(Items.GOLD_INGOT,0.05f, 1.0f,3.0f,  id, tableBuilder);
            //addItem(Items.GOLD_BLOCK,0.05f, 1.0f,2.0f,  id, tableBuilder);
            //addItem(Items.ENCHANTING_TABLE,0.05f, 1.0f,1.0f,  id, tableBuilder);
            //addItem(Items.TOTEM_OF_UNDYING,0.05f, 1.0f,1.0f,  id, tableBuilder);
            //addItem(Items.TNT,0.05f, 1.0f,9.0f,  id, tableBuilder);
            //addItem(Items.EXPERIENCE_BOTTLE,0.05f, 2.0f,8.0f,  id, tableBuilder);

        }));

    }

}
