package com.client.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.client.controller.HttpConnection;
import com.client.controller.LoginControl;

/*
 * 로그인창을 생성한다.
 */

public class Login extends JFrame{

	private JLabel userLabel;
	private JLabel gradeLabel;
	private JTextField userText;
	private JComboBox gradeCombo;
	private JButton loginButton;
	private JButton cancleButton;
	private String Test;
	private JPanel panel;
	private int width = 400;
	private int height = 225;
	private String id;
	private String grade;
	


	public Login() {
		init();
	}

	private void init() {
		setSize(width,height);
		Dimension d = getToolkit().getScreenSize();
		setBounds((d.width-width)/2,(d.height-height)/2,width,height);
		JPanel panel = new JPanel();
		setComponet(panel);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	void setComponet(JPanel panel) {
		panel.setLayout(null);

		userLabel = new JLabel("ID_NO");
		userLabel.setBounds(40, 40, 60, 27);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(110, 40, 250, 27);
		panel.add(userText);

		gradeLabel = new JLabel("Grade");
		gradeLabel.setBounds(40, 90, 60, 27);
		panel.add(gradeLabel);

		gradeCombo = new JComboBox();
		gradeCombo.setBounds(110, 90, 250, 27);
		gradeCombo.addItem("1");
		gradeCombo.addItem("2");
		gradeCombo.addItem("3");
		gradeCombo.addItem("4");
		panel.add(gradeCombo);

		loginButton = new JButton("sign in");
		loginButton.setBounds(191, 140, 77, 27);
		panel.add(loginButton);

		cancleButton = new JButton("Cancel");
		cancleButton.setBounds(285, 140, 77, 27);
		panel.add(cancleButton);


		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				id = userText.getText();
				grade = (String) gradeCombo.getSelectedItem();

				LoginControl loginControl = new LoginControl(id, grade);
				//로그인 컨트롤을 생성한다.
				boolean result = loginControl.connectServer();
				if(result == true){
					dispose();

					MainFrame f = new MainFrame();
				}
				else{
					System.exit(0);
				}
				//로그인이 성공적으로 이루어 지면 메인프레임을 생성한다.
				




			}

		});
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
	}


}
