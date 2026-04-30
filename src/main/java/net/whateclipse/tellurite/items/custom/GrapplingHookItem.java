package net.whateclipse.tellurite.items.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.whateclipse.tellurite.entity.GrapplingHookEntity;

import javax.annotation.Nonnull;
import java.util.List;

public class GrapplingHookItem extends Item {
    public GrapplingHookItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);

        if (!level.isClientSide) {
            GrapplingHookEntity activeHook = null;
            
            // Search for an existing hook owned by the player
            List<GrapplingHookEntity> hooks = level.getEntitiesOfClass(GrapplingHookEntity.class, player.getBoundingBox().inflate(64.0D), (hook) -> hook.getOwner() == player);
            if (!hooks.isEmpty()) {
                activeHook = hooks.get(0);
            }

            if (player.isShiftKeyDown()) {
                // Sneak + Right Click: Immediate Recall
                if (activeHook != null) {
                    activeHook.setFlag(GrapplingHookEntity.FLAG_RECALLED, true);
                    activeHook.setFlag(GrapplingHookEntity.FLAG_REELING, false);
                }
            } else {
                // Normal Right Click
                if (activeHook == null) {
                    // No active hook: Spawn one
                    GrapplingHookEntity hook = new GrapplingHookEntity(level, player);
                    hook.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                    level.addFreshEntity(hook);
                } else {
                    // Hook exists: Toggle Reeling or Recall
                    if (activeHook.isInGround()) {
                        activeHook.setFlag(GrapplingHookEntity.FLAG_REELING, !activeHook.getFlag(GrapplingHookEntity.FLAG_REELING));
                    } else {
                        // If it's in flight, just trigger recall
                        activeHook.setFlag(GrapplingHookEntity.FLAG_RECALLED, true);
                    }
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
