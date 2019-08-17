import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;

public class displayAttack extends Applet implements ActionListener
{
	int width = 800, height = 480;
	JButton A, B;
	
	public displayAttack() {
		A = new JButton("");
		B = new JButton("");
		A.setFont(new Font("serif", Font.BOLD, 30));
		B.setFont(new Font("serif", Font.BOLD, 30));
		A.setBounds(20, 20, 150, 50);
		B.setBounds(width-170, 20, 150, 50);
		
		A.setOpaque(true);
		B.setOpaque(true);
		A.setBorderPainted(false);
		B.setBorderPainted(false);
		A.setBackground(Color.GRAY);
		B.setBackground(Color.GRAY);
		
		A.addActionListener(this);
        B.addActionListener(this);
        
	}
	
	public void setTeamName(String name1, String name2) {
		A.setText(name1);
		B.setText(name2);
	}
	public JButton getTeam1Attack() {
		return A;
	}
	public JButton getTeam2Attack() {
		return B;
	}
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==A) {
        	A.setBackground(Color.red);
    		B.setBackground(Color.GRAY);
        }
        else {
        	A.setBackground(Color.GRAY);
    		B.setBackground(Color.red);
        }
        
    }    
}