package me.marc3308.siedlungundberufe;

import me.marc3308.siedlungundberufe.GUI.Verteilergui;
import me.marc3308.siedlungundberufe.GUI.siedlungsgui;
import me.marc3308.siedlungundberufe.Siedlungsevents.siedlungeinstellung.spawningregulation;
import me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents.blockevents;
import me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents.joinevent;
import me.marc3308.siedlungundberufe.commands.Commandmanager;
import me.marc3308.siedlungundberufe.commands.einladungen;
import me.marc3308.siedlungundberufe.commands.loadsiedlung;
import me.marc3308.siedlungundberufe.commands.savecommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

import static me.marc3308.siedlungundberufe.utilitys.inasone;
import static me.marc3308.siedlungundberufe.utilitys.savesiedlungen;

public final class Siedlungundberufe extends JavaPlugin {

    public static ArrayList<siedlung> siedlungsliste=new ArrayList<>();

    public static Siedlungundberufe plugin;
    @Override
    public void onEnable() {

        plugin=this;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {


                //check if in a sone with cool new stream funktion
                for(Player p : Bukkit.getOnlinePlayers()){

                    //check if player is in a sone
                    int sone=inasone(p.getLocation());

                    //send massage
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin, "insone"), PersistentDataType.INTEGER) && sone>=0){
                        p.getPersistentDataContainer().set(new NamespacedKey(plugin, "insone"), PersistentDataType.INTEGER,sone);
                        p.sendTitle(siedlungsliste.get(sone).getWelckomemasage(),"");
                    } else if (p.getPersistentDataContainer().has(new NamespacedKey(plugin, "insone"), PersistentDataType.INTEGER) && sone<0) {
                        p.sendTitle(siedlungsliste.get(p.getPersistentDataContainer().get(new NamespacedKey(plugin, "insone"), PersistentDataType.INTEGER)).getLeavemessage(),"");
                        p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "insone"));
                    }
                }
            }
        },0,20*2);


        //make the list from the YML
        File file = new File("plugins/KMS Plugins/Siedlungundberufe","Siedlungen.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        //load siedlungen from yml
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

        //commands
        getCommand("savesiedlungen").setExecutor(new savecommand());
        getCommand("loadsiedlungyml").setExecutor(new loadsiedlung());
        getCommand("Siedlung").setExecutor(new Commandmanager());
        getCommand("einladung").setExecutor(new einladungen());

        //events
        Bukkit.getPluginManager().registerEvents(new Verteilergui(),this);
        Bukkit.getPluginManager().registerEvents(new siedlungsgui(),this);
        Bukkit.getPluginManager().registerEvents(new joinevent(),this);
        Bukkit.getPluginManager().registerEvents(new blockevents(),this);

        //siedluingseinstellungen
        Bukkit.getPluginManager().registerEvents(new spawningregulation(),this);

    }

    @Override
    public void onDisable() {
        savesiedlungen();
    }

    public static Siedlungundberufe getPlugin() {
        return plugin;
    }
}
