package me.marc3308.siedlungundberufe.commands.eventcomamnds;

import org.bukkit.entity.Player;

public abstract class subcommands {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player p, String args[]);

}
