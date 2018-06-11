package com.openbravo.pos.inventory;

import net.sourceforge.barbecue.*;
import net.sourceforge.barbecue.linear.ean.*;

public class EANCode extends EAN13Barcode{

    public EANCode(String data) throws BarcodeException {
    	super(data);
    }
     
    
    public Module checksum(){
    	return calculateChecksum();
    }
    
    
}