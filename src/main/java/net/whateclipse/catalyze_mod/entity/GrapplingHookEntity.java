package net.whateclipse.catalyze_mod.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.whateclipse.catalyze_mod.entities.ModEntities;
import net.whateclipse.catalyze_mod.items.ModItems;
import javax.annotation.Nonnull;

public class GrapplingHookEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Byte> ANCHOR_FLAGS = SynchedEntityData.defineId(GrapplingHookEntity.class, EntityDataSerializers.BYTE);

    public static final byte FLAG_DEALT_DAMAGE = 1;
    public static final byte FLAG_REELING = 2;
    public static final byte FLAG_RECALLED = 4;

    private static final double HOMING_SPEED = 0.6;
    private static final double PULL_STRENGTH = 0.2;

    public GrapplingHookEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public GrapplingHookEntity(Level level, LivingEntity shooter) {
        super(ModEntities.GRAPPLING_HOOK.get(), shooter, level, new ItemStack(ModItems.GRAPPLING_HOOK.get()), null);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANCHOR_FLAGS, (byte) 0);
    }

    public boolean getFlag(byte flag) {
        return (this.entityData.get(ANCHOR_FLAGS) & flag) != 0;
    }

    public void setFlag(byte flag, boolean value) {
        byte b = this.entityData.get(ANCHOR_FLAGS);
        if (value) {
            this.entityData.set(ANCHOR_FLAGS, (byte) (b | flag));
        } else {
            this.entityData.set(ANCHOR_FLAGS, (byte) (b & ~flag));
        }
    }

    @Override
    public void tick() {
        if (this.getFlag(FLAG_DEALT_DAMAGE) && !this.level().isClientSide) {
             // Logic can be added here if needed to stop processing after damage
        }

        if (this.getFlag(FLAG_RECALLED)) {
            this.setNoClip(true);
            LivingEntity owner = (LivingEntity) this.getOwner();
            if (owner != null) {
                Vec3 ownerPos = owner.getEyePosition();
                Vec3 toOwner = ownerPos.subtract(this.position());
                if (toOwner.lengthSqr() < 1.0 || this.distanceTo(owner) > 30.0) {
                    this.discard();
                    return;
                }
                this.setDeltaMovement(toOwner.normalize().scale(HOMING_SPEED));
            } else {
                this.discard();
                return;
            }
        } else if (this.inGround && this.getFlag(FLAG_REELING)) {
            LivingEntity owner = (LivingEntity) this.getOwner();
            if (owner != null) {
                Vec3 pullVector = this.position().subtract(owner.getEyePosition());
                owner.setDeltaMovement(owner.getDeltaMovement().scale(0.95).add(pullVector.normalize().scale(PULL_STRENGTH)));
                owner.hurtMarked = true;
            }
        }
        super.tick();
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult result) {
        this.setFlag(FLAG_DEALT_DAMAGE, true);
        super.onHitEntity(result);
        
        if (this.getFlag(FLAG_REELING)) {
            if (result.getEntity() instanceof LivingEntity target) {
                LivingEntity owner = (LivingEntity) this.getOwner();
                if (owner != null) {
                    Vec3 pull = owner.position().subtract(target.position()).normalize().scale(PULL_STRENGTH * 2);
                    target.setDeltaMovement(target.getDeltaMovement().add(pull));
                    target.hurtMarked = true;
                }
            }
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.GRAPPLING_HOOK.get());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.GRAPPLING_HOOK.get());
    }
}
