package me.marc3308.siedlungundberufe.commands.eventcomamnds;

import me.marc3308.siedlungundberufe.commands.eventcomamnds.eventsubcommands.eventcreate;
import me.marc3308.siedlungundberufe.commands.eventcomamnds.eventsubcommands.eventdelete;
import me.marc3308.siedlungundberufe.commands.eventcomamnds.eventsubcommands.eventinfo;
import me.marc3308.siedlungundberufe.objektorientierung.eventzone;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.eventzoneliste;

public class CommandManager implements CommandExecutor, TabCompleter {

    private ArrayList<subcommands> subcommands =new ArrayList<>();

    public CommandManager(){
        subcommands.add(new eventcreate());
        subcommands.add(new eventdelete());
        subcommands.add(new eventinfo());

    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))return false;
        Player p= (Player) sender;
        if(!p.isOp())return false;

        if(args.length>0){

            switch (args[0]){
                case "create":
                    subcommands.get(1).perform(p,args);
                    break;
                case "delete":
                    subcommands.get(2).perform(p,args);
                    break;
                case "info":
                    subcommands.get(3).perform(p,args);
                    break;
            }
        }




        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p= (Player) sender;
        ArrayList<String> list =new ArrayList<>();
        if(!p.isOp())return list;

        try {
            if(args.length == 0)return list;
            if(args.length == 1)for (subcommands sub : subcommands)list.add(sub.getName().toString());
            if(args.length == 3 && !args[0].equals("create"))return list;
            if(args.length == 2){
                if(args[0].equals("create")){
                    list.add("<Name>");
                } else {
                    for(eventzone s : eventzoneliste)list.add(s.getName());
                }
            }
            if(args.length == 3)list.add(String.valueOf(p.getLocation().getBlockX()));
            if(args.length == 4)list.add(String.valueOf(p.getLocation().getBlockY()));
            if(args.length == 5)list.add(String.valueOf(p.getLocation().getBlockZ()));
            if(args.length == 6)list.add(String.valueOf(p.getLocation().getBlockX()));
            if(args.length == 7)list.add(String.valueOf(p.getLocation().getBlockY()));
            if(args.length == 8)list.add(String.valueOf(p.getLocation().getBlockZ()));

            if(args.length == 9)list.add("<Zeit>");
            if(args.length == 10){
                list.add("true");
                list.add("false");
            }
            if(args.length == 11)list.add(String.valueOf(p.getLocation().getBlockX()));
            if(args.length == 12)list.add(String.valueOf(p.getLocation().getBlockY()));
            if(args.length == 13)list.add(String.valueOf(p.getLocation().getBlockZ()));
            if(args.length == 13)list.add("<Schaden>");

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

}
