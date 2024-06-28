package tech.thatgravyboat.duckling.common.items;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.duckling.common.entity.DuckEggEntity;

public class DuckEggItem extends Item implements ProjectileItem {
    public DuckEggItem(Item.Properties settings) {
        super(settings);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide()) {
            DuckEggEntity eggEntity = new DuckEggEntity(world, player);
            eggEntity.setItem(itemStack);
            eggEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(eggEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);

        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    @Override
    public @NotNull Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        DuckEggEntity eggEntity = new DuckEggEntity(level, position.x(), position.y(), position.z());
        eggEntity.setItem(itemStack);
        return eggEntity;
    }
}
