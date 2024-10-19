package me.marc3308.siedlungundberufe.objektorientierung;

import org.bukkit.Location;
import org.checkerframework.checker.units.qual.N;

public class eventzone {
    private String Name;
    private Location loc1;
    private Location loc2;
    private int Time;
    private boolean Tp;
    private Location TpLocation;
    private double Schaden;

    public eventzone(String Name,Location loc1,Location loc2,int Time,boolean Tp,Location TpLocation,double Schaden){
        this.Name=Name;
        this.loc1=loc1;
        this.loc2=loc2;
        this.Time=Time;
        this.Tp=Tp;
        this.TpLocation=TpLocation;
        this.Schaden=Schaden;
    }

    public String getName() {
        return Name;
    }

    public double getSchaden() {
        return Schaden;
    }

    public Location getLoc2() {
        return loc2;
    }

    public Location getLoc1() {
        return loc1;
    }

    public int getTime() {
        return Time;
    }

    public Location getTpLocation() {
        return TpLocation;
    }

    public boolean isTp() {
        return Tp;
    }
}
