//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007 Openbravo, S.L.
//    http://sourceforge.net/projects/openbravopos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package com.openbravo.pos.forms;

import java.sql.SQLException;

/**
 *
 * @author adrianromero
 */
public class BeanFactoryData implements BeanFactoryApp {
    
    private BeanFactoryApp bf;
    
    /** Creates a new instance of BeanFactoryData */
    public BeanFactoryData() {
    }
    
    public void init(AppView app) throws BeanFactoryException {  
        
        try {
            
            String sfactoryname = this.getClass().getName();
            if (sfactoryname.endsWith("Create")) {
                sfactoryname = sfactoryname.substring(0, sfactoryname.length() - 6);
            }
            String sdbmanager = app.getSession().getConnection().getMetaData().getDatabaseProductName();            
            
            if ("HSQL Database Engine".equals(sdbmanager)) {
                bf = (BeanFactoryApp) Class.forName(sfactoryname + "HSQLDB").newInstance();
            } else if ("MySQL".equals(sdbmanager)) {
                bf = (BeanFactoryApp) Class.forName(sfactoryname + "MySQL").newInstance();
            } else if ("PostgreSQL".equals(sdbmanager)) {
                bf = (BeanFactoryApp) Class.forName(sfactoryname + "PostgreSQL").newInstance();
            } else if ("Oracle".equals(sdbmanager)) {
                bf = (BeanFactoryApp) Class.forName(sfactoryname + "Oracle").newInstance();
            } else {
                throw new BeanFactoryException(AppLocal.getIntString("message.databasenotsupported", sdbmanager));
            }                     
            bf.init(app);                     
        } catch (SQLException ex) {
            throw new BeanFactoryException(ex);
        }  catch (Exception ex) {
            throw new BeanFactoryException(ex);
        }
    }   
    
    public Object getBean() {
        return bf.getBean();
    }      
}
