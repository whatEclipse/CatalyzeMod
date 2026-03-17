package net.whateclipse.catalyze_mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.whateclipse.catalyze_mod.entities.ModEntities;
import javax.annotation.Nonnull;

public class BloodProjectileEntity extends AbstractArrow {

    public BloodProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.setBaseDamage(17.33); // Velocity = 0.75, Damage = 13.0 (approx 17.33 * 0.75 = 12.9975)
        this.setNoGravity(true);
    }

    public BloodProjectileEntity(Level level, LivingEntity shooter, @Nonnull ItemStack pickupItem) {
        super(ModEntities.BLOOD_PROJECTILE.get(), shooter, level, pickupItem, null);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);
    }

    public BloodProjectileEntity(Level level, double x, double y, double z, @Nonnull ItemStack pickupItem) {
        super(ModEntities.BLOOD_PROJECTILE.get(), x, y, z, level, pickupItem, null);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult result) {
        super.onHitEntity(result);
        this.discard();
    }

    @Override
    public void tick() {
        // Run on both sides to ensure smooth movement and avoid jitter/teleporting
        if (!this.inGround) {
            Vec3 vec3 = this.getDeltaMovement();
            // If it has no velocity (e.g. summoned), initialize it once
            if (vec3.lengthSqr() < 0.001) {
                Vec3 look = Vec3.directionFromRotation(this.getXRot(), this.getYRot());
                this.setDeltaMovement(look.scale(0.75));
            } else {
                // Force constant speed of 0.75 blocks/tick (15 blocks/s) on both sides
                // This overrides air drag in AbstractArrow.tick() and prevents divergence
                @SuppressWarnings("null")
                Vec3 normalized = vec3.normalize();
                this.setDeltaMovement(normalized.scale(0.75));
            }
        }
        super.tick();
    }

    @Override
    protected void onHitBlock(@Nonnull net.minecraft.world.phys.BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDefaultHitGroundSoundEvent() {
        return null; // Silent on ground hit
    }

    @Override
    public void setSoundEvent(@Nonnull net.minecraft.sounds.SoundEvent sound) {
        // Do nothing to prevent other sounds from being set
    }

    @Override
    public void playSound(@Nonnull net.minecraft.sounds.SoundEvent sound, float volume, float pitch) {
        // Entirely suppress sounds from this entity to ensure it's silent
    }

    // Set constant speed if it somehow slows down due to air resistance
    @Override
    public void setDeltaMovement(@Nonnull Vec3 delta) {
        super.setDeltaMovement(delta);
    }
}
