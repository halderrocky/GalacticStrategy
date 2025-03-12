package edu.sdccd.cisc191.game;

/**
 * Represents a spaceship in the Galactic Strategy game.
 * Each ship has a name, health, and attack power, and can engage in combat.
 */
public class GalacticShip {
    private String name;
    private int health;
    private int attackPower;

    /**
     * Constructs a GalacticShip with the specified name, health, and attack power.
     *
     * @param name The name of the ship.
     * @param health The initial health of the ship.
     * @param attackPower The attack power of the ship.
     */
    public GalacticShip(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public GalacticShip(String enterprise) {
        this.name = enterprise;
    }

    public GalacticShip(String shipName, int shipHealth) {
        this.name = shipName;
        this.health = shipHealth;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0; // health cannot go below 0
        }
    }

    public void attack(GalacticShip target) {
        target.takeDamage(this.attackPower);
    }

    public boolean isDestroyed() {
        return this.health <= 0;
    }
}