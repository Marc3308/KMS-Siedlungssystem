package me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.*;

public class joinevent implements Listener {

    @EventHandler
    public void onjoin(PlayerJoinEvent e){
        Player p=e.getPlayer();


        p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "einladung"));
        p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "insone"));

        for (spielerprovil sp : spielerliste)if(sp.getUuid().equals(p.getUniqueId().toString()))sp.setName(p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));

        //removes gast status
        for (int i = 0; i < siedlungsliste.size(); i++)p.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), i+"gast"));

        //setze deine siedlung
        p.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
        for (siedlung s : siedlungsliste){
            for (String ms : s.getOwner()){
                if(p.getUniqueId().toString().equalsIgnoreCase(ms)){
                    p.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER, siedlungsliste.indexOf(s));
                    break;
                }
            }
            for (String ms : s.getMemberlist()){
                if(p.getUniqueId().toString().equalsIgnoreCase(ms)){
                    p.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER, siedlungsliste.indexOf(s));
                    break;
                }
            }
        }
    }
}
