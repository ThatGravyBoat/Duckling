package tech.thatgravyboat.duckling.common.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;
import tech.thatgravyboat.duckling.common.registry.ModSounds;

import java.util.EnumSet;

public class QuacklingEntity extends AbstractVillager implements IAnimatable {

    private static final VillagerTrades.ItemListing HOLIDAY_CAKE = (entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(ModItems.HOLIDAY_FRUIT_CAKE.get(), 3), 12, 10, 0.05F);

    public static final Ingredient BREEDING_INGREDIENT = Ingredient.of(ModItems.HOLIDAY_FRUIT_CAKE.get());
    private static final EntityDataAccessor<Boolean> DRIPPED = SynchedEntityData.defineId(QuacklingEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<ItemStack> FISHING_ROD = SynchedEntityData.defineId(QuacklingEntity.class, EntityDataSerializers.ITEM_STACK);

    private final AnimationFactory factory = new AnimationFactory(this);

    public QuacklingEntity(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createQuacklingAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DRIPPED, false);
        this.getEntityData().define(FISHING_ROD, ItemStack.EMPTY);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        var initialize = super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
        setDripped(world.getRandom().nextBoolean());
        return initialize;
    }

    public boolean isDripped() {
        return this.getEntityData().get(DRIPPED);
    }

    public void setDripped(boolean dripped) {
        this.getEntityData().set(DRIPPED, dripped);
    }

    public boolean isFishing() {
        return !this.getEntityData().get(FISHING_ROD).isEmpty();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(2, new FishingGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DEEP_QUACK.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return ModSounds.QUACKLING_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.QUACKLING_DEATH.get();
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return getAmbientSound();
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean sold) {
        return getAmbientSound();
    }

    @Override
    public void playCelebrateSound() {
        //Do nothing
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (!isInvulnerableTo(source)) {
            if (isFishing()) {
                var stack = getEntityData().get(FISHING_ROD).copy();
                spawnAtLocation(stack);
                getEntityData().set(FISHING_ROD, ItemStack.EMPTY);
            }
            this.level.broadcastEntityEvent(this, (byte) 13);
        }
        return super.hurt(source, amount);
    }

    @Override
    protected void rewardTradeXp(@NotNull MerchantOffer merchantOffer) {
        this.level.broadcastEntityEvent(this, (byte) 14);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 13) {
            this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
        } else if (status == 14) {
            this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    protected void updateTrades() {
        fillRecipesForLevel(1);
        fillRecipesForLevel(2);
        fillRecipesForLevel(3);
        this.getOffers().add(HOLIDAY_CAKE.getOffer(this, this.random));
    }

    private void fillRecipesForLevel(int level) {
        Int2ObjectMap<VillagerTrades.ItemListing[]> int2ObjectMap = VillagerTrades.TRADES.get(VillagerProfession.FISHERMAN);
        if (int2ObjectMap != null && !int2ObjectMap.isEmpty()) {
            VillagerTrades.ItemListing[] factories = int2ObjectMap.get(level);
            if (factories != null) {
                MerchantOffers tradeOfferList = this.getOffers();
                this.addOffersFromItemListings(tradeOfferList, factories, 2);
            }
        }
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.FISHING_ROD) && !this.isFishing()) {
            getEntityData().set(FISHING_ROD, itemStack);
            player.setItemInHand(hand, ItemStack.EMPTY);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (this.isFishing() && itemStack.isEmpty() && player.isShiftKeyDown()) {
            var stack = getEntityData().get(FISHING_ROD).copy();
            getEntityData().set(FISHING_ROD, ItemStack.EMPTY);
            player.setItemInHand(hand, stack);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (itemStack.is(Items.SHEARS) && isDripped()) {
            setDripped(false);
            spawnAtLocation(Items.BIG_DRIPLEAF);
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else if (this.getHealth() < this.getMaxHealth() && itemStack.is(ModItems.HOLIDAY_FRUIT_CAKE.get()) && this.isAlive()) {
            this.heal(1f);
            itemStack.shrink(1);
            this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomX(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }else if (!itemStack.is(ModItems.QUACKLING_SPAWN_EGG.get()) && this.isAlive() && !this.isTrading() && !this.isFishing()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.getOffers().isEmpty() && !level.isClientSide()) {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        setDripped(nbt.getBoolean("Dripped"));
        if (nbt.contains("FishingRod", 10))
            this.getEntityData().set(FISHING_ROD, ItemStack.of(nbt.getCompound("FishingRod")));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Dripped", isDripped());
        var stack = this.getEntityData().get(FISHING_ROD);
        if (!stack.isEmpty())
            nbt.put("FishingRod", stack.save(new CompoundTag()));
    }

    @Nullable
    @Override
    public QuacklingEntity getBreedOffspring(@NotNull ServerLevel world, @NotNull AgeableMob entity) {
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
    public boolean requiresCustomPersistence() {
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
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return entity.isFishing();
        }
    }
}
