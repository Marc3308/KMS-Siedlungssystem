package me.marc3308.siedlungundberufe.objektorientierung;

import java.util.List;

public class spielerprovil {

    private String Name;
    private String uuid;
    private boolean abbau;
    private boolean hinbau;
    private boolean kisten;
    private boolean gaste;
    private boolean rules;
    private boolean mitglied;
    private List<String> voteckicks;

    public spielerprovil(String Name, String uuid, boolean abbau, boolean hinbau, boolean kisten, boolean gaste, boolean rules, boolean mitglied, List<String> voteckicks){
        this.Name=Name;
        this.uuid=uuid;
        this.abbau=abbau;
        this.hinbau=hinbau;
        this.kisten=kisten;
        this.gaste=gaste;
        this.rules=rules;
        this.mitglied=mitglied;
        this.voteckicks=voteckicks;
    }

    public String getName() {
        return Name;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isAbbau() {
        return abbau;
    }

    public boolean isHinbau() {
        return hinbau;
    }

    public boolean isKisten() {
        return kisten;
    }

    public boolean isGaste() {
        return gaste;
    }

    public boolean isRules() {
        return rules;
    }

    public List<String> getVoteckicks() {
        return voteckicks;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAbbau(boolean abbau) {
        this.abbau = abbau;
    }

    public void setGaste(boolean gaste) {
        this.gaste = gaste;
    }

    public void setHinbau(boolean hinbau) {
        this.hinbau = hinbau;
    }

    public void setKisten(boolean kisten) {
        this.kisten = kisten;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setRules(boolean rules) {
        this.rules = rules;
    }

    public void setVoteckicks(List<String> voteckicks) {
        this.voteckicks = voteckicks;
    }

    public void setMitglied(boolean mitglied) {
        this.mitglied = mitglied;
    }

    public boolean isMitglied() {
        return mitglied;
    }
}
