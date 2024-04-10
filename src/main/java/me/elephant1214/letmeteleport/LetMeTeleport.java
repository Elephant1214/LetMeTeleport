package me.elephant1214.letmeteleport;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public final class LetMeTeleport implements ModInitializer {
    private static final LazySupplier<ArrayList<ServerPlayerEntity>> TELEPORTING_PLAYERS = new LazySupplier<>(ArrayList::new);

    public static void addTeleport(ServerPlayerEntity player) {
        TELEPORTING_PLAYERS.get().add(player);
    }

    public static boolean isTeleporting(ServerPlayerEntity player) {
        return TELEPORTING_PLAYERS.get().contains(player);
    }

    public static void removeTeleport(ServerPlayerEntity player) {
        TELEPORTING_PLAYERS.get().remove(player);
    }

    @Override
    public void onInitialize() {
    }
}