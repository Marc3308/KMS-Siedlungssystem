package me.marc3308.siedlungundberufe.commands.subcommands;

import me.marc3308.siedlungundberufe.commands.subcommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;

public class infocommand extends subcommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "gives infomaton";
    }

    @Override
    public String getSyntax() {
        return "/siedlung info <siedlung>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<2){
            p.sendMessage(net.md_5.bungee.api.ChatColor.RED+getSyntax());
            return;
        }

        siedlung s=siedlungsliste.get(0);
        for (siedlung ss : siedlungsliste)if(ss.getOwner().equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()))s=ss;

        ArrayList<String> owner=new ArrayList<>();
        for (String ss : s.getOwner())owner.add(Bukkit.getPlayer(UUID.fromString(ss)).getName());
        ArrayList<String> member=new ArrayList<>();
        for (String ss : s.getMemberlist())owner.add(Bukkit.getPlayer(UUID.fromString(ss)).getName());

        p.sendMessage(ChatColor.DARK_GREEN+"------------Info-----------");
        p.sendMessage(ChatColor.DARK_GREEN+"Anführer: "+ChatColor.GREEN+owner);
        p.sendMessage(ChatColor.DARK_GREEN+"Beschreibung: "+ChatColor.GREEN+s.getBeschreibung());
        p.sendMessage(ChatColor.DARK_GREEN+"Name: "+ChatColor.GREEN+s.getName());
        p.sendMessage(ChatColor.DARK_GREEN+"Mitglieder: "+ChatColor.GREEN+member);
        p.sendMessage(ChatColor.DARK_GREEN+"Spieler Gesamt: "+ChatColor.GREEN+(s.getOwner().size()+s.getMemberlist().size()));
        p.sendMessage(ChatColor.DARK_GREEN+"Stufe: "+ChatColor.GREEN+s.getStufe());
        p.sendMessage(ChatColor.DARK_GREEN+"Eintrit Nachricht: "+ChatColor.GREEN+s.getWelckomemasage());
        p.sendMessage(ChatColor.DARK_GREEN+"Austrit Nachricht: "+ChatColor.GREEN+s.getLeavemessage());
        TextComponent loc1=new TextComponent(ChatColor.DARK_GREEN+"Eckpunkt 1: "+ChatColor.YELLOW+"["+(int) s.getLoc1().getX()+"x "+(int) s.getLoc1().getZ()+"z ]");
        loc1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp "+s.getLoc1().getX() +" "+(p.getWorld().getHighestBlockAt(s.getLoc1()).getY()+1)+ " "+ s.getLoc1().getZ()));
        p.sendMessage(loc1);
        TextComponent loc2=new TextComponent(ChatColor.DARK_GREEN+"Eckpunkt 2: "+ChatColor.YELLOW+"["+(int) s.getLoc2().getX()+"x "+(int) s.getLoc2().getZ()+"z ]");
        loc2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp "+s.getLoc2().getX() +" "+(p.getWorld().getHighestBlockAt(s.getLoc2()).getY()+1)+ " "+ s.getLoc2().getZ()));
        p.sendMessage(loc2);
        int x=s.getLoc1().getX()<s.getLoc2().getX() ? s.getLoc2().getBlockX()-s.getLoc1().getBlockX() : s.getLoc1().getBlockX()-s.getLoc2().getBlockX();
        int z=s.getLoc1().getZ()<s.getLoc2().getZ() ? s.getLoc2().getBlockZ()-s.getLoc1().getBlockZ() : s.getLoc1().getBlockZ()-s.getLoc2().getBlockZ();
        p.sendMessage(ChatColor.DARK_GREEN+"Gesamtgröße: "+ChatColor.YELLOW+"["+x+"x"+z+"]");
        p.sendMessage(ChatColor.DARK_GREEN+"---------------------------");

    }
}
