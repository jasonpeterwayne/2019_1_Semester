import java.awt.*; //windows toolkit
import java.applet.*; //applet support
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class gameTimer extends Applet implements Runnable,ActionListener{
	int width = 800, height = 480;
	int storedTPQ;
	int MaxQN;
	int QN = 1;
	int minute, second, millisecond;
	Thread thread;

	JLabel display, quarterLabel;
	JButton start, stop;
	String disp;
	boolean on;

	//initialization
	public gameTimer() {
		on = false;
		
		quarterLabel = new JLabel("1");
    	quarterLabel.setBounds(width/2, height/2-50, 200, 70);
    	quarterLabel.setForeground(Color.green);
    	quarterLabel.setFont(new Font("serif", Font.BOLD, 30));
		
		//Label
		display = new JLabel();
		display.setFont(new Font("serif", Font.BOLD, 150));
		display.setBounds(width/2-200, 50, 500, 150);
		
		//start button
		start = new JButton("START");
		start.addActionListener((ActionListener) this);
		start.setBounds(width/2-130, 20, 120, 25);
		
		//stop button
		stop = new JButton("STOP");
		stop.addActionListener((ActionListener)this);
		stop.setBounds(width/2+10, 20, 120, 25);
				
		//starting thread?
		new Thread(this).start();	
	}

	public void setMinute(int TPQ) {
		storedTPQ = TPQ;
		minute = TPQ;
		millisecond = minute * 60 * 1000;
		
		disp = String.format("%02d", minute) + " : " + String.format("%02d", millisecond / 1000 % 60);
		display.setText(disp);
	}
	public void setQuarterNum(int TNQ) {
		MaxQN = TNQ;
	}
	public JLabel getQuarterLabel() {
    	return quarterLabel;
    }
	public JLabel getLabel() {
		return display;
	}
	public JButton getStartButton() {
		return start;
	}
	public JButton getStopButton() {
		return stop;
	}
	
	public void updateQuarterNum() {
		if(QN < MaxQN) {
			QN++;
			quarterLabel.setText("" + QN);
			setMinute(storedTPQ);
		}
	}
	//change label
	public void changeLabel()
	{
		disp = String.format("%02d", minute) + " : " + String.format("%02d", second);
		display.setText(disp);
	}
	
	public void update()
	{
		millisecond -= 100;
		minute = millisecond / 60000;
		second = millisecond / 1000 % 60;
		
		if(millisecond <= 0) {
			on =false;
			this.updateQuarterNum();
		}
	}
	
	public void run() 
	{	// executed by Thread
		while(on)
		{
				try {
					Thread.sleep(100);
				}
				catch ( InterruptedException e) {
					 System.out.println(e); 
				}
				//--second;
				update();
				changeLabel();
			}
	}
	
		
	
	//listening to the actions on the buttons
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == start) {
			if(millisecond > 0 && on == false) {
				on = true;
				thread = new Thread(this);
				thread.start();
			}
		}
		
		if(e.getSource() == stop) {
			on=false;
		}
	}
}
