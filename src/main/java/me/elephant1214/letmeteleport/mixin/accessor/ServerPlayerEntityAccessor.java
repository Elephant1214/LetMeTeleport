package me.elephant1214.letmeteleport.mixin.accessor;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayerEntity.class)
public interface ServerPlayerEntityAccessor {
    @Accessor("inTeleportationState")
    void setInTeleportationState(boolean state);
}
