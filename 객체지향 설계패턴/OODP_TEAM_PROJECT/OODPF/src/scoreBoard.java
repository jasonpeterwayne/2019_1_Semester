import javax.swing.*;
import java.awt.Color;
import java.awt.Font; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class scoreBoard implements  ActionListener {
	int width = 800, height = 480;
    int redScoreAmount = 0;
    int blueScoreAmount = 0;

    JLabel redScore, blueScore;
    JButton redUpButton, redDownButton, blueUpButton, blueDownButton, resetButton;

    public scoreBoard() {
 
        redScore = new JLabel(String.format("%02d", redScoreAmount));
        redScore.setBounds(150, height/2+50, 120, 70);
        redScore.setFont(new Font("serif", Font.BOLD, 50));

        blueScore = new JLabel(String.format("%02d", blueScoreAmount));
        blueScore.setBounds(width-180, height/2+50, 120, 70);
        blueScore.setFont(new Font("serif", Font.BOLD, 50));

  

        redUpButton = new JButton("↑");
        redUpButton.setBounds(30, height/2+50, 60, 30);
        redUpButton.addActionListener(this);
        
        redDownButton = new JButton("↓");
        redDownButton.setBounds(30, height/2+100, 60, 30);
        redDownButton.addActionListener(this);

        blueUpButton = new JButton("↑");
        blueUpButton.setBounds(width-90, height/2+50, 60, 30);
        blueUpButton.addActionListener(this);
        
        blueDownButton = new JButton("↓");
        blueDownButton.setBounds(width-90, height/2+100, 60, 30);
        blueDownButton.addActionListener(this);

        resetButton = new JButton("Reset Score");
        resetButton.setBounds(0, 40, 250, 30);
        resetButton.addActionListener(this);
        
    }
    
 
    public JLabel getRedScore() {
    	return redScore;
    }
    public JLabel getBlueScore() {
    	return blueScore;
    }
    
    public JButton getRedUp() {
    	return redUpButton;
    }
    public JButton getBlueUp() {
    	return blueUpButton;
    }
    public JButton getRedDown() {
    	return redDownButton;
    }
    public JButton getBlueDown() {
    	return blueDownButton;
    }
    
     
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == redUpButton)
        {
        	redDownButton.setEnabled(true);
            redScoreAmount = redScoreAmount + 1;
            redScore.setText(String.format("%02d", redScoreAmount));
        }
        else if(e.getSource()== redDownButton) {
        	if(redScoreAmount<=0) {
        		redDownButton.setEnabled(false);
        	}
        	else {
        	redScoreAmount = redScoreAmount - 1;
            redScore.setText(String.format("%02d", redScoreAmount));
        	}
        }
        else if(e.getSource() == blueUpButton)
        {
        	blueDownButton.setEnabled(true);
            blueScoreAmount = blueScoreAmount + 1;
            blueScore.setText(String.format("%02d", blueScoreAmount));
        }
        else if(e.getSource() == blueDownButton)
        {	
        	if(blueScoreAmount<=0) {
        		blueDownButton.setEnabled(false);
        	}
        	else {
            blueScoreAmount = blueScoreAmount - 1;
            blueScore.setText(String.format("%02d", blueScoreAmount));
        	}
        }
        else if(e.getSource() == resetButton)
        {
            redScoreAmount = 0;
            blueScoreAmount = 0;
            redScore.setText(String.format("%02d", redScoreAmount));
            blueScore.setText(String.format("%02d", blueScoreAmount));
        }
    }
}