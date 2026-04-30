package net.whateclipse.tellurite.items.weapons;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.whateclipse.tellurite.entity.BloodProjectileEntity;

import javax.annotation.Nonnull;

public class NetheriteScytheItem extends SwordItem {
    public NetheriteScytheItem() {
        super(Tiers.NETHERITE, new Item.Properties()
                .attributes(createAttributes(Tiers.NETHERITE, 5.5F, -2.7F)));

    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        
        CustomData customData = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (customData.contains("tellurite")) {
            net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("tellurite");
            if (modTag.getBoolean("blood_reaper")) {
                if (!level.isClientSide) {
                    BloodProjectileEntity projectile = new BloodProjectileEntity(level, player, ItemStack.EMPTY);
                    projectile.setPos(player.getX(), player.getEyeY() - 0.1D, player.getZ());
                    level.addFreshEntity(projectile);
                }
                
                player.getCooldowns().addCooldown(this, 400); // 20 seconds cooldown (20 * 20 ticks)
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        }
        
        return InteractionResultHolder.pass(itemstack);
    }
}