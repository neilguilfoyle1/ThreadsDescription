/* Student Name: Neil Guilfoyle */
/* SID: C11722875 */
/* The class that gets all data from the database and
 * holds it in one array list
 * when item is requested by category it searches through list to find 
 * first matching, if none is found it reloads that category from database
 * and searches again */

package com.assignment.clothes;

import java.sql.*;
import java.util.ArrayList;

public class Database 
{	
	
	ArrayList<ClothesItem> allItems;
	
	public Database()
	{
		allItems = new ArrayList<ClothesItem>(30);	// 30 items - 6 of each
//		getItemsByCategory("Trousers");
//		getItemsByCategory("T-Shirts");
//		getItemsByCategory("Jackets");
//		getItemsByCategory("Shoes");
//		getItemsByCategory("Sweaters");
		
	}
	
	public void getItemsByCategory(String category)
	{
		ResultSet rs;
		Connection conn = null;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String database = 
              "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=src/clothes.accdb;";
            conn = DriverManager.getConnection(database, "", "");
            
            Statement s = conn.createStatement();
            
            rs = s.executeQuery("SELECT * FROM clothes WHERE category = '"+category+"'");
                       
            while ( rs.next() ) // this will step through the data row-by-row
    		{
            	
    			allItems.add(new ClothesItem(rs.getString("picture"), rs.getString("category"), rs.getDouble("price"), rs.getString("description")) );
    		}

			conn.close();
		}
		catch( ClassNotFoundException e)
		{
			System.out.println("Not the right class");
		} 
		catch (SQLException e) 
		{
			System.out.println("SQL error \n Message:"+e.getMessage());
		}
		catch (NullPointerException e) 
		{
			System.out.println("Null at object.\n Message:"+e.getCause());
			e.printStackTrace();
		}
	} 
	
	
	// method returns the clothes item for the category passed in as a string
	
	public ClothesItem getItem(String category)
	{	//searches through allitems array list
		for(int i = 0; i < allItems.size(); i++ )
		{	//if the category passed in matches the item category of the current element which is i i=0 i=1 
			//
			if( allItems.get(i).getCategory().equals(category))
			{
				//take the item matching, from the allitems list and save it to local variable temp
				ClothesItem temp = allItems.get(i);
				//remove it from the list because it is now used up and will be displayed in GUI
				allItems.remove(temp);
				//return it to the place where getItem() was called (always in GUI in our case)
				return temp;
			}
			//keep looping if category doesn't match
			
		}
		//if i am here we never returned an item, 
		//that means that there were no items in the allitems list with category string searched for
		
		//therefore we call getItemsByCategory(category) which will reload all items of the required category
		//back into the list
		getItemsByCategory(category);
		
		//we call this function again to now find the item( and we know it will because we reloaded them in line above)
		//it will return it here and line below will return it back to GUI to add to panel
		return getItem(category);
	}

}
