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
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spieler verwalten")){

            Inventory inv=e.getInventory();
            inv.setItem(11,getItem("owner"));
            inv.setItem(13,getItem("mitglieder"));
            inv.setItem(15,getItem("gäste"));
            inv.setItem(26,getItem("pfeil"));

        }

        //gäste gui
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Gäste verwalten")){

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
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Mitglieder verwalten")){

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
                spielerprovil sp =getSpielerprovile(ss);

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName().toString());
                beschreibung.clear();
                if(!sp.getVoteckicks().isEmpty()){
                    beschreibung.add(ChatColor.YELLOW+"VoteKicks: "+sp.getVoteckicks().size());
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
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Anführer verwalten")){

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
                spielerprovil sp =getSpielerprovile(ss);

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName().toString());
                beschreibung.clear();
                if(!sp.getVoteckicks().isEmpty()){
                    beschreibung.add(ChatColor.YELLOW+"Votes: "+sp.getVoteckicks().size());
                    skull.addEnchant(Enchantment.MENDING,1,false);
                    skull.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                skull.setLore(beschreibung);
                head.setItemMeta(skull);
                inv.setItem(hinzulist.get(0),head);
                hinzulist.remove(0);
                if(hinzulist.isEmpty())break;
            }

            //Owner
            for (String ss : s.getOwner()){

                //get spielerprofil
                spielerprovil sp =getSpielerprovile(ss);

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName().toString());
                beschreibung.clear();
                if(!sp.getVoteckicks().isEmpty()){
                    beschreibung.add(ChatColor.YELLOW+"VoteKicks: "+sp.getVoteckicks().size());
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

            inv.setItem(12,getInvoBlock("einladungplus",siedlungsliste.get(p.getPersistentDataContainer().get(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER))));
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

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spielerinformationen")){

            Inventory wildinv=e.getInventory();
            //Owner
            for (String ss : s.getOwner()){
                //create player skull
                spielerprovil sp =getSpielerprovile(ss);

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(ChatColor.GRAY+sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName().toString());
                beschreibung.clear();
                beschreibung.add(ChatColor.GRAY+"Anführer");
                skull.setLore(beschreibung);
                head.setItemMeta(skull);
                wildinv.setItem(wildinv.firstEmpty(),head);
            }

            //Mitglieder
            for (String ss : s.getMemberlist()){

                //create player skull
                spielerprovil sp =getSpielerprovile(ss);

                //create player skull
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();
                skull.setDisplayName(ChatColor.WHITE+sp.getName());
                skull.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName().toString());
                beschreibung.clear();
                beschreibung.add(ChatColor.WHITE+"Mitglied");
                skull.setLore(beschreibung);
                head.setItemMeta(skull);
                wildinv.setItem(wildinv.firstEmpty(),head);
            }

            //Hinzufügen der gäste
            for (Player gast : Bukkit.getOnlinePlayers()){
                if(gast.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"), PersistentDataType.BOOLEAN)){
                    //create player skull
                    ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                    SkullMeta skull=(SkullMeta) head.getItemMeta();
                    skull.setDisplayName(ChatColor.GREEN+gast.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                    skull.setOwner(gast.getName().toString());
                    beschreibung.clear();
                    beschreibung.add(ChatColor.GREEN+"Gast");
                    skull.setLore(beschreibung);
                    head.setItemMeta(skull);
                    wildinv.setItem(wildinv.firstEmpty(),head);
                    rauslist.remove(0);
                }
            }

            wildinv.setItem(53,getItem("pfeil"));
        }


        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spielerinformationen > Einstellungen")){
            Inventory inv = e.getInventory();
            spielerprovil sp = getSpielerprovile(Bukkit.getOfflinePlayer(((SkullMeta) inv.getItem(0).getItemMeta()).getOwner()).getUniqueId().toString());

            inv.setItem(11,getRuleItem("Abbauen"));
            inv.setItem(12,sp.isAbbau() ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(20,getRuleItem("Platzieren"));
            inv.setItem(21,sp.isHinbau() ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(29,getRuleItem("Looten"));
            inv.setItem(30,sp.isKisten() ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(14,getRuleItem("Gaste"));
            inv.setItem(15,sp.isGaste() ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(23,getRuleItem("Mitglieder"));
            inv.setItem(24,sp.isMitglied() ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(32,getRuleItem("Regeln"));
            inv.setItem(33,sp.isRules() ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(44,getItem("pfeil"));
        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Gäste > Einstellungen")){
            Inventory inv = e.getInventory();
            Player gast=isGast(((SkullMeta) e.getInventory().getItem(0).getItemMeta()).getOwner(),siedlungsliste.indexOf(s));

            inv.setItem(2,getRuleItem("Abbauen"));
            inv.setItem(11,gast.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"abbaun"), PersistentDataType.BOOLEAN) ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(4,getRuleItem("Platzieren"));
            inv.setItem(13,gast.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"hinbaun"), PersistentDataType.BOOLEAN) ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(6,getRuleItem("Looten"));
            inv.setItem(15,gast.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"kisten"), PersistentDataType.BOOLEAN) ? getRuleItem("Erlaubt") : getRuleItem("Verwehrt"));

            inv.setItem(26,getItem("pfeil"));
        }

    }

    @EventHandler
    public void onwartschlange(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("Siedlungswarteschlange    "))e.setCancelled(true);
    }
}
