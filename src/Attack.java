import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Attack.java will dictate the attack action on other objects. It will also
 * keep track of other values such as army count.
 * 
 * @author Paul Singh
 */

@SuppressWarnings("serial")
/*
 * Declarations of starting actions
 */
public class Attack extends JPanel implements ActionListener {

    String name;
    int attack;
    int defense;
    int gold;
    int food;
    Main main;
    GamePanel gp;
    boolean success;
    JButton atk;
    String bpmessage;

    /*
     * The attack object takes in a string name as 'n', an attack value as 'a',
     * a defense value as 'd', a starting gold value of 'g', starting resource
     * of food as 'f'.
     */
    public Attack(String n, int a, int d, int g, int f, Main m, GamePanel gp) {
        setLayout(null);
        name = n;
        attack = a;
        defense = d;
        gold = g;
        food = f;
        main = m;
        this.gp = gp;
        success = false;
        atk = new JButton("Attack");
        atk.setSize(100, 50);
        atk.setLocation(50, 98);
        atk.addActionListener(this);
        add(atk);
    }

    public void attack() {
        if (gp.overallAtk > defense) {
            success = true;
            gp.gold += gold;
            gp.food += food;
            gold = 0;
            food = 0;
            if (attack == 1500) {
                gp.consoleMSG = "Mackenzie's base destroyed!";
                // bp.gamemsg = bpmessage;
                gp.destroyed1 = true;
            }
            if (attack == 750) {
                gp.consoleMSG = "Alex's base destroyed!";
                // bp.gamemsg = bpmessage;
                gp.destroyed2 = true;
            }
        } else {
            success = false;
            gp.consoleMSG = "Failed attack! Your army was destroyed!";
            gp.armyList.clear();
            gp.overallAtk = 0;
        }
    }

    /*
     * Controls the count of army.
     */
    public void armyLoss(ArrayList<Integer> army) {
        Random r = new Random();
        int kills = r.nextInt(army.size()) + 1;
        for (int i = 0; i < kills; i++) {
            if (army.get(new Integer(i)) == 1) {
                gp.overallAtk -= 25;
                army.remove(new Integer(i));
            } else if (army.get(new Integer(i)) == 2) {
                gp.overallAtk -= 50;
                army.remove(new Integer(i));
            } else if (army.get(new Integer(i)) == 3) {
                gp.overallAtk -= 100;
                army.remove(new Integer(i));
            }

        }
    }

    /*
     * Information and confirmation of player initiated attack.
     */
    public void confirmAtk(Graphics g) {
        if (success == false) {
            g.fillRect(0, 0, 200, 150);
            g.setColor(Color.red);
            g.drawString("You are about to attack: ", 30, 14);
            g.drawString("> " + name, 40, 26);
            g.drawString("He has " + defense + " defense!", 30, 38);
            g.drawString("If you win you will steal: ", 30, 50);
            g.drawString("> " + gold + " gold", 40, 62);
            g.drawString("> " + food + " food", 40, 74);
            g.drawString("Are you sure you want to attack?", 15, 86);
        } else if (success == true) {
            g.drawString("Destroyed!@@!@!@#", 0, 20);
        }

    }

    public String toString() {
        return "" + name;
    }

    public void paintComponent(Graphics g) {
        Font f = new Font("Calibri", Font.PLAIN, 12);
        g.setFont(f);
        confirmAtk(g);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        attack();
        armyLoss(gp.armyList);
    }
}