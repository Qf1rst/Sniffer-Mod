package de.qfirst.caravasmod.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SnifferShooter extends RangedWeaponItem implements Vanishable {
    public static final int TICKS_PER_SECOND = 20;
    public static final int RANGE = 15;

    public SnifferShooter(Item.Settings settings) {
        super(settings);
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            float f = getPullProgress(i);
            if (!((double)f < 0.1)) {
                if (!world.isClient) {
                    Vec3d pos = player.getEyePos();
                    Vec3d direction = player.getRotationVec(1.0f);
                    SnifferEntity sniffer = new SnifferEntity(EntityType.SNIFFER, world);
                    sniffer.setPosition(pos);
                    sniffer.setYaw(player.getYaw());
                    sniffer.setVelocity(direction.x * 3.0D, direction.y * 3.0D, direction.z * 3.0D);
                    world.spawnEntity(sniffer);
                }
                world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SNIFFER_EAT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.incrementStat(Stats.USED.getOrCreateStat(this));
                player.getItemCooldownManager().set(this, 20);
            }
        }
    }

    public static float getPullProgress(int useTicks){
        float f=(float)useTicks/20.0F;
        f=(f*f+f*2.0F)/3.0F;
        if(f>1.0F){
            f=1.0F;
        }
        return f;
    }

    public int getMaxUseTime(ItemStack stack){
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack){
        return UseAction.BOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){
        ItemStack itemStack=user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    public Predicate<ItemStack> getProjectiles(){
        return BOW_PROJECTILES;
    }

    public int getRange(){
        return 15;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.caravasmod.sniffer_shooter.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}