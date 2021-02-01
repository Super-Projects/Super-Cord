package de.z1up.supercord.util.manager;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.event.PlayServerConnectEvent;
import de.z1up.supercord.event.PlayServerDisconnectEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * A class to manage connected and disconnected servers. Will be important
 * for {@link de.z1up.supercord.module.server.command.CommandServer} to change servers and list connected ones.
 */
public class ServerManager {

    private ServerChecker serverChecker;
    private Collection<ServerInfo> onlineServers;

    /**
     * Initialises the {@link ServerChecker} and the collection
     * as an ArrayList of the connected servers.
     */
    public ServerManager() {
        this.serverChecker = new ServerChecker();
        this.onlineServers = new ArrayList();
    }

    /**
     * Adds a new server to the collection by its serverinfo.
     * @param serverInfo The serverinfo of the server, that will be added.
     */
    public void addOnlineServer(ServerInfo serverInfo) {
        onlineServers.add(serverInfo);
    }

    /**
     * Removes a server from the collection by its serverinfo.
     * @param serverInfo The serverinfo of the server, that has to be removed.
     */
    public void removeOnlineServer(ServerInfo serverInfo) {
        onlineServers.remove(serverInfo);
    }

    /**
     * @param serverInfo The server that needs to be checked.
     * @return If the server is connected or not.
     */
    public boolean existsOnlineServer(ServerInfo serverInfo) {
        return onlineServers.contains(serverInfo);
    }

    /**
     * Returns a collection of the servers a connection could be established to.
     * @return A collection of their serverinfos.
     */
    public Collection<ServerInfo> getOnlineServers() {
        return onlineServers;
    }

    /**
     * A sub class of {@link ServerManager}, starts a {@link net.md_5.bungee.api.scheduler.TaskScheduler}
     * which checks for a new connection or a disconnected playserver.
     */
    public class ServerChecker {

        /**
         * The scheduler is started, when constructor is called.
         * No attribute needed.
         */
        public ServerChecker() {

            ProxyServer.getInstance().getScheduler().schedule(SuperCord.instance, new Runnable() {

                @Override
                public void run() {
                    ProxyServer.getInstance().getServers().forEach((server, serverInfo) -> {

                        try {
                            Socket s = new Socket();
                            s.connect(serverInfo.getSocketAddress(), 15);
                            s.close();

                            if (!Core.server.serverManager.existsOnlineServer(serverInfo)) {
                                PlayServerConnectEvent connectEvent = new PlayServerConnectEvent(serverInfo);
                                ProxyServer.getInstance().getPluginManager().callEvent(connectEvent);
                            }

                        } catch (UnknownHostException e) {
                            if (Core.server.serverManager.existsOnlineServer(serverInfo)) {
                                PlayServerDisconnectEvent disconnectEvent = new PlayServerDisconnectEvent(serverInfo);
                                ProxyServer.getInstance().getPluginManager().callEvent(disconnectEvent);
                            }
                        } catch (IOException e) {
                            if (Core.server.serverManager.existsOnlineServer(serverInfo)) {
                                PlayServerDisconnectEvent disconnectEvent = new PlayServerDisconnectEvent(serverInfo);
                                ProxyServer.getInstance().getPluginManager().callEvent(disconnectEvent);
                            }
                        }
                    });
                }

            }, 1, 5, TimeUnit.SECONDS);
        }
    }
}
