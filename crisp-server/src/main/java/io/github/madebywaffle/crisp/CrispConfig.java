package io.github.madebywaffle.crisp;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import org.bukkit.configuration.file.YamlConfiguration;

public final class CrispConfig {

    private static final File CONFIG_FILE = new File("crisp.yml");

    // Dynamic Activation of Brain (attribution: see PATCH-LICENSE)
    public static boolean dabEnabled;
    public static int startDistance;
    public static int startDistanceSquared;
    public static int maximumActivationPrio;
    public static int activationDistanceMod;
    public static boolean dabDontEnableIfInWater;

    private CrispConfig() {
    }

    public static void load() throws IOException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(CONFIG_FILE);
        config.options().setHeader(List.of(
            "Crisp configuration file",
            "See https://github.com/madebywaffle/Crisp for documentation"
        ));
        config.options().copyDefaults(true);

        config.addDefault("dab.enabled", true);
        config.addDefault("dab.start-distance", 12);
        config.addDefault("dab.max-tick-freq", 20);
        config.addDefault("dab.activation-dist-mod", 8);
        config.addDefault("dab.dont-enable-if-in-water", true);
        config.addDefault("dab.blacklisted-entities", List.of());
        config.setComments("dab", List.of(
            "Dynamic Activation of Brain: entities far away from players",
            "tick their AI goals and behaviors less frequently.",
            "Entities that are fighting, recently hurt, or breeding always tick",
            "at full rate regardless of distance, so throttling stays invisible.",
            "start-distance: distance from a player at which throttling starts",
            "max-tick-freq: how often (in ticks) the furthest entities tick their AI",
            "activation-dist-mod: freq = (distanceToPlayer^2) / (2^value); lower = tick less often",
            "dont-enable-if-in-water: land mobs in water keep full AI so they can swim out",
            "blacklisted-entities: entity ids excluded from DAB, e.g. [villager, zombie]"
        ));

        dabEnabled = config.getBoolean("dab.enabled");
        startDistance = config.getInt("dab.start-distance");
        startDistanceSquared = startDistance * startDistance;
        maximumActivationPrio = config.getInt("dab.max-tick-freq");
        activationDistanceMod = config.getInt("dab.activation-dist-mod");
        dabDontEnableIfInWater = config.getBoolean("dab.dont-enable-if-in-water");

        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            entityType.dabEnabled = true; // reset all, before disabling the blacklisted ones
        }
        for (String name : config.getStringList("dab.blacklisted-entities")) {
            Identifier key = Identifier.tryParse(name);
            Optional<EntityType<?>> entityType = key == null ? Optional.empty() : BuiltInRegistries.ENTITY_TYPE.getOptional(key);
            entityType.ifPresentOrElse(
                type -> type.dabEnabled = false,
                () -> MinecraftServer.LOGGER.warn("crisp.yml: unknown entity \"{}\" in dab.blacklisted-entities", name)
            );
        }

        config.save(CONFIG_FILE);
    }
}
