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

package com.openbravo.pos.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author adrianromero
 */
public class LabelIcon extends JLabel implements Icon {
    
    private int iconwidth;
    private int iconheight;
    
    public LabelIcon(int width, int height) {
        iconwidth = width;
        iconheight = height;
    }

    public BufferedImage getImage(int mywidth, int myheight) {
        
        BufferedImage imgtext = new BufferedImage(mywidth, myheight,  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgtext.createGraphics();
        
        setBounds(0, 0, mywidth, myheight);          
        paint(g2d);
        g2d.dispose();
        
        return imgtext;         
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        
        setBounds(0, 0, iconwidth, iconheight);
        g.translate(x, y);
        paint(g);
        g.translate(-x, -y);
    }

    public int getIconWidth() {
        return iconwidth;
    }

    public int getIconHeight() {
        return iconheight;
    }

}
