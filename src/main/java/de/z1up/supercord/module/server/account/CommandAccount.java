package de.z1up.supercord.module.server.account;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class CommandAccount extends Command {

    public CommandAccount() {
        super("account", "sb.command.account", "acc");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED
                            + "Command can only be used by players."
            ).create());
            return;
        }

        if(args.length != 3) {
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED
                            + "Please use: /account <Email> <Password> <Password>"
            ).create());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        UUID uuid = player.getUniqueId();
        String email = args[0];
        String password = args[1];
        String passwordConfirm = args[2];

        if (Core.server.getAccountWrapper().hasPlayerAccount(uuid)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED
                            + "You already own an account."
            ).create());
            return;
        }

        if (Core.server.getAccountWrapper().isEmailInUse(email)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED
                            + "This email is already in use."
            ).create());
            return;
        }

        if(!password.equals(passwordConfirm)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED
                            + "Your passwords do not match."
            ).create());
            return;
        }

        Account account = new Account(0, uuid, email, password);
        account.create();

        sender.sendMessage(new ComponentBuilder(
                Core.server.getPrefix() + ChatColor.GRAY
                        + "Account created successfully! Login at report.super.com"
        ).create());
    }
}
