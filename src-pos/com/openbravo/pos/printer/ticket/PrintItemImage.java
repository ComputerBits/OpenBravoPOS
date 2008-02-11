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

package com.openbravo.pos.printer.ticket;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.openbravo.pos.printer.screen.*;

public class PrintItemImage implements PrintItem {
    
    private BufferedImage image;
    
    /** Creates a new instance of PrintItemImage */
    public PrintItemImage(BufferedImage image) {
        this.image = image;
    }
    
    public void draw(Graphics2D g, int x, int y, int width) {
        g.drawImage(image,  x + (width - image.getWidth()) / 2,  y, image.getWidth(), image.getHeight(), null);
    }
    
    public int getHeight() {
        return image.getHeight();
    }
    
}
