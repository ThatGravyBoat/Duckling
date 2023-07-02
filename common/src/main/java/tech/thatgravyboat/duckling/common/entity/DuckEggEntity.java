package tech.thatgravyboat.duckling.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;

public class DuckEggEntity extends ThrowableItemProjectile {
    public DuckEggEntity(EntityType<? extends DuckEggEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DuckEggEntity(Level level, LivingEntity owner) {
        super(ModEntities.DUCK_EGG.get(), owner, level);
    }

    public DuckEggEntity(Level level, double d, double e, double f) {
        super(ModEntities.DUCK_EGG.get(), d, e, f, level);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(level().damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            if (this.random.nextInt(8) == 0) {
                int i = this.random.nextInt(32) == 0 ? 4 : 1;

                for(int j = 0; j < i; ++j) {
                    DuckEntity duck = ModEntities.DUCK.get().create(this.level());
                    if (duck != null) {
                        duck.setAge(-24000);
                        BlockPos pos = this.blockPosition();
                        duck.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.getYRot(), 0.0F);
                        this.level().addFreshEntity(duck);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.DUCK_EGG.get();
    }
}

