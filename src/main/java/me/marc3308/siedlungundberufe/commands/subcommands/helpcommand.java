package me.marc3308.siedlungundberufe.commands.subcommands;

import me.marc3308.siedlungundberufe.commands.subcommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class helpcommand extends subcommand {
    @Override
    public String getName() {
        return "hilfe";
    }

    @Override
    public String getDescription() {
        return "sagt dir sachen";
    }

    @Override
    public String getSyntax() {
        return "/siedlung hilfe";
    }

    @Override
    public void perform(Player p, String[] args) {

        p.sendMessage(ChatColor.DARK_GREEN+"----------------------------------------------------");
        p.sendMessage("/siedlung create <Besitzer> <loc1-x> <loc1-z> <loc2-x> <loc2-z>");
        p.sendMessage("/siedlung edit <siedlung> <art> <neuerwert>");
        p.sendMessage("/siedlung delete <siedlung>");
        p.sendMessage("/siedlung info <siedlung>");
        p.sendMessage(ChatColor.DARK_GREEN+"----------------------------------------------------");

    }
}
