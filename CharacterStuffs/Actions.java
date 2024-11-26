import javax.swing.*;

interface Actions {
  int attack();

  void revive();

  void takeDmg(int dmg);

  boolean isalive();

  JProgressBar healthBar();
}// end Actions