package com.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;





import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Accordion
 */

public class Accordion extends JPanel implements ActionListener {
	
	private static final String String = null;
	private JPanel topPanel = new JPanel( new GridLayout( 1, 1 ));
	private JPanel bottomPanel = new JPanel( new GridLayout( 1, 1 ));
	private Map bars = new LinkedHashMap();
	private int visibleBar = 0;
	private JComponent visibleComponent = null;
	private List list1;
	private List list2;
	private List list3;
	private List list4;
	//JTable로 구성된 list를 생성한다.

	
	public Accordion()
	{
		this.setLayout( new BorderLayout() );
	    this.add( topPanel, BorderLayout.NORTH );
	    this.add( bottomPanel, BorderLayout.SOUTH );
	    
	    List list1 = new List(new String("1"));
	    List list2 = new List(new String("2"));
	    List list3 = new List(new String("3"));
	    List list4 = new List(new String("4"));
	    
	    
		
	    //Accordion에 List를 추가한다.
	    addBar( "1st", list1 );
	    addBar( "2nd", list2 );
	    addBar( "3th", list3 );
	    addBar( "4th", list4 );

	    setVisibleBar( 0 );
		
	}
	
	public void addBar( String name, JComponent component ){
		BarInfo barInfo = new BarInfo( name, component );
		barInfo.getButton().addActionListener( this );
		this.bars.put( name, barInfo );
		render();
		
	}
	public void render(){
		int totalBars = this.bars.size();
		int topBars = this.visibleBar + 1;
		int bottomBars = totalBars - topBars;
		
		Iterator itr = this.bars.keySet().iterator();
		
		this.topPanel.removeAll();
		GridLayout topLayout = ( GridLayout )this.topPanel.getLayout();
		topLayout.setRows( topBars);
		BarInfo barInfo = null;
		for( int i=0; i<topBars; i++){
			String barName = ( String )itr.next();
			barInfo = ( BarInfo )this.bars.get( barName );
			this.topPanel.add( barInfo.getButton());
		}
		this.topPanel.validate();
		if( this.visibleComponent != null ){
			this.remove( this.visibleComponent );
		}
		this.visibleComponent = barInfo.getComponent();
		this.add( visibleComponent, BorderLayout.CENTER);
		this.bottomPanel.removeAll();
		GridLayout bottomLayout = ( GridLayout )this.bottomPanel.getLayout();
		bottomLayout.setRows( bottomBars );
	    for( int i=0; i<bottomBars; i++ ){
	      String barName = ( String )itr.next();
	      barInfo = ( BarInfo )this.bars.get( barName );
	      this.bottomPanel.add( barInfo.getButton() );
	    }
	    this.bottomPanel.validate();
	    this.validate();
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int currentBar = 0;
		for( Iterator i = this.bars.keySet().iterator(); i.hasNext();){
			String barName = ( String )i.next();
			BarInfo barInfo = ( BarInfo )this.bars.get(barName);
			if( barInfo.getButton() == e.getSource()){
				this.visibleBar = currentBar;
				render();
				return;
				
			}
			currentBar++;
		}
	
	}
	

	
	public static JPanel getDummyPanel( String name ){
		JPanel panel = new JPanel( new BorderLayout() );
		panel.add(new JLabel( name, JLabel.CENTER));
		return panel;
	}
	  public void setVisibleBar( int visibleBar )
	  {
	    if( visibleBar > 0 &&
	      visibleBar < this.bars.size() - 1 )
	    {
	      this.visibleBar = visibleBar;
	      render();
	    }
	  }
	  
	  public void removeBar( String name )
	  {
	    this.bars.remove( name );
	    render();
	  }
	  public int getVisibleBar()
	  {
	    return this.visibleBar;
	  }
	
	class BarInfo {
		private String name;
		private JButton button;
		private JComponent component;
		
		public BarInfo( String name, JComponent component) {
			this.name = name;
			this.component = component;
			this.button = new JButton( name );
			
		}
		
		public String getName(){
			return this.name;
		}
		
		public void setName( String name ){
			this.name = name;
		}
		
		public JComponent getComponent(){
			return this.component;
		}
		
		public void setComponent( JComponent component ){
			this.component = component;
		}
		
		public JButton getButton(){
			return this.button;
		}
		
	}
	

}
