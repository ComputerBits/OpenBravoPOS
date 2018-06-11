package com.openbravo.pos.inventory;

import java.sql.*;
import javax.swing.*;

public class SQLQueryer {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public SQLQueryer(String query){
		conn = null;
		stmt = null;
		rs = null;
		
		try{
			conn = DriverManager.getConnection("jdbc:mysql://zeus:3306/sales", 
				      	"sales","sa1es");
            stmt= conn.createStatement();
            
            rs= stmt.executeQuery(query);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            
            rs.last();
            int length = rs.getRow();
            rs.beforeFirst();
   
            if(length > 1){
            	JOptionPane.showMessageDialog(null,"ERROR:SQL query yielded more than one result");
            }
            rs.next();
            /*
            while(rs.next()){
	            for(int n= 1 ; n <= length; n++){
	            	System.out.print(rs.getString(n));
	            }	
	      		System.out.println();		
            }	
            */
            

			
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
	public String getAttribute(String attr) throws SQLException{
		return rs.getString(attr);
		
	}
	
}
