import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class firstSetting {
	private JPanel panel;
	 
	
	static private JTextField TN1, TN2, TPQ, TNQ;
	static private JButton nextButton;
	
	public firstSetting(int width, int height) {
		JFrame frame = new JFrame("Demo application");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		frame.add(panel);
		placeComponents(panel, width, height);
		
	}

	private static void placeComponents(JPanel panel, int width, int height) {

		panel.setLayout(null);
		
		 int tn1Num = 0 ;
		 int tn2Num = 0 ;
		 int tpqNum = 0 ;
		 int tnqNum = 0 ;
		 
		IntegerDocument id1 = new IntegerDocument();
		IntegerDocument id2 = new IntegerDocument();

		JLabel teamNameLabel1 = new JLabel("팀 이름을 입력하세요");
		teamNameLabel1.setBounds(width/2-65, 30, 180, 25);
		panel.add(teamNameLabel1);

		TN1 = new JTextField(20);
		TN1.setBounds(width/2-95, 55, 180, 25);
		panel.add(TN1);
	    
		
		JLabel teamNameLabel2 = new JLabel("팀 이름을 입력하세요");
		teamNameLabel2.setBounds(width/2-65, 100, 180, 25);
		panel.add(teamNameLabel2);

		TN2 = new JTextField(20);
		TN2.setBounds(width/2-95, 125, 180, 25);
		panel.add(TN2);
		
		JLabel TPQLabel = new JLabel("쿼터당 시간을 입력하세요");
		TPQLabel.setBounds(width/2-65, 170, 180, 25);
		panel.add(TPQLabel);

		TPQ = new JTextField(20);
		TPQ.setBounds(width/2-95, 195, 180, 25);
		TPQ.setDocument(id1);
		panel.add(TPQ);
		
		JLabel TNQLabel = new JLabel("총 쿼터 수를 입력하세요");
		TNQLabel.setBounds(width/2-65, 240, 180, 25);
		panel.add(TNQLabel);

		TNQ = new JTextField(20);
		TNQ.setBounds(width/2-95, 265, 180, 25);
		TNQ.setDocument(id2);
		panel.add(TNQ);

        
		nextButton = new JButton("Next");
		nextButton.setBounds(width/2-50, 330, 90, 25);
		panel.add(nextButton);
				
	}
	
	public JPanel getPanel() {
		return panel;
	}
	public JTextField getTN1() {
		return TN1;
	}
	public JTextField getTN2() {
		return TN2;
	}
	public JTextField getTPQ() {
		return TPQ;
	}
	public JTextField getTNQ() {
		return TNQ;
	}
	public JButton getNextButton() {
		return nextButton;
	}

}