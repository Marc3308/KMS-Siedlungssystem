package me.marc3308.siedlungundberufe.objektorientierung;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class siedlung {
    private List<String> Owner;
    private Location loc1;
    private Location loc2;
    private String Name;
    private String Beschreibung;
    private int Custemmoddeldata;
    private String Block;
    private List<String> memberlist;
    private int stufe;

    private String welckomemasage;
    private String leavemessage;

    public siedlung(List<String> owner, Location loc1,Location loc2, String name, String Beschreibung,int Custemmodeldata,String Block, List<String> memberlist, int stufe, String welckomemasage, String leavemessage){
        this.Owner=owner;
        this.loc1=loc1;
        this.loc2=loc2;
        this.Name=name;
        this.Beschreibung=Beschreibung;
        this.Custemmoddeldata=Custemmodeldata;
        this.Block=Block;
        this.memberlist=memberlist;
        this.stufe=stufe;
        this.welckomemasage=welckomemasage;
        this.leavemessage=leavemessage;
    }

    public String getName() {
        return Name;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public List<String> getOwner() {
        return Owner;
    }

    public Integer getCustemmoddeldata() {
        return Custemmoddeldata;
    }

    public String getBlock() {
        return Block;
    }

    public List<String> getMemberlist() {
        return memberlist;
    }

    public int getStufe() {
        return stufe;
    }

    public String getWelckomemasage() {
        return welckomemasage;
    }
    public String getLeavemessage() {
        return leavemessage;
    }

    public void setOwner(List<String> owner) {
        Owner = owner;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public void setLeavemessage(String leavemessage) {
        this.leavemessage = leavemessage;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setStufe(int stufe) {
        this.stufe = stufe;
    }

    public void setWelckomemasage(String welckomemasage) {
        this.welckomemasage = welckomemasage;
    }

    public void setMemberlist(List<String> memberlist) {
        this.memberlist = memberlist;
    }
}
