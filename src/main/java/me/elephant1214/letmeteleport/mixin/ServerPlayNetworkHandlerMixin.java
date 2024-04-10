package me.elephant1214.letmeteleport.mixin;

import me.elephant1214.letmeteleport.LetMeTeleport;
import me.elephant1214.letmeteleport.mixin.accessor.ServerPlayerEntityAccessor;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(
            method = "onTeleportConfirm",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;requestedTeleportPos:Lnet/minecraft/util/math/Vec3d;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void addTeleportingPlayer(TeleportConfirmC2SPacket packet, CallbackInfo ci) {
        LetMeTeleport.addTeleport(this.player);
    }

    @Inject(
            method = "onPlayerMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isInTeleportationState()Z"
            )
    )
    private void setIsInTeleportState(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (LetMeTeleport.isTeleporting(this.player)) {
            ((ServerPlayerEntityAccessor) this.player).setInTeleportationState(true);
        }
    }

    @Inject(method = "onPlayerMove", at = @At("TAIL"))
    private void resetIsInTeleportState(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (LetMeTeleport.isTeleporting(this.player)) {
            this.player.onTeleportationDone();
            LetMeTeleport.removeTeleport(this.player);
        }
    }
}
