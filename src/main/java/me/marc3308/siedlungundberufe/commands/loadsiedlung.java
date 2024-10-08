package me.marc3308.siedlungundberufe.commands;

import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;

public class loadsiedlung implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player)return false;
        File file = new File("plugins/KMS Plugins/Siedlungundberufe","Siedlungen.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        //clears the liste
        siedlungsliste.clear();

        for (int i = 0; i < 300; i++) {
            if(con.get(i+".Owner")==null)break;
            siedlungsliste.add(new siedlung(
                    con.getStringList(i+".Owner")
                    ,con.getLocation(i+".loc1")
                    ,con.getLocation(i+".loc2")
                    ,con.getString(i+".Name")
                    ,con.getString(i+".Beschreibung")
                    ,con.getInt(i+".Custemmoddeldata")
                    ,con.getString(i+".Block")
                    ,con.getStringList(i+".memberlist")
                    ,con.getInt(i+".Stufe")
                    ,con.getString(i+".wilkommen")
                    ,con.getString(i+".verlassen")));
        }

        System.out.println(ChatColor.GREEN+"Siedlungen wurden geladen");

        return false;
    }
}
