package de.z1up.supercord.module.server.command;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandServer extends Command {

    public CommandServer() {
        super("server", "sb.command.server");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(new ComponentBuilder(Core.server.getPrefix() + ChatColor.RED + "Wrong usage! Please use: ").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/server list" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Lists all registered servers").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/server join <name>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Connects you to a server").create());
            return;
        }

        if (args[0].equalsIgnoreCase("list")) {

            Collection<ServerInfo> onlineServers = Core.server.serverManager.getOnlineServers();

            ProxyServer.getInstance().getServers().forEach((s, serverInfo) -> {

                boolean online;
                if (onlineServers.contains(serverInfo)) {
                    online = true;
                } else {
                    online = false;
                }

                String msg = ChatColor.DARK_GRAY + "- "
                        + Core.server.getThemeColor() + serverInfo.getName()
                        + ChatColor.GRAY + " uses port " + Core.server.getThemeColor() + serverInfo.getAddress().getPort()
                        + ChatColor.GRAY + "! " +
                        ChatColor.DARK_GRAY + "[" + (online ? ChatColor.GREEN + "ONLINE" : ChatColor.RED + "OFFLINE") + ChatColor.DARK_GRAY + "]";

                if (sender instanceof ProxiedPlayer) {

                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    if (serverInfo.equals(player.getServer().getInfo())) {

                        msg = msg + " " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Your current server" + ChatColor.DARK_GRAY + "]";

                    }

                }

                sender.sendMessage(new ComponentBuilder(msg).create());

            });


            return;
        }

        if (args[0].equalsIgnoreCase("join")) {

            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(new ComponentBuilder(Core.server.getPrefix()
                        + ChatColor.RED + "Command can only be used by players!").create());
                return;
            }

            ProxiedPlayer player = (ProxiedPlayer) sender;


            if (args.length < 2) {
                player.sendMessage(new ComponentBuilder(Core.server.getPrefix()
                        + ChatColor.RED + "Use /server join <name>").create());
                return;
            }

            String identifier = args[1];

            AtomicBoolean serverFound = new AtomicBoolean(false);

            Core.server.serverManager.getOnlineServers().forEach(serverInfo -> {

                if (serverInfo.getName().equalsIgnoreCase(identifier)) {

                    serverFound.set(true);

                    if (player.getServer().getInfo().equals(serverInfo)) {
                        player.sendMessage(new ComponentBuilder(Core.server.getPrefix()
                                + ChatColor.RED + "You are already connected to " + serverInfo.getName() + "!").create());
                        return;
                    }

                    player.connect(serverInfo);
                    player.sendMessage(new ComponentBuilder(Core.server.getPrefix()
                            + ChatColor.GRAY + "Connecting to " + Core.server.getThemeColor() + serverInfo.getName()
                            + ChatColor.GRAY + "...").create());

                    return;
                }

            });

            if (!serverFound.get()) {
                player.sendMessage(new ComponentBuilder(Core.server.getPrefix()
                        + ChatColor.RED + "Server with identifier \"" + identifier + "\" coulnd't be found or isn't online!").create());
            }

            return;
        }
    }
}
