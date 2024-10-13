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
                    Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler Verwalten");
                    p.openInventory(spielerinv);
                    break;
                case GREEN_CONCRETE:
                    Inventory infoinv=Bukkit.createInventory(p,27,s.getName()+" > Informationen");
                    p.openInventory(infoinv);
                    break;
                case GREEN_CONCRETE_POWDER:
                    Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Gäste Verwalten");
                    p.openInventory(gästeinv);
                    break;
                case ARROW: //zurück
                    Bukkit.getServer().dispatchCommand(p,"profil");
                    break;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Spieler Verwalten")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;

            switch (e.getCurrentItem().getType()){
                case GREEN_CONCRETE_POWDER:
                    Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Gäste Verwalten");
                    p.openInventory(gästeinv);
                    break;
                case WHITE_CONCRETE_POWDER:
                    Inventory mitinv=Bukkit.createInventory(p,54,s.getName()+" > Mitglieder Verwalten");
                    p.openInventory(mitinv);
                    break;
                case GRAY_CONCRETE_POWDER:
                    Inventory onerinv=Bukkit.createInventory(p,54,s.getName()+" > Anführer Verwalten");
                    p.openInventory(onerinv);
                    break;
                case ARROW:
                    p.openInventory(siedlung);
                    break;
            }
        }

        //gäste inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Gäste Verwalten")){
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
                }
            } else if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler Verwalten");
                p.openInventory(s.getStufe()==0 ? siedlung : spielerinv);
                return;
            }

            //reloaden
            Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Gäste Verwalten");
            p.openInventory(gästeinv);

        }

        //Mitglieder inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Mitglieder Verwalten")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                //getting the gast
                SkullMeta skull=(SkullMeta) e.getCurrentItem().getItemMeta();

                //in einer siedlung
                for (String ss : s.getMemberlist()){
                    if(ss.equals(Bukkit.getOfflinePlayer(skull.getOwner()).getUniqueId().toString())){

                        //get spielerprofil
                        spielerprovil sp=spielerliste.get(0);
                        for (spielerprovil sdp : spielerliste)if(sdp.getUuid().equals(ss))sp=sdp;

                        if(s.getOwner().size()<2){
                            List<String> memberlist=s.getMemberlist();
                            memberlist.remove(sp.getUuid());
                            s.setMemberlist(memberlist);
                            if(Bukkit.getPlayer(skull.getOwner())!=null)Bukkit.getPlayer(skull.getOwner()).getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
                        } else {
                            int kickvotes=sp.getVoteckicks();

                            if(p.getPersistentDataContainer().has(new NamespacedKey(plugin, "kick"+skull.getOwner()), PersistentDataType.BOOLEAN)){
                                kickvotes--;
                                p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "kick"+skull.getOwner()));
                            } else {
                                kickvotes++;
                                p.getPersistentDataContainer().set(new NamespacedKey(plugin, "kick"+skull.getOwner()), PersistentDataType.BOOLEAN,true);
                            }

                            if(s.getOwner().size()/2.0>kickvotes){
                                sp.setVoteckicks(0);
                                List<String> memberlist=s.getMemberlist();
                                memberlist.remove(sp.getUuid());
                                s.setMemberlist(memberlist);
                                if(Bukkit.getPlayer(skull.getOwner())!=null)Bukkit.getPlayer(skull.getOwner()).getPersistentDataContainer().remove(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"));
                            } else {
                                sp.setVoteckicks(kickvotes);
                            }
                        }

                        //reloaden
                        Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Mitglieder Verwalten");
                        p.openInventory(gästeinv);
                        return;
                    }
                }

                //keine siedlung aber online
                Player gast=Bukkit.getPlayer(skull.getOwner());

                //invite
                p.sendMessage(ChatColor.GREEN+gast.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)+ChatColor.DARK_GREEN+" wurde eingeladen");
                TextComponent loc2=new TextComponent(ChatColor.DARK_GREEN+"Du wurdest als Volksmitglied eingeladen: "+ChatColor.GREEN+s.getName()+ChatColor.YELLOW+"[Click um begutachten]");
                loc2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/einladung"));
                gast.sendMessage(loc2);
                gast.getPersistentDataContainer().set(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER, siedlungsliste.indexOf(s));
                Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> gast.getPersistentDataContainer().remove(new NamespacedKey(plugin, "einladung")), 20*60);


            } else if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler Verwalten");
                p.openInventory(s.getStufe()==0 ? siedlung : spielerinv);
                return;
            }

            //reloaden
            Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Mitglieder Verwalten");
            p.openInventory(gästeinv);

        }

        //Owner inventare
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Anführer Verwalten")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            //skull
            SkullMeta skull=(SkullMeta) e.getCurrentItem().getItemMeta();

            if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){

                //is a owner
                for(String ss : s.getOwner()){
                    if(ss.equals(Bukkit.getOfflinePlayer(skull.getOwner()).getUniqueId().toString())) {

                        //get spielerprofil
                        spielerprovil sp = spielerliste.get(0);
                        for (spielerprovil sdp : spielerliste) if (sdp.getUuid().equals(ss)) sp = sdp;

                        if (s.getOwner().size() > 2) {
                            int kickvotes = sp.getVoteckicks();

                            if (p.getPersistentDataContainer().has(new NamespacedKey(plugin, "kick" + skull.getOwner()), PersistentDataType.BOOLEAN)) {
                                kickvotes--;
                                p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "kick" + skull.getOwner()));
                            } else {
                                kickvotes++;
                                p.getPersistentDataContainer().set(new NamespacedKey(plugin, "kick" + skull.getOwner()), PersistentDataType.BOOLEAN, true);
                            }

                            if (s.getOwner().size() / 2.0 > kickvotes) {
                                sp.setVoteckicks(0);
                                //remove owner
                                List<String> ownerlist = s.getOwner();
                                ownerlist.remove(sp.getUuid());
                                s.setOwner(ownerlist);

                                //add to member
                                List<String> memberlist = s.getMemberlist();
                                memberlist.add(sp.getUuid());
                                s.setMemberlist(memberlist);
                            } else {
                                sp.setVoteckicks(kickvotes);
                            }
                        }
                        //reloaden
                        Inventory gästeinv = Bukkit.createInventory(p, 54, s.getName() + " > Anführer Verwalten");
                        p.openInventory(gästeinv);
                        return;
                    }
                }

                //will be owner
                spielerprovil sp = spielerliste.get(0);
                for (spielerprovil sdp : spielerliste) if (sdp.getUuid().equals(Bukkit.getOfflinePlayer(skull.getOwner()).getUniqueId().toString())) sp = sdp;

                List<String> ownerlist=s.getOwner();
                ownerlist.add(sp.getUuid());
                s.setOwner(ownerlist);

                List<String> memberlist=s.getMemberlist();
                memberlist.remove(sp.getUuid());
                s.setMemberlist(memberlist);

            } else if(e.getCurrentItem().getType().equals(Material.ARROW)){
                Inventory spielerinv=Bukkit.createInventory(p,27,s.getName()+" > Spieler Verwalten");
                p.openInventory(s.getStufe()==0 ? siedlung : spielerinv);
                return;
            }

            //reloaden
            Inventory gästeinv=Bukkit.createInventory(p,54,s.getName()+" > Anführer Verwalten");
            p.openInventory(gästeinv);
            return;
        }

        //Einladungs inv
        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+ " > Einladung")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case GREEN_CONCRETE:
                    p.sendMessage(ChatColor.GREEN+"Du hast die einladung angenommen");
                    p.getPersistentDataContainer().set(new NamespacedKey(Siedlungundberufe.getPlugin(), "siedlung"), PersistentDataType.INTEGER,p.getPersistentDataContainer().get(new NamespacedKey(plugin, "einladung"), PersistentDataType.INTEGER));
                    p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "einladung"));
                    //add member
                    List<String> ownerlist=s.getMemberlist();
                    ownerlist.add(p.getUniqueId().toString());
                    s.setMemberlist(ownerlist);
                    spielerliste.add(new spielerprovil(p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)
                            ,p.getUniqueId().toString(),false,false,false,false,false, 0));
                    p.closeInventory();
                    break;
                case RED_CONCRETE:
                    p.sendMessage(ChatColor.GREEN+"Du hast die einladung abgelent");
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
                                for (int i=p.getWorld().getMinHeight();i<p.getWorld().getMaxHeight();i++){
                                    loc.setY(i);
                                    p.spawnParticle(END_ROD,loc.clone().add(0.5,0,0.5),2,0,0,0,0.01);
                                }
                            }
                        }
                    }.runTaskTimer(Siedlungundberufe.getPlugin(),0,20);
                    break;
                case WHITE_CONCRETE_POWDER:
                    Inventory wildinv=Bukkit.createInventory(p,54,s.getName()+" > SpielerInvormationen");
                    p.openInventory(wildinv);
                    break;
                case ARROW:
                    p.openInventory(siedlung);
                    break;
            }

        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > SpielerInvormationen")){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case PLAYER_HEAD:
                    //Inventory infoinv=Bukkit.createInventory(p,54,Bukkit.getOfflinePlayer(e.getCurrentItem().getOw)+" > Einstellungen");
                    //p.openInventory(infoinv);
                    break;
                case ARROW:
                    Inventory infoinv=Bukkit.createInventory(p,27,s.getName()+" > Informationen");
                    p.openInventory(infoinv);
                    break;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase(s.getName()+" > Namensänderung")){
            System.out.println(e.getSlot());
            System.out.println(e.getCurrentItem());
        }

    }
}
