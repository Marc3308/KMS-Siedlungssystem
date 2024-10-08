package me.marc3308.siedlungundberufe;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.craftbukkit.v1_20_R1.persistence.CraftPersistentDataContainer;
import net.minecraft.server.v1_20_R1.NBTTagCompound;
import net.minecraft.server.v1_20_R1.MojangsonParser;
import net.minecraft.server.v1_20_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;
import static org.bukkit.Bukkit.getServer;

public class utilitys {

    public static void savesiedlungen(){
        File file = new File("plugins/KMS Plugins/Siedlungundberufe","Siedlungen.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        //remove?
        for (int i=0;i<100;i++)con.set(String.valueOf(i),null);

        for (siedlung s : siedlungsliste){
            con.set(siedlungsliste.indexOf(s)+".Owner",s.getOwner());
            con.set(siedlungsliste.indexOf(s)+".loc1",s.getLoc1());
            con.set(siedlungsliste.indexOf(s)+".loc2",s.getLoc2());
            con.set(siedlungsliste.indexOf(s)+".Name",s.getName());
            con.set(siedlungsliste.indexOf(s)+".Beschreibung",s.getBeschreibung());
            con.set(siedlungsliste.indexOf(s)+".Custemmoddeldata",s.getCustemmoddeldata());
            con.set(siedlungsliste.indexOf(s)+".Block",s.getBlock());
            con.set(siedlungsliste.indexOf(s)+".memberlist",s.getMemberlist());
            con.set(siedlungsliste.indexOf(s)+".Stufe",s.getStufe());
            con.set(siedlungsliste.indexOf(s)+".wilkommen",s.getWelckomemasage());
            con.set(siedlungsliste.indexOf(s)+".verlassen",s.getLeavemessage());

        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static int inasone(Location loc){

        for(siedlung s : siedlungsliste){
            double minx = s.getLoc1().getX()<s.getLoc2().getX() ? s.getLoc1().getX() : s.getLoc2().getX();
            double minz = s.getLoc1().getZ()<s.getLoc2().getZ() ? s.getLoc1().getZ() : s.getLoc2().getZ();
            double maxx = s.getLoc1().getX()>s.getLoc2().getX() ? s.getLoc1().getX() : s.getLoc2().getX();
            double maxz = s.getLoc1().getZ()>s.getLoc2().getZ() ? s.getLoc1().getZ() : s.getLoc2().getZ();

            if(minx<=loc.getX() && maxx>=loc.getX()
                    && minz<=loc.getZ() && maxz>=loc.getZ())return siedlungsliste.indexOf(s);

        }
        return -1;
    }

    public static boolean darferdas(Player p,Location loc){
        if(!p.getGameMode().equals(GameMode.SURVIVAL))return true; //ist als mod/admin unterwegs
        int sone=inasone(loc);
        if(sone<0)return true; //in keiner zone
        if(p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), sone+"gast")))return true; //gast einer zone
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER))return false; //hat keine zonenenangehörikeit
        if(p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER).equals(sone))return true; // mittglied der zone
        return false; //einfach so
    }

    public static ItemStack getItem(String item){
        switch (item){
            case "einladungplus":
                ItemStack einladungplus=new ItemStack(Material.GREEN_CONCRETE);
                ItemMeta einladungplus_meta=einladungplus.getItemMeta();
                einladungplus_meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Annehmen");
                einladungplus.setItemMeta(einladungplus_meta);
                return einladungplus;
            case "einladungminus":
                ItemStack einladungminus=new ItemStack(Material.RED_CONCRETE);
                ItemMeta einladungminus_meta=einladungminus.getItemMeta();
                einladungminus_meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Ablehnen");
                einladungminus.setItemMeta(einladungminus_meta);
                return einladungminus;
            case "rightarrow":

                String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM1ZTQyZmM3MDYwYzIyM2FjYzk2NWY3YzU5OTZmMjcyNjQ0YWY0MGE0NzIzYTM3MmY1OTAzZjhlOWYxODhlNyJ9fX0=";
                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

                // Create a PlayerProfile with a random UUID and apply the base64 texture
                PlayerProfile profile = getServer().createProfile(UUID.randomUUID(), "CustomHead");
                profile.getProperties().add(new ProfileProperty("textures", base64));

                // Set the profile to the skull meta
                skullMeta.setPlayerProfile(profile);
                skullMeta.setDisplayName(" ");
                head.setItemMeta(skullMeta);

                return head;
            case "leftarrow":

                String base642 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWVlMGY4MmZiMzNmNmNmYTUxNjliOWY1ZWFmZTRkYzFjNzM2MThjOTc4M2IxMzFhZGFkYTQxMWQ4ZjYwNTUwNSJ9fX0=";
                ItemStack head2 = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skullMeta2 = (SkullMeta) head2.getItemMeta();

                // Create a PlayerProfile with a random UUID and apply the base64 texture
                PlayerProfile profile2 = getServer().createProfile(UUID.randomUUID(), "CustomHead");
                profile2.getProperties().add(new ProfileProperty("textures", base642));

                // Set the profile to the skull meta
                skullMeta2.setPlayerProfile(profile2);
                skullMeta2.setDisplayName(" ");
                head2.setItemMeta(skullMeta2);

                return head2;
            case "hinzufügen":
                ItemStack hinzufügen=new ItemStack(Material.GREEN_WOOL);
                ItemMeta hinzufügen_meta=hinzufügen.getItemMeta();
                hinzufügen_meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Hinzufügen");
                hinzufügen.setItemMeta(hinzufügen_meta);
                return hinzufügen;
            case "rauschmeisen":
                ItemStack rauschmeisen=new ItemStack(Material.RED_WOOL);
                ItemMeta rauschmeisen_meta=rauschmeisen.getItemMeta();
                rauschmeisen_meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Rauswefen");
                rauschmeisen.setItemMeta(rauschmeisen_meta);
                return rauschmeisen;
            case "glass":
                ItemStack glass=new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                ItemMeta glass_meta=glass.getItemMeta();
                glass_meta.setDisplayName(" ");
                glass.setItemMeta(glass_meta);
                return glass;
            case "people":
                //Menschen
                ItemStack people=new ItemStack(Material.WHITE_CONCRETE);
                ItemMeta people_meta= people.getItemMeta();
                people_meta.setDisplayName(ChatColor.WHITE+"Spieler Verwalten");
                people.setItemMeta(people_meta);
                return people;
            case "beruf":
                //berufe
                ItemStack beruf = new ItemStack(Material.BARRIER);
                ItemMeta beruf_meta=beruf.getItemMeta();
                beruf_meta.setDisplayName("Comming Soon");
                beruf.setItemMeta(beruf_meta);
                return beruf;
            case "einstellung":
                //Einstellungen
                ItemStack einstellung=new ItemStack(Material.GRAY_CONCRETE);
                ItemMeta einstellung_meta= einstellung.getItemMeta();
                einstellung_meta.setDisplayName(ChatColor.GRAY+"Einstellungen");
                einstellung.setItemMeta(einstellung_meta);
                return einstellung;
            case "pfeil":
                //backpfeil
                ItemStack pfeil=new ItemStack(Material.ARROW);
                ItemMeta pfeil_met= pfeil.getItemMeta();
                pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
                pfeil.setItemMeta(pfeil_met);
                return pfeil;
            case "gäste":
                //bgäste
                ItemStack gäste=new ItemStack(Material.GREEN_CONCRETE_POWDER);
                ItemMeta gäste_met= gäste.getItemMeta();
                gäste_met.setDisplayName(ChatColor.GREEN+"Gäste Verwalten");
                gäste.setItemMeta(gäste_met);
                return gäste;
            case "mitglieder":
                //mitglied
                ItemStack mitglieder=new ItemStack(Material.WHITE_CONCRETE_POWDER);
                ItemMeta mitglieder_met= mitglieder.getItemMeta();
                mitglieder_met.setDisplayName(ChatColor.WHITE+"Mitglieder Verwalten");
                mitglieder.setItemMeta(mitglieder_met);
                return mitglieder;
            case "owner":
                //owner
                ItemStack owner=new ItemStack(Material.GRAY_CONCRETE_POWDER);
                ItemMeta owner_met= owner.getItemMeta();
                owner_met.setDisplayName(ChatColor.GRAY+"Anführer Verwalten");
                owner.setItemMeta(owner_met);
                return owner;
        }
        ItemStack error=new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta error_meta= error.getItemMeta();
        error_meta.setDisplayName(ChatColor.DARK_GREEN+"ERROR with "+item);
        error.setItemMeta(error_meta);
        return  error;
    }

    public static ItemStack getInvoBlock(String item,siedlung s){

        switch (item){
            case "infoblock":
                //informationen
                ItemStack infoblock=new ItemStack(Material.GREEN_CONCRETE);
                ItemMeta infoblock_meta= infoblock.getItemMeta();
                infoblock_meta.setDisplayName(ChatColor.DARK_GREEN+"Informationen");
                ArrayList<String> beschreibung=new ArrayList<>();
                beschreibung.add(ChatColor.DARK_GREEN+"Name: "+ChatColor.GREEN+s.getName());
                beschreibung.add(ChatColor.DARK_GREEN+"Motto: "+ChatColor.GREEN+s.getBeschreibung());
                beschreibung.add(ChatColor.DARK_GREEN+"Stufe: "+ChatColor.GREEN+s.getStufe());
                beschreibung.add(ChatColor.DARK_GREEN+"Beigetreten: "+ChatColor.GREEN+(s.getMemberlist().size()+s.getOwner().size()));
                int x=s.getLoc1().getX()<s.getLoc2().getX() ? s.getLoc2().getBlockX()-s.getLoc1().getBlockX() : s.getLoc1().getBlockX()-s.getLoc2().getBlockX();
                int z=s.getLoc1().getZ()<s.getLoc2().getZ() ? s.getLoc2().getBlockZ()-s.getLoc1().getBlockZ() : s.getLoc1().getBlockZ()-s.getLoc2().getBlockZ();
                beschreibung.add(ChatColor.DARK_GREEN+"Gesamt Größe: "+ net.md_5.bungee.api.ChatColor.YELLOW+"["+x+"x"+z+"]");
                beschreibung.add(" ");
                beschreibung.add(ChatColor.YELLOW+"Linksklick für mehr Informationen");
                infoblock_meta.setLore(beschreibung);
                infoblock.setItemMeta(infoblock_meta);
                return infoblock;
            case "eckblock":
                ItemStack eckblock=new ItemStack(Material.GRAY_CONCRETE_POWDER);
                ItemMeta eckblock_meta= eckblock.getItemMeta();
                eckblock_meta.setDisplayName(ChatColor.GRAY+""+ChatColor.BOLD+"Eckpunkt Informationen");
                ArrayList<String> eckblock_beschreibung=new ArrayList<>();
                eckblock_beschreibung.add(ChatColor.GRAY+"Eckpunkt 1: "+ChatColor.YELLOW+"["+s.getLoc1().getBlockX()+"x "+s.getLoc1().getBlockZ()+"z ]");
                eckblock_beschreibung.add(ChatColor.GRAY+"Eckpunkt 2: "+ChatColor.YELLOW+"["+s.getLoc1().getBlockX()+"x "+s.getLoc2().getBlockZ()+"z ]");
                eckblock_beschreibung.add(ChatColor.GRAY+"Eckpunkt 3: "+ChatColor.YELLOW+"["+s.getLoc2().getBlockX()+"x "+s.getLoc1().getBlockZ()+"z ]");
                eckblock_beschreibung.add(ChatColor.GRAY+"Eckpunkt 4: "+ChatColor.YELLOW+"["+s.getLoc2().getBlockX()+"x "+s.getLoc2().getBlockZ()+"z ]");
                int xx=s.getLoc1().getX()<s.getLoc2().getX() ? s.getLoc2().getBlockX()-s.getLoc1().getBlockX() : s.getLoc1().getBlockX()-s.getLoc2().getBlockX();
                int zz=s.getLoc1().getZ()<s.getLoc2().getZ() ? s.getLoc2().getBlockZ()-s.getLoc1().getBlockZ() : s.getLoc1().getBlockZ()-s.getLoc2().getBlockZ();
                eckblock_beschreibung.add(ChatColor.GRAY+"Gesamt Größe: "+ ChatColor.YELLOW+"["+xx+"x"+zz+"]");
                eckblock_beschreibung.add(" ");
                eckblock_beschreibung.add(ChatColor.YELLOW+"Linksklick um Visualisation zu Toggeln");
                eckblock_meta.setLore(eckblock_beschreibung);
                eckblock.setItemMeta(eckblock_meta);
                return eckblock;
            case "spielerinvo":
                ItemStack spielerinvo=new ItemStack(Material.WHITE_CONCRETE_POWDER);
                ItemMeta spielerinvo_meta=spielerinvo.getItemMeta();
                spielerinvo_meta.setDisplayName(ChatColor.WHITE+""+ChatColor.BOLD+"Mitglieder");
                ArrayList<String> spielerinvo_beschreibung=new ArrayList<>();
                spielerinvo_beschreibung.add(ChatColor.WHITE+"Anführer:");
                for (String ss : s.getOwner())spielerinvo_beschreibung.add(ChatColor.WHITE+(Bukkit.getOfflinePlayer(UUID.fromString(ss))).getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                if(!s.getMemberlist().isEmpty()){
                    spielerinvo_beschreibung.add(ChatColor.WHITE+"Mitglieder:");
                    for (String ss : s.getMemberlist())spielerinvo_beschreibung.add(ChatColor.WHITE+((Player) Bukkit.getOfflinePlayer(UUID.fromString(ss))).getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                }
                spielerinvo_meta.setLore(spielerinvo_beschreibung);
                spielerinvo.setItemMeta(spielerinvo_meta);

                return spielerinvo;
            case "restinvos":
                ItemStack restinvos=new ItemStack(Material.GREEN_CONCRETE_POWDER);
                ItemMeta restinvos_meta= restinvos.getItemMeta();
                restinvos_meta.setDisplayName(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"Schrifltiche Invormationen");
                ArrayList<String> restinvos_beschreibung=new ArrayList<>();
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Name: "+ChatColor.GREEN+s.getName());
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Motto: "+ChatColor.GREEN+s.getBeschreibung());
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Willkommens Nachricht: "+ChatColor.GREEN+s.getWelckomemasage());
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Verlassen Nachricht: "+ChatColor.GREEN+s.getLeavemessage());
                restinvos_meta.setLore(restinvos_beschreibung);
                restinvos.setItemMeta(restinvos_meta);
                return restinvos;
        }

        ItemStack error=new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta error_meta= error.getItemMeta();
        error_meta.setDisplayName(ChatColor.DARK_GREEN+"ERROR with "+item);
        error.setItemMeta(error_meta);
        return  error;
    }

}
