package tech.thatgravyboat.duckling.common.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class HolidayFruitCakeItem extends Item {

    //Kinda fruity
    private static final FoodProperties FOOD = new FoodProperties.Builder().nutrition(3).saturationMod(1).build();

    public HolidayFruitCakeItem(Properties settings) {
        super(settings.food(FOOD));
    }
}
