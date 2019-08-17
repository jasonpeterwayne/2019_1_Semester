import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class playGame extends Frame implements ActionListener{
	int width = 800, height = 480;
	JButton nextButton;
	JPanel p1,p2;
	CardLayout card; //카드레이아웃 사용을 위해
	firstSetting fs = new firstSetting(width, height);
	mainBoard mb = new mainBoard();
	
	public playGame(){
		super("BBS");
		p1 = fs.getPanel();
		p2 = mb.getPanel();
     
		nextButton = fs.getNextButton();  
		nextButton.addActionListener(this);
		//버튼을 클릭했을 때 카드가 바뀌도록 처리

		p1.add(nextButton);		
  
		card = new CardLayout(0,0);   // 0,0은 윈도우 창과 레이아웃 사이의 공백 사이즈를 의미함.
		setLayout(card);

  
		add("a1",p1);
		add("a2",p2);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		

		if(str.equals("Next")){
			if((fs.getTN1().getText().equals(fs.getTN2().getText())))
				JOptionPane.showMessageDialog(null, "팀 이름을 다르게 해주세요","Warning",JOptionPane.WARNING_MESSAGE );
			else if(fs.getTN1().getText().equals("") || fs.getTN2().getText().equals("")||fs.getTPQ().getText().equals("")||fs.getTNQ().getText().equals(""))
			  JOptionPane.showMessageDialog(null, "빈 텍스트 채워주세요","Warning",JOptionPane.WARNING_MESSAGE );
			else {
			mb.gt.setMinute(Integer.parseInt(fs.getTPQ().getText()));
			mb.gt.setQuarterNum(Integer.parseInt(fs.getTNQ().getText()));
			System.out.println(Integer.parseInt(fs.getTNQ().getText()));
			mb.da.setTeamName(fs.getTN1().getText(), fs.getTN2().getText());
			card.show(this,"a2"); //이와같은 방법으로 다른 카드(Panel)로의 변환이 가능하다.*/
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		playGame css = new playGame();
		css.setVisible(true);
		css.setSize(800, 480);
  
		css.addWindowListener(new WindowAdapter(){
			public void windowClosing (WindowEvent  e) {
				System.exit(1);
			}
		});
	}
	
}