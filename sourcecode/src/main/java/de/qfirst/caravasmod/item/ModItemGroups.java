package de.qfirst.caravasmod.item;


import de.qfirst.caravasmod.CaravasMod;
import de.qfirst.caravasmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CARAVAS_MOD = Registry.register(Registries.ITEM_GROUP, new Identifier(CaravasMod.MOD_ID, "caravas"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.caravas"))
                    .icon(() -> new ItemStack(Items.SUNFLOWER)).entries((displayContext, entries) -> {
                        entries.add(ModItems.SNIFFER_SHOOTER);
                        entries.add(ModBlocks.ANCIENT_BOX);
                        entries.add(ModItems.SNIFFER_BOOTS);
                        entries.add(ModItems.SNIFFER_LEGGINGS);
                        entries.add(ModItems.SNIFFER_CHESTPLATE);
                        entries.add(ModItems.SNIFFER_HELMET);
                        entries.add(ModItems.SNIFFER_KEY);
                    }).build());

    public static void registerItemGroups(){}
}
