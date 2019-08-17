import java.awt.*; //windows toolkit
import java.applet.*; //applet support
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class Attack_Timer extends Applet implements Runnable,ActionListener{
	int width = 800, height = 480;
	int counter;
	int second, millisecond;
	Thread thread;

	JLabel display;
	JButton start, stop, reset, reset14;
	String disp;
	boolean on;

	//initialization
	public Attack_Timer() {
		on = false;
		
		//initial time 24 seconds
		second = 24;
		millisecond = second * 1000;
		
		//Label
		display = new JLabel();
		disp = String.format("%02d", second) + " : " + String.format("%02d", millisecond % 1000 / 10);
		display.setText(disp);
		display.setBounds(width/2-90, 250, 270, 70);
		display.setFont(new Font("serif", Font.BOLD, 70));
		
		//start button
		start = new JButton("START");
		start.addActionListener((ActionListener) this);
		start.setBounds(width/2-55, 330, 120, 25);
		
		//stop button
		stop = new JButton("STOP");
		stop.addActionListener((ActionListener)this);
		stop.setBounds(width/2-55, 360, 120, 25);
		
		//reset button
		reset = new JButton("RESET");
		reset.addActionListener((ActionListener)this);
		reset.setBounds(width/2-55, 390, 120, 25);
		
		//reset 14 seconds button
		reset14 = new JButton("RESET_14SEC");
		reset14.addActionListener((ActionListener)this);
		reset14.setBounds(width/2-55, 420, 120, 25);
		
		
		//starting thread?
		new Thread(this).start();	
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
	public JButton getResetButton() {
		return reset;
	}
	public JButton getReset14Button() {
		return reset14;
	}

	//reset
	public void reset()
	{
		try {
			Thread.sleep(1);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		second = 24;
		millisecond = second * 1000;
	}
	
	//reset14seconds
	public void reset14()
	{
		try {
			Thread.sleep(1);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		second = 14;
		millisecond = second * 1000;
	}
	
	//change label
	public void changeLabel()
	{
	    disp = String.format("%02d", second) + " : " + String.format("%02d", millisecond % 1000 / 10);
		display.setText(disp);
	}

	public void update()
	{
		millisecond --;
		second = millisecond / 1000;
		
		if(millisecond <= 0) on =false;
	}
	
	public void run() 
	{	// executed by Thread
		while(on)
		{
			try {
				Thread.sleep(1);
			}
			catch ( InterruptedException e) {
				 System.out.println(e); 
			}
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
		
		if(e.getSource() == reset) {
			on = false;
			reset();
			changeLabel();
		}
		
		if(e.getSource() == stop) {
			on=false;
		}
		
		if(e.getSource() == reset14) {
			on = false;
			reset14();
			changeLabel();
		}
	}
}