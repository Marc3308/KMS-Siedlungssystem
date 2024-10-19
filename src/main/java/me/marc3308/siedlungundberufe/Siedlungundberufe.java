package me.marc3308.siedlungundberufe;

import me.marc3308.siedlungundberufe.GUI.Verteilergui;
import me.marc3308.siedlungundberufe.GUI.siedlungsgui;
import me.marc3308.siedlungundberufe.Siedlungsevents.siedlungeinstellung.spawningregulation;
import me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents.blockevents;
import me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents.joinevent;
import me.marc3308.siedlungundberufe.commands.eventcomamnds.CommandManager;
import me.marc3308.siedlungundberufe.commands.siedlungscommands.Commandmanager;
import me.marc3308.siedlungundberufe.commands.einladungen;
import me.marc3308.siedlungundberufe.commands.loadsiedlung;
import me.marc3308.siedlungundberufe.commands.savecommand;
import me.marc3308.siedlungundberufe.objektorientierung.eventzone;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static me.marc3308.siedlungundberufe.utilitys.*;

public final class Siedlungundberufe extends JavaPlugin {

    public static ArrayList<siedlung> siedlungsliste=new ArrayList<>();
    public static ArrayList<eventzone> eventzoneliste=new ArrayList<>();

    public static ArrayList<spielerprovil> spielerliste=new ArrayList<>();
    public static HashMap<Location, BlockData> kapputblockliste=new HashMap<>();

    public static Siedlungundberufe plugin;
    @Override
    public void onEnable() {

        plugin=this;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {


                //check if in a sone with cool new stream funktion
                for(Player p : Bukkit.getOnlinePlayers()){

                    if(ineventsone(p.getLocation())!=-1){
                        eventzone ev=eventzoneliste.get(ineventsone(p.getLocation()));
                        if(ev.isTp()){
                            p.setFallDistance(0);
                            p.teleport(ev.getTpLocation());
                            p.damage(ev.getSchaden());
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,20*3,1,false,false));
                        } else {
                            p.setPlayerTime(ev.getTime(),false);
                        }
                    } else if(!siedlungsliste.isEmpty()){
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
                    } else {
                        p.resetPlayerTime();
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

        File file2 = new File("plugins/KMS Plugins/Siedlungundberufe","Spielerprofile.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file2);

        //load spieler from yml
        for (int i = 0; i < 300; i++) {
            if(con2.get(i+".Name")==null)break;
            spielerliste.add(new spielerprovil(
                    con2.getString(i+".Name")
                    ,con2.getString(i+".uuid")
                    ,con2.getBoolean(i+".abbau")
                    ,con2.getBoolean(i+".hinbau")
                    ,con2.getBoolean(i+".kisten")
                    ,con2.getBoolean(i+".gaste")
                    ,con2.getBoolean(i+".rules")
                    ,con2.getBoolean(i+".mitglied")
                    ,con2.getStringList(i+".voteckicks")));
        }

        File file3 = new File("plugins/KMS Plugins/Siedlungundberufe","Eventzonen.yml");
        FileConfiguration con3= YamlConfiguration.loadConfiguration(file2);

        //load eventzonen
        for (int i = 0; i < 300; i++) {
            if(con3.get(i+".Name")==null)break;
            eventzoneliste.add(new eventzone(
                    con2.getString(i+".Name")
                    ,con2.getLocation(i+".loc1")
                    ,con2.getLocation(i+".loc2")
                    ,con2.getInt(i+".Time")
                    ,con2.getBoolean(i+".Tp")
                    ,con2.getLocation(i+".TpLocation")
                    ,con2.getDouble(i+".Schaden")));
        }


        //commands
        getCommand("savesiedlungen").setExecutor(new savecommand());
        getCommand("loadsiedlungyml").setExecutor(new loadsiedlung());
        getCommand("Siedlung").setExecutor(new Commandmanager());
        getCommand("einladung").setExecutor(new einladungen());
        getCommand("event").setExecutor(new CommandManager());

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
        for (Location loc : kapputblockliste.keySet())Bukkit.getWorld("world").setBlockData(loc,kapputblockliste.get(loc));
    }

    public static Siedlungundberufe getPlugin() {
        return plugin;
    }
}
