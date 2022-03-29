package tech.thatgravyboat.duckling.common.entity;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import tech.thatgravyboat.duckling.Duckling;

public class ItemInteractCriterion extends AbstractCriterion<ItemInteractCriterion.Condition> {
    static final Identifier ID = Duckling.modId("interact");

    @Override
    protected Condition conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
        EntityPredicate.Extended extended = EntityPredicate.Extended.getInJson(jsonObject, "entity", predicateDeserializer);
        return new Condition(playerPredicate, extended, itemPredicate);
    }

    public void trigger(ServerPlayerEntity player, Entity entity, ItemStack stack) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
        this.trigger(player, (conditions) -> conditions.matches(lootContext, stack));
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static class Condition extends AbstractCriterionConditions {

        private final EntityPredicate.Extended entity;
        private final ItemPredicate item;

        public Condition(EntityPredicate.Extended playerPredicate, EntityPredicate.Extended entity, ItemPredicate predicate) {
            super(ID, playerPredicate);
            this.entity = entity;
            this.item = predicate;
        }

        public boolean matches(LootContext context, ItemStack stack) {
            return this.entity.test(context) && item.test(stack);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            return super.toJson(predicateSerializer);
        }
    }
}
