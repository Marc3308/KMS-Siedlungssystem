package me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

import static me.marc3308.siedlungundberufe.utilitys.darferdas;

public class blockevents implements Listener {

    public static ArrayList<Material> blockliste=new ArrayList<>();

    public blockevents(){
        blockliste.add(Material.CHEST);
        blockliste.add(Material.CHEST_MINECART);
        blockliste.add(Material.ENDER_CHEST);
        blockliste.add(Material.TRAPPED_CHEST);
        blockliste.add(Material.ANVIL);
        blockliste.add(Material.CHIPPED_ANVIL);
        blockliste.add(Material.DAMAGED_ANVIL);
        blockliste.add(Material.ENCHANTING_TABLE);
    }

    @EventHandler
    public void onbreak(BlockBreakEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation()))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(e.getBlock().getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"WARNUNG"+ChatColor.RESET+""+ChatColor.GRAY+" du bist weder ein mitglied noch ein gast in diesem gebiet");

    }

    @EventHandler
    public void onblockplace(BlockPlaceEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation()))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(e.getBlock().getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"WARNUNG"+ChatColor.RESET+""+ChatColor.GRAY+" du bist weder ein mitglied noch ein gast in diesem gebiet");
    }

    @EventHandler
    public void onknopf(InventoryOpenEvent e){
        Player p= (Player) e.getPlayer();
        if(darferdas(p,p.getLocation()))return;
        if(e.getInventory().getType().equals(InventoryType.CHEST) && !e.getView().getTitle().equals("Chest") && !e.getView().getTitle().equals("Large Chest"))return; //if it is  acustem inv
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"WARNUNG"+ChatColor.RESET+""+ChatColor.GRAY+" du bist weder ein mitglied noch ein gast in diesem gebiet");
    }

    @EventHandler
    public void oneimer(PlayerBucketEmptyEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation()))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(e.getBlock().getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"WARNUNG"+ChatColor.RESET+""+ChatColor.GRAY+" du bist weder ein mitglied noch ein gast in diesem gebiet");
    }
}
