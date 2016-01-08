package com.client.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.client.model.BeanBucket;
/*
 * Accordion에 추가되는 Contents 부분
 * 아직 구현되지 않았다.
 */
public class List extends JPanel {
	TableColumnModel colmodel;
	DefaultTableModel model;

	public List(){
		init();
	}

	public List(String grade){
		BeanBucket beanBucket = BeanBucket.getInstance();
//		JOptionPane.showMessageDialog(null, beanBucket.getBeanList().get(0).getGrade());
		model = new DefaultTableModel();
		model.addColumn("Subject");
		model.addColumn("Professor");
		model.addColumn("grade");
		;
		for(int i=0; i<beanBucket.getBeanList().size();i++){
			if(grade.equals(beanBucket.getBeanList().get(i).getGrade())){
				model.addRow(new Object[]{ beanBucket.getBeanList().get(i).getLectureName(),
						beanBucket.getBeanList().get(i).getProfName(), beanBucket.getBeanList().get(i).getGrade() });
			}
		}
		setPanel();
	}

	private void init(){
		model = new DefaultTableModel();
		model.addColumn("Subject");
		model.addColumn("Professor");
		model.addRow(new Object[]{"�ý���","����ȣ"});
		setPanel();


	}

	private void firstInit(){
		BeanBucket beanBucket = BeanBucket.getInstance();
		JOptionPane.showMessageDialog(null, beanBucket.getBeanList().get(0).getGrade());
		model = new DefaultTableModel();
		model.addColumn("Subject");
		model.addColumn("Professor");
		model.addColumn("Day");
		model.addColumn("StartTime");
		model.addColumn("FinishTime");
		for(int i=0; i<beanBucket.getBeanList().size();i++){
			model.addRow(new Object[]{ beanBucket.getBeanList().get(i).getLectureName(),
					beanBucket.getBeanList().get(i).getProfName(), beanBucket.getBeanList().get(i).getDay(),
					beanBucket.getBeanList().get(i).getStartTime(), beanBucket.getBeanList().get(i).getFinishTime()});
		}
		setPanel();



	}
	private void setPanel(){
		JTable table = new JTable(model);
		table.setEnabled(true);
		JScrollPane scrollpane = new JScrollPane(table);
		this.setRequestFocusEnabled(isDisplayable());		
		this.setLayout(new GridLayout());
		this.add(scrollpane);
	}

}
