package de.z1up.supercord.util.uuid;

// imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {

    // variables

    public static final long FEBRUARY_2015 = 1422748800000L;

    private static Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

    private static Map<String, UUID> uuidCache = new HashMap<String, UUID>();
    private static Map<UUID, String> nameCache = new HashMap<UUID, String>();

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private String name;
    private UUID id;

    // methods

    /**
     * check if player exists
     *
     * @param name
     * @return
     */
    public static boolean existsPlayer(String name) {
        return getUUID(name) != null;
    }

    /**
     * get uuid of player by name with consumer
     *
     * @param name
     * @param action
     */
    public static void getUUID(String name, Consumer<UUID> action) {
        pool.execute(() -> action.accept(getUUID(name)));
    }

    /**
     * get uuid of player by name
     *
     * @param name
     * @return
     */
    public static UUID getUUID(String name) {
        return getUUIDAt(name, System.currentTimeMillis());
    }

    /**
     * get uuid of player at timestamp by consumer
     *
     * @param name
     * @param timestamp
     * @param action
     */
    public static void getUUIDAt(String name, long timestamp, Consumer<UUID> action) {
        pool.execute(() -> action.accept(getUUIDAt(name, timestamp)));
    }


    /**
     * get uuid of player at timestamp
     *
     * @param name
     * @param timestamp
     * @return
     */
    public static UUID getUUIDAt(String name, long timestamp) {
        name = name.toLowerCase();
        if (uuidCache.containsKey(name)) {
            return uuidCache.get(name);
        }

        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(String.format(UUID_URL, name, timestamp / 1000)).openConnection();
            connection.setReadTimeout(5000);
            UUIDFetcher data = gson.fromJson(
                    new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher.class);

            if (data == null) {
                return null;
            }

            uuidCache.put(name, data.id);
            nameCache.put(data.id, data.name);
            return data.id;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get the name by consumer
     *
     * @param uuid
     * @param action
     */
    public static void getName(UUID uuid, Consumer<String> action) {
        pool.execute(() -> action.accept(getName(uuid)));
    }

    /**
     * get the current name of a player
     *
     * @param uuid
     * @return
     */
    public static String getName(UUID uuid) {
        if (nameCache.containsKey(uuid)) {
            return nameCache.get(uuid);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);
            UUIDFetcher[] nameHistory = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher[].class);
            UUIDFetcher currentNameData = nameHistory[nameHistory.length - 1];

            uuidCache.put(currentNameData.name.toLowerCase(), uuid);
            nameCache.put(uuid, currentNameData.name);

            return currentNameData.name;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}