import java.util.*;
import javax.swing.*;
import java.awt.*;

class Boss implements Actions {
  // creates variables
  private final int strength = 30;
  private final int def = 8;
  private int cooldown = 0;
  private int healcooldown = 0;
  private int health = 1000;
  private final int totalhealth = 1000;
  private boolean alive = true;
  private boolean crit = false;
  private boolean attackorspecial;
  private String whatdo;
  private int amtdone;
  private JProgressBar healthBar;

  // creates objects and arraylists
  Random randomizer = new Random();
  private ArrayList<Integer> inv = new ArrayList<Integer>();
  private ArrayList<Integer> healamt = new ArrayList<Integer>();
  Archer archer = new Archer();
  Mage mage = new Mage();

  public Boss() {
    // adds items to the inventory
    inv.addAll(Arrays.asList(1, 5, 2));
    healamt.addAll(Arrays.asList(500, 100, 200));
  }// end constructor

  public int attack() {
    // regular attack
    attackorspecial = true;
    crit = false;
    int hit = randomizer.nextInt(100);
    if (hit < 15) {
      // miss
      whatdo = "<html>&nbsp;<br>";
      // System.out.println("miss");
      return 0;
    } // end if
    else if (hit < 92) {
      // regular hit
      // System.out.println("Hit");
      whatdo = "<html>&nbsp;<br>";
      return randomizer.nextInt(20) + strength;
    } // end else if
    else {
      // critical
      // System.out.println("Crit");
      whatdo = "<html><center>Critical Hit!</center>";
      return (randomizer.nextInt(20) + strength) * 2;
    } // end else
  }// end attack

  private int specialAttack() {
    // the bosses special attack, it uses a cooldown
    attackorspecial = false;
    crit = false;
    cooldown = 5;
    int hit = randomizer.nextInt(100);
    if (hit < 5) {
      // miss
      // whatdo = "<html><center>Critical Hit!</center>";
      whatdo = "<html>&nbsp;<br>";
      return 0;
    } // end if
    else if (hit < 95) {
      // regular hit
      // whatdo = "<html><center>Critical Hit!</center>";
      whatdo = "<html>&nbsp;<br>";
      return randomizer.nextInt(40) + strength;
    } // end else if
    else {
      // critical
      crit = true;
      whatdo = "<html><center>Critical Hit!</center>";
      return (randomizer.nextInt(40) + strength) * 2;
    } // end else
  }// end specialAttack

  public boolean attackorspecial() {
    // returns if it's a regular attack or a special attack
    return attackorspecial;
  }// end attackorspecial

  public boolean isalive() {
    // is the boss still alive?
    return alive;
  }// end isalive

  public void takeDmg(int dmg) {
    // taking damage
    int dmgTaken = dmg - def;
    if (health - dmgTaken < 0) {
      // dead
      health = 0;
      alive = false;
    } // end if
    else {
      health -= dmgTaken;
    } // end else

    healthBar.setValue(health);
  }// end take dmg

  public void revive() {
    // brings it back on a replay
    inv.clear();
    inv.addAll(Arrays.asList(1, 5, 2));
    health = totalhealth;
    cooldown = 0;
    alive = true;
    healthBar.setValue(health);
  }// end revive

  public JProgressBar healthBar() {
    // creates health bar
    healthBar = new JProgressBar(0, totalhealth);
    healthBar.setForeground(Color.GREEN);
    healthBar.setValue(health);

    return healthBar;
  }// end healthBar

  public int ai() {
    // dictates the bosses action
    int whataction = randomizer.nextInt(100);

    if (cooldown != 0) {
      cooldown -= 1;
    } // end if
    if (healcooldown != 0) {
      healcooldown -= 1;
    } // end if

    if (whataction < 7 && cooldown == 0) {
      // special attack
      amtdone = specialAttack();
      // crit = true;
      whatdo += "<center>Special Attack!</center>" + Integer.toString(amtdone) + " Damage Was Dealt To ";
      // System.out.println("SpecialAttack");
    } // end if
    else if (whataction < 12 && health < totalhealth / 2 && healcooldown == 0) {
      // healing
      healcooldown = 5;
      amtdone = -1;
      int heal = randomizer.nextInt(inv.size());
      inv.set(heal, inv.get(heal) - 1);
      health += healamt.get(heal);
      healthBar.setValue(health);
      whatdo = "<html>&nbsp;<br>The Boss Healed " + Integer.toString(healamt.get(heal)) + "HP<br>&nbsp;</html>";

      if (inv.get(heal).equals(0)) {
        inv.remove(heal);
        healamt.remove(heal);
      } // end if
        // System.out.println(inv);
    } // end else if
    else {
      // regular attack
      amtdone = attack();
      whatdo += Integer.toString(amtdone) + " Damage Was Dealt To ";
    } // end else

    if (amtdone == 0) {
      // miss
      whatdo = "<html><br>&nbsp;Miss<br>&nbsp;</html>";
    } // end if

    return amtdone;
  }// end ai

  public String action() {
    // returns a string on what the boss did
    return whatdo;
  }// end action
}// end Boss