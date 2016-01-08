package com.client.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.client.controller.TableControl;
/*
 * Table이 들어갈 Panel
 * TableControl 과 button이 생성될 2개의 패널은 포함한다.
 * 버튼 패널부부은 임시로 구현하였다.
 */
public class TimeTable extends JPanel {


	private TableControl tb = new TableControl();
	private JPanel buttomPanel = new JPanel();
	
	
	
	
	public TimeTable(){
		init();
		this.setLayout(new BorderLayout());
		this.add("Center", tb);
		this.add("South", buttomPanel);

		
	}
	public void init(){
		buttomPanel.setLayout(new FlowLayout());
		buttomPanel.add(new JButton("pre"));
		buttomPanel.add(new JButton("next"));
		
		
	}
	public void rePaintRect(int index){
		tb.rePaintRect(index);
	}
	public int convertType(String str, String start){
		int result;
		result = tb.convertType(str, start);
		return result;
	}
	

		
	

}
