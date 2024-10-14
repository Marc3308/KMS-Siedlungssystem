package me.marc3308.siedlungundberufe.commands.subcommands;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.commands.subcommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;
import static me.marc3308.siedlungundberufe.Siedlungundberufe.spielerliste;
import static me.marc3308.siedlungundberufe.utilitys.savesiedlungen;

public class createsiedlung extends subcommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "erstelle eine Siedlung";
    }

    @Override
    public String getSyntax() {
        return "/siedlung create <Besitzer> <loc1-x> <loc1-z> <loc2-x> <loc2-z>";
    }

    @Override
    public void perform(Player p, String[] args) {
        String error="Bitte versuche: Siedlung <Besitzer> <loc1-x> <loc1-z> <loc2-x> <loc2-z>";

        if(args.length<6){
            p.sendMessage(ChatColor.RED+error);
            return;
        }

        if(Bukkit.getPlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler Existiert nicht");
            return;
        }

        Player pp=Bukkit.getPlayer(args[1]);

        try {
            //try to create the siedlung
            String name= pp.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)+"´s grundstück";
            Location loc1=new Location(p.getWorld(),Integer.valueOf(args[2]),0,Integer.valueOf(args[3]));
            Location loc2=new Location(p.getWorld(),Integer.valueOf(args[4]),0,Integer.valueOf(args[5]));
            ArrayList<String> ownerliste =new ArrayList<>();
            ownerliste.add(pp.getUniqueId().toString());
            siedlungsliste.add(new siedlung(ownerliste,loc1,loc2,name,"",200,"DIAMOND",new ArrayList<String>(),0,"",""));
            pp.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER,(siedlungsliste.size()-1));
            savesiedlungen();

            //try to worldgard
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"dmarker addcorner "+args[2]+" 0 "+args[3]+" world");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"dmarker addcorner "+args[2]+" 0 "+args[5]+" world");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"dmarker addcorner "+args[4]+" 0 "+args[5]+" world");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"dmarker addcorner "+args[4]+" 0 "+args[3]+" world");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"dmarker addarea "+pp.getName());


            //inform the players
            p.sendMessage(ChatColor.GREEN+name+ChatColor.DARK_GREEN+" wurde erfolgreich erstellt");
            pp.sendMessage(ChatColor.GREEN+"Dir gehört nun dieser Bereich");

            //if already init
            for (spielerprovil sp : spielerliste){
                if(sp.getUuid().equals(pp.getUniqueId().toString())){
                    sp.setAbbau(true);
                    sp.setHinbau(true);
                    sp.setKisten(true);
                    sp.setGaste(true);
                    sp.setMitglied(true);
                    sp.setRules(true);
                    sp.setVoteckicks(new ArrayList<>());
                    pp.closeInventory();
                    return;
                }
            }

            spielerliste.add(new spielerprovil(pp.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)
                    ,pp.getUniqueId().toString(),true,true,true,true,true,true,new ArrayList<String>()));

        } catch (IllegalArgumentException e){
            p.sendMessage(ChatColor.RED+error);
        } catch (ArrayIndexOutOfBoundsException e){
            p.sendMessage(ChatColor.RED+error);
        }
    }
}
