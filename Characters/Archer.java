import java.util.*;

class Archer extends CharacterMenus implements Actions {
  // creates variables
  private final int strength = 15;
  private int arrows = 16;

  // creates a random object
  Random randomizer = new Random();

  public Archer() {
    // sets the values for evertything and fills the inventory
    inv.addAll(Arrays.asList("Potion", "Hi-Potion", "X-Potion"));
    des.addAll(Arrays.asList("Restores 50 HP", "Restores 150 HP", "Fully Restore HP"));
    amtHealed.addAll(Arrays.asList(150, 1150, 11000));
    num.addAll(Arrays.asList(10, 5, 2));
    alive = true;
    health = 500;
    totalhealth = 500;
    mana = 30;
    totalmana = 30;
    def = 5;
  }// constructor

  public int attack() {
    // a normal attack
    crit = false;
    int hit = randomizer.nextInt(100);
    if (hit < 15) {
      // miss
      // System.out.println("miss");
      return 0;
    } // end if
    else if (hit < 90) {
      // regular hit
      // System.out.println("Hit");
      return randomizer.nextInt(15) + strength;
    } // end else if
    else {
      // critical
      // System.out.println("Crit");
      crit = true;
      return (randomizer.nextInt(15) + strength) * 2;
    } // end else
  }// end attack

  public int shoot() {
    // uses a bow and go pew pew
    arrows -= 1;
    crit = false;
    int hit = randomizer.nextInt(100);
    if (hit < 5) {
      // miss
      return 0;
    } // end if
    else if (hit < 85) {
      // regular hit
      return randomizer.nextInt(35) + strength;
    } // end else if
    else {
      // critical
      crit = true;
      return (randomizer.nextInt(35) + strength) * 2;
    } // end else
  }// end shoot

  public int arrowamt() {
    // returns the amount of arrows left
    return arrows;
  }// ends arrowamt

  public void revive() {
    // revives the archer if the player plays again
    num.clear();
    num.addAll(Arrays.asList(10, 5, 2));
    alive = true;
    health = totalhealth;
    mana = totalmana;
    arrows = 16;
    manaBar.setString(Integer.toString(mana) + "/" + Integer.toString(totalmana));
    manaBar.setValue(mana);
    healthBar.setString(Integer.toString(health) + "/" + Integer.toString(totalhealth));
    healthBar.setValue(health);
    // System.out.println(inv);
  }// end revive

}// end Archer