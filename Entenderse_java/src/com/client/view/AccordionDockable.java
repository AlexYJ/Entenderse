package com.client.view;

import javax.swing.JPanel;



import bibliothek.gui.dock.DefaultDockable;

public class AccordionDockable extends DefaultDockable {
private JPanel panel;
	
	public AccordionDockable(){
		
		Accordion ac = new Accordion();
		add(ac);
	}
}
		


