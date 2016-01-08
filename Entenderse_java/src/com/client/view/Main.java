package com.client.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

public class Main {
	public static void main(String[] args) {
		init();
	}

	private static void init(){
		try 
		{
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		Login lg = new Login();
		//로그인 창 생성
		
		
		/*
		 *  테스트용
		 */
		
		
//		JFrame f = new JFrame();
//		TimeTable pan = new TimeTable();
//		int result;
//		result = pan.convertType("월", "5");
//		JOptionPane.showMessageDialog(null, result);
//		
//		f.setBounds(0, 0, 900, 900);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setContentPane(pan);
//		f.setVisible(true);
		
		
	}
}
