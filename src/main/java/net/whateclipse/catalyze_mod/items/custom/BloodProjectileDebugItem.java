package net.whateclipse.catalyze_mod.items.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.whateclipse.catalyze_mod.entity.BloodProjectileEntity;

import javax.annotation.Nonnull;

public class BloodProjectileDebugItem extends Item {
    public BloodProjectileDebugItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        
        if (!level.isClientSide) {
            BloodProjectileEntity projectile = new BloodProjectileEntity(level, player, ItemStack.EMPTY);
            projectile.setPos(player.getX(), player.getEyeY() - 0.1D, player.getZ());
            // It will inherently use the shooter's aim since we added shootFromRotation in its constructor
            level.addFreshEntity(projectile);
        }
        
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
