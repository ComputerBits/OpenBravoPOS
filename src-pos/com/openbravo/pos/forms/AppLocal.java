//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.forms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.openbravo.beans.LocaleResources;

/**
 *
 * @author adrianromero
 */
public class AppLocal {
    
    public static final String APP_NAME = "OpenBravoPOS";
    public static final String APP_ID = "openbravopos";
    public static final String APP_VERSION;
    public static final String GIT_REVISION;
  
    // private static List<ResourceBundle> m_messages;
    private static LocaleResources m_resources;
    
    static {
        m_resources = new LocaleResources();
        m_resources.addBundleName("pos_messages");
        m_resources.addBundleName("erp_messages");
        Properties versionProps = new Properties();
        try {
			versionProps.load(AppLocal.class.getClassLoader().getResourceAsStream("version.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        APP_VERSION = versionProps.getProperty("APP_VERSION", "0.0.0");
        GIT_REVISION = versionProps.getProperty("GIT_REVISION", "r0");
    }
    
    /** Creates a new instance of AppLocal */
    private AppLocal() {
    }
    
    public static String getIntString(String sKey) {
        return m_resources.getString(sKey);
    }
    
    public static String getIntString(String sKey, Object ... sValues) {
        return m_resources.getString(sKey, sValues);
    }
    
    public static String getBaseTitle() {
    	return AppLocal.APP_NAME + " - " + AppLocal.APP_VERSION + " - " + AppLocal.GIT_REVISION;
    }
}
