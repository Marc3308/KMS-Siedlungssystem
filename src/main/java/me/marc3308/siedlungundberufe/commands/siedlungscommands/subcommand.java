package me.marc3308.siedlungundberufe.commands.siedlungscommands;

import org.bukkit.entity.Player;

public abstract class subcommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player p, String args[]);

}
