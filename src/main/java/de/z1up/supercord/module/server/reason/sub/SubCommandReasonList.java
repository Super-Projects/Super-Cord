package de.z1up.supercord.module.server.reason.sub;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.server.reason.Reason;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;

public class SubCommandReasonList implements SubCommand {

    private CommandSender sender;
    private String[] args;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandReasonList(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // reason list <name>
    @Override
    public void runSubCommand() {

        if(args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/reason list <Reason- Type>"
            ).create());
            return;
        }

        String reasonTypeName = args[1].toUpperCase();

        if(Reason.ReasonType.valueOf(reasonTypeName) == null) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This Reason- Type doesn't exist!"
            ).create());

            String types = "";
            for(Reason.ReasonType reasonType : Reason.ReasonType.values()) {
                types = cc + reasonType.toString() + ChatColor.DARK_GRAY + ", ";
            }

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Possible Reason- Types: " + types
            ).create());
            return;
        }

        Reason.ReasonType reasonType = Reason.ReasonType.valueOf(reasonTypeName);
        ArrayList<Reason> reasons = Core.server.getReasonWrapper().getAllReasonsOf(reasonType);

        for(Reason reason : reasons) {
            sender.sendMessage(new ComponentBuilder(
                    cc + "#"+ reason.getId() + ChatColor.DARK_GRAY + " - " + cc + reason.getName()
                            + ChatColor.GRAY + ": "+ ChatColor.RED
                            + Core.server.getReasonWrapper().formDuration(reason.getDuration())
            ).create());

        }
        return;
    }
}
