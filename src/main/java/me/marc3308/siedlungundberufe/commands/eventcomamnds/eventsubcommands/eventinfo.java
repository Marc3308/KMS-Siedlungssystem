package me.marc3308.siedlungundberufe.commands.eventcomamnds.eventsubcommands;

import me.marc3308.siedlungundberufe.commands.eventcomamnds.subcommands;
import me.marc3308.siedlungundberufe.objektorientierung.eventzone;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import me.marc3308.siedlungundberufe.objektorientierung.spielerprovil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.*;

public class eventinfo extends subcommands {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "info about eventzone";
    }

    @Override
    public String getSyntax() {
        return "/event info <Name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<2)return;

        eventzone s=eventzoneliste.get(0);
        for (eventzone e : eventzoneliste)if(args[1].equals(e.getName()))s=e;

        p.sendMessage(ChatColor.DARK_GREEN+"------------Info-----------");
        p.sendMessage(ChatColor.DARK_GREEN+"Name: "+ChatColor.GREEN+s.getName());
        p.sendMessage(ChatColor.DARK_GREEN+"Time: "+ChatColor.GREEN+s.getTime());
        p.sendMessage(ChatColor.DARK_GREEN+"Tp: "+ChatColor.GREEN+s.isTp());
        TextComponent tploc=new TextComponent(ChatColor.DARK_GREEN+"Tp Location: "+ChatColor.YELLOW+"["+(int) s.getTpLocation().getX()+"x "+(int) s.getTpLocation().getY()+"y "+(int) s.getTpLocation().getZ()+"z ]");
        tploc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp "+s.getTpLocation().getX() +" "+s.getTpLocation().getY()+" "+ s.getTpLocation().getZ()));
        p.sendMessage(tploc);
        p.sendMessage(ChatColor.DARK_GREEN+"Schaden: "+ChatColor.GREEN+s.getSchaden());

        TextComponent loc1=new TextComponent(ChatColor.DARK_GREEN+"Eckpunkt 1: "+ChatColor.YELLOW+"["+(int) s.getLoc1().getX()+"x "+(int) s.getLoc1().getY()+"y "+(int) s.getLoc1().getZ()+"z ]");
        loc1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp "+s.getLoc1().getX() +" "+s.getLoc1().getY()+ " "+ s.getLoc1().getZ()));
        p.sendMessage(loc1);
        TextComponent loc2=new TextComponent(ChatColor.DARK_GREEN+"Eckpunkt 2: "+ChatColor.YELLOW+"["+(int) s.getLoc2().getX()+"x "+(int) s.getLoc2().getY()+"y "+(int) s.getLoc2().getZ()+"z ]");
        loc2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp "+s.getLoc2().getX() +" "+s.getLoc2().getY()+ " "+ s.getLoc2().getZ()));
        p.sendMessage(loc2);
        int x=s.getLoc1().getX()<s.getLoc2().getX() ? s.getLoc2().getBlockX()-s.getLoc1().getBlockX() : s.getLoc1().getBlockX()-s.getLoc2().getBlockX();
        int z=s.getLoc1().getZ()<s.getLoc2().getZ() ? s.getLoc2().getBlockZ()-s.getLoc1().getBlockZ() : s.getLoc1().getBlockZ()-s.getLoc2().getBlockZ();
        p.sendMessage(ChatColor.DARK_GREEN+"Gesamtgröße: "+ChatColor.YELLOW+"["+x+"x"+z+"]");
        p.sendMessage(ChatColor.DARK_GREEN+"---------------------------");

    }
}
