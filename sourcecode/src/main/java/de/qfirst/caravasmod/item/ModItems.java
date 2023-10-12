package de.qfirst.caravasmod.item;

import de.qfirst.caravasmod.CaravasMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SNIFFER_SHOOTER = registerItem("sniffer_shooter", new SnifferShooter(new FabricItemSettings().maxCount(1)));
    public static final Item SNIFFER_KEY = registerItem("ancient_key", new Item(new FabricItemSettings()));
    public static final Item SNIFFER_BOOTS = registerItem("sniffer_boots", new ArmorItem(ModArmorMaterials.SNIFFER, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));
    public static final Item SNIFFER_LEGGINGS = registerItem("sniffer_leggings", new ArmorItem(ModArmorMaterials.SNIFFER, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item SNIFFER_CHESTPLATE = registerItem("sniffer_chestplate", new ArmorItem(ModArmorMaterials.SNIFFER, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item SNIFFER_HELMET = registerItem("sniffer_helmet", new ArmorItem(ModArmorMaterials.SNIFFER, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries){

    }
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(CaravasMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
