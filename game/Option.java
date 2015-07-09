package game;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;

public class Option extends JFrame {
    private JPanel panel = new JPanel(new GridLayout(8,2));
    private JButton button = new JButton("Save!");
    private int up, down, left, right, attack, special, pause;
    
    JLabel noLabel = new JLabel(" ");
    JLabel upLabel = new JLabel("UP ");
    JLabel downLabel = new JLabel("DOWN ");
    JLabel rightLabel = new JLabel("RIGHT ");
    JLabel leftLabel = new JLabel("LEFT ");
    JLabel attackLabel = new JLabel("ATTACK ");
    JLabel specialLabel = new JLabel("SPECIAL ");
    JLabel pauseLabel = new JLabel("PAUSE ");
    JTextField upTextField = new JTextField();
    JTextField downTextField = new JTextField();
    JTextField rightTextField = new JTextField();
    JTextField leftTextField = new JTextField();
    JTextField attackTextField = new JTextField();
    JTextField specialTextField = new JTextField();
    JTextField pauseTextField = new JTextField();
    
    public Option(int up, int down, int right, int left, int attack, int special, int pause){
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.attack = attack;
        this.special = special;
        this.pause = pause;
        
        setTitle("Option");
        setLocationRelativeTo(null);
        setSize(250, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        upTextField.setPreferredSize(new Dimension(100, 20));
        downTextField.setPreferredSize(new Dimension(100, 20));
        rightTextField.setPreferredSize(new Dimension(100, 20));
        leftTextField.setPreferredSize(new Dimension(100, 20));
        attackTextField.setPreferredSize(new Dimension(100, 20));
        specialTextField.setPreferredSize(new Dimension(100, 20));
        pauseTextField.setPreferredSize(new Dimension(100, 20));
        
        upTextField.setHorizontalAlignment(JTextField.CENTER);
        downTextField.setHorizontalAlignment(JTextField.CENTER);
        rightTextField.setHorizontalAlignment(JTextField.CENTER);
        leftTextField.setHorizontalAlignment(JTextField.CENTER);
        attackTextField.setHorizontalAlignment(JTextField.CENTER);
        specialTextField.setHorizontalAlignment(JTextField.CENTER);
        pauseTextField.setHorizontalAlignment(JTextField.CENTER);
        
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        
        panel.add(upLabel, BorderLayout.WEST);
        panel.add(upTextField, BorderLayout.CENTER);
        panel.add(downLabel, BorderLayout.WEST);
        panel.add(downTextField, BorderLayout.CENTER);
        panel.add(rightLabel, BorderLayout.WEST);
        panel.add(rightTextField, BorderLayout.CENTER);
        panel.add(leftLabel, BorderLayout.WEST);
        panel.add(leftTextField, BorderLayout.CENTER);
        panel.add(attackLabel, BorderLayout.WEST);
        panel.add(attackTextField, BorderLayout.CENTER);
        panel.add(specialLabel, BorderLayout.WEST);
        panel.add(specialTextField, BorderLayout.CENTER);
        panel.add(pauseLabel, BorderLayout.WEST);
        panel.add(pauseTextField, BorderLayout.CENTER);
        panel.add(" ", noLabel); //blank space
        panel.add(button, BorderLayout.CENTER);
        
        add(panel);
    }
    
    public void setOption() throws HeadlessException{
        setVisible(true);
        
        upTextField.setText(KeyEvent.getKeyText(up));
        downTextField.setText(KeyEvent.getKeyText(down));
        rightTextField.setText(KeyEvent.getKeyText(right));
        leftTextField.setText(KeyEvent.getKeyText(left));
        attackTextField.setText(KeyEvent.getKeyText(attack));
        specialTextField.setText(KeyEvent.getKeyText(special));
        pauseTextField.setText(KeyEvent.getKeyText(pause));
        
        upTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) downTextField.requestFocusInWindow(); 
                else{
                    up = e.getKeyCode();
                    upTextField.setText(KeyEvent.getKeyText(up));
                }
            }
        });
        
        downTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) rightTextField.requestFocusInWindow(); 
                else{
                    down = e.getKeyCode();
                    downTextField.setText(KeyEvent.getKeyText(down));
                }
            }
        });
        
        rightTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) leftTextField.requestFocusInWindow(); 
                else{
                    right = e.getKeyCode();
                    rightTextField.setText(KeyEvent.getKeyText(right));
                }
            }
        });
        
        leftTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) attackTextField.requestFocusInWindow(); 
                else{
                    left = e.getKeyCode();
                    leftTextField.setText(KeyEvent.getKeyText(left));
                }
            }
        });
        
        attackTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) specialTextField.requestFocusInWindow(); 
                else{
                    attack = e.getKeyCode();
                    attackTextField.setText(KeyEvent.getKeyText(attack));
                }
            }
        });
        
        specialTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) pauseTextField.requestFocusInWindow(); 
                else{
                    special = e.getKeyCode();
                    specialTextField.setText(KeyEvent.getKeyText(special));
                }
            }
        });
        
        pauseTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) button.requestFocusInWindow(); 
                else{
                    pause = e.getKeyCode();
                    pauseTextField.setText(KeyEvent.getKeyText(pause));
                }
            }
        });
        
        button.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) { if(e.getKeyCode() == KeyEvent.VK_ENTER) dispose(); }
        });
        
        button.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) 
            { if(SwingUtilities.isLeftMouseButton(e) == true) dispose(); }
        });
    }
    
    public int getUP(){
        return up;
    }
    public int getDOWN(){
        return down;
    }
    public int getRIGHT(){
        return right;
    }
    public int getLEFT(){
        return left;
    }
    public int getATTACK(){
        return attack;
    }
    public int getSPECIAL(){
        return special;
    }
    public int getPAUSE(){
        return pause;
    }

}
