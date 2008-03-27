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

package com.openbravo.pos.mant;

import javax.swing.ListCellRenderer;
import com.openbravo.data.gui.ListCellRendererBasic;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.format.Formats;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.pos.panels.*;

/**
 *
 * @author adrianromero
 */
public class JPanelFloors extends JPanelTable {
    
    private TableDefinition tfloors;
    private FloorsEditor jeditor;
    
    /** Creates a new instance of JPanelFloors */
    public JPanelFloors() {
    }
    
    protected void init() {
        tfloors = new TableDefinition(app.getSession(),
            "FLOORS"
            , new String[] {"ID", "NAME", "IMAGE"}
            , new String[] {"ID", AppLocal.getIntString("Label.Name"), "IMAGE"}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.IMAGE}
            , new Formats[] {Formats.NULL, Formats.STRING}
            , new int[] {0}
        );  
        jeditor = new FloorsEditor(dirty); 
    }
    
    public ListProvider getListProvider() {
        return new ListProviderCreator(tfloors);
    }
    
    public Vectorer getVectorer() {
        return tfloors.getVectorerBasic(new int[]{1});
    }
    
    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(tfloors.getRenderStringBasic(new int[]{1}));
    }
    
    public SaveProvider getSaveProvider() {
        return new SaveProvider(tfloors);      
    }
    
    public EditorRecord getEditor() {
        return jeditor;
    }
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Floors");
    }     
}
