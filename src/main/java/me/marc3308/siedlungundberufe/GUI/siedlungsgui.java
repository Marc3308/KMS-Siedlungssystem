package me.marc3308.siedlungundberufe.GUI;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.*;
import static me.marc3308.siedlungundberufe.Siedlungundberufe.spielerliste;
import static me.marc3308.siedlungundberufe.utilitys.getSpielerprovile;
import static me.marc3308.siedlungundberufe.utilitys.isGast;
import static org.bukkit.Particle.END_ROD;

public class siedlungsgui implements Listener {

    @EventHandler
    public void ondruck(InventoryClickEvent e){

        Player p= (Player) e.getWhoClicked();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER) && !p.getPersistentDataContainer().has(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER))return;
        siedlung s =siedlungsliste.get(p.getPersistentDataContainer().has(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER)
                ? p.getPersistentDataContainer().get(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER)
                : p.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER));
        Inventory siedlung= Bukkit.createInventory(p,27,"Siedlungswarteschlange    ");

        //siedlungsinventory
        if(e.getView().getTitle().equalsIgnoreCase(s.getName())){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case WHITE_CONCRETE: //spieler gui
                    Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler verwalten");
                    p.openInventory(spielerinv);
                    break;
                case GREEN_CONCRETE:
                    Inventory infoinv=Bukkit.createInventory(p,27,s.getName()+" > Informationen");
                    p.openInventory(infoinv);
                    break;
                case GREEN_CONCRETE_POWDER:
                    if(!getSpielerprovile(p.getUniqueId().toString()).isGaste())return;
                    Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Gäste Verwalten");
                    p.openInventory(gästeinv);
                    break;
                case ARROW: //zurück
                    Bukkit.getServer().dispatchCommand(p,"profil");
                    break;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spieler verwalten")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;

            switch (e.getCurrentItem().getType()){
                case GREEN_CONCRETE_POWDER:
                    if(!getSpielerprovile(p.getUniqueId().toString()).isGaste())return;
                    Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Gäste verwalten");
                    p.openInventory(gästeinv);
                    break;
                case WHITE_CONCRETE_POWDER:
                    if(!getSpielerprovile(p.getUniqueId().toString()).isMitglied())return;
                    Inventory mitinv=Bukkit.createInventory(p,54,s.getName()+" > Mitglieder verwalten");
                    p.openInventory(mitinv);
                    break;
                case GRAY_CONCRETE_POWDER:
                    if(!s.getOwner().contains(p.getUniqueId().toString()))return;
                    Inventory onerinv=Bukkit.createInventory(p,54,s.getName()+" > Anführer verwalten");
                    p.openInventory(onerinv);
                    break;
                case ARROW:
                    p.openInventory(siedlung);
                    break;
            }
        }

        //gäste inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Gäste verwalten")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                //getting the gast
                SkullMeta skull=(SkullMeta) e.getCurrentItem().getItemMeta();
                Player gast = Bukkit.getPlayer(skull.getOwner());
                if(gast==null)return;

                //eddit the gast rolle
                if(gast.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"), PersistentDataType.BOOLEAN)){
                    gast.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"));
                } else {
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"), PersistentDataType.BOOLEAN,true);
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"abbaun"), PersistentDataType.BOOLEAN,true);
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"hinbaun"), PersistentDataType.BOOLEAN,true);
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"kisten"), PersistentDataType.BOOLEAN,false);
                }
            } else if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler verwalten");
                p.openInventory(s.getStufe()==0 ? siedlung : spielerinv);
                return;
            }

            //reloaden
            Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Gäste verwalten");
            p.openInventory(gästeinv);

        }

        //Mitglieder inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Mitglieder verwalten")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                //getting the gast
                SkullMeta skull=(SkullMeta) e.getCurrentItem().getItemMeta();

                //in einer siedlung
                for (String ss : s.getMemberlist()){
                    if(ss.equals(Bukkit.getOfflinePlayer(skull.getOwner()).getUniqueId().toString())){

                        //only owner
                        if(!s.getOwner().contains(p.getUniqueId().toString()))return;
                        if(s.getStufe()==1 && s.getMemberlist().size()<4)return; //so that you dont kick wehn s1 and 4min

                        //get spielerprofil
                        spielerprovil sp=spielerliste.get(0);
                        for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                        if(s.getOwner().size()<1){
                            List<String> memberlist=s.getMemberlist();
                            memberlist.remove(sp.getUuid());
                            s.setMemberlist(memberlist);
                            if(Bukkit.getPlayer(skull.getOwner())!=null)Bukkit.getPlayer(skull.getOwner()).getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
                        } else {
                            List<String> kickvotes=sp.getVoteckicks();
                            if(kickvotes.contains(p.getUniqueId().toString())){
                                kickvotes.remove(p.getUniqueId().toString());
                            } else {
                                kickvotes.add(p.getUniqueId().toString());
                            }

                            if(s.getOwner().size()/2.0<kickvotes.size()){
                                sp.setVoteckicks(new ArrayList<String>());
                                List<String> memberlist=s.getMemberlist();
                                memberlist.remove(sp.getUuid());
                                s.setMemberlist(memberlist);
                                if(Bukkit.getPlayer(skull.getOwner())!=null)Bukkit.getPlayer(skull.getOwner()).getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
                            } else {
                                sp.setVoteckicks(kickvotes);
                            }
                        }

                        //reloaden
                        Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Mitglieder verwalten");
                        p.openInventory(gästeinv);
                        return;
                    }
                }

                //keine siedlung aber online
                Player gast=Bukkit.getPlayer(skull.getOwner());

                //invite
                p.sendMessage(ChatColor.GREEN+gast.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)+ChatColor.DARK_GREEN+" wurde eingeladen");
                TextComponent loc2=new TextComponent(ChatColor.DARK_GREEN+"Du hast eine Siedlungseinladung erhalten "+ChatColor.YELLOW+"[Linksklick zum Anschauen]");
                loc2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/einladung"));
                gast.sendMessage(loc2);
                gast.getPersistentDataContainer().set(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER, siedlungsliste.indexOf(s));
                Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> gast.getPersistentDataContainer().remove(new NamespacedKey(plugin, "einladung")), 20*60);


            } else if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler verwalten");
                p.openInventory(s.getStufe()==0 ? siedlung : spielerinv);
                return;
            }

            //reloaden
            Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Mitglieder verwalten");
            p.openInventory(gästeinv);

        }

        //Owner inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Anführer verwalten")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){

                //skull
                SkullMeta skull=(SkullMeta) e.getCurrentItem().getItemMeta();

                //is a owner
                for(String ss : s.getOwner()){
                    if(ss.equals(Bukkit.getOfflinePlayer(skull.getOwner()).getUniqueId().toString())) {

                        //get spielerprofil
                        spielerprovil sp = getSpielerprovile(ss);

                        if (s.getOwner().size() > 1) {
                            List<String> kickvotes=sp.getVoteckicks();

                            if(kickvotes.contains(p.getUniqueId().toString())){
                                kickvotes.remove(p.getUniqueId().toString());
                            } else {
                                kickvotes.add(p.getUniqueId().toString());
                            }

                            if(s.getOwner().size()/2.0<kickvotes.size()) {
                                sp.setVoteckicks(new ArrayList<String>());
                                //remove owner
                                List<String> ownerlist = s.getOwner();
                                ownerlist.remove(sp.getUuid());
                                s.setOwner(ownerlist);

                                sp.setMitglied(false);
                                sp.setRules(false);
                                sp.setGaste(false);

                                //add to member
                                List<String> memberlist = s.getMemberlist();
                                memberlist.add(sp.getUuid());
                                s.setMemberlist(memberlist);
                            } else {
                                sp.setVoteckicks(kickvotes);
                            }
                        }
                        //reloaden
                        Inventory gästeinv = Bukkit.createInventory(p, 54, s.getName() + " > Anführer verwalten");
                        p.openInventory(gästeinv);
                        return;
                    }
                }

                //will be owner
                spielerprovil sp = getSpielerprovile(Bukkit.getOfflinePlayer(skull.getOwner()).getUniqueId().toString());

                if (s.getOwner().size() > 1) {
                    List<String> kickvotes=sp.getVoteckicks();

                    if(kickvotes.contains(p.getUniqueId().toString())){
                        kickvotes.remove(p.getUniqueId().toString());
                    } else {
                        kickvotes.add(p.getUniqueId().toString());
                    }

                    if(s.getOwner().size()/2.0<kickvotes.size()) {

                        sp.setVoteckicks(new ArrayList<String>());
                        //remove owner
                        List<String> ownerlist=s.getOwner();
                        ownerlist.add(sp.getUuid());
                        s.setOwner(ownerlist);

                        sp.setAbbau(true);
                        sp.setHinbau(true);
                        sp.setKisten(true);
                        sp.setGaste(true);
                        sp.setRules(true);
                        sp.setMitglied(true);

                        List<String> memberlist=s.getMemberlist();
                        memberlist.remove(sp.getUuid());
                        s.setMemberlist(memberlist);
                    } else {

                        sp.setVoteckicks(kickvotes);

                    }
                } else {
                    List<String> ownerlist=s.getOwner();
                    ownerlist.add(sp.getUuid());
                    s.setOwner(ownerlist);

                    sp.setGaste(true);
                    sp.setRules(true);
                    sp.setMitglied(true);

                    List<String> memberlist=s.getMemberlist();
                    memberlist.remove(sp.getUuid());
                    s.setMemberlist(memberlist);
                }

            } else if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler verwalten");
                p.openInventory(s.getStufe()==0 ? siedlung : spielerinv);
                return;
            }

            //reloaden
            Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Anführer verwalten");
            p.openInventory(gästeinv);
            return;
        }

        //Einladungs inv
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+ " > Einladung")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case GREEN_CONCRETE:
                    p.sendMessage(ChatColor.GREEN+"Du hast die Einladung angenommen");
                    p.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER,p.getPersistentDataContainer().get(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER));
                    p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "einladung"));
                    p.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"gast"));

                    //add member
                    List<String> ownerlist=s.getMemberlist();
                    ownerlist.add(p.getUniqueId().toString());
                    s.setMemberlist(ownerlist);

                    //if already init
                    for (spielerprovil sp : spielerliste){
                        if(sp.getUuid().equals(p.getUniqueId().toString())){
                            sp.setAbbau(true);
                            sp.setHinbau(true);
                            sp.setKisten(true);
                            sp.setGaste(false);
                            sp.setRules(false);
                            sp.setMitglied(false);
                            sp.setVoteckicks(new ArrayList<String>());
                            p.closeInventory();
                            return;
                        }
                    }

                    spielerliste.add(new spielerprovil(p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)
                            ,p.getUniqueId().toString(),true,true,true,false,false, false,new ArrayList<String>()));
                    p.closeInventory();
                    break;
                case RED_CONCRETE:
                    p.sendMessage(ChatColor.GREEN+"Du hast die Einladung abgelent");
                    p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "einladung"));
                    p.closeInventory();
                    break;
                case ARROW:
                    p.closeInventory();
                    break;
            }
        }

        //Informationen inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Informationen")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case GRAY_CONCRETE_POWDER:
                    if(p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "infobeam"), PersistentDataType.BOOLEAN)){
                        p.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "infobeam"));
                        return;
                    } else {
                        p.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "infobeam"), PersistentDataType.BOOLEAN,true);
                    }
                    Random r=new Random();

                    int minx = s.getLoc1().getBlockX()<s.getLoc2().getBlockX() ? s.getLoc1().getBlockX() : s.getLoc2().getBlockX();
                    int maxx = s.getLoc1().getBlockX()>s.getLoc2().getBlockX() ? s.getLoc1().getBlockX() : s.getLoc2().getBlockX();
                    int minz = s.getLoc1().getBlockZ()<s.getLoc2().getBlockZ() ? s.getLoc1().getBlockZ() : s.getLoc2().getBlockZ();
                    int maxz = s.getLoc1().getBlockZ()>s.getLoc2().getBlockZ() ? s.getLoc1().getBlockZ() : s.getLoc2().getBlockZ();

                    ArrayList<Location> loclist=new ArrayList<>();
                    for(int i=minx;i<maxx;i+=3)loclist.add(new Location(p.getWorld(),i,0,maxz));
                    for(int i=minx;i<maxx;i+=3)loclist.add(new Location(p.getWorld(),i,0,minz));
                    for(int i=minz;i<maxz;i+=3)loclist.add(new Location(p.getWorld(),maxx,0,i));
                    for(int i=minz;i<maxz;i+=3)loclist.add(new Location(p.getWorld(),minx,0,i));

                    new BukkitRunnable(){
                        @Override
                        public void run() {

                            if(!p.isOnline() || !p.getPersistentDataContainer().has(new NamespacedKey(Siedlungundberufe.getPlugin(), "infobeam"), PersistentDataType.BOOLEAN)){
                                p.getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "infobeam"));
                                cancel();
                                return;
                            }

                            for (Location loc : loclist){
                                for (int i=p.getWorld().getMinHeight();i<250;i++){
                                    loc.setY(i);
                                    p.spawnParticle(END_ROD,loc.clone().add(0.5,0,0.5),2,0,0,0,0.01);
                                }
                            }
                        }
                    }.runTaskTimer(Siedlungundberufe.getPlugin(),0,20);
                    break;
                case WHITE_CONCRETE_POWDER:
                    Inventory wildinv=Bukkit.createInventory(p,54,s.getName()+" > Spielerinformationen");
                    p.openInventory(wildinv);
                    break;
                case ARROW:
                    p.openInventory(siedlung);
                    break;
            }

        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spielerinformationen")){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case PLAYER_HEAD:
                    if(!getSpielerprovile(p.getUniqueId().toString()).isRules())return;

                    boolean gastinv=isGast(((SkullMeta) e.getCurrentItem().getItemMeta()).getOwner(),siedlungsliste.indexOf(s))!=null;
                    Inventory infoeinstellung=Bukkit.createInventory(p,gastinv ? 27 : 45, gastinv ? s.getName()+" > Gäste > Einstellungen" : s.getName()+" > Spielerinformationen > Einstellungen");
                    infoeinstellung.setItem(0,e.getCurrentItem());
                    p.openInventory(infoeinstellung);
                    break;
                case ARROW:
                    Inventory infoinv=Bukkit.createInventory(p,27,s.getName()+" > Informationen");
                    p.openInventory(infoinv);
                    break;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spielerinformationen > Einstellungen")){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory wildinv=Bukkit.createInventory(p,54,s.getName()+" > Spielerinformationen");
                p.openInventory(wildinv);
                return;
            }

            spielerprovil sp = getSpielerprovile(Bukkit.getOfflinePlayer(((SkullMeta) e.getInventory().getItem(0).getItemMeta()).getOwner()).getUniqueId().toString());

            //cant chanche a owner
            if(s.getOwner().contains(sp.getUuid()))return;

            switch (e.getSlot()){
                case 12:
                    sp.setAbbau(!sp.isAbbau());
                    break;
                case 21:
                    sp.setHinbau(!sp.isHinbau());
                    break;
                case 30:
                    sp.setKisten(!sp.isKisten());
                    break;
                case 15:
                    sp.setGaste(!sp.isGaste());
                    break;
                case 24:
                    sp.setMitglied(!sp.isMitglied());
                    break;
                case 33:
                    sp.setRules(!sp.isRules());
                    break;
            }

            Inventory infoeinstellung=Bukkit.createInventory(p,45,s.getName()+" > Spielerinformationen > Einstellungen");
            infoeinstellung.setItem(0,e.getInventory().getItem(0));
            p.openInventory(infoeinstellung);

        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Gäste > Einstellungen")){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory wildinv=Bukkit.createInventory(p,54,s.getName()+" > Spielerinformationen");
                p.openInventory(wildinv);
                return;
            }

            Player gast=isGast(((SkullMeta) e.getInventory().getItem(0).getItemMeta()).getOwner(),siedlungsliste.indexOf(s));


            switch (e.getSlot()){
                case 11:
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"abbaun"), PersistentDataType.BOOLEAN,
                            !gast.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"abbaun"), PersistentDataType.BOOLEAN));
                    break;
                case 13:
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"hinbaun"), PersistentDataType.BOOLEAN,
                            !gast.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"hinbaun"), PersistentDataType.BOOLEAN));
                    break;
                case 15:
                    gast.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"kisten"), PersistentDataType.BOOLEAN,
                            !gast.getPersistentDataContainer().get(new NamespacedKey(Siedlungundberufe.getPlugin(), siedlungsliste.indexOf(s)+"kisten"), PersistentDataType.BOOLEAN));
                    break;
            }

            Inventory infoeinstellung=Bukkit.createInventory(p,27,s.getName()+" > Gäste > Einstellungen");
            infoeinstellung.setItem(0,e.getInventory().getItem(0));
            p.openInventory(infoeinstellung);

        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Namensänderung")){
            System.out.println(e.getSlot());
            System.out.println(e.getCurrentItem());
        }

    }
}
