package me.marc3308.siedlungundberufe.commands.subcommands;

import me.marc3308.siedlungundberufe.Siedlungundberufe;
import me.marc3308.siedlungundberufe.commands.subcommand;
import me.marc3308.siedlungundberufe.objektorientierung.siedlung;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static me.marc3308.siedlungundberufe.Siedlungundberufe.siedlungsliste;
import static me.marc3308.siedlungundberufe.utilitys.getInvoBlock;
import static me.marc3308.siedlungundberufe.utilitys.getItem;

public class opencommand extends subcommand {
    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getDescription() {
        return "open a inventory";
    }

    @Override
    public String getSyntax() {
        return "/siedlung open <Besitzer>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if(args.length<2){
            p.sendMessage(ChatColor.RED+getSyntax());
            return;
        }

        siedlung s=siedlungsliste.get(0);
        for (siedlung ss : siedlungsliste)if(ss.getOwner().equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()))s=ss;
        switch (s.getStufe()){
            case 0:
                //create inventory
                Inventory siedlung= Bukkit.createInventory(p,27,s.getName());

                //set items
                siedlung.setItem(12,getItem("gäste"));
                siedlung.setItem(14,getInvoBlock("infoblock",s));
                siedlung.setItem(26,getItem("pfeil"));

                //set items
                p.openInventory(siedlung);
                break;
            case 1:
                //create inventory
                Inventory siedlungtier1= Bukkit.createInventory(p,27,s.getName());

                //set items
                siedlungtier1.setItem(10,getItem("einstellung"));
                siedlungtier1.setItem(12,getItem("people"));
                siedlungtier1.setItem(14,getItem("beruf"));
                siedlungtier1.setItem(16,getInvoBlock("infoblock",s));
                siedlungtier1.setItem(26,getItem("pfeil"));

                //set items
                p.openInventory(siedlungtier1);
                break;
            case 2:
                break;
            default:
                break;
        }

    }
}
