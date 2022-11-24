package saimoneiia.mods.saimoneiiasgearplus.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgression;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;
import saimoneiia.mods.saimoneiiasgearplus.util.MemoryLevelScaling;

import java.util.Collection;

public class MemorySetCommand {
    private static String points = "points";
    private static String levels = "levels";
    public MemorySetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("memory").requires((source)-> {
            return source.hasPermission(2);
        }).then(Commands.argument("targets", EntityArgument.players())
                .then(Commands.literal("set")
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .then(Commands.literal(points)
                                        .executes(command -> {
                                            return set(command.getSource(),
                                                    EntityArgument.getPlayers(command, "targets"),
                                                    IntegerArgumentType.getInteger(command, "amount"),
                                                    points);
                                        }))
                                .then(Commands.literal(levels)
                                        .executes(command -> {
                                            return set(command.getSource(),
                                                    EntityArgument.getPlayers(command, "targets"),
                                                    IntegerArgumentType.getInteger(command, "amount"),
                                                    levels);
                                        }))))
                .then(Commands.literal("reset")
                        .executes(command -> {
                            return reset(command.getSource(),
                                    EntityArgument.getPlayers(command, "targets"));
                        }))
                .then(Commands.literal("add")
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .then(Commands.literal(points)
                                        .executes(command -> {
                                            return add(command.getSource(),
                                                    EntityArgument.getPlayers(command, "targets"),
                                                    IntegerArgumentType.getInteger(command, "amount"),
                                                    points);
                                        }))
                                .then(Commands.literal(levels)
                                        .executes(command -> {
                                            return add(command.getSource(),
                                                    EntityArgument.getPlayers(command, "targets"),
                                                    IntegerArgumentType.getInteger(command, "amount"),
                                                    levels);
                                        }))))));
    }

    private int set(CommandSourceStack source, Collection<? extends ServerPlayer> players, int amount, String type) throws CommandSyntaxException {
        for(ServerPlayer player : players) {
            player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                if (type.equals(points)) {
                    memProg.setMem(amount);
                } else if (type.equals(levels)) {
                    memProg.setLevel(amount);
                }
            });
            MemoryProgression.syncPlayerMem(player);
        }

        source.sendSuccess(Component.literal(players.size() + " memory progressions changed."), true);

        return players.size();
    }

    private int reset(CommandSourceStack source, Collection<? extends ServerPlayer> players) throws CommandSyntaxException {
        for(ServerPlayer player : players) {
            player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                memProg.setMem(MemoryLevelScaling.MIN_MEM);
            });
            MemoryProgression.syncPlayerMem(player);
        }

        source.sendSuccess(Component.literal(players.size() + " memory progressions resetted."), true);

        return players.size();
    }

    private int add(CommandSourceStack source, Collection<? extends ServerPlayer> players, int amount, String type) throws CommandSyntaxException {
        for(ServerPlayer player : players) {
            player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                if (type.equals(points)) {
                    memProg.addMem(amount);
                } else if (type.equals(levels)) {
                    memProg.addLevel(amount);
                }
            });
            MemoryProgression.syncPlayerMem(player);
        }

        source.sendSuccess(Component.literal(players.size() + " memory progressions changed."), true);

        return players.size();
    }
}
