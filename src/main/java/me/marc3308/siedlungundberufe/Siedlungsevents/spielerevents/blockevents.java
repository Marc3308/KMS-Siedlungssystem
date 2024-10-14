package me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.kapputblockliste;
import static me.marc3308.siedlungundberufe.utilitys.*;

public class blockevents implements Listener {

    private static ArrayList<Material> blockliste=new ArrayList<>();
    private static ArrayList<Material> spizhackenlist=new ArrayList<>();

    private static String error=ChatColor.RED+""+ChatColor.BOLD+"WARNUNG"+ChatColor.RESET+""+ChatColor.GRAY+" Du hast hier keine Berichtung für diese Aktion.";

    public blockevents(){
        blockliste.add(Material.CHEST);
        blockliste.add(Material.CHEST_MINECART);
        blockliste.add(Material.ENDER_CHEST);
        blockliste.add(Material.TRAPPED_CHEST);
        blockliste.add(Material.ANVIL);
        blockliste.add(Material.CHIPPED_ANVIL);
        blockliste.add(Material.DAMAGED_ANVIL);
        blockliste.add(Material.ENCHANTING_TABLE);
        blockliste.add(Material.BARREL);
        blockliste.add(Material.FURNACE);
        blockliste.add(Material.FURNACE_MINECART);
        blockliste.add(Material.BLAST_FURNACE);
        blockliste.add(Material.SMOKER);
        blockliste.add(Material.PLAYER_WALL_HEAD);
        blockliste.add(Material.PLAYER_HEAD);


        spizhackenlist.add(Material.WOODEN_PICKAXE);
        spizhackenlist.add(Material.STONE_PICKAXE);
        spizhackenlist.add(Material.IRON_PICKAXE);
        spizhackenlist.add(Material.DIAMOND_PICKAXE);

        spizhackenlist.add(Material.WOODEN_SHOVEL);
        spizhackenlist.add(Material.STONE_SHOVEL);
        spizhackenlist.add(Material.IRON_SHOVEL);
        spizhackenlist.add(Material.DIAMOND_SHOVEL);

        spizhackenlist.add(Material.WOODEN_AXE);
        spizhackenlist.add(Material.STONE_AXE);
        spizhackenlist.add(Material.IRON_AXE);
        spizhackenlist.add(Material.DIAMOND_AXE);

        spizhackenlist.add(Material.WOODEN_HOE);
        spizhackenlist.add(Material.STONE_HOE);
        spizhackenlist.add(Material.IRON_HOE);
        spizhackenlist.add(Material.DIAMOND_HOE);
    }

    @EventHandler
    public void onbreak(BlockBreakEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation(), "abbaun"))return;
        if(!spizhackenlist.contains(p.getInventory().getItemInMainHand().getType()) || blockliste.contains(e.getBlock().getType())){
            e.setCancelled(true);
            //warnung an den spieler
            p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
            p.playEffect(e.getBlock().getLocation().add(0,1,0), Effect.EXTINGUISH,20);
            p.sendMessage(error);
            return;
        }

        kapputblockliste.put(e.getBlock().getLocation(), e.getBlock().getBlockData());
        e.setDropItems(false);
        Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> p.getWorld().setBlockData(e.getBlock().getLocation(),kapputblockliste.get(e.getBlock().getLocation())), 20*30);
        Bukkit.getScheduler().runTaskLater(Siedlungundberufe.getPlugin(), () -> kapputblockliste.remove(e.getBlock().getLocation()), 20*30);


    }

    @EventHandler
    public void onblockplace(BlockPlaceEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation(), "hinbaun")){
            if(kapputblockliste.containsKey(e.getBlock().getLocation())){
                e.setCancelled(true);
                p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
                p.playEffect(e.getBlock().getLocation().add(0,1,0), Effect.EXTINGUISH,20);
                p.sendMessage(ChatColor.GRAY+" Das kannst du derzeit leider nicht tun");
            }
            return;
        }
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(e.getBlock().getLocation().add(0,1,0), Effect.EXTINGUISH,20);
        p.sendMessage(error);
    }

    @EventHandler
    public void onknopf(InventoryClickEvent e){
        Player p= (Player) e.getWhoClicked();
        if(darferdas(p,p.getLocation(), "kisten"))return;
        if(e.getInventory().getType().equals(InventoryType.CHEST) && !e.getView().getTitle().equals("Chest") && !e.getView().getTitle().equals("Large Chest"))return; //if it is  acustem inv
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.sendMessage(error);
    }

    @EventHandler
    public void oneimer(PlayerBucketEmptyEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation(), "abbaun"))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(e.getBlock().getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(error);
    }
}