package me.marc3308.siedlungundberufe.Siedlungsevents.siedlungeinstellung;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import static me.marc3308.siedlungundberufe.utilitys.inasone;

public class spawningregulation implements Listener {

    @EventHandler
    public void onspawn(EntitySpawnEvent e){
        if(inasone(e.getLocation())<0)return;
        if(e.getEntity() instanceof Monster)e.setCancelled(true);
    }
}
