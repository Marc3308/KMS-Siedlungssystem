package me.marc3308.siedlungundberufe.commands.siedlungscommands.subcommands;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.commands.siedlungscommands.subcommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;
import static me.marc3308.siedlungundberufe.utilitys.savesiedlungen;

public class deletecommand extends subcommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletse a siedlung";
    }

    @Override
    public String getSyntax() {
        return "/siedlung delete <siedlung>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if (Bukkit.getOfflinePlayer(args[1]) == null) {
            p.sendMessage(ChatColor.RED + "Dieser Spieler Existiert nicht");
            return;
        }

        //get the siedlung
        siedlung s = siedlungsliste.get(0);
        for (siedlung ss : siedlungsliste)
            for (String sss : ss.getOwner())
                if (sss.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString())) s = ss;
        if (!s.getOwner().contains(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString())) {
            p.sendMessage(ChatColor.RED + "Diese Siedlung Existiert nicht");
            return;
        }

        //remove the members
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (s.getOwner().contains(online.getUniqueId().toString()))
                online.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
            if (s.getMemberlist().contains(online.getUniqueId().toString()))
                online.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
        }

        //lösche die siedlung
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "dmarker deletearea " + Bukkit.getOfflinePlayer(args[1]).getName());
        p.sendMessage(ChatColor.GREEN + "Die Siedlung " + ChatColor.DARK_RED + s.getName() + ChatColor.GREEN + " wurde erfolgreich gelöscht" + " Nr[" + siedlungsliste.indexOf(s) + "]");
        siedlungsliste.remove(siedlungsliste.indexOf(s));
        savesiedlungen();
    }
}
