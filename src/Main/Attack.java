package Main;

import Attacks.AttackModule;

import java.util.Comparator;

public class Attack {
    private int AttackID;
    private String name;
    private int speed;
    private int damage;
    private double damageMultiplier;
    private int currSpeed;
    private Cock owner;
    private AttackModule attackModule;
    private boolean isDisabled;

    public boolean getIsDisabled() {
        return isDisabled;
    }

    public Attack setAttackID(int attackID) {
        AttackID = attackID;
        return this;
    }

    public Attack setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
        return this;
    }
    public Attack(String name, int speed, int damage, double damageMultiplier, AttackModule attackModule) {
        this.name = name;
        this.speed = speed;
        this.damage = damage;
        this.damageMultiplier = damageMultiplier;
        currSpeed = this.speed;
        this.attackModule = attackModule;
    }
    public Attack(String name, int speed, int damage,double damageMultiplier, AttackModule attackModule,int AttackID) {
       this(name, speed, damage, damageMultiplier, attackModule);
       this.AttackID = AttackID;
    }

    public Attack resetCurrSpeed(){
        currSpeed = 0;
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public Attack setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public int getCurrSpeed() {
        return currSpeed;
    }

    public Attack setCurrSpeed(int currSpeed) {
        this.currSpeed = currSpeed;
        return this;
    }

    public Attack incrementCurrSpeed(){
        setCurrSpeed(getCurrSpeed() + getSpeed());
        return this;
    }

    public void apply(Cock owner, Cock target){
        attackModule.apply(owner, target, this);
        this.incrementCurrSpeed();
    }

    public String getName() {
        return name;
    }

    public Attack setName(String name) {
        this.name = name;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public Attack setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public static class sortBySpeed implements Comparator<Attack>{
        @Override
        public int compare(Attack o1, Attack o2) {
            return Integer.compare(o1.getCurrSpeed(), o2.getCurrSpeed());
        }
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public String toString(){
        return String.format("%s\t%s\t%s\t%s", getName(), getDamage(), getSpeed(), getCurrSpeed());
    }

    public Attack setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
        return this;
    }

    public Cock getOwner() {
        return owner;
    }

    public Attack setOwner(Cock owner) {
        if(this.owner != null) throw new ClassCastException("Attack Already Has an Owner");
        this.owner = owner;
        return this;
    }

    public AttackModule getAttackModule() {
        return attackModule;
    }

    public int getAttackID(){
        return AttackID;
    }
}