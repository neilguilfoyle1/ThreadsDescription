/* Student Name: Neil Guilfoyle */
/* SID: C11722875 */
/* Class for saving each of the clothes items information from database
 * it extends the JPanel so it can be displayed when added to the main pane 
 * in the frame, It implements actionlistener to show description 
 * when the checkbox is checked */

package com.assignment.clothes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClothesItem extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox;
	private JLabel price, pictureItem, descriptionLabel;
	private double realPrice;
	private JScrollPane scrollDescription;
	
	public ClothesItem(String pic, String category, double price, String description)
	{
		super();
		
		checkBox = new JCheckBox(category);
		checkBox.addActionListener(this);
		checkBox.setBackground(Color.getHSBColor(225, 66, 100));
		
		
		this.realPrice = price;
		this.price = new JLabel("Price: "+String.format("€%.2f", price));
		
		pictureItem = new JLabel();

		setLayout(new BorderLayout());
		add(checkBox, BorderLayout.NORTH);
		add(pictureItem, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.add(this.price, BorderLayout.NORTH);
		bottom.setBackground(Color.getHSBColor(225, 66, 100));
		
		descriptionLabel = new JLabel(description);
		scrollDescription = new JScrollPane(descriptionLabel);
		
		//set size for the scrollPane  
        scrollDescription.setPreferredSize(new Dimension(100,40));
        scrollDescription.setVisible(false);
        
		
		bottom.add(scrollDescription, BorderLayout.SOUTH);
		
		add(bottom, BorderLayout.SOUTH);
		
			
		try
		{
			pictureItem.setIcon(new ImageIcon(ClothesItem.class.getResource("/"+category+"/"+pic)));
		}
		catch (Exception ex)
		{
			//System.out.println("Testing execution here);
			//when image is not found use default image
			pictureItem.setIcon(new ImageIcon(ClothesItem.class.getResource("js_testing_icon.png")));
			
		}
	
	}
	
	
	public double getPrice()
	{
		return realPrice;
	}
	
	public String getCategory()
	{
		return checkBox.getText();
	}

	public boolean isChecked()
	{
		return checkBox.isSelected();
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (checkBox.isSelected())
		{
			scrollDescription.setVisible(true);
			revalidate();
			
		}
		else
		{
			scrollDescription.setVisible(false);
			revalidate();
		}
	}
	
}
