//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2008 Openbravo, S.L.
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

package com.openbravo.pos.sales;

import com.openbravo.pos.ticket.TaxInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianromero
 */
public class TaxesLogicElement {
    
    private TaxInfo tax;
    private List<TaxesLogicElement> taxsons;
    
    public TaxesLogicElement(TaxInfo tax) {
        this.tax = tax;
        this.taxsons = new ArrayList<TaxesLogicElement>();
    }
    
    public TaxInfo getTax() {
        return tax;
    }
    
    public List<TaxesLogicElement> getSons() {
        return taxsons;
    }
}
