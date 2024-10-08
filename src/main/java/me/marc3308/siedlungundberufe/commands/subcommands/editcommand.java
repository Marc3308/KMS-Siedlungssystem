package me.marc3308.siedlungundberufe.commands.subcommands;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.commands.subcommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.List;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;
import static me.marc3308.siedlungundberufe.utilitys.savesiedlungen;

public class editcommand extends subcommand {
    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "edit a siedlung";
    }

    @Override
    public String getSyntax() {
        return "/siedlung edit <siedlung> <art> <neuerwert>";
    }

    @Override
    public void perform(Player p, String[] args) {
        String error="/siedlung edit <siedlung> <art> <neuerwert>";
        if(args.length<4){
            p.sendMessage(ChatColor.RED+error);
            return;
        }

        if(Bukkit.getOfflinePlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler Existiert nicht");
            return;
        }

        siedlung s=siedlungsliste.get(0);
        for (siedlung ss : siedlungsliste)for(String sss : ss.getOwner())if(sss.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()))s=ss;

        try {

            switch (args[2]){
                case "Owner":
                    if(Bukkit.getOfflinePlayer(args[4])==null){
                        p.sendMessage(ChatColor.RED+"Dieser Spieler Existiert nicht");
                        return;
                    }
                    if(args[3].equals("add")){
                        List<String> ownerlist=s.getOwner();
                        ownerlist.add(Bukkit.getOfflinePlayer(args[4]).getUniqueId().toString());
                        s.setOwner(ownerlist);
                        p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich geaddet");
                    } else {
                        List<String> ownerlist=s.getOwner();
                        ownerlist.remove(Bukkit.getOfflinePlayer(args[4]).getUniqueId().toString());
                        s.setOwner(ownerlist);
                        p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich rausgescmissen");
                    }

                    savesiedlungen();
                    break;
                case "Mitglieder":
                    if(Bukkit.getOfflinePlayer(args[4])==null){
                        p.sendMessage(ChatColor.RED+"Dieser Spieler Existiert nicht");
                        return;
                    }
                    if(args[3].equals("add")){
                        List<String> ownerlist=s.getMemberlist();
                        ownerlist.add(Bukkit.getOfflinePlayer(args[4]).getUniqueId().toString());
                        s.setMemberlist(ownerlist);
                        p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich geaddet");
                    } else {
                        List<String> ownerlist=s.getMemberlist();
                        ownerlist.remove(Bukkit.getOfflinePlayer(args[4]).getUniqueId().toString());
                        s.setMemberlist(ownerlist);
                        p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich rausgescmissen");
                    }

                    savesiedlungen();
                    break;
                case "Eckpunkt1":
                    if(args.length<5){
                        p.sendMessage(ChatColor.RED+error);
                        return;
                    }
                    Location loc=new Location(p.getWorld(),Integer.valueOf(args[3]),0,Integer.valueOf(args[4]));
                    s.setLoc1(loc);
                    p.sendMessage(ChatColor.GREEN+"Der Neue Eckpunkt 1 von "+s.getName()+" ist "+args[3]+" "+args[4]);
                    break;
                case "Eckpunkt2":
                    if(args.length<5){
                        p.sendMessage(ChatColor.RED+error);
                        return;
                    }
                    Location loc2=new Location(p.getWorld(),Integer.valueOf(args[3]),0,Integer.valueOf(args[4]));
                    s.setLoc2(loc2);
                    p.sendMessage(ChatColor.GREEN+"Der Neue Eckpunkt 2 von "+s.getName()+" ist "+args[3]+" "+args[4]);
                    break;
                case "Name":
                    String alltheargs=args[3];
                    for (int i=4;i<args.length;i++)alltheargs+=" "+args[i];
                    s.setName(alltheargs);
                    p.sendMessage(ChatColor.GREEN+"Der Neue Besitzer von "+s.getName()+" ist "+alltheargs);
                    savesiedlungen();
                    break;
                case "Beschreibung":
                    String alltheargsb=args[3];
                    for (int i=4;i<args.length;i++)alltheargsb+=" "+args[i];
                    s.setBeschreibung(alltheargsb);
                    p.sendMessage(ChatColor.GREEN+"Die Neue Beschreibung von "+s.getName()+" ist "+alltheargsb);
                    savesiedlungen();
                    break;
                case "Stufe":
                    s.setStufe(Integer.valueOf(args[3]));
                    p.sendMessage(ChatColor.GREEN+"Die Neue Stufe von "+s.getName()+" ist "+args[3]);
                    break;
                case "Wikommennachricht":
                    String alltheargsw=args[3];
                    for (int i=4;i<args.length;i++)alltheargsw+=" "+args[i];
                    s.setWelckomemasage(alltheargsw);
                    p.sendMessage(ChatColor.GREEN+"Die Neue Wikommennachricht von "+s.getName()+" ist "+alltheargsw);
                    savesiedlungen();
                    break;
                case "Verlassennachricht":
                    String alltheargsv=args[3];
                    for (int i=4;i<args.length;i++)alltheargsv+=" "+args[i];
                    s.setLeavemessage(alltheargsv);
                    p.sendMessage(ChatColor.GREEN+"Die Neue Verlassennachricht von "+s.getName()+" ist "+alltheargsv);
                    savesiedlungen();
                    break;
                default:
                    p.sendMessage(ChatColor.RED+"Did not Find "+args);
                    break;
            }



        } catch (IllegalArgumentException e){
            p.sendMessage(ChatColor.RED+error);
        } catch (ArrayIndexOutOfBoundsException e){
            p.sendMessage(ChatColor.RED+error);
        }
    }
}
