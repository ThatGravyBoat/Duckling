package tech.thatgravyboat.duckling.common.entity;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;

public record TrackedEnum<T extends Enum<T>>(Class<T> enumClass) implements TrackedDataHandler<T> {

    @Override
    public void write(PacketByteBuf buf, T value) {
        buf.writeEnumConstant(value);
    }

    @Override
    public T read(PacketByteBuf buf) {
        return buf.readEnumConstant(enumClass);
    }

    @Override
    public T copy(T value) {
        return value;
    }
}

