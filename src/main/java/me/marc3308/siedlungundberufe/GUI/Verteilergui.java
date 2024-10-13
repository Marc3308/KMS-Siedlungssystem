package me.marc3308.siedlungundberufe.GUI;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.*;
import static me.marc3308.siedlungundberufe.Siedlungundberufe.spielerliste;
import static me.marc3308.siedlungundberufe.utilitys.*;

public class Verteilergui implements Listener {

    public static ArrayList<String> beschreibung=new ArrayList<>();

    @EventHandler
    public void onverteilerinf(InventoryOpenEvent e){


        //get spieler und siedlung
        Player p= (Player) e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER) && !p.getPersistentDataContainer().has(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER))return;


        siedlung s =siedlungsliste.get(p.getPersistentDataContainer().has(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER)
                ? p.getPersistentDataContainer().get(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER)
                : p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER));

        //ja idk
        ArrayList<Integer> hinzulist=new ArrayList<Integer>();
        ArrayList<Integer> rauslist=new ArrayList<Integer>();
        hinzulist.add(10);
        hinzulist.add(11);
        hinzulist.add(12);
        hinzulist.add(19);
        hinzulist.add(20);
        hinzulist.add(21);
        hinzulist.add(28);
        hinzulist.add(29);
        hinzulist.add(30);
        hinzulist.add(39);
        hinzulist.add(40);
        hinzulist.add(41);

        rauslist.add(14);
        rauslist.add(15);
        rauslist.add(16);
        rauslist.add(23);
        rauslist.add(24);
        rauslist.add(25);
        rauslist.add(32);
        rauslist.add(33);
        rauslist.add(34);
        rauslist.add(41);
        rauslist.add(42);
        rauslist.add(43);


        //die warteshlange
        if(e.getView().getTitle().equalsIgnoreCase("Siedlungswarteschlange    ")){

            if(!s.getOwner().contains(p.getUniqueId().toString())){
                Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> Bukkit.getServer().dispatchCommand(p,"profil"), 1L);
                return;
            }

            switch (s.getStufe()){
                case 0:
                    //create inventory
                    Inventory siedlung= Bukkit.createInventory(p,27,s.getName());

                    //set items
                    siedlung.setItem(12,getItem("gäste"));
                    siedlung.setItem(14,getInvoBlock("infoblock",s));
                    siedlung.setItem(26,getItem("pfeil"));

                    //set items
                    Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> p.openInventory(siedlung), 1L);
                    break;
                case 1:
                    //create inventory
                    Inventory siedlungtier1= Bukkit.createInventory(p,27,s.getName());

                    //set items
                    siedlungtier1.setItem(10,getItem("einstellung"));
                    siedlungtier1.setItem(12,getItem("people"));
                    siedlungtier1.setItem(14,getItem("beruf"));
                    siedlungtier1.setItem(16,getInvoBlock("infoblock",s));
                    siedlungtier1.setItem(26,getItem("pfeil"));

                    //set items
                    Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> p.openInventory(siedlungtier1), 1L);
                    break;
                case 2:
                    break;
                default:
                    break;
            }


        }

        //spielergui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spieler Verwalten")){

            Inventory inv=e.getInventory();
            inv.setItem(11,getItem("owner"));
            inv.setItem(13,getItem("mitglieder"));
            inv.setItem(15,getItem("gäste"));
            inv.setItem(26,getItem("pfeil"));

        }

        //gäste gui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Gäste Verwalten")){

            Inventory inv=e.getInventory();

            //glass
            for (int i=0;i<9;i++)inv.setItem(i,getItem("glass")); //top
            for (int i=1;i<5;i++)inv.setItem(i*9,getItem("glass")); //left side
            for (int i=0;i<5;i++)inv.setItem(4+(i*9),getItem("glass")); //middel
            for (int i=0;i<5;i++)inv.setItem(8+(i*9),getItem("glass")); //right side
            for (int i=45;i<54;i++)inv.setItem(i,getItem("glass")); //bot


            //Hinzufügen noch net gäste
            for (Player gast : Bukkit.getOnlinePlayers()){
                if(!gast.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"), PersistentDataType.BOOLEAN)
                        && !s.getMemberlist().contains(gast.getUniqueId().toString())
                        && !s.getOwner().contains(gast.getUniqueId().toString())){
                    //create player skull
                    ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                    SkullMeta skull=(SkullMeta) head.getItemMeta();
                    skull.setDisplayName(gast.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                    skull.setOwner(gast.getName().toString());
                    head.setItemMeta(skull);
                    inv.setItem(hinzulist.get(0),head);
                    hinzulist.remove(0);
                    if(hinzulist.isEmpty())break;
                }
            }

            //Hinzufügen der gäste
            for (Player gast : Bukkit.getOnlinePlayers()){
                if(gast.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"), PersistentDataType.BOOLEAN)){
                    //create player skull
                    ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                    SkullMeta skull=(SkullMeta) head.getItemMeta();
                    skull.setDisplayName(gast.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                    skull.setOwner(gast.getName().toString());
                    head.setItemMeta(skull);
                    inv.setItem(rauslist.get(0),head);
                    rauslist.remove(0);
                    if(rauslist.isEmpty())break;
                }
            }


            //einzele sachen
            inv.setItem(2,getItem("hinzufügen"));
            inv.setItem(6,getItem("rauschmeisen"));
            inv.setItem(22,getItem("leftarrow"));
            inv.setItem(31,getItem("rightarrow"));
            inv.setItem(49,getItem("pfeil"));
        }

        //Mitglieder gui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Mitglieder Verwalten")){

            Inventory inv=e.getInventory();

            //glass
            for (int i=0;i<9;i++)inv.setItem(i,getItem("glass")); //top
            for (int i=1;i<5;i++)inv.setItem(i*9,getItem("glass")); //left side
            for (int i=0;i<5;i++)inv.setItem(4+(i*9),getItem("glass")); //middel
            for (int i=0;i<5;i++)inv.setItem(8+(i*9),getItem("glass")); //right side
            for (int i=45;i<54;i++)inv.setItem(i,getItem("glass")); //bot


            //Hinzufügen noch net mitglieder
            for (Player neuzugang : Bukkit.getOnlinePlayers()){ //keine owner
                if(!neuzugang.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER) && inasone(neuzugang.getLocation())==siedlungsliste.indexOf(s)){
                    //create player skull
                    ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                    SkullMeta skull=(SkullMeta) head.getItemMeta();
                    skull.setDisplayName(neuzugang.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                    skull.setOwner(neuzugang.getName().toString());
                    head.setItemMeta(skull);
                    inv.setItem(hinzulist.get(0),head);
                    hinzulist.remove(0);
                    if(hinzulist.isEmpty())break;
                }
            }

            //Hinzufügen der mitglieder
            for (String ss : s.getMemberlist()){

                //get spielerprofil
                spielerprovil sp=spielerliste.get(0);
                for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(ss).getName().toString());
                beschreibung.clear();
                if(sp.getVoteckicks()>0){
                    beschreibung.add(ChatColor.YELLOW+"VoteKicks: "+sp.getVoteckicks());
                    skull.addEnchant(Enchantment.MENDING,1,false);
                    skull.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                skull.setLore(beschreibung);
                head.setItemMeta(skull);
                inv.setItem(rauslist.get(0),head);
                rauslist.remove(0);
                if(rauslist.isEmpty())break;
            }


            //einzele sachen
            inv.setItem(2,getItem("hinzufügen"));
            inv.setItem(6,getItem("rauschmeisen"));
            inv.setItem(22,getItem("leftarrow"));
            inv.setItem(31,getItem("rightarrow"));
            inv.setItem(49,getItem("pfeil"));
        }

        //Owner gui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Anführer Verwalten")){

            Inventory inv=e.getInventory();

            //glass
            for (int i=0;i<9;i++)inv.setItem(i,getItem("glass")); //top
            for (int i=1;i<5;i++)inv.setItem(i*9,getItem("glass")); //left side
            for (int i=0;i<5;i++)inv.setItem(4+(i*9),getItem("glass")); //middel
            for (int i=0;i<5;i++)inv.setItem(8+(i*9),getItem("glass")); //right side
            for (int i=45;i<54;i++)inv.setItem(i,getItem("glass")); //bot


            //Mitglieder die owner werden können
            for (String ss : s.getMemberlist()){

                //get spielerprofil
                spielerprovil sp=spielerliste.get(0);
                for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(ss).getName().toString());
                beschreibung.clear();
                if(sp.getVoteckicks()>0){
                    beschreibung.add(ChatColor.YELLOW+"VoteKicks: "+sp.getVoteckicks());
                    skull.addEnchant(Enchantment.MENDING,1,false);
                    skull.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                skull.setLore(beschreibung);
                head.setItemMeta(skull);
                inv.setItem(rauslist.get(0),head);
                hinzulist.remove(0);
                if(hinzulist.isEmpty())break;
            }

            //Owner
            for (String ss : s.getOwner()){
                //get spielerprofil
                spielerprovil sp=spielerliste.get(0);
                for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(ss).getName().toString());
                beschreibung.clear();
                if(sp.getVoteckicks()>0){
                    beschreibung.add(ChatColor.YELLOW+"VoteKicks: "+sp.getVoteckicks());
                    skull.addEnchant(Enchantment.MENDING,1,false);
                    skull.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                skull.setLore(beschreibung);
                head.setItemMeta(skull);
                inv.setItem(rauslist.get(0),head);
                rauslist.remove(0);
                if(rauslist.isEmpty())break;
            }


            //einzele sachen
            inv.setItem(2,getItem("hinzufügen"));
            inv.setItem(6,getItem("rauschmeisen"));
            inv.setItem(22,getItem("leftarrow"));
            inv.setItem(31,getItem("rightarrow"));
            inv.setItem(49,getItem("pfeil"));
        }

        //Einladungs gui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+ " > Einladung")){

            Inventory inv=e.getInventory();

            inv.setItem(12,getItem("einladungplus"));
            inv.setItem(14,getItem("einladungminus"));
            inv.setItem(26,getItem("pfeil"));

        }

        //Invo gui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Informationen")){
            Inventory inv=e.getInventory();

            inv.setItem(11,getInvoBlock("eckblock",s));
            inv.setItem(13,getInvoBlock("spielerinvo",s));
            inv.setItem(15,getInvoBlock("restinvos",s));
            inv.setItem(26,getItem("pfeil"));

        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > SpielerInvormationen")){

            Inventory wildinv=e.getInventory();
            //Owner
            for (String ss : s.getOwner()){
                //create player skull
                spielerprovil sp=spielerliste.get(0);
                for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(ss).getName().toString());
                head.setItemMeta(skull);
                wildinv.setItem(wildinv.firstEmpty(),head);
            }

            //Mitglieder
            for (String ss : s.getMemberlist()){

                //create player skull
                spielerprovil sp=spielerliste.get(0);
                for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(ss).getName().toString());
                head.setItemMeta(skull);
                wildinv.setItem(wildinv.firstEmpty(),head);
            }

            wildinv.setItem(53,getItem("pfeil"));
        }
    }

    @EventHandler
    public void onwartschlange(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("Siedlungswarteschlange    "))e.setCancelled(true);
    }
}
