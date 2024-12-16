package me.marc3308.siedlungundberufe;

import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.*;
import static org.bukkit.Bukkit.getServer;

public class utilitys {

    public static void savesiedlungen(){
        File file = new File("plugins/KMS Plugins/Siedlungundberufe","Siedlungen.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        //remove
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

        file = new File("plugins/KMS Plugins/Siedlungundberufe","Spielerprofile.yml");
        con= YamlConfiguration.loadConfiguration(file);

        //remove
        for (int i=0;i<100;i++)con.set(String.valueOf(i),null);

        //save
        for (spielerprovil s : spielerliste){
            con.set(spielerliste.indexOf(s)+".Name",s.getName());
            con.set(spielerliste.indexOf(s)+".uuid",s.getUuid());
            con.set(spielerliste.indexOf(s)+".abbau",s.isAbbau());
            con.set(spielerliste.indexOf(s)+".hinbau",s.isHinbau());
            con.set(spielerliste.indexOf(s)+".kisten",s.isKisten());
            con.set(spielerliste.indexOf(s)+".gaste",s.isGaste());
            con.set(spielerliste.indexOf(s)+".mitglied",s.isMitglied());
            con.set(spielerliste.indexOf(s)+".rules",s.isRules());
            con.set(spielerliste.indexOf(s)+".voteckicks",s.getVoteckicks());

        }


        //final save
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

    public static boolean darferdas(Player p,Location loc, String berechtigung){
        if(!p.getGameMode().equals(GameMode.SURVIVAL))return true; //ist als mod/admin unterwegs
        int sone=inasone(loc);
        if(sone<0)return true; //in keiner zone
        if(berechtigung.equals("kisten") && (p.getWorld().getBlockAt(loc).getState() instanceof Container || p.getWorld().getBlockAt(loc).getState() instanceof Chest) ){ //kisten

            String name=p.getWorld().getBlockAt(loc).getState() instanceof Container container ? container.getCustomName()
                    : ((Chest) ((DoubleChest) p.getWorld().getBlockAt(loc).getState()).getLeftSide()).getCustomName();
            switch (name){
                case "§aJeder":
                    return true;
                case "§eNur Mitglieder & Gäste":
                    if(p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), sone+"gast"))
                            && p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), inasone(p.getLocation())+berechtigung), PersistentDataType.BOOLEAN))return true; //gast einer zone
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER))return false; //hat keine zonenenangehörikeit
                    if(p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER).equals(sone))return getSpielerprovile(p.getUniqueId().toString()).isKisten(); //darf kistenöfnen
                    return false;
                case "§6Nur Mitglieder":
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER))return false; //hat keine zonenenangehörikeit
                    if(p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER).equals(sone))return getSpielerprovile(p.getUniqueId().toString()).isKisten(); //darf kistenöfnen
                    return false;
                case "§cNur Volksanführer":
                    if(siedlungsliste.get(sone).getOwner().contains(p.getUniqueId().toString()))return true;
                    return false;
                default:
                    if(siedlungsliste.get(sone).getOwner().contains(p.getUniqueId().toString()))return true;
                    if(p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING).equals(name))return true;
                    return false;
            }
        }

        //restliche berechtigungen
        if(p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), sone+"gast"))
                && p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), inasone(p.getLocation())+berechtigung), PersistentDataType.BOOLEAN))return true; //gast einer zone
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER))return false; //hat keine zonenenangehörikeit
        if(p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER).equals(sone)){ //mitglied der zone
            spielerprovil sp =getSpielerprovile(p.getUniqueId().toString());
            if(berechtigung.equals("abbaun")){ //darf abbaun
                if(siedlungsliste.get(sone).getOwner().contains(p.getUniqueId().toString())
                        && p.isSneaking()
                        && p.getInventory().getItemInMainHand().getType().isAir()
                        && p.getWorld().getBlockAt(loc).getState() instanceof Container container){ //check if openmenu
                    try {
                        switch (container.getCustomName()){
                            case "§aJeder":
                                container.setCustomName("§eNur Mitglieder & Gäste");
                                container.update();
                                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 1.0f); // Color and size
                                p.getWorld().spawnParticle(Particle.DUST, container.getLocation().add(0.5,0,0.5), 50, 0.5, 0.5, 0.5, dustOptions);

                                break;
                            case "§eNur Mitglieder & Gäste":
                                container.setCustomName("§6Nur Mitglieder");
                                container.update();
                                Particle.DustOptions dustOptions2 = new Particle.DustOptions(Color.ORANGE, 1.0f); // Color and size
                                p.getWorld().spawnParticle(Particle.DUST, container.getLocation().add(0.5,0,0.5), 50, 0.5, 0.5, 0.5, dustOptions2);
                                break;
                            case "§6Nur Mitglieder":
                                container.setCustomName("§cNur Volksanführer");
                                container.update();
                                Particle.DustOptions dustOptions3 = new Particle.DustOptions(Color.RED, 1.0f); // Color and size
                                p.getWorld().spawnParticle(Particle.DUST, container.getLocation().add(0.5,0,0.5), 50, 0.5, 0.5, 0.5, dustOptions3);
                                break;
                            case "§cNur Volksanführer":

                                Player pl =container.getLocation().getWorld().getNearbyEntities(container.getLocation(),5,5,5).stream()
                                        .filter(entity -> entity instanceof Player) // Filter for players
                                        .map(entity -> (Player) entity) // Cast to Player
                                        .min((p1, p2) -> Double.compare(p1.getLocation().distance(container.getLocation()), p2.getLocation().distance(container.getLocation())))
                                        .orElse(p);

                                container.setCustomName(pl.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                                container.update();
                                Particle.DustOptions dustOptions4 = new Particle.DustOptions(Color.GRAY, 1.0f); // Color and size
                                p.getWorld().spawnParticle(Particle.DUST, container.getLocation().add(0.5,0,0.5), 50, 0.5, 0.5, 0.5, dustOptions4);
                                break;
                            default:
                                container.setCustomName("§aJeder");
                                container.update();
                                Particle.DustOptions dustOptions6 = new Particle.DustOptions(Color.GREEN, 1.0f); // Color and size
                                p.getWorld().spawnParticle(Particle.DUST, container.getLocation().add(0.5,0,0.5), 50, 0.5, 0.5, 0.5, dustOptions6);
                                break;
                        }
                    } catch (NullPointerException e){
                        container.setCustomName("§eNur Mitglieder & Gäste");
                        container.update();
                    }
                    ArmorStand newowner=p.getWorld().spawn(container.getLocation().add(0.5,0,0.5),ArmorStand.class);
                    newowner.setVisible(false);
                    newowner.setCustomNameVisible(true);
                    newowner.setCustomName(container.getCustomName());
                    newowner.setGravity(false);
                    newowner.setSmall(true);
                    newowner.setInvulnerable(true);
                    Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> newowner.remove(), 20L);

                    if(p.getWorld().getBlockAt(loc).getState() instanceof Chest chest && chest.getInventory().getHolder() instanceof DoubleChest dp){
                        Chest lt= (Chest) dp.getLeftSide();
                        Chest rt= (Chest) dp.getRightSide();
                        lt.setCustomName(container.getCustomName());
                        rt.setCustomName(container.getCustomName());
                        lt.update();
                        rt.update();
                    }
                }
                return sp.isAbbau(); //darf abbaun
            }
            if(berechtigung.equals("hinbaun"))return sp.isHinbau(); //darf baun
        }
        return false; //einfach so
    }

    public static ItemStack getItem(String item){
        switch (item){
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
                beruf_meta.setDisplayName("Coming Soon");
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
                gäste_met.setDisplayName(ChatColor.GREEN+"Gäste verwalten");
                gäste.setItemMeta(gäste_met);
                return gäste;
            case "mitglieder":
                //mitglied
                ItemStack mitglieder=new ItemStack(Material.WHITE_CONCRETE_POWDER);
                ItemMeta mitglieder_met= mitglieder.getItemMeta();
                mitglieder_met.setDisplayName(ChatColor.WHITE+"Mitglieder verwalten");
                mitglieder.setItemMeta(mitglieder_met);
                return mitglieder;
            case "owner":
                //owner
                ItemStack owner=new ItemStack(Material.GRAY_CONCRETE_POWDER);
                ItemMeta owner_met= owner.getItemMeta();
                owner_met.setDisplayName(ChatColor.GRAY+"Anführer verwalten");
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

        ArrayList<String> beschreibung=new ArrayList<>();

        switch (item){
            case "einladungplus":
                ItemStack einladungplus=new ItemStack(Material.GREEN_CONCRETE);
                ItemMeta einladungplus_meta=einladungplus.getItemMeta();
                einladungplus_meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Annehmen");
                beschreibung.clear();
                beschreibung.add("Hiermit trittst du der Siedlung "+s.getName()+" bei.");
                einladungplus.setItemMeta(einladungplus_meta);
                return einladungplus;
            case "infoblock":
                //informationen
                ItemStack infoblock=new ItemStack(Material.GREEN_CONCRETE);
                ItemMeta infoblock_meta= infoblock.getItemMeta();
                infoblock_meta.setDisplayName(ChatColor.DARK_GREEN+"Informationen");
                beschreibung.clear();
                beschreibung.add(ChatColor.DARK_GREEN+"Name: "+ChatColor.GREEN+s.getName());
                beschreibung.add(ChatColor.DARK_GREEN+"Motto: "+ChatColor.GREEN+s.getBeschreibung());
                beschreibung.add(ChatColor.DARK_GREEN+"Stufe: "+ChatColor.GREEN+s.getStufe());
                beschreibung.add(ChatColor.DARK_GREEN+"Mitgliederanzahl: "+ChatColor.GREEN+(s.getMemberlist().size()+s.getOwner().size()));
                int x=s.getLoc1().getX()<s.getLoc2().getX() ? s.getLoc2().getBlockX()-s.getLoc1().getBlockX() : s.getLoc1().getBlockX()-s.getLoc2().getBlockX();
                int z=s.getLoc1().getZ()<s.getLoc2().getZ() ? s.getLoc2().getBlockZ()-s.getLoc1().getBlockZ() : s.getLoc1().getBlockZ()-s.getLoc2().getBlockZ();
                beschreibung.add(ChatColor.DARK_GREEN+"Gesamtgröße: "+ net.md_5.bungee.api.ChatColor.YELLOW+"["+x+"x"+z+"]");
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
                eckblock_beschreibung.add(ChatColor.GRAY+"Gesamtgröße: "+ ChatColor.YELLOW+"["+xx+"x"+zz+"]");
                eckblock_beschreibung.add(" ");
                eckblock_beschreibung.add(ChatColor.YELLOW+"Linksklick um Visualisierung umzuschalten");
                eckblock_meta.setLore(eckblock_beschreibung);
                eckblock.setItemMeta(eckblock_meta);
                return eckblock;
            case "spielerinvo":
                ItemStack spielerinvo=new ItemStack(Material.WHITE_CONCRETE_POWDER);
                ItemMeta spielerinvo_meta=spielerinvo.getItemMeta();
                spielerinvo_meta.setDisplayName(ChatColor.WHITE+""+ChatColor.BOLD+"Mitglieder");
                ArrayList<String> spielerinvo_beschreibung=new ArrayList<>();
                spielerinvo_beschreibung.add(ChatColor.GRAY+"Anführer: "+s.getOwner().size());
                spielerinvo_beschreibung.add(ChatColor.WHITE+"Mitglieder: "+s.getMemberlist().size());
                int i=0;
                for (Player gast : Bukkit.getOnlinePlayers())if(gast.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"), PersistentDataType.BOOLEAN))i++;
                spielerinvo_beschreibung.add(ChatColor.GREEN+"Gäste: "+i);
                spielerinvo_meta.setLore(spielerinvo_beschreibung);
                spielerinvo.setItemMeta(spielerinvo_meta);

                return spielerinvo;
            case "restinvos":
                ItemStack restinvos=new ItemStack(Material.GREEN_CONCRETE_POWDER);
                ItemMeta restinvos_meta= restinvos.getItemMeta();
                restinvos_meta.setDisplayName(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"Schriftliche Informationen");
                ArrayList<String> restinvos_beschreibung=new ArrayList<>();
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Name: "+ChatColor.GREEN+s.getName());
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Motto: "+ChatColor.GREEN+s.getBeschreibung());
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Willkommensnachricht: ");
                restinvos_beschreibung.add(ChatColor.GREEN+s.getWelckomemasage());
                restinvos_beschreibung.add(ChatColor.DARK_GREEN+"Verabschiedungsnachricht: ");
                restinvos_beschreibung.add(ChatColor.GREEN+s.getLeavemessage());
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

    public static ItemStack getRuleItem(String item){
        switch (item){
            case "Abbauen":
                ItemStack Abbauen=new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta Abbauen_meta=Abbauen.getItemMeta();
                Abbauen_meta.setDisplayName(ChatColor.BOLD+"Abbauen");
                Abbauen.setItemMeta(Abbauen_meta);
                return Abbauen;
            case "Platzieren":
                ItemStack Platzieren=new ItemStack(Material.DIRT);
                ItemMeta Platzieren_meta=Platzieren.getItemMeta();
                Platzieren_meta.setDisplayName(ChatColor.BOLD+"Platzieren");
                Platzieren.setItemMeta(Platzieren_meta);
                return Platzieren;
            case "Looten":
                ItemStack Looten=new ItemStack(Material.CHEST);
                ItemMeta Looten_meta=Looten.getItemMeta();
                Looten_meta.setDisplayName(ChatColor.BOLD+"Kistenzugriff");
                Looten.setItemMeta(Looten_meta);
                return Looten;
            case "Gaste":
                ItemStack Gaste=new ItemStack(Material.GRAY_CONCRETE);
                ItemMeta Gaste_meta=Gaste.getItemMeta();
                Gaste_meta.setDisplayName(ChatColor.BOLD+"Gäste bearbeiten");
                Gaste.setItemMeta(Gaste_meta);
                return Gaste;
            case "Mitglieder":
                ItemStack Mitglieder=new ItemStack(Material.WHITE_CONCRETE);
                ItemMeta Mitglieder_meta=Mitglieder.getItemMeta();
                Mitglieder_meta.setDisplayName(ChatColor.BOLD+"Mitglieder bearbeiten");
                Mitglieder.setItemMeta(Mitglieder_meta);
                return Mitglieder;
            case "Regeln":
                ItemStack Regeln=new ItemStack(Material.BLACK_CONCRETE);
                ItemMeta Regeln_meta=Regeln.getItemMeta();
                Regeln_meta.setDisplayName(ChatColor.BOLD+"Rechte bearbeiten");
                Regeln.setItemMeta(Regeln_meta);
                return Regeln;
            case "Verwehrt":
                ItemStack Verwehrt=new ItemStack(Material.RED_CONCRETE_POWDER);
                ItemMeta Verwehrt_meta=Verwehrt.getItemMeta();
                Verwehrt_meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Verwehrt");
                Verwehrt.setItemMeta(Verwehrt_meta);
                return Verwehrt;
            case "Erlaubt":
                ItemStack Erlaubt=new ItemStack(Material.GREEN_CONCRETE_POWDER);
                ItemMeta Erlaubt_meta=Erlaubt.getItemMeta();
                Erlaubt_meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Erlaubt");
                Erlaubt.setItemMeta(Erlaubt_meta);
                return Erlaubt;
        }
        ItemStack error=new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta error_meta= error.getItemMeta();
        error_meta.setDisplayName(ChatColor.DARK_GREEN+"ERROR with "+item);
        error.setItemMeta(error_meta);
        return  error;
    }

    public static spielerprovil getSpielerprovile(String UUidString){

        //get spielerprofil
        spielerprovil sp=spielerliste.get(0);
        for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(UUidString))sp=sdp;
        if(!sp.getUuid().equals(UUidString)){
            spielerliste.add(new spielerprovil("",UUidString,false,false,false,false,false,false,new ArrayList<>()));
            return new spielerprovil(Bukkit.getPlayer(UUID.fromString(UUidString))==null
                    ? "" : Bukkit.getPlayer(UUID.fromString(UUidString)).getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING) ,UUidString,false,false,false,false,false,false,new ArrayList<>());
        } else {
            return sp;
        }
    }

    public static Player isGast(String Uuid, int zone){
        if(Bukkit.getPlayer(Uuid)==null)return null;
        Player p=Bukkit.getPlayer(Uuid);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), zone+"gast"),PersistentDataType.BOOLEAN))return null;
        return p;
    }

}
