package de.z1up.supercord.module.report.command;

import de.z1up.supercord.module.report.command.sub.SubCommandReportsList;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandReports extends Command {

    public CommandReports() {
        super("reports", "cb.report.handle");
    }

    // reports list
    // reports handle <id/name>
    // reports accept
    // reports reject

    @Override
    public void execute(CommandSender sender, String[] args) {

       if(args.length >= 1) {

           if(args[0].equalsIgnoreCase("list")) {
               new SubCommandReportsList(sender, args);
               return;
           }

           if(args[0].equalsIgnoreCase("handle")) {
               new SubCommandReportsList(sender, args);
               return;
           }

           /*
           if(args[0].equalsIgnoreCase("accept")) {
               new SubCommandReportsAccept(sender, args);
               return;
           }

           if(args[0].equalsIgnoreCase("reject")) {
               new SubCommandReportsReject(sender, args);
               return;
           }
            */

       }
    }
}
