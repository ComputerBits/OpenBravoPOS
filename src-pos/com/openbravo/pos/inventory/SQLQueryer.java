package com.openbravo.pos.inventory;

import java.sql.*;
import javax.swing.*;

import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppViewConnection;

public class SQLQueryer {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public SQLQueryer(Session s, String query) {
		conn = null;
		stmt = null;
		rs = null;

		try {
			if (s == null) {
				AppConfig config = new AppConfig(new String[0]);
				config.load();
				s = AppViewConnection.createSession(config);
			}

			stmt= s.getConnection().createStatement();
			
			rs= stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			int length = rs.getRow();
			rs.beforeFirst();
   
			if(length > 1){
				JOptionPane.showMessageDialog(null,"ERROR:SQL query yielded more than one result");
			}
			rs.next();			
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public String getAttribute(String attr) throws SQLException {
		return rs.getString(attr);
	}
}
