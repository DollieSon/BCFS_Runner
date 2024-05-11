package Main;

import Attacks.AttackModule;

import java.util.Comparator;

public class Attack {
    private String Name;
    private int Speed;
    private int Damage;
    private double damageScaler;
    private int CurrSpeed;

    private Entity Owner;

    private AttackModule attackModule;

    public Attack(String name, int speed, int damage,double damageScaler, AttackModule attackModule) {
        Name = name;
        Speed = speed;
        Damage = damage;
        this.damageScaler = damageScaler;
        CurrSpeed = Speed;
        this.attackModule = attackModule;
    }

    public Attack resetCurrSpeed(){
        CurrSpeed = 0;
        return this;
    }

    public int getDamage() {
        return Damage;
    }

    public Attack setDamage(int damage) {
        Damage = damage;
        return this;
    }

    public int getCurrSpeed() {
        return CurrSpeed;
    }

    public Attack setCurrSpeed(int currSpeed) {
        CurrSpeed = currSpeed;
        return this;
    }

    public Attack incrementCurrSpeed(){
        setCurrSpeed(getCurrSpeed()+getSpeed());
        return this;
    }

    public void apply(Entity Owner, Entity Target){
        attackModule.apply(Owner,Target,this);
        this.incrementCurrSpeed();
    }

    public String getName() {
        return Name;
    }

    public Attack setName(String name) {
        Name = name;
        return this;
    }

    public int getSpeed() {
        return Speed;
    }

    public Attack setSpeed(int speed) {
        Speed = speed;
        return this;
    }

    public static class sortBySpeed implements Comparator<Attack>{
        @Override
        public int compare(Attack o1, Attack o2) {
            return Integer.compare(o1.getCurrSpeed(),o2.getCurrSpeed());
        }
    }

    public double getDamageScaler() {
        return damageScaler;
    }

    public String toString(){
        return String.format("%s\t%s\t%s\t%s",getName(),getDamage(),getSpeed(),getCurrSpeed());
    }

    public Attack setDamageScaler(double damageScaler) {
        this.damageScaler = damageScaler;
        return this;
    }

    public Entity getOwner() {
        return Owner;
    }

    public Attack setOwner(Entity owner) {
        if(Owner!= null) throw new ClassCastException("Attack Already Has an Owner");
        Owner = owner;
        return this;
    }
}