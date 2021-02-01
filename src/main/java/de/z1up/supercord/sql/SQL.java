package de.z1up.supercord.sql;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.util.log.Log;
import de.z1up.supercord.util.message.SQLMessages;
import net.md_5.bungee.api.ProxyServer;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SQL {

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private Connection con;
    private static ExecutorService executor;

    // constructor

    /**
     * @param config
     */
    public SQL(HashMap<String, String> config) {
        this.host = config.get("host");
        this.port = config.get("port");
        this.database = config.get("database");
        this.username = config.get("username");
        this.password = config.get("password");
    }

    // methods

    /**
     * connect with database
     */
    public void connect() {
        if (!isConnected()) {
            try {
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";

                con = DriverManager.getConnection(url, username, password);
                executor = Executors.newCachedThreadPool();
                Log.a(SQLMessages.CON_CREATE_SUCCESS, Log.LogType.INFO, true);
            } catch (SQLException e) {
                Log.a(SQLMessages.CON_CREATE_FAILED, Log.LogType.WARNING, true);
            }
        }
    }

    /**
     * disconnect from database
     */
    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                Log.a(SQLMessages.CON_CLOSE, Log.LogType.WARNING, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * return if is connected
     *
     * @return
     */
    public boolean isConnected() {
        return (con == null ? false : true);
    }

    /**
     * get connection to db
     *
     * @return
     */
    public Connection getConnection() {
        try {
            if (isConnected()) {
                if (con.isValid(2)) {
                    return con;
                } else {
                    con.close();

                    Log.a(SQLMessages.CON_LOST, Log.LogType.WARNING, true);

                    String url = "jdbc:mysql://" + host + ":" + port + "/"
                            + database + "?autoReconnect=true";

                    con = DriverManager.getConnection(url, username, password);

                    Log.a(SQLMessages.CON_CREATE_SUCCESS, Log.LogType.INFO, true);
                    return con;
                }
            } else {
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";

                con = DriverManager.getConnection(
                        url, username, password);
                Log.a(SQLMessages.CON_CREATE_SUCCESS, Log.LogType.INFO, true);
                return con;
            }
        } catch (SQLException e) {
            Log.a(SQLMessages.CON_CREATE_FAILED, Log.LogType.WARNING, true);
        }
        return con;
    }

    /**
     * get statement from db
     *
     * @param statement
     * @return
     */
    public PreparedStatement getStatement(String statement) {
        try {
            return getConnection().prepareStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * execute update to db
     *
     * @param query
     * @param values
     */
    public void executeUpdate(String query, List<Object> values) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            if (values != null) {
                int stmts = values.size();
                for (int i = 1; i <= stmts; i++) {
                    Object obj = values.get(i - 1);
                    if (obj instanceof String || obj instanceof UUID) {
                        ps.setString(i, obj.toString());
                    } else if (obj instanceof Integer) {
                        ps.setInt(i, Integer.parseInt(obj.toString()));
                    } else if (obj instanceof Long) {
                        ps.setLong(i, Long.parseLong(obj.toString()));
                    } else if (obj instanceof Boolean) {
                        ps.setInt(i, (Boolean.getBoolean(obj.toString()) ? 1 : 0));
                    } else {
                        ps.setString(i, obj.toString());
                    }
                }
            }
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * get if result exists in db
     *
     * @param table
     * @param where
     * @param is
     * @return
     */
    public boolean existResult(String table, String where, Object is) {
        String stmt = "SELECT " + where + " FROM " + table + " WHERE " + where + " = ?";
        ResultSet rs = getResult(stmt, Arrays.asList(is));
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * count result from db
     *
     * @param table
     * @param where
     * @param is
     * @return
     */
    public int countResult(String table, String where, Object is) {
        String stmt = "SELECT count(*) FROM " + table + " WHERE " + where + " = ?";
        ResultSet rs = getResult(stmt, Arrays.asList(is));
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countTableSize(String table) {
        String stmt = "SELECT count(*) FROM " + table;
        ResultSet rs = getResult(stmt, null);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * get result from db with resultset
     *
     * @param query
     * @param values
     * @return
     */
    public ResultSet getResult(String query, List<Object> values) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            if (values != null) {
                int stmts = values.size();
                for (int i = 1; i <= stmts; i++) {
                    Object obj = values.get(i - 1);
                    if (obj instanceof String || obj instanceof UUID) {
                        ps.setString(i, obj.toString());
                    } else if (obj instanceof Integer) {
                        ps.setInt(i, Integer.parseInt(obj.toString()));
                    } else if (obj instanceof Long) {
                        ps.setLong(i, Long.parseLong(obj.toString()));
                    } else if (obj instanceof Boolean) {
                        ps.setInt(i, (Boolean.getBoolean(obj.toString()) ? 1 : 0));
                    } else {
                        ps.setString(i, obj.toString());
                    }
                }
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    // Asynchronous methods

    /**
     * execute update to db async
     *
     * @param query
     * @param values
     */
    public void executeUpdateAsync(String query, List<Object> values) {
        executor.execute(() -> executeUpdate(query, values));
    }

    /**
     * get result from db with resultset async
     *
     * @param query
     * @param values
     * @param consumer
     */
    public void getResultAsync(String query, List<Object> values, Consumer<ResultSet> consumer) {
        executor.execute(() -> {
            ResultSet result = getResult(query, values);
            ProxyServer.getInstance().getScheduler().schedule(SuperCord.instance, new Runnable() {
                @Override
                public void run() {
                    consumer.accept(result);
                }
            }, 1, TimeUnit.MILLISECONDS);
        });
    }
}