package tech.thatgravyboat.duckling.common.registry;

import tech.thatgravyboat.duckling.common.entity.ItemInteractCriterion;
import tech.thatgravyboat.duckling.platform.CommonServices;

public class ModCriteria {

    public static final ItemInteractCriterion INTERACT = CommonServices.REGISTRY.createCriteria(new ItemInteractCriterion());

    public static void register() {
        //Initalize class
    }
}
