package me.marc3308.siedlungundberufe.commands.eventcomamnds.eventsubcommands;

import me.marc3308.siedlungundberufe.commands.eventcomamnds.subcommands;
import me.marc3308.siedlungundberufe.objektorientierung.eventzone;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.eventzoneliste;

public class eventcreate extends subcommands {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "create a eventzone";
    }

    @Override
    public String getSyntax() {
        return "/event create <Name> <x-y-z> <x-y-z> <Zeit> <DarfTp> <x-y-z> <Schaden>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<14)return;

        Location loc1 =p.getLocation().set(Integer.valueOf(args[2]),Integer.valueOf(args[3]),Integer.valueOf(args[4]));
        Location loc2 =p.getLocation().set(Integer.valueOf(args[5]),Integer.valueOf(args[6]),Integer.valueOf(args[7]));
        Location tploc =p.getLocation().set(Integer.valueOf(args[10]),Integer.valueOf(args[11]),Integer.valueOf(args[12]));

        eventzoneliste.add(new eventzone(args[1],loc1,loc2,Integer.valueOf(args[8]),Boolean.valueOf(args[9]),tploc,Integer.valueOf(args[13])));

        System.out.println(ChatColor.GREEN+args[1]+" erfolgreich geadded");

    }
}
