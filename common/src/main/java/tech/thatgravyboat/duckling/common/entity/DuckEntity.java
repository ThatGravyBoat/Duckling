package tech.thatgravyboat.duckling.common.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import tech.thatgravyboat.duckling.common.constants.DuckVariant;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;
import tech.thatgravyboat.duckling.common.registry.ModSounds;

public class DuckEntity extends TameableEntity implements IAnimatable {

    public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.BREAD);
    private static final TrackedData<DuckVariant> VARIANT = DataTracker.registerData(DuckEntity.class, ModEntities.DUCK_VARIANT);

    private final AnimationFactory factory = new AnimationFactory(this);
    public int eggLayTime;

    public DuckEntity(EntityType<DuckEntity> entityType, World world) {
        super(entityType, world);
        this.eggLayTime = this.random.nextInt(6000) + 6000;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    public static DefaultAttributeContainer.Builder createDuckAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        var stackInHand = player.getStackInHand(hand);
        if (stackInHand.isOf(ModItems.HOLIDAY_FRUIT_CAKE.get()) && this.isAlive() && this.getHealth() < this.getMaxHealth()) {
            this.heal(1);
            stackInHand.decrement(1);
            this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            return ActionResult.success(player.world.isClient);
        }
        if (stackInHand.isEmpty() && this.isAlive()) {
            this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            return ActionResult.success(player.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(VARIANT, DuckVariant.PEKIN);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        var initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.getDataTracker().set(VARIANT, world.getRandom().nextBoolean() ? DuckVariant.MALLARD : DuckVariant.PEKIN);
        return initialize;
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
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.DUCK_DEATH.get();
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.getDataTracker().set(VARIANT, DuckVariant.getVariant(nbt.getString("Variant")));
        if (nbt.contains("EggLayTime")) {
            this.eggLayTime = nbt.getInt("EggLayTime");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Variant", this.getDataTracker().get(VARIANT).name());
        nbt.putBoolean("AgentD", this.getName().asString().equalsIgnoreCase("agent d"));
        nbt.putInt("EggLayTime", this.eggLayTime);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.8D, 1.0D));
        }
        if (!this.world.isClient && this.isAlive() && !this.isBaby() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ModItems.DUCK_EGG.get());
            this.eggLayTime = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        var duckEntity = ModEntities.DUCK.get().create(world);
        DuckVariant variant = null;
        if (entity instanceof DuckEntity duck && duckEntity != null) {
            var texture = this.getTexture();
            if (duck.getTexture() == texture && texture != DuckVariant.AGENTD) {
                variant = texture;
            }
        }
        if (duckEntity != null) {
            if (variant == null) {
                variant = world.random.nextBoolean() ? DuckVariant.PEKIN : DuckVariant.MALLARD;
            }
            duckEntity.getDataTracker().set(VARIANT, variant);
        }
        return duckEntity;
    }

    public DuckVariant getTexture() {
        return this.getName().asString().equalsIgnoreCase("agent d") ? DuckVariant.AGENTD : this.getDataTracker().get(VARIANT);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        var builder = new AnimationBuilder();
        if (this.world.getFluidState(getBlockPos().down()).isIn(FluidTags.WATER)) {
            builder.addAnimation("walking", true);
        } else if (!this.onGround) {
            builder.addAnimation("falling", true);
        } else if (event.isMoving()) {
            builder.addAnimation("walking", true);
        } else {
            builder.addAnimation("idle", true);
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
