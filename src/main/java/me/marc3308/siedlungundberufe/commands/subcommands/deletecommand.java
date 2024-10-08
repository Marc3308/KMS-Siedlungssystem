package me.marc3308.siedlungundberufe.commands.subcommands;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.commands.subcommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

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

        //löschen falschen durch falschen namen
        for (siedlung s : siedlungsliste) {
            for (String ss : s.getOwner()) {
                if (ss.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString())) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "dmarker deletearea " + Bukkit.getOfflinePlayer(args[1]).getName());
                    p.sendMessage(ChatColor.GREEN + "Die Siedlung " + ChatColor.DARK_RED + s.getName() + ChatColor.GREEN + " wurde erfolgreich gelöscht" + " Nr[" + siedlungsliste.indexOf(s) + "]");

                    for (Player pp : Bukkit.getOnlinePlayers()){
                        //setze deine siedlung
                        pp.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
                        for (siedlung sss : siedlungsliste){
                            for (String ms : sss.getOwner()){
                                if(pp.getUniqueId().toString().equalsIgnoreCase(ms)){
                                    pp.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER, siedlungsliste.indexOf(sss));
                                    break;
                                }
                            }
                            for (String ms : sss.getMemberlist()){
                                if(pp.getUniqueId().toString().equalsIgnoreCase(ms)){
                                    pp.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER, siedlungsliste.indexOf(sss));
                                    break;
                                }
                            }
                        }
                    }


                    siedlungsliste.remove(s);
                    savesiedlungen();
                    return;
                }
            }
        }
    }
}
