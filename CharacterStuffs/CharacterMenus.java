import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class CharacterMenus {
  // the panel
  private JPanel invPanel;
  protected JProgressBar healthBar;
  protected JProgressBar manaBar;

  // creating variables and arraylists needed by the child class
  protected int chosenItem, health, totalhealth, mana, totalmana, def;
  protected boolean alive;
  protected boolean crit;
  protected ArrayList<String> inv = new ArrayList<String>();
  protected ArrayList<Integer> num = new ArrayList<Integer>();
  protected ArrayList<String> des = new ArrayList<String>();
  protected ArrayList<Integer> amtHealed = new ArrayList<Integer>();

  public void buttons(CardLayout layout, JPanel cards, String nMenu, String lMenu) {
    // sets the dimensions and layout
    invPanel = new JPanel();
    invPanel.setPreferredSize(new Dimension(609, 100));
    invPanel.setLayout(new GridBagLayout());
    GridBagConstraints con = new GridBagConstraints();

    invPanel.setBackground(Color.lightGray);

    // adds the menu label
    JLabel description = new JLabel("Inventory");
    con.gridx = 1;
    con.gridy = 0;
    con.weighty = 0.5;
    invPanel.add(description, con);

    for (int i = 0; i < inv.size(); i++) {
      // adds all the buttons in the arraylist
      final int index = i;

      MouseListener listener = new MouseAdapter() {
        // every time the mouse is hovering over a button a description of what the item
        // does appears in place of the menu name
        public void mouseEntered(MouseEvent e) {
          description.setText(des.get(index));
        }// end mouseEntered

        public void mouseExited(MouseEvent e) {
          description.setText("Inventory");
        }// end mouseExited
      };// end listner

      JButton item = new JButton(inv.get(i) + "    " + num.get(i));
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // dictates what item is clicked
          chosenItem = index;
          int type = 0;
          int id = amtHealed.get(index);
          num.set(index, num.get(index) - 1);
          item.setText(inv.get(index) + "    " + num.get(index));
          // System.out.println(chosenItem);

          while (id != 0) {
            // checks the type of item
            type = id % 10;
            id /= 10;
          } // end while
          String temp = String.valueOf(amtHealed.get(index)).substring(1);
          int heal = Integer.parseInt(temp);

          // either heals or restores mana
          if (health + heal < totalhealth && type == 1) {
            // heals if health is going to be under max
            health += heal;
            // System.out.println(health);
          } // end else if
          else if (type == 1) {
            // if at or over max health
            health = totalhealth;
            // System.out.println(health);
          } // end else if
          else if (mana + heal < totalmana && type == 2) {
            // restores if mana is going to be under the max
            mana += heal;
            // System.out.println(mana);
          } // end else if
          else if (type == 2) {
            // restores full mana
            mana = totalmana;
            // System.out.println(mana);
          } // end else if

          if (num.get(index).equals(0)) {
            // disables items that the user has no more of
            description.setText("Inventory");
            item.setEnabled(false);
            item.removeMouseListener(listener);
          } // end if

          healthBar.setString(Integer.toString(health) + "/" + Integer.toString(totalhealth));
          healthBar.setValue(health);
          manaBar.setString(Integer.toString(mana) + "/" + Integer.toString(totalmana));
          manaBar.setValue(mana);
          // shows the next menu
          layout.show(cards, nMenu);
          // System.out.println(chosenItem);
        }// end actionPerformed
      });// end actionListener

      item.addMouseListener(listener);

      // sets size and location
      item.setPreferredSize(new Dimension(175, 25));
      con.weighty = 0.1;
      con.weightx = 0.5;
      if (i % 2 == 0) {
        con.gridx = 0;
      } // end if
      else {
        con.gridx = 1;
      } // end else
      if (i > 1 && i < 4) {
        con.gridy = 2;
      } // end if
      else if (i >= 4) {
        con.gridy = 3;
      } else {
        con.gridy = 1;
      } // end else
      invPanel.add(item, con);
    } // end for

    // adds the back button
    JButton back = new JButton("Back");
    back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        chosenItem = inv.size();
        layout.show(cards, lMenu);
        // System.out.println(chosenItem);
      }// end actionPerformed
    });// end actionListener

    back.setPreferredSize(new Dimension(175, 25));
    con.gridx = 2;
    con.gridy = 1;
    invPanel.add(back, con);
  }// end buttons

  public JPanel getButtons() {
    // returns the panel
    return invPanel;
  }// ends getButtons

  public JProgressBar healthBar() {
    //creates a health bar
    healthBar = new JProgressBar(0, totalhealth);
    healthBar.setStringPainted(true);
    healthBar.setString(Integer.toString(health) + "/" + Integer.toString(totalhealth));
    healthBar.setForeground(Color.GREEN);
    healthBar.setValue(health);

    return healthBar;
  }// end healthBar

  public JProgressBar manaBar() {
    //creats a mana bar
    manaBar = new JProgressBar(0, totalmana);
    manaBar.setStringPainted(true);
    manaBar.setString(Integer.toString(mana) + "/" + Integer.toString(totalmana));
    manaBar.setForeground(Color.BLUE);
    manaBar.setValue(mana);

    return manaBar;
  }// end manaBar

  public void takeDmg(int dmg) {
    // makes the character take damage
    int dmgTaken = dmg /*- def*/;
    if (health - dmgTaken < 0) {
      //if health is at or below 0 the character is dead
      health = 0;
      alive = false;
    } // end if
    else {
      health -= dmgTaken;
    } // end else
    healthBar.setString(Integer.toString(health) + "/" + Integer.toString(totalhealth));
    healthBar.setValue(health);
  }// end take dmg

  public boolean crit() {
    //is it a crit
    return crit;
  }//end crit

  public boolean isalive() {
    //IS THERE A HEARTBEAT, CLEAR *ZAP*
    return alive;
  }// end isalive
}// end Menus