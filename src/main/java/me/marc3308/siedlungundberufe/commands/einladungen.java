package me.marc3308.siedlungundberufe.commands;

import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.plugin;
import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;

public class einladungen implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //creating the player
        if(!(sender instanceof Player))return false;
        Player p= (Player) sender;

        //get the siedlung
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER)){
            p.sendMessage(ChatColor.RED+"Deine Einladung ist leider abgelaufen");
            return false;
        }
        siedlung s=siedlungsliste.get(p.getPersistentDataContainer().get(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER));
        Inventory einladungsinv= Bukkit.createInventory(p,27,s.getName()+ " > Einladung");
        p.openInventory(einladungsinv);

        return false;
    }
}
