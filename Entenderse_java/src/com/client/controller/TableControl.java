package com.client.controller;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * TableControl 
 * 시간표를 출력할 테이블을 만든다.
 * 테이블 각각의 요소는 subject 객체로 구성되어 있다
 * subject객체는  linkedhash맵으로 관리하며 키값인 인덱스는
 * 요일 + 시간 으로 구성된다.
 * 
 */
class Subject{
	int x;
	int y;
	String color;
	String name;
	String prof;
	JLabel label = new JLabel();


}


public class TableControl extends JPanel {
	private int width = super.getWidth() / 5;
	private int height = super.getHeight()/9;
	private LinkedHashMap<Integer,Subject> linkedHashMap = new LinkedHashMap<Integer,Subject>();



	public TableControl(){
		drawRect();


	}
	private void drawRect(){
		int i = 0;
		this.setLayout(new GridLayout(9,5));
		
		for(int j=1;j<10;j++){
			for(int k=1;k<6;k++){


				Subject sub = new Subject();
				
				Integer index = new Integer(j *10 + k);

				i++;
				sub.x = k;
				sub.y = j;
				sub.color = "red";
				sub.label.setText(j + " " + k + " " + index);
				sub.label.setOpaque(true);
				add(sub.label);

				linkedHashMap.put(index, sub);
				
			}
		}


		Set<Entry<Integer, Subject>> set = linkedHashMap.entrySet();
		Iterator<Entry<Integer, Subject>> it = set.iterator();

	}
	public int convertType(String str, String start){
		int result = 0;
		Integer i = new Integer(start);
		int head = 0;
		int tail = i;
		switch(str){
		case "월" : 
			head = 1;
			break;
		case "화" :
			head = 2;
			break;
		case "수" :
			head = 3;
			break;
		case "목" :
			head = 4;
			break;
		case "금" : 
			head = 5;
			break;
		}
		result = head * 10 + tail;
		
		
		
		return result;
		
	}
	
	public void rePaintRect(int index){
		linkedHashMap.get(index).label.setBackground(Color.red);
		
	}



}
