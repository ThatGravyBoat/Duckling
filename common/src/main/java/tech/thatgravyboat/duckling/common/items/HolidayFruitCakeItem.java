package tech.thatgravyboat.duckling.common.items;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public class HolidayFruitCakeItem extends Item {

    //Kinda fruity
    private static final FoodComponent FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(1).build();

    public HolidayFruitCakeItem(Settings settings) {
        super(settings.food(FOOD));
    }
}
