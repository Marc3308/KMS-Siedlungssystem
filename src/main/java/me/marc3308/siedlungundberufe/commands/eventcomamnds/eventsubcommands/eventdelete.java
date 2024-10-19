package me.marc3308.siedlungundberufe.commands.eventcomamnds.eventsubcommands;

import me.marc3308.siedlungundberufe.commands.eventcomamnds.subcommands;
import me.marc3308.siedlungundberufe.objektorientierung.eventzone;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.eventzoneliste;

public class eventdelete extends subcommands{
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "delets a event sone";
    }

    @Override
    public String getSyntax() {
        return "/event delete <name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<2)return;

        for (eventzone e : eventzoneliste)if(args[1].equals(e.getName()))eventzoneliste.remove(e);

        p.sendMessage(ChatColor.RED+args[1]+" wurde erfolgreich gelöscht");

    }
}
