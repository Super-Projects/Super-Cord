package de.z1up.supercord.event;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Event;

/**
 * Called when a successful connection to a
 * game/play server is terminated.
 */
public class PlayServerDisconnectEvent extends Event {

    /** The {@link ServerInfo} of the newly connected server. */
    private ServerInfo serverInfo;

    /**
     * Initialises {@code serverInfo}
     * @param serverInfo The serverinfo of the disconnected server.
     */
    public PlayServerDisconnectEvent(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**
     * @return {@code serverInfo}
     */
    public ServerInfo getServerInfo() {
        return serverInfo;
    }
}
