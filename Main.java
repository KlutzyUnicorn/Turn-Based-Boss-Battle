import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Main {
  // private static int chosenItem;
  // class objects
  static CardLayout charMenuLayout = new CardLayout();
  static JPanel charMenus = new JPanel(charMenuLayout);
  static CardLayout menuLayout = new CardLayout();
  static JPanel menus = new JPanel(menuLayout);
  static JLabel dmgDone, archerHealthbarName, archerMenuName, mageHealthbarName, mageMenuName;
  static JLabel ending = new JLabel();
  static JPanel healthBars = new JPanel();
  static Boss boss = new Boss();

  // class variables
  private static int dmg;
  private static boolean crit;
  private static String archerNextMenu, bossNextMenu, archerName, mageName;

  public static void main(String[] args) {
    // creating objects
    JFrame frame = new JFrame("Boss Battle");
    JProgressBar bossHealth, archerHealth, mageHealth;
    Dimension buttonSize = new Dimension(175, 25);
    GridBagConstraints con = new GridBagConstraints();
    ActionListener exit = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    };
    JPanel endScreen = new JPanel();

    Mage mage = new Mage();
    Archer archer = new Archer();
    Random randomizer = new Random();
    // setting the name of the next menu needed by other menus
    archerNextMenu = "mageMenu";
    bossNextMenu = "archerMenu";

    // setting frame stuff
    frame.setSize(609, 375);
    frame.setLocation(0, 0);
    frame.getContentPane().setBackground(Color.darkGray);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    /*
     * ArrayList<String> inv = new ArrayList<String>();
     * inv.addAll(Arrays.asList("Potion", "Hi-Potion", "X-Potion", "Phoenix Down"));
     * JPanel archInv = new JPanel(new GridLayout(3,2)); JButton[] items = new
     * JButton[inv.size() + 1]; archInv.setBounds(0, 0, 304, 100); for (int i = 0; i
     * < inv.size(); i++) { final int index = i; items[i] = new JButton(inv.get(i));
     * items[i].setSize(104, 30); items[i].addActionListener(new ActionListener(){
     * public void actionPerformed(ActionEvent e) { chosenItem = index;
     * System.out.println(chosenItem); }//end actionPerformed });
     * archInv.add(items[i]); }// end for
     */

    // creates the main menu
    menus.setOpaque(false);
    JPanel mainMenu = new JPanel();
    mainMenu.setLayout(new GridBagLayout());
    mainMenu.setOpaque(false);

    // creates the title
    JLabel title = new JLabel("<html><center>Turn-Based</center><center>Battle Simulator</center></html>");
    title.setForeground(Color.WHITE);
    title.setFont(new Font("Serif", Font.PLAIN, 48));
    con.gridx = 0;
    con.gridy = 0;
    con.weighty = 0.5;
    mainMenu.add(title, con);

    // start button
    JButton start = new JButton("Start");
    start.setPreferredSize(new Dimension(175, 50));
    start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // brings up the next menu
        menuLayout.show(menus, "names");
      }// end actionPerformed
    });// end Action Listener
    con.gridy = 1;
    mainMenu.add(start, con);

    // quit button
    JButton startingQuit = new JButton("Quit");
    startingQuit.setPreferredSize(new Dimension(175, 50));
    startingQuit.addActionListener(exit);
    con.gridy = 2;
    mainMenu.add(startingQuit, con);

    // the menu after the start menu, name the characters
    JPanel names = new JPanel();
    names.setLayout(new GridBagLayout());
    names.setOpaque(false);

    // menu title
    JLabel nameTheChars = new JLabel("Name Your Characters");
    nameTheChars.setForeground(Color.WHITE);
    nameTheChars.setFont(new Font("Serif", Font.PLAIN, 24));
    con.gridy = 0;
    con.weighty = 0;
    names.add(nameTheChars, con);

    // where error messages will appear and also instructions
    JLabel errormsg = new JLabel("No Special Characters or Numbers");
    errormsg.setForeground(Color.WHITE);
    con.gridy = 1;
    names.add(errormsg, con);

    // empty space
    JLabel namesempty1 = new JLabel();
    namesempty1.setPreferredSize(new Dimension(10, 40));
    con.gridy = 2;
    names.add(namesempty1, con);

    // Label to identify the box that corresponds with the archer
    JLabel char1class = new JLabel("The Archer");
    char1class.setForeground(Color.WHITE);
    con.gridy = 3;
    names.add(char1class, con);

    // text field for the archer
    JTextField chooseArcherName = new JTextField();
    chooseArcherName.setPreferredSize(buttonSize);
    chooseArcherName.setDocument(new JTextFieldLimit(6));
    con.gridy = 4;
    names.add(chooseArcherName, con);

    // empty space
    JLabel namesempty2 = new JLabel();
    namesempty2.setPreferredSize(new Dimension(10, 50));
    con.gridy = 5;
    names.add(namesempty2, con);

    // Label to identify the box that corresponds with the mage
    JLabel char2class = new JLabel("The Mage");
    char2class.setForeground(Color.WHITE);
    con.gridy = 6;
    names.add(char2class, con);

    // text field for the mage
    JTextField chooseMageName = new JTextField();
    chooseMageName.setPreferredSize(buttonSize);
    chooseMageName.setDocument(new JTextFieldLimit(6));
    con.gridy = 7;
    names.add(chooseMageName, con);

    // more empty space
    JLabel namesempty3 = new JLabel();
    namesempty3.setPreferredSize(new Dimension(10, 50));
    con.gridy = 8;
    names.add(namesempty3, con);

    // button to confirm names
    JButton submit = new JButton("Confirm");
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // gets the text from the boxes
        archerName = chooseArcherName.getText();
        mageName = chooseMageName.getText();

        try {
          if (archerName.equals("") || mageName.equals("")) {
            // if any of the fields are empty an exception is thrown
            throw new EmptyFieldException();
          } // end if
          else {
            // checks both names for if all the characters are letters, will throw exception
            // if theres something other than letters
            for (char c : archerName.toCharArray()) {
              if (!Character.isLetter(c))
                throw new NotALetterException();
            } // end for
            for (char c : mageName.toCharArray()) {
              if (!Character.isLetter(c))
                throw new NotALetterException();
            } // end for
          } // end else
            // adds classes to the end of the name
          archerName += " (Archer)";
          mageName += " (Mage)";

          // sets the labels to the character names
          archerHealthbarName.setText(archerName);
          archerMenuName.setText(archerName);
          mageHealthbarName.setText(mageName);
          mageMenuName.setText(mageName);

          // moves to the game screen
          menus.setVisible(false);
          menus.setEnabled(false);
          healthBars.setVisible(true);
          healthBars.setEnabled(true);
          charMenus.setVisible(true);
          charMenus.setEnabled(true);
        } // end try
        catch (EmptyFieldException n) {
          // gives error message if theres an empty text field
          errormsg.setText("Error: One or Both Fields Are Empty");
        } // end catch
        catch (NotALetterException n) {
          // gives an error if theres numbers or special characters
          errormsg.setText("Error: No Special Characters or Numbers");
          chooseArcherName.setText("");
          chooseMageName.setText("");
        } // end catch
        finally {
          // no matter what happens the message at the top gets set back to original text
          Timer timer = new Timer(1000, event -> {
            errormsg.setText("No Special Characters or Numbers");
          });// end timer
          timer.setRepeats(false);
          timer.start();
        } // end finally
      }// end actionPerformed
    });// end ActionListener
    con.gridy = 9;
    names.add(submit, con);

    // the health bars
    healthBars.setPreferredSize(new Dimension(609, 275));
    healthBars.setLayout(new GridBagLayout());
    healthBars.setOpaque(false);

    // adds the archer's name on top of their health bar
    // name
    archerHealthbarName = new JLabel();
    con.gridx = 0;
    con.gridy = 0;
    con.insets = new Insets(0, 0, 0, 10);
    archerHealthbarName.setForeground(Color.WHITE);
    healthBars.add(archerHealthbarName, con);

    // health bar
    con.gridx = 0;
    con.gridy = 1;
    healthBars.add(archer.healthBar(), con);

    // mana bar
    con.gridx = 0;
    con.gridy = 2;
    healthBars.add(archer.manaBar(), con);

    // empty space number 4
    JLabel emhealth = new JLabel();
    emhealth.setPreferredSize(new Dimension(10, 50));
    con.gridx = 0;
    con.gridy = 3;
    healthBars.add(emhealth, con);

    // adds the mage's name on top of their health bar
    // name
    mageHealthbarName = new JLabel();
    mageHealthbarName.setForeground(Color.WHITE);
    con.gridx = 0;
    con.gridy = 4;
    healthBars.add(mageHealthbarName, con);

    // health
    con.gridx = 0;
    con.gridy = 5;
    healthBars.add(mage.healthBar(), con);

    // mana
    con.gridx = 0;
    con.gridy = 6;
    healthBars.add(mage.manaBar(), con);

    // now its space inbetween the player controlled characters and the boss
    JLabel spaceBetween = new JLabel();
    spaceBetween.setPreferredSize(new Dimension(235, 10));
    con.gridx = 1;
    healthBars.add(spaceBetween, con);

    // boss name
    JLabel bossname = new JLabel("Boss");
    bossname.setForeground(Color.WHITE);
    con.gridx = 2;
    con.gridy = 2;
    healthBars.add(bossname, con);

    // health
    con.gridx = 2;
    con.gridy = 3;
    healthBars.add(boss.healthBar(), con);

    // sets the dmg message at the bottom
    dmgDone = new JLabel("<html>&nbsp;<br>&nbsp;<br>&nbsp;</html>");
    dmgDone.setForeground(Color.WHITE);
    con.gridx = 1;
    con.gridy = 7;
    healthBars.add(dmgDone, con);


    
    JPanel bossTurn = new JPanel();
    bossTurn.setPreferredSize(new Dimension(609, 100));
    bossTurn.setBackground(Color.lightGray);

    bossTurn.addComponentListener(new ComponentListener() {
      public void componentHidden(ComponentEvent e) {
      }// end componentHidden

      public void componentMoved(ComponentEvent e) {
      }// end componentMoved

      public void componentResized(ComponentEvent e) {
      }// end componentResized

      public void componentShown(ComponentEvent e) {
        dmg = boss.ai();
        boolean attack = boss.attackorspecial();
        String whatdo = boss.action();

        if (dmg > 0) {
          int whotoattack;
          // decides who to attack if the boss attacks, if one is dead then the boss will
          // only attack the one still alive
          if (archer.isalive() && mage.isalive()) {
            whotoattack = randomizer.nextInt(2);
          } else if (archer.isalive()) {
            whotoattack = 0;
          } else {
            whotoattack = 1;
          }
          // whotoattack = randomizer.nextInt(2);

          if (archer.isalive() && whotoattack == 0) {
            // checks if the archer is alive
            archer.takeDmg(dmg);
            whatdo += archerName.substring(0, archerName.length() - 9);
            if (!archer.isalive()) {
              // if the archer is dead the menus will skip the archer's turn
              bossNextMenu = "mageMenu";
            }
            // System.out.println(whatdo);
          } // end if
          else if (mage.isalive() && whotoattack != 0) {
            // checks if the mage is alvie
            mage.takeDmg(dmg);
            whatdo += mageName.substring(0, mageName.length() - 7);
            if (!mage.isalive()) {
              // if the mage is dead the menus will skip the mage's turn
              archerNextMenu = "bossTurn";
            }
          } // end else

          if (attack) {
            whatdo += "<br>&nbsp;";
          } // end if

          // sets the dmg message to what the boss did
          dmgDone.setText(whatdo + "</html>");
        } // end if
        else {
          // if the boss heals
          dmgDone.setText(whatdo);
        } // end else

        // after the attack message it checks if both characters are dead
        Timer timer = new Timer(1000, event -> {
          // you lose
          if (!mage.isalive() && !archer.isalive()) {
            ending.setText("<html><center>You</center><center>Lose</center></html>");
            charMenus.setEnabled(false);
            charMenus.setVisible(false);
            healthBars.setEnabled(false);
            healthBars.setVisible(false);
            // endScreen.setEnabled(true);
            // endScreen.setVisible(true);
            menus.setEnabled(true);
            menus.setVisible(true);
            menuLayout.show(menus, "endScreen");
          } // end if

          // resets the dmg msg and brings up the next menu
          dmgDone.setText("<html>&nbsp;<br>&nbsp;<br>&nbsp;</html>");
          charMenuLayout.show(charMenus, bossNextMenu);
        });// end timer
        timer.setRepeats(false);
        timer.start();
      }// end componentShown
    });// end ComponentListener

    
    // the archer's menus
    JPanel archerMenu = new JPanel();
    archerMenu.setPreferredSize(new Dimension(609, 100));
    archerMenu.setLayout(new GridBagLayout());
    archerMenu.setBackground(Color.lightGray);
    con.weightx = 0.5;
    con.weighty = 0.5;

    // adds the menu label for the archer
    archerMenuName = new JLabel();
    con.gridx = 1;
    con.gridy = 0;
    archerMenu.add(archerMenuName, con);

    // attack button
    JButton archerAttack = new JButton("Attack");
    archerAttack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dmg = archer.attack();
        crit = archer.crit();

        // System.out.println(dmg);
        hitormiss(archerNextMenu);
      }// end ActionPerformed
    });// end ActionListener
    con.gridx = 0;
    con.gridy = 1;
    archerAttack.setPreferredSize(buttonSize);
    archerMenu.add(archerAttack, con);

    // shoot button
    JButton archerShoot = new JButton("Shoot");
    archerShoot.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        // displays amount of arrows left
        archerMenuName.setText("Arrows    " + Integer.toString(archer.arrowamt()));
      }// end mouseEntered

      public void mouseExited(MouseEvent e) {
        archerMenuName.setText(archerName);
      }// end mouseExited
    });// end MouseListener

    archerShoot.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // the actual shot
        dmg = archer.shoot();
        crit = archer.crit();

        hitormiss(archerNextMenu);
        archerMenuName.setText(archerName);

        if (archer.arrowamt() == 0) {
          archerShoot.setEnabled(false);
        } // end if
      }// end ActionPerformed
    });// end ActionListener
    con.gridx = 1;
    archerShoot.setPreferredSize(buttonSize);
    archerMenu.add(archerShoot, con);

    // inventory button
    JButton archerInv = new JButton("Inventory");
    archerInv.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        charMenuLayout.show(charMenus, "archerInv");
      }// end ActionPerformed
    });// end ActionListener
    con.gridx = 2;
    archerInv.setPreferredSize(buttonSize);
    archerMenu.add(archerInv, con);

    
    // the mage's menu
    JPanel mageMenu = new JPanel();
    mageMenu.setPreferredSize(new Dimension(609, 100));
    mageMenu.setLayout(new GridBagLayout());
    mageMenu.setBackground(Color.lightGray);

    // adds the menu label to the mage menu
    mageMenuName = new JLabel();
    con.gridx = 1;
    con.gridy = 0;
    mageMenu.add(mageMenuName, con);

    // attack button
    JButton mageAttack = new JButton("Attack");
    mageAttack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        crit = mage.crit();
        dmg = mage.attack();
        hitormiss("bossTurn");
      }// end ActionPerformed
    });// end ActionListener
    con.gridx = 0;
    con.gridy = 1;
    mageAttack.setPreferredSize(buttonSize);
    mageMenu.add(mageAttack, con);

    // magic button
    JButton mageMagic = new JButton("Magic");
    mageMagic.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        // shows mana cost
        mageMenuName.setText("Uses 25 Mana");
      }// end mouseEntered

      public void mouseExited(MouseEvent e) {
        mageMenuName.setText(mageName);
      }// end mouseExited
    });// end MouseListener

    mageMagic.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // the actual attack
        crit = mage.crit();
        dmg = mage.magic();

        hitormiss("bossTurn");
        mageMenuName.setText(mageName);

        if (mage.mana() < 25) {
          mageMagic.setEnabled(false);
        }
      }// end ActionPerformed
    });// end ActionListener
    mageMenu.addComponentListener(new ComponentListener() {
      public void componentHidden(ComponentEvent e) {
      }// end componentHidden

      public void componentMoved(ComponentEvent e) {
      }// end componentMoved

      public void componentResized(ComponentEvent e) {
      }// end componentResized

      public void componentShown(ComponentEvent e) {
        // if the magic button was disabled because of insufficent mana will check if
        // there is enough mana
        if (!mageMagic.isEnabled()) {
          if (mage.mana() >= 25) {
            mageMagic.setEnabled(true);
          }
        }
      }// end componentShown
    });// end ComponentListener
    con.gridx = 1;
    mageMagic.setPreferredSize(buttonSize);
    mageMenu.add(mageMagic, con);

    // inventory button
    JButton mageInv = new JButton("Inventory");
    mageInv.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        charMenuLayout.show(charMenus, "mageInv");
      }// end ActionPerformed
    });// end ActionListener
    con.gridx = 2;
    mageInv.setPreferredSize(buttonSize);
    mageMenu.add(mageInv, con);

    // empty space again
    JLabel aEmpty = new JLabel();
    JLabel mEmpty = new JLabel();
    con.gridx = 1;
    con.gridy = 2;
    con.weightx = 0;
    mageMenu.add(mEmpty, con);
    archerMenu.add(aEmpty, con);

    // adds it all to the card layout
    archer.buttons(charMenuLayout, charMenus, "mageMenu", "archerMenu");
    mage.buttons(charMenuLayout, charMenus, "bossTurn", "mageMenu");
    charMenus.add(archer.getButtons(), "archerInv");
    charMenus.add(mage.getButtons(), "mageInv");
    charMenus.add(archerMenu, "archerMenu");
    charMenus.add(mageMenu, "mageMenu");
    charMenus.add(bossTurn, "bossTurn");
    /*
     * archerInv.setVisible(false); archerInv.setEnabled(false);
     */

    
    // the end screen
    endScreen.setLayout(new GridBagLayout());
    endScreen.setOpaque(false);

    ending.setForeground(Color.WHITE);
    ending.setFont(new Font("Serif", Font.PLAIN, 48));
    con.gridx = 0;
    con.gridy = 0;
    // adds the ending message
    endScreen.add(ending, con);

    // button to let the player play again
    JButton replay = new JButton("Replay");
    replay.setPreferredSize(new Dimension(175, 50));
    replay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        archerNextMenu = "mageMenu";
        bossNextMenu = "archerMenu";
        archer.revive();
        mage.revive();
        boss.revive();
        archerShoot.setEnabled(true);
        charMenuLayout.show(charMenus, "archerMenu");
        charMenus.setEnabled(true);
        charMenus.setVisible(true);
        healthBars.setEnabled(true);
        healthBars.setVisible(true);
        menus.setEnabled(false);
        menus.setVisible(false);
      }
    });
    con.gridy = 1;
    endScreen.add(replay, con);

    // quit button
    JButton endingQuit = new JButton("Quit");
    endingQuit.setPreferredSize(new Dimension(175, 50));
    endingQuit.addActionListener(exit);
    con.gridy = 2;
    endScreen.add(endingQuit, con);

    
    // adds all the screens to a cardlayout
    menus.add(endScreen, "endScreen");
    menus.add(mainMenu, "mainMenu");
    menus.add(names, "names");

    // adds the menus and cardlayouts
    frame.add(charMenus, BorderLayout.PAGE_END);
    frame.add(healthBars, BorderLayout.PAGE_START);
    frame.add(menus);
    charMenuLayout.show(charMenus, "archerMenu");
    menuLayout.show(menus, "mainMenu");
    // mainMenu.setVisible(true);
    // mainMenu.setEnabled(true);

    // sets the game screen to invisible
    charMenus.setEnabled(false);
    charMenus.setVisible(false);
    healthBars.setEnabled(false);
    healthBars.setVisible(false);
    // endScreen.setEnabled(false);
    // endScreen.setVisible(false);

    /*
     * charMenus.setVisible(false); charMenus.setEnabled(false);
     */
    frame.setVisible(true);
  }// end main method

  public static void hitormiss(String menuname) {
    // checks if the character's attack hit missed or not
    // why is it in another method because this code gets repeated 4 times
    String dmgdealt = Integer.toString(dmg) + " Damage Was Dealt";
    if (dmg > 0) {
      // if its hit
      boss.takeDmg(dmg);
      if (crit) {
        // checks crit
        // System.out.println("test");
        dmgDone.setText("<html><center>Critical Hit!</center>" + dmgdealt + "<br>&nbsp;</html>");
      } // end if
      else {
        dmgDone.setText("<html><br>&nbsp;" + dmgdealt + "<br>&nbsp;</html>");
      }
    } // end if
    else {
      // wow you missed nice going
      dmgDone.setText("<html><br>&nbsp;Miss<br>&nbsp;</html>");
    } // end else

    Timer timer = new Timer(1000, event -> {
      // checks if the boss is dead
      if (!boss.isalive()) {
        ending.setText("<html><center>You</center><center>Win</center></html>");
        charMenus.setEnabled(false);
        charMenus.setVisible(false);
        healthBars.setEnabled(false);
        healthBars.setVisible(false);
        menus.setEnabled(true);
        menus.setVisible(true);
        menuLayout.show(menus, "endScreen");
      }
      charMenuLayout.show(charMenus, menuname);
      dmgDone.setText("<html>&nbsp;<br>&nbsp;<br>&nbsp;</html>");
    });// end timer
    timer.setRepeats(false);
    timer.start();
  }// end hitormiss
}// end main class