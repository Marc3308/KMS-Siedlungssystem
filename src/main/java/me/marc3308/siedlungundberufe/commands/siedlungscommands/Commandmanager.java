package me.marc3308.siedlungundberufe.commands.siedlungscommands;

import me.marc3308.siedlungundberufe.commands.siedlungscommands.subcommands.*;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;

public class Commandmanager implements CommandExecutor , TabCompleter {

    private ArrayList<subcommand> subcommands =new ArrayList<>();

    public Commandmanager(){
        subcommands.add(new helpcommand());
        subcommands.add(new createsiedlung());
        subcommands.add(new editcommand());
        subcommands.add(new deletecommand());
        subcommands.add(new infocommand());
        subcommands.add(new opencommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player))return false;
        Player p= (Player) sender;

        //get me some arrows
        if(args.length>0){

            if(args[0].equals("create")){
                getSubcommands().get(1).perform(p,args);
                return true;
            }
            if(!p.hasPermission("technikdetails"))return false;

            switch (args[0]){
                case "edit":
                    getSubcommands().get(2).perform(p,args);
                    break;
                case "delete":
                    getSubcommands().get(3).perform(p,args);
                    break;
                case "info":
                    getSubcommands().get(4).perform(p,args);
                    break;
                case "open":
                    getSubcommands().get(5).perform(p,args);
                    break;
                default:
                    getSubcommands().get(0).perform(p,args);
                    break;
            }
        } else {
            getSubcommands().get(0).perform(p,args);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        Player p= (Player) commandSender;
        ArrayList<String> list =new ArrayList<>();
        if(!p.hasPermission("technikdetails"))return list;

        try {
            if(args.length == 0)return list;
            if(args.length == 1)for (subcommand sub : subcommands)list.add(sub.getName().toString());
            if(args[0].equals("help"))return list;
            if(args.length == 2){
                if(args[0].equals("create")){
                    for (Player pp : Bukkit.getOnlinePlayers())list.add(pp.getName().toString());
                } else if (p.hasPermission("technikdetails")){
                    for(siedlung s : siedlungsliste){
                        for (String ss : s.getOwner()){
                            list.add(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName());
                        }
                    }
                } else {
                    return list;
                }
            }
            if(args.length == 3){
                switch (args[0]){
                    case "edit":
                        if(!p.hasPermission("technikdetails"))return list;
                        list.add("Owner");
                        list.add("Mitglieder");
                        list.add("Eckpunkt1");
                        list.add("Eckpunkt2");
                        list.add("Name");
                        list.add("Beschreibung");
                        list.add("Stufe");
                        list.add("Wikommennachricht");
                        list.add("Verlassennachricht");
                        break;
                    case "create":
                        list.add(String.valueOf(p.getLocation().getBlockX()));
                        break;
                    default:
                        return list;
                }
            }
            if(args.length==4){
                switch (args[0]){
                    case "edit":
                        if(!p.hasPermission("technikdetails"))return list;
                        if(args[2].equals("Eckpunkt1") || args[2].equals("Eckpunkt2")){
                            list.add(String.valueOf(p.getLocation().getBlockX()));
                        } else if(args[2].equals("Owner") || args[2].equals("Mitglieder")){
                            list.add("remove");
                            list.add("add");
                        } else {
                            list.add("<Neu>");
                        }
                        break;
                    case "create":
                        list.add(String.valueOf(p.getLocation().getBlockZ()));
                        break;
                    default:
                        return list;
                }
            }
            if(args.length==5){
                switch (args[0]){
                    case "edit":
                        if(!p.hasPermission("technikdetails"))return list;
                        if(args[2].equals("Eckpunkt1") || args[2].equals("Eckpunkt2"))list.add(String.valueOf(p.getLocation().getBlockZ()));
                        if(args[2].equals("Owner")){
                            if(args[3].equals("add")){
                                for (Player pp : Bukkit.getOnlinePlayers())list.add(pp.getName());
                            } else {
                                siedlung s=siedlungsliste.get(0);
                                for (siedlung ss : siedlungsliste)if(ss.getOwner().equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()))s=ss;
                                for (String ss : s.getOwner()){
                                    list.add(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName());
                                }
                            }
                        }
                        if(args[2].equals("Mitglieder")){
                            if(args[3].equals("add")){
                                for (Player pp : Bukkit.getOnlinePlayers())list.add(pp.getName());
                            } else {
                                siedlung s=siedlungsliste.get(0);
                                for (siedlung ss : siedlungsliste)if(ss.getOwner().equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()))s=ss;
                                for (String ss : s.getMemberlist()){
                                    list.add(Bukkit.getOfflinePlayer(UUID.fromString(ss)).getName());
                                }
                            }
                        }
                        break;
                    case "create":
                        list.add(String.valueOf(p.getLocation().getBlockX()));
                        break;
                    default:
                        return list;
                }
            }
            if(args.length==6){
                switch (args[0]){
                    case "create":
                        list.add(String.valueOf(p.getLocation().getBlockZ()));
                        break;
                    default:
                        return list;
                }
            }

            //autocompetion
            ArrayList<String> commpleteList = new ArrayList<>();
            String currentarg = args[args.length-1].toLowerCase();
            for (String s : list){
                if(s==null)return list;
                String s1 =s.toLowerCase();
                if(s1.startsWith(currentarg)){
                    commpleteList.add(s);
                }
            }

            return commpleteList;
        } catch (CommandException e){
            return list;
        }
    }

    public ArrayList<subcommand> getSubcommands() {
        return subcommands;
    }
}
