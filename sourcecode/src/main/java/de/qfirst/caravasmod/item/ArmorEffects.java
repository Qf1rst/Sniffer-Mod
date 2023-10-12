package de.qfirst.caravasmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.util.List;


public class ArmorEffects {
    public static void applyEffects(PlayerEntity player) {
        if (isWearingFullArmor(player)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 219, 1, true, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION,219, 0,true, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 219, 0, true, false, true));

            if (player.isSneaking()) {
                Box box = new Box(player.getX() - 50, player.getY() - 50, player.getZ() - 50, player.getX() + 50, player.getY() + 50, player.getZ() + 50);
                List<Entity> entities = player.getWorld().getOtherEntities(player, box);
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200));
                    }
                }
                player.sendMessage(Text.translatable("text.caravasmod.glowing.text"),true);
            }
        }
    }

    private static boolean isWearingFullArmor(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.SNIFFER_HELMET
                && player.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.SNIFFER_CHESTPLATE
                && player.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.SNIFFER_LEGGINGS
                && player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.SNIFFER_BOOTS;
    }
}
