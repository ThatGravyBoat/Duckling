package tech.thatgravyboat.duckling.common.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
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
import tech.thatgravyboat.duckling.common.registry.ModCriteria;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;
import tech.thatgravyboat.duckling.common.registry.ModSounds;

import java.util.EnumSet;

public class QuacklingEntity extends MerchantEntity implements IAnimatable {

    private static final TradeOffers.Factory HOLIDAY_CAKE = (entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(ModItems.HOLIDAY_FRUIT_CAKE.get(), 3), 12, 10, 0.05F);

    public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.HOLIDAY_FRUIT_CAKE.get());
    private static final TrackedData<Boolean> DRIPPED = DataTracker.registerData(QuacklingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<ItemStack> FISHING_ROD = DataTracker.registerData(QuacklingEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    private final AnimationFactory factory = new AnimationFactory(this);

    public QuacklingEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createQuacklingAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(DRIPPED, false);
        this.getDataTracker().startTracking(FISHING_ROD, ItemStack.EMPTY);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        var initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        setDripped(world.getRandom().nextBoolean());
        return initialize;
    }

    public boolean isDripped() {
        return this.getDataTracker().get(DRIPPED);
    }

    public void setDripped(boolean dripped) {
        this.getDataTracker().set(DRIPPED, dripped);
    }

    public boolean isFishing() {
        return !this.getDataTracker().get(FISHING_ROD).isEmpty();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new StopFollowingCustomerGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2D));
        this.goalSelector.add(1, new LookAtCustomerGoal(this));
        this.goalSelector.add(2, new FishingGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new GoToWalkTargetGoal(this, 1D));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(9, new StopAndLookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DEEP_QUACK.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.QUACKLING_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.QUACKLING_DEATH.get();
    }

    @Override
    public SoundEvent getYesSound() {
        return getAmbientSound();
    }

    @Override
    protected SoundEvent getTradingSound(boolean sold) {
        return getAmbientSound();
    }

    @Override
    public void playCelebrateSound() {
        //Do nothing
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!isInvulnerableTo(source)) {
            if (isFishing()) {
                var stack = getDataTracker().get(FISHING_ROD).copy();
                dropStack(stack);
                getDataTracker().set(FISHING_ROD, ItemStack.EMPTY);
            }
            this.world.sendEntityStatus(this, (byte) 13);
        }
        return super.damage(source, amount);
    }

    @Override
    protected void afterUsing(TradeOffer offer) {
        this.world.sendEntityStatus(this, (byte) 14);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 13) {
            this.produceParticles(ParticleTypes.ANGRY_VILLAGER);
        } else if (status == 14) {
            this.produceParticles(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void fillRecipes() {
        fillRecipesForLevel(1);
        fillRecipesForLevel(2);
        fillRecipesForLevel(3);
        this.getOffers().add(HOLIDAY_CAKE.create(this, this.random));
    }

    private void fillRecipesForLevel(int level) {
        Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.FISHERMAN);
        if (int2ObjectMap != null && !int2ObjectMap.isEmpty()) {
            TradeOffers.Factory[] factories = int2ObjectMap.get(level);
            if (factories != null) {
                TradeOfferList tradeOfferList = this.getOffers();
                this.fillRecipesFromPool(tradeOfferList, factories, 2);
            }
        }
    }

    @Override
    public boolean isLeveledMerchant() {
        return false;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.FISHING_ROD) && !this.isFishing()) {
            getDataTracker().set(FISHING_ROD, itemStack);
            player.setStackInHand(hand, ItemStack.EMPTY);
            if (player instanceof ServerPlayerEntity serverPlayer)
                ModCriteria.INTERACT.trigger(serverPlayer, this, itemStack);
            return ActionResult.success(world.isClient);
        }
        if (this.isFishing() && itemStack.isEmpty() && player.isSneaking()) {
            var stack = getDataTracker().get(FISHING_ROD).copy();
            getDataTracker().set(FISHING_ROD, ItemStack.EMPTY);
            player.setStackInHand(hand, stack);
            return ActionResult.success(world.isClient);
        }

        if (itemStack.isOf(Items.SHEARS) && isDripped()) {
            setDripped(false);
            dropItem(Items.BIG_DRIPLEAF);
            return ActionResult.success(world.isClient);
        } else if (this.getHealth() < this.getMaxHealth() && itemStack.isOf(ModItems.HOLIDAY_FRUIT_CAKE.get()) && this.isAlive()) {
            this.heal(1f);
            itemStack.decrement(1);
            this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            return ActionResult.success(this.world.isClient);
        }else if (!itemStack.isOf(ModItems.QUACKLING_SPAWN_EGG.get()) && this.isAlive() && !this.hasCustomer() && !this.isFishing()) {
            if (hand == Hand.MAIN_HAND) {
                player.incrementStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.getOffers().isEmpty() && !this.world.isClient) {
                this.setCustomer(player);
                this.sendOffers(player, this.getDisplayName(), 1);
            }
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setDripped(nbt.getBoolean("Dripped"));
        if (nbt.contains("FishingRod", 10))
            this.getDataTracker().set(FISHING_ROD, ItemStack.fromNbt(nbt.getCompound("FishingRod")));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Dripped", isDripped());
        var stack = this.getDataTracker().get(FISHING_ROD);
        if (!stack.isEmpty())
            nbt.put("FishingRod", stack.writeNbt(new NbtCompound()));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.QUACKLING.get().create(world);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        var builder = new AnimationBuilder();
        if (this.isFishing()) {
            builder.addAnimation("fishing", true);
        } else if (event.isMoving()) {
            builder.addAnimation("walking", true);
        } else {
            builder.addAnimation("idle", true);
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    static class FishingGoal extends Goal {

        private final QuacklingEntity entity;

        public FishingGoal(QuacklingEntity entity) {
            this.entity = entity;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return entity.isFishing();
        }
    }
}
