package de.z1up.supercord.interfaces;

/**
 * A subcommand from a Real command. Is used
 * when a command has a lot oc attributes or
 * variations
 */
public interface SubCommand {

    /**
     * Called in the constructor, when subcommand is
     * executed.
     */
    void runSubCommand();

}
