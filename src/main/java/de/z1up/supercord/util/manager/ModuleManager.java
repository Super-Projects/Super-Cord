package de.z1up.supercord.util.manager;

import de.z1up.supercord.interfaces.Module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * A class to register new modules, and save them.
 * Can also create a new local id for each {@link Module}.
 */
public class ModuleManager {

    /** All the registered modules.*/
    public static Collection<Module> modules = new ArrayList<Module>();

    /**
     * Creates a new ID for a module. Checks if the id exists already,
     * if so, reruns the method.
     * @return The newly created ID.
     */
    public static int createInitID() {

        int id = new Random().nextInt(9999);

        modules.forEach(module -> {
            if(module.getInitID() == id) {
                createInitID();
                return;
            }
        });

        return id;
    }

    /**
     * Registers a new module to the collection {@code modules}.
     * @param e The module that needs to be registered.
     */
    public static void registerModule(Module e) {
        if(modules.contains(e)) return;
        modules.add(e);
    }

    /**
     * Unregisteres a module from the collection {@code modules}.
     * @param e The module that needs to be unregistered.
     */
    public static void unregisterModule(Module e) {
        if(modules.contains(e)) modules.remove(e);
    }

}
