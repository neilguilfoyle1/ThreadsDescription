/* Student Name: Neil Guilfoyle */
/* SID: C11722875 */
/* Class for the main frame that is shown  
 * It gets its own database object that allows to load all clothes
 * creates content pane with all panels and labels
 * implements ActionListener for all the buttons and popup menu
 * Has internal mouse adapter class to make popup */


package com.assignment.clothes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ClothesGUI extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JLabel totalPrice;
	private JLabel selectedPrice;
	private JButton selectOutfit, replaceOutfit;
	private JPanel frontEndPane, topPanel;
	private JPanel startFrontEndPane;
	private JPanel buttonHolder;
	private Database db;
	private JMenuItem help, total, exit;
	
	public ClothesGUI (String myTitle) 
	{
		// Set the title of the window
		super (myTitle);
		
		db = new Database();
		// Set the default size of the window
		setSize(1150,600);
		setLocation(100,100);
		setContentPane(createContentPane());
		setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	
	private Container createContentPane() 
    {
		startFrontEndPane = new JPanel(new BorderLayout());
		JPanel centeringPanel = new JPanel(new FlowLayout());
		JLabel startLabel = new JLabel();
		startLabel.setIcon(new ImageIcon(ClothesItem.class.getResource("clothesshop.jpg")));
		centeringPanel.add(startLabel);
		startFrontEndPane.add(centeringPanel, BorderLayout.CENTER);
		
		
		frontEndPane = (JPanel) getContentPane();
		frontEndPane.setLayout(new BorderLayout());
		frontEndPane.addMouseListener(new MouseForPopup(this));
		frontEndPane.setBackground(Color.getHSBColor(44, 56, 100));
		
		
		//top panel currently empty before "Select outfit" is pressed
		topPanel = new JPanel(new GridLayout(1,5));
		
		frontEndPane.add(topPanel, BorderLayout.NORTH);
		
		JPanel centre = new JPanel(new GridLayout(1,2));
		JPanel c1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 30));
		JPanel c2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 30));
		
		
		//Total Price label with prompt string until the items are shown
		
		totalPrice = new JLabel("Press Button Below To Start");
		c1.add(totalPrice);
		centre.add(c1);
		
		//Selected Price label empty until items selected
		selectedPrice = new JLabel();
		c2.add(selectedPrice);
		centre.add(c2);
		
		frontEndPane.add(centre, BorderLayout.CENTER);
		
		//adding buttons 
		buttonHolder = new JPanel(new GridLayout(1, 2));
		
		selectOutfit = new JButton("Select Outfit");
		selectOutfit.addActionListener(this);
		buttonHolder.add(selectOutfit);
		
		
		replaceOutfit = new JButton("Replace");	//added after the selectOutfit is pressed once
		replaceOutfit.addActionListener(this);
		
		startFrontEndPane.add(selectOutfit, BorderLayout.SOUTH);
		frontEndPane.add(buttonHolder, BorderLayout.SOUTH);
		
		
		return startFrontEndPane;
    }
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==selectOutfit)
		{
			
			setContentPane(frontEndPane);
			startFrontEndPane = null; // not used after button is pressed
		
			buttonHolder.add(replaceOutfit);
			topPanel.add(db.getItem("Trousers"));
			topPanel.add(db.getItem("T-Shirts"));
			topPanel.add(db.getItem("Jackets"));
			topPanel.add(db.getItem("Shoes"));
			topPanel.add(db.getItem("Sweaters"));
			showTotalPrice(true);			//displays the price of selected items on right
			showTotalPrice(false);			//displays total price of all items on left
		}
		
		//Suggest all 5 clothes categories again
		//if replace button is pressed
		if(e.getSource()==replaceOutfit)
		{
			//if clothesItem topPanel is NOT checked
			if(!((ClothesItem)topPanel.getComponent(0)).isChecked())
			{   //remove 0 element, 1 element, and so on..
				topPanel.remove(0);
				//topPanel gets the next trousers from the database
				topPanel.add(db.getItem("Trousers"), 0);
			}
			
			if(!((ClothesItem)topPanel.getComponent(1)).isChecked())
			{
				topPanel.remove(1);
				topPanel.add(db.getItem("T-Shirts"), 1);
			}
			
			if(!((ClothesItem)topPanel.getComponent(2)).isChecked())
			{
				topPanel.remove(2);
				topPanel.add(db.getItem("Jackets"), 2);
			}
			
			if(!((ClothesItem)topPanel.getComponent(3)).isChecked())
			{
				topPanel.remove(3);
				topPanel.add(db.getItem("Shoes"), 3);
			}
			
			if(!((ClothesItem)topPanel.getComponent(4)).isChecked())
			{
				topPanel.remove(4);
				topPanel.add(db.getItem("Sweaters"), 4);
			}
			showTotalPrice(true);
			showTotalPrice(false);
		}
		
		if( e.getSource() == help )
		{
			totalPrice.setText("Pressed Help, press \"Replace\" to switch clothes already shown and unchecked");
		}
		
		if( e.getSource() == exit )
		{
			totalPrice.setText("Exiting...");
			this.dispose();
		}
		
		if( e.getSource() == total )
		{
			showTotalPrice(false);
		}
		
	}

	private void showTotalPrice(boolean selected)
	{
		//gets all the components of the container and stores in all items array
		Component[] allItems = topPanel.getComponents();
		double price = 0;
		
		//searches through the allItem list and puts the list into item
		for( Component item : allItems)
		{	
			//if true and clothesItem is checked
			if( selected && ((ClothesItem) item).isChecked()) 
				//price = price + realprice that is passed in from list
				price += ((ClothesItem) item).getPrice();
			//if false and clothes is not checked
			else if( !selected )
				//price = price + realprice
				price += ((ClothesItem) item).getPrice();
		}
		//if not selected 
		if( !selected )
			//price is equal to allItems in the list
			totalPrice.setText(String.format("Total Price: €%.2f", price));
		else
			//price  is equal to the selected price thats in the list of the components in top panel
			selectedPrice.setText(String.format("Selected Price: €%.2f", price));
	}
	
	/*public void showPane() 
	{
		setVisible(true);
	}
	
	public void hidePane() 
	{
		setVisible(false);
	}*/
	
	/* the internal mouse adapter class that makes a popup when you right mouse click at the position where it was clicked at */
	class MouseForPopup extends MouseAdapter
	{  
		JPopupMenu popupMenu;
		
		public MouseForPopup(ActionListener al)
		{
			popupMenu = new JPopupMenu();
			
			help = new JMenuItem("Help");
			help.addActionListener(al);
			popupMenu.add(help);
			
			total = new JMenuItem("Total Price");
			total.addActionListener(al);
			popupMenu.add(total);
			
			exit = new JMenuItem("Exit");
			exit.addActionListener(al);
			popupMenu.add(exit);
		}
		
	    public void mousePressed( MouseEvent e ) 
	    { 
	    	checkForTriggerEvent( e ); 
	    }  
	
	    public void mouseReleased( MouseEvent e )   
	    { 
	    	checkForTriggerEvent( e ); 
	    }  
	
	    private void checkForTriggerEvent( MouseEvent e )  
	    {  
	        if ( e.isPopupTrigger() )  
	           popupMenu.show( e.getComponent(), e.getX(), e.getY() );  
	    }  
  }  	

}
