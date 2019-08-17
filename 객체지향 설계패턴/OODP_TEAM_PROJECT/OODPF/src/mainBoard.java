import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class mainBoard {
	int width = 800, height = 480;
	JPanel panel;
	gameTimer gt = new gameTimer();
	scoreBoard sb = new scoreBoard();
	Attack_Timer at = new Attack_Timer();
	displayAttack da = new displayAttack();
	countPenalty cp = new countPenalty();
	
	public mainBoard() {
		panel = new JPanel();
		panel.setLayout(null);
		
		// game timer
		panel.add(gt.getQuarterLabel());
		panel.add(gt.getLabel());
		panel.add(gt.getStartButton());
		panel.add(gt.getStopButton());
		
		// attack timer
		panel.add(at.getLabel());
		panel.add(at.getStartButton());
		panel.add(at.getStopButton());
		panel.add(at.getResetButton());
		panel.add(at.getReset14Button());
		
		// score
//		panel.add(sb.getRedLabel());
//		panel.add(sb.getBlueLabel());
		panel.add(sb.getRedScore());
		panel.add(sb.getBlueScore());
		panel.add(sb.getRedUp());
		panel.add(sb.getRedDown());
		panel.add(sb.getBlueUp());
		panel.add(sb.getBlueDown());
		
		
		// attack direction
		panel.add(da.getTeam1Attack());
		panel.add(da.getTeam2Attack());
		
		panel.add(cp.AP1());
		panel.add(cp.AP2());
		panel.add(cp.AP3());
		panel.add(cp.AP4());
		panel.add(cp.AP5());
		
		panel.add(cp.BP1());
		panel.add(cp.BP2());
		panel.add(cp.BP3());
		panel.add(cp.BP4());
		panel.add(cp.BP5());
	}
	
	public JPanel getPanel() {
		return panel;
	}

}
