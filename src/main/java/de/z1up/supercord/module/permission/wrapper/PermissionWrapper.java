package de.z1up.supercord.module.permission.wrapper;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.module.player.o.Player;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class PermissionWrapper {

    private final String PERM_BYPASS = "sb.permission.bypass";

    public void updatePermissions(ProxiedPlayer proxiedPlayer) {

        if(Core.player.getPlayerWrapper().existsPlayer(proxiedPlayer)) {

            Player player = Core.player.getPlayerWrapper().getPlayer(proxiedPlayer);

            ArrayList<String> customPermissions = player.getPermissions();

            customPermissions.forEach(permission -> {
                proxiedPlayer.setPermission(permission, true);
            });

            Group group = player.getGroup();
            ArrayList<String> groupPerms = group.getPermissions();
            groupPerms.forEach(permission -> {
                proxiedPlayer.setPermission(permission, true);
            });

        }

    }

    public String getPERM_BYPASS() {
        return PERM_BYPASS;
    }
}
