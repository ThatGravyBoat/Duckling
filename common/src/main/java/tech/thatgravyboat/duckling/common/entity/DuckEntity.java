package tech.thatgravyboat.duckling.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.thatgravyboat.duckling.common.constants.AnimationConstants;
import tech.thatgravyboat.duckling.common.constants.DuckVariant;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;
import tech.thatgravyboat.duckling.common.registry.ModSounds;

public class DuckEntity extends TamableAnimal implements GeoEntity {

    public static final Ingredient BREEDING_INGREDIENT = Ingredient.of(Items.BREAD);
    private static final EntityDataAccessor<Byte> VARIANT = SynchedEntityData.defineId(DuckEntity.class, EntityDataSerializers.BYTE);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public int eggLayTime;

    public DuckEntity(EntityType<DuckEntity> entityType, Level level) {
        super(entityType, level);
        this.eggLayTime = this.random.nextInt(6000) + 6000;
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static boolean canDuckSpawn(EntityType<DuckEntity> entity, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        if (type == MobSpawnType.SPAWNER) return true;
        return (level.isEmptyBlock(pos.above()) || level.isEmptyBlock(pos)) && level.getFluidState(pos.below()).is(Fluids.WATER);
    }

    public static AttributeSupplier.Builder createDuckAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 4);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        var stackInHand = player.getItemInHand(hand);
        if (stackInHand.is(ModItems.HOLIDAY_FRUIT_CAKE.get()) && this.isAlive() && this.getHealth() < this.getMaxHealth()) {
            this.heal(1);
            stackInHand.shrink(1);
            this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            return InteractionResult.sidedSuccess(player.level().isClientSide());
        }
        if (stackInHand.isEmpty() && this.isAlive()) {
            this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            return InteractionResult.sidedSuccess(player.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, (byte)1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        var group = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
        this.getEntityData().set(VARIANT, serverLevelAccessor.getRandom().nextBoolean() ? DuckVariant.MALLARD.id : DuckVariant.PEKIN.id);
        return group;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.QUACK.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DUCK_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return ModSounds.DUCK_DEATH.get();
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.getEntityData().set(VARIANT, DuckVariant.getVariant(nbt.getString("Variant")).id);
        if (nbt.contains("EggLayTime")) {
            this.eggLayTime = nbt.getInt("EggLayTime");
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("Variant", getVariant().name());
        nbt.putInt("EggLayTime", this.eggLayTime);
        //Note this is for the advancement do not remove!
        nbt.putBoolean("AgentD", getTexture().equals(DuckVariant.AGENTD));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3d = this.getDeltaMovement();
        if (!this.onGround() && vec3d.y < 0.0D) {
            this.setDeltaMovement(vec3d.multiply(1.0D, 0.8D, 1.0D));
        }
        if (!this.level().isClientSide() && this.isAlive() && !this.isBaby() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(ModItems.DUCK_EGG.get());
            this.eggLayTime = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    public DuckEntity getBreedOffspring(@NotNull ServerLevel world, @NotNull AgeableMob entity) {
        DuckEntity duckEntity = ModEntities.DUCK.get().create(world);
        DuckVariant variant = null;
        if (entity instanceof DuckEntity duck) {
            DuckVariant texture = this.getTexture();
            if (duck.getTexture() == texture && texture != DuckVariant.AGENTD) {
                variant = texture;
            }
        }
        if (duckEntity != null) {
            if (variant == null) {
                variant = world.random.nextBoolean() ? DuckVariant.PEKIN : DuckVariant.MALLARD;
            }
            duckEntity.getEntityData().set(VARIANT, variant.id);
        }
        return duckEntity;
    }

    public DuckVariant getVariant() {
        return DuckVariant.getVariant(this.entityData.get(VARIANT));
    }

    public DuckVariant getTexture() {
        return this.getName().getString().equalsIgnoreCase("agent d") ? DuckVariant.AGENTD : getVariant();
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (this.level().getFluidState(blockPosition().below()).is(FluidTags.WATER)) {
            event.getController().setAnimation(AnimationConstants.WALKING);
        } else if (!this.onGround()) {
            event.getController().setAnimation(AnimationConstants.FALLING);
        } else if (event.isMoving()) {
            event.getController().setAnimation(AnimationConstants.WALKING);
        } else {
            event.getController().setAnimation(AnimationConstants.IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
