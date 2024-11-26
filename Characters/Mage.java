import java.util.*;

class Mage extends CharacterMenus implements Actions {
  // creates variables
  private final int strength = 12;

  // creates a random object
  Random randomizer = new Random();

  public Mage() {
    // sets the values for evertything and fills the inventory
    inv.addAll(Arrays.asList("Potion", "Hi-Potion", "X-Potion", "Ether", "Dry Ether"));
    des.addAll(
        Arrays.asList("Restores 50 HP", "Restores 150 HP", "Fully Restore HP", "Restore 50 MP", "Restore 150 MP"));
    amtHealed.addAll(Arrays.asList(150, 1150, 11000, 250, 2150));
    num.addAll(Arrays.asList(12, 4, 2, 5, 1));
    alive = true;
    health = 375;
    totalhealth = 375;
    mana = 200;
    totalmana = 200;
    def = 8;
  }// end constructor

  public int attack() {
    // regular attack
    crit = false;
    int hit = randomizer.nextInt(100);
    if (hit < 30) {
      // miss
      return 0;
    } // end if
    else if (hit < 95) {
      // hit
      return randomizer.nextInt(10) + strength;
    } // end else if
    else {
      // critical
      crit = true;
      return (randomizer.nextInt(10) + strength) * 2;
    } // end else
  }// end attack

  public int magic() {
    // magic attack, takes mana
    mana -= 25;
    manaBar.setString(Integer.toString(mana) + "/" + Integer.toString(totalmana));
    manaBar.setValue(mana);
    crit = false;
    int hit = randomizer.nextInt(100);
    if (hit < 5) {
      // miss
      return 0;
    } // end if
    else if (hit < 87) {
      // hit
      return randomizer.nextInt(20) + strength + 8;
    } // end else if
    else {
      // critical
      crit = true;
      return (randomizer.nextInt(20) + strength + 8) * 2;
    } // end else
  }// end magic

  public int mana() {
    // returns mana
    return mana;
  }// end mana

  public void revive() {
    // I LIVE
    num.clear();
    num.addAll(Arrays.asList(12, 4, 1, 5, 1));
    alive = true;
    health = totalhealth;
    mana = totalmana;
    manaBar.setString(Integer.toString(mana) + "/" + Integer.toString(totalmana));
    manaBar.setValue(mana);
    healthBar.setString(Integer.toString(health) + "/" + Integer.toString(totalhealth));
    healthBar.setValue(health);
  }// end revive
}// end Mage