package me.marc3308.siedlungundberufe.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.marc3308.siedlungundberufe.utilitys.savesiedlungen;

public class savecommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player)return false;

        savesiedlungen();
        System.out.println(ChatColor.GREEN+"Siedlungen wurden gesichert");


        return true;
    }
}
