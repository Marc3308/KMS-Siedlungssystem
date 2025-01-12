package me.marc3308.siedlungundberufe.Siedlungsevents.spielerevents;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.kapputblockliste;
import static me.marc3308.siedlungundberufe.utilitys.*;

public class blockevents implements Listener {

    private static ArrayList<Material> blockliste=new ArrayList<>();
    private static ArrayList<Material> spizhackenlist=new ArrayList<>();
    private static ArrayList<Material> flowerpods=new ArrayList<>();


    private static String error=ChatColor.RED+""+ChatColor.BOLD+"WARNUNG"+ChatColor.RESET+""+ChatColor.GRAY+" Du hast hier keine Berechtigung für diese Aktion.";

    public blockevents() {
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
        blockliste.add(Material.BLACK_BED);
        blockliste.add(Material.BLUE_BED);
        blockliste.add(Material.BROWN_BED);
        blockliste.add(Material.GREEN_BED);
        blockliste.add(Material.CYAN_BED);
        blockliste.add(Material.GRAY_BED);
        blockliste.add(Material.LIGHT_BLUE_BED);
        blockliste.add(Material.LIGHT_GRAY_BED);
        blockliste.add(Material.LIME_BED);
        blockliste.add(Material.MAGENTA_BED);
        blockliste.add(Material.ORANGE_BED);
        blockliste.add(Material.PINK_BED);
        blockliste.add(Material.PURPLE_BED);
        blockliste.add(Material.RED_BED);
        blockliste.add(Material.WHITE_BED);
        blockliste.add(Material.YELLOW_BED);


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

        flowerpods.add(Material.CAVE_VINES);
        flowerpods.add(Material.TWISTING_VINES_PLANT);
        flowerpods.add(Material.WEEPING_VINES_PLANT);
        flowerpods.add(Material.FLOWER_POT);
        flowerpods.add(Material.POTTED_MANGROVE_PROPAGULE);
        flowerpods.add(Material.POTTED_WITHER_ROSE);
        flowerpods.add(Material.POTTED_ALLIUM);
        flowerpods.add(Material.POTTED_ACACIA_SAPLING);
        flowerpods.add(Material.POTTED_BAMBOO);
        flowerpods.add(Material.POTTED_AZALEA_BUSH);
        flowerpods.add(Material.POTTED_AZURE_BLUET);
        flowerpods.add(Material.POTTED_BIRCH_SAPLING);
        flowerpods.add(Material.POTTED_BLUE_ORCHID);
        flowerpods.add(Material.POTTED_BROWN_MUSHROOM);
        flowerpods.add(Material.POTTED_CACTUS);
        flowerpods.add(Material.POTTED_CORNFLOWER);
        flowerpods.add(Material.POTTED_CHERRY_SAPLING);
        flowerpods.add(Material.POTTED_CRIMSON_FUNGUS);
        flowerpods.add(Material.POTTED_CRIMSON_ROOTS);
        flowerpods.add(Material.POTTED_DANDELION);
        flowerpods.add(Material.POTTED_DARK_OAK_SAPLING);
        flowerpods.add(Material.POTTED_DEAD_BUSH);
        flowerpods.add(Material.POTTED_FERN);
        flowerpods.add(Material.POTTED_FLOWERING_AZALEA_BUSH);
        flowerpods.add(Material.POTTED_JUNGLE_SAPLING);
        flowerpods.add(Material.POTTED_LILY_OF_THE_VALLEY);
        flowerpods.add(Material.POTTED_OAK_SAPLING);
        flowerpods.add(Material.POTTED_ORANGE_TULIP);
        flowerpods.add(Material.POTTED_OXEYE_DAISY);
        flowerpods.add(Material.POTTED_PINK_TULIP);
        flowerpods.add(Material.POTTED_SPRUCE_SAPLING);
        flowerpods.add(Material.POTTED_TORCHFLOWER);
        flowerpods.add(Material.POTTED_WARPED_FUNGUS);
        flowerpods.add(Material.POTTED_WARPED_ROOTS);
        flowerpods.add(Material.POTTED_WHITE_TULIP);
        flowerpods.add(Material.POTTED_POPPY);
        flowerpods.add(Material.POTTED_RED_MUSHROOM);
        flowerpods.add(Material.POTTED_RED_TULIP);
    }


    @EventHandler
    public void onbreak(BlockBreakEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getBlock().getLocation(), "abbaun")){
            if(e.getBlock().getState() instanceof Container container){
                if (container.getCustomName()!=null){
                    container.setCustomName(null);
                    container.update();
                }
            }
            return;
        }
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
        if(!(e.getInventory().getHolder() instanceof Container || e.getInventory().getHolder() instanceof DoubleChest))return;
        if(darferdas(p,e.getInventory().getLocation(), "kisten"))return; //container.getBlock().getLocation()
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.sendMessage(error);
    }

    @EventHandler
    public void oninvopen(InventoryOpenEvent e){
        Player p = (Player) e.getPlayer();
        if(e.getInventory().getLocation()==null)return;
        if(inasone(e.getInventory().getLocation())<0)return; //check if in a sone
        if(e.getInventory().getHolder() instanceof Container container && container.getCustomName()==null){
            container.setCustomName("§eNur Mitglieder & Gäste");
            container.update();
            p.updateInventory();
        }
        if(e.getInventory().getHolder() instanceof DoubleChest container && ((Chest) container.getLeftSide()).getCustomName()==null){
            Chest lt= (Chest) container.getLeftSide();
            Chest rt= (Chest) container.getRightSide();
            lt.setCustomName("§eNur Mitglieder & Gäste");
            rt.setCustomName("§eNur Mitglieder & Gäste");
            lt.update();
            rt.update();
            p.updateInventory();
        }
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

    @EventHandler
    public void onrustung(PlayerInteractAtEntityEvent e){
        Player p=e.getPlayer();
        if(darferdas(p,e.getRightClicked().getLocation(), "abbaun"))return;
        if(!(e.getRightClicked() instanceof ArmorStand) && !(e.getRightClicked() instanceof ItemFrame) && !(e.getRightClicked() instanceof GlowItemFrame))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(p.getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(error);
    }

    @EventHandler
    public void onitem(EntityDeathEvent e){
        if(!(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof GlowItemFrame || e.getEntity() instanceof Painting))return;
        if(e.getEntity().getKiller()==null)return;
        Player p=e.getEntity().getKiller();
        if(darferdas(p,e.getEntity().getLocation(), "abbaun"))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(p.getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(error);
    }

    @EventHandler
    public void onblock(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(e.getClickedBlock()==null)return;
        if(darferdas(p,e.getClickedBlock().getLocation(), "abbaun"))return;
        if(!flowerpods.contains(e.getClickedBlock().getType()))return;
        e.setCancelled(true);

        //warnung an den spieler
        p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
        p.playEffect(p.getLocation(), Effect.EXTINGUISH,20);
        p.sendMessage(error);
    }

    @EventHandler
    public void onschlag(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof ArmorStand || e.getEntity() instanceof ItemFrame || e.getEntity() instanceof GlowItemFrame))return;
        if(e.getDamager() instanceof Player){
            Player p= (Player) e.getDamager();
            if(darferdas(p,e.getEntity().getLocation(), "abbaun"))return;

            e.setCancelled(true);
            //warnung an den spieler
            p.playSound(p, Sound.BLOCK_LAVA_EXTINGUISH,1,1);
            p.playEffect(p.getLocation(), Effect.EXTINGUISH,20);
            p.sendMessage(error);
        } else if(e.getDamager() instanceof Projectile){
            e.setCancelled(true);
            return;
        }
    }
}
