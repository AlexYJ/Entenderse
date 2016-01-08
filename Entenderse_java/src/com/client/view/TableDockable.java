package com.client.view;

import javax.swing.JPanel;

import bibliothek.gui.dock.DefaultDockable;

public class TableDockable extends DefaultDockable {
	private JPanel panel;

	public TableDockable(){

		TimeTable tt = new TimeTable();

		add(tt);


	}
}
