package com.client.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import tutorial.core.basics.CombinerExample;
import tutorial.support.ColorDockable;
import tutorial.support.JTutorialFrame;
import tutorial.support.Tutorial;
import bibliothek.gui.DockController;
import bibliothek.gui.DockStation;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.StackDockStation;
import bibliothek.gui.dock.action.ActionOffer;
import bibliothek.gui.dock.displayer.DisplayerCombinerTarget;
import bibliothek.gui.dock.station.Combiner;
import bibliothek.gui.dock.station.StationPaint;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import bibliothek.gui.dock.station.support.CombinerSource;
import bibliothek.gui.dock.station.support.CombinerTarget;
import bibliothek.gui.dock.station.support.Enforcement;
import bibliothek.gui.dock.station.support.PlaceholderMap;
import bibliothek.gui.dock.themes.CombinerValue;
import bibliothek.gui.dock.themes.ThemeManager;
import bibliothek.gui.dock.util.UIBridge;
import bibliothek.gui.dock.util.UIValue;

@Tutorial( id="Combiner", title="Combining Dockables" )
public class MainFrame {
	public MainFrame(){
		init();
	}
	private void init(){
		AccordionDockable ac = new AccordionDockable();
		TableDockable tt = new TableDockable();
		JTutorialFrame frame = new JTutorialFrame( CombinerExample.class );
		DockController controller = new DockController();
		Dimension d = new Dimension();
		d = frame.getToolkit().getScreenSize();
		frame.setBounds(0,0,d.width, d.height - 40);
		
		controller.setRootWindow( frame );
		frame.destroyOnClose( controller );
		//종료 버튼 클릭 시 확인 팝업창 생성. 그러나 종료되고 생성된다. 어떻게 바꾼담. 
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
			
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

					       if(result == JOptionPane.YES_OPTION){
					               System.exit(0);
					       }else{
					               //Do nothing
					       }
							}
		});
		

	
		
		
		ThemeManager themeManager = controller.getThemeManager();
		themeManager.setCombinerBridge( CombinerValue.KIND_COMBINER, new UIBridge<Combiner, UIValue<Combiner>>(){
			public void add( String id, UIValue<Combiner> uiValue ){
				// ignore	
			}
			public void remove( String id, UIValue<Combiner> uiValue ){
				// ignore
			}
			public void set( String id, Combiner value, UIValue<Combiner> uiValue ){
				uiValue.set( new CustomCombiner() );
			}
		});
		
		SplitDockStation splitDockStation = new SplitDockStation();
		controller.add( splitDockStation );
		frame.add( splitDockStation );
		
		SplitDockGrid grid = new SplitDockGrid();
		
		//메인 프레임에 AccordionDockable 삽입
		grid.addDockable(  0, 30,  30, 80, ac); 
		
	
		grid.addDockable( 10, 30,  30, 80, new ColorDockable( "ContentsList", Color.WHITE ));
		//메인 프레임에 TimeTableDockable 삽입
		grid.addDockable( 50, 30,  30, 80, new ColorDockable("Contents", Color.RED));
		
		splitDockStation.dropTree( grid.toTree() );
		
		frame.setVisible( true );
	}
	
	private static class CustomCombiner implements Combiner{
		public CombinerTarget prepare( final CombinerSource source, Enforcement force ){
			if( force.getForce() < 0.5f ){
				return null;
			}
			
			return new CombinerTarget(){
				public void paint( Graphics g, Component component, StationPaint paint, Rectangle stationBounds, Rectangle dockableBounds ){
					/* Our custom painting code paints some arrows... */
					Graphics2D g2 = (Graphics2D)g.create();
					g2.setColor( Color.GREEN );
					
					int[] x = new int[]{ 0, 20, 8, 8, -8, -8, -20 };
					int[] y = new int[]{ 0, 20, 20, 40, 40, 20, 20 };
					Polygon arrow = new Polygon( x, y, x.length );
					
					g2.translate( stationBounds.x + stationBounds.width/2, stationBounds.y + stationBounds.height/2 );
					AffineTransform transform = new AffineTransform();
					transform.rotate( Math.PI/2 );
					
					g2.translate( 0, 10 );
					g2.fillPolygon( arrow );
					g2.translate( 0, -10 );
					
					g2.transform( transform );
					g2.translate( 0, 10 );
					g2.fillPolygon( arrow );
					g2.translate( 0, -10 );
					
					g2.transform( transform );
					g2.translate( 0, 10 );
					g2.fillPolygon( arrow );
					g2.translate( 0, -10 );
					
					g2.transform( transform );
					g2.translate( 0, 10 );
					g2.fillPolygon( arrow );
					g2.translate( 0, -10 );
					
					g2.setStroke( new BasicStroke( 10 ) );
					g2.drawOval( -55, -55, 110, 110 );
					
					g2.dispose();
					
					// ... and a point at the location of the mouse
					Point mouse = source.getMousePosition();
					if( mouse != null ){
						mouse = SwingUtilities.convertPoint( source.getOld().getComponent(), mouse, component );
						g.setColor( new Color( 0, 150, 0 ) );
						g.fillOval( mouse.x-5, mouse.y-5, 10, 10 );
					}
				}
				
				public DisplayerCombinerTarget getDisplayerCombination(){
					return null;
				}
			};
		}
		
		public Dockable combine( CombinerSource source, CombinerTarget target ){
			DockStation parent = source.getParent();
			PlaceholderMap placeholders = source.getPlaceholders();
			
		    StackDockStation stack = new StackDockStation( parent.getTheme() );
	        stack.setController( parent.getController() );
	        
	        if( placeholders != null ){
	        	stack.setPlaceholders( placeholders );
	        }
	        
	        stack.drop( source.getOld() );
	        stack.drop( source.getNew() );
	        
	        return stack;
	    }		
	}
}
