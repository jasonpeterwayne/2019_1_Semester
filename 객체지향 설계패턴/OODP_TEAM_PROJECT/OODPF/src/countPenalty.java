import java.applet.*;
import java.awt.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class countPenalty extends Applet implements ActionListener {
	int width = 800, height = 480;		//창 사이즈
	
	//팀 파울 버튼 
	JButton A1, B1, A2, B2, A3, B3, A4, B4, A5, B5;
	//버튼클릭횟수 카운트 어레이 
	int[] CA = {0,0,0,0,0};
	int[] CB = {0,0,0,0,0};
	
	public countPenalty() {
		//A팀 페널티 버튼
		A1 = new JButton();
		A1.setOpaque(true);
		A1.setBorderPainted(false);
		A1.setBounds(20, height/2-20, 40, 30);
		A1.setBackground(Color.GRAY);
		A1.addActionListener(this);
        
		A2 = new JButton();
		A2.setOpaque(true);
		A2.setBorderPainted(false);
		A2.setBounds(75, height/2-20, 40, 30);
		A2.setBackground(Color.GRAY);
		A2.addActionListener(this);
		
		A3 = new JButton();
		A3.setOpaque(true);
		A3.setBorderPainted(false);
		A3.setBounds(130, height/2-20, 40, 30);
		A3.setBackground(Color.GRAY);
		A3.addActionListener(this);
		
		A4 = new JButton();
		A4.setOpaque(true);
		A4.setBorderPainted(false);
		A4.setBounds(185, height/2-20, 40, 30);
		A4.setBackground(Color.GRAY);
		A4.addActionListener(this);
        
		A5 = new JButton();
		A5.setOpaque(true);
		A5.setBorderPainted(false);
		A5.setBounds(240, height/2-20, 40, 30);
		A5.setBackground(Color.GRAY);
		A5.addActionListener(this);
        
		//B팀 페널티 버튼
		B1 = new JButton();
		B1.setOpaque(true);
		B1.setBorderPainted(false);
		B1.setBounds(width-60, height/2-20, 40, 30);
		B1.setBackground(Color.GRAY);
		B1.addActionListener(this);
        
		B2 = new JButton();
		B2.setOpaque(true);
		B2.setBorderPainted(false);
		B2.setBounds(width-115, height/2-20, 40, 30);
		B2.setBackground(Color.GRAY);
		B2.addActionListener(this);
        
		B3 = new JButton();
		B3.setOpaque(true);
		B3.setBorderPainted(false);
		B3.setBounds(width-170, height/2-20, 40, 30);
		B3.setBackground(Color.GRAY);
		B3.addActionListener(this);
        
		B4 = new JButton();
		B4.setOpaque(true);
		B4.setBorderPainted(false);
		B4.setBounds(width-225, height/2-20, 40, 30);
		B4.setBackground(Color.GRAY);
		B4.addActionListener(this);
        
		B5 = new JButton();
		B5.setOpaque(true);
		B5.setBorderPainted(false);
		B5.setBounds(width-280, height/2-20, 40, 30);
		B5.setBackground(Color.GRAY);
		B5.addActionListener(this);
	}
	
	
	//mainBoard에서 불러오기 위한
	public JButton AP1() {
    	return A1;
    }
	public JButton AP2() {
    	return A2;
    }
	public JButton AP3() {
    	return A3;
    }
	public JButton AP4() {
    	return A4;
    }
	public JButton AP5() {
    	return A5;
    }
	public JButton BP1() {
    	return B1;
    }
	public JButton BP2() {
    	return B2;
    }
	public JButton BP3() {
    	return B3;
    }
	public JButton BP4() {
    	return B4;
    }
	public JButton BP5() {
    	return B5;
    }
	
	
	//버튼클릭으로 색상변화 체크 
	@Override
	public void actionPerformed(ActionEvent e) {
		///A팀
		if(e.getSource() == A1) {
			if (CA[0] == 0) {
				A1.setBackground(new Color(255, 220, 0));
				CA[0] = 1;
			}
			else if (CA[0] == 1) {
				A1.setBackground(Color.gray);
				CA[0] = 0;
			}
        }
        else if(e.getSource()== A2) {
        	if (CA[1] == 0) {
				A2.setBackground(new Color(255, 180, 0));
				CA[1] = 1;
			}
			else if (CA[1] == 1) {
				A2.setBackground(Color.gray);
				CA[1] = 0;
			}
        }
        else if(e.getSource()== A3) {
        	if (CA[2] == 0) {
				A3.setBackground(new Color(255, 130, 0));
				CA[2] = 1;
			}
			else if (CA[2] == 1) {
				A3.setBackground(Color.gray);
				CA[2] = 0;
			}
        }
        else if(e.getSource()== A4) {
        	if (CA[3] == 0) {
				A4.setBackground(new Color(255, 50, 0));
				CA[3] = 1;
			}
			else if (CA[3] == 1) {
				A4.setBackground(Color.gray);
				CA[3] = 0;
			}
        }
        else if(e.getSource()== A5) {
        	if (CA[4] == 0) {
				A5.setBackground(Color.red);
				CA[4] = 1;
			}
			else if (CA[4] == 1) {
				A5.setBackground(Color.gray);
				CA[4] = 0;
			}
        }
		
		///B
        else if(e.getSource() == B1) {
			if (CB[0] == 0) {
				B1.setBackground(new Color(255, 220, 0));
				CB[0] = 1;
			}
			else if (CB[0] == 1) {
				B1.setBackground(Color.gray);
				CB[0] = 0;
			}
        }
        else if(e.getSource()== B2) {
        	if (CB[1] == 0) {
				B2.setBackground(new Color(255, 180, 0));
				CB[1] = 1;
			}
			else if (CB[1] == 1) {
				B2.setBackground(Color.gray);
				CB[1] = 0;
			}
        }
        else if(e.getSource()== B3) {
        	if (CB[2] == 0) {
        		B3.setBackground(new Color(255, 130, 0));
				CB[2] = 1;
			}
			else if (CB[2] == 1) {
				B3.setBackground(Color.gray);
				CB[2] = 0;
			}
        }
        else if(e.getSource()== B4) {
        	if (CB[3] == 0) {
        		B4.setBackground(new Color(255, 50, 0));
				CB[3] = 1;
			}
			else if (CB[3] == 1) {
				B4.setBackground(Color.gray);
				CB[3] = 0;
			}
        }
        else if(e.getSource()== B5) {
        	if (CB[4] == 0) {
        		B5.setBackground(Color.red);
				CB[4] = 1;
			}
			else if (CB[4] == 1) {
				B5.setBackground(Color.gray);
				CB[4] = 0;
			}
        }
		
	}
}
