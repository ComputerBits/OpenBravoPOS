package com.openbravo.pos.inventory;

import net.sourceforge.barbecue.*;
import net.sourceforge.barbecue.linear.ean.*;
import javax.swing.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.image.*;
import java.text.*;

public class Barcodes  implements Printable{

	//16ths of inches
	private static float TOP_MARGIN = 5.7f*4.5f;
	private static float LEFT_MARGIN = 3f*4.5f;
	private static float VERT_SEP = 0*4.5f;
	private static float HORIZ_SEP = 1.5f*4.5f;
	private static float CODE_WIDTH = 24f*4.5f;
	private static float CODE_HEIGHT = 13.4f*4.5f;
	private static int BARCODE_HEIGHT = 25;

	private static int BARCODE_TRANS_YOFFSET = 33;
	private static int BARCODE_TRANS_XOFFSET = 2;
	private static int PRICE_XOFFSET = 8;
	private static int PRICE_YOFFSET = 30;
	private static int NAME_XOFFSET = 8;
	private static int NAME_YOFFSET = 16;

	private int xco = 1,yco = 2; //label coords to pbe printed to.
	private int copies = 1; 
	private String wholeCode = "1234567894444"; //the EAN13 barcode number
	private float price = 1.23f; //product price in pounds
	private String name = "Default Label"; //product name

	PrinterJob job;

	public Barcodes(){

	}
	public Barcodes(int xco,int yco,String name, float price, String barcode){

		this.xco = xco;
		this.yco = yco;
		this.name = name;
		this.price = price;
		this.wholeCode = barcode;

		job = PrinterJob.getPrinterJob();

		Paper p = new Paper();
		p.setSize(8.3d * 72d,11.7d * 72d);

		p.setImageableArea(0,0,8.3d * 72d,11.7d * 72d);
		PageFormat pf = new PageFormat();
		pf.setPaper(p);
		job.setPrintable(this,pf);
	}

	public void doPrint(int copies){
		this.copies = copies;
		try{
			if(job.printDialog()){
				job.print();
			}
		}
		catch(Exception e){};
	}

	public int print(Graphics g, PageFormat pf, int page){
		if(page == 0){

			Graphics2D g2d = (Graphics2D)g;
			boolean printingCopies = false;
			int copiesDone = 0;
			EAN13Barcode bc = null;

			try{
				bc = new EAN13Barcode(wholeCode);
				bc.setBarWidth(1);
				bc.setBarHeight(BARCODE_HEIGHT);
				bc.setFont(new Font("Arial",Font.PLAIN,12));
			}
			catch(Exception e){e.printStackTrace();}

			for(int n = 0; n < (int)(pf.getWidth()/(CODE_WIDTH + HORIZ_SEP));n++){
				for(int m = 0; m < (int)(pf.getHeight()/(CODE_HEIGHT + VERT_SEP)); m++){
					
					float currX = LEFT_MARGIN + n * CODE_WIDTH + n * HORIZ_SEP;
					float currY = TOP_MARGIN + m * CODE_HEIGHT + m * VERT_SEP;
					
					if((n == xco && m == yco) || (printingCopies && copiesDone < copies)){
						try{
							Graphics2D g2d2 = (Graphics2D)g2d.create(Math.round(currX),Math.round(currY),
							Math.round(CODE_WIDTH),Math.round(CODE_HEIGHT));
							//draw barcode
							float scale = 0.95f;
							g2d2.translate(BARCODE_TRANS_XOFFSET,BARCODE_TRANS_YOFFSET);
							g2d2.scale(scale,scale);
							bc.draw(g2d2,0,0); 
							g2d2.translate(-BARCODE_TRANS_XOFFSET,-(BARCODE_TRANS_YOFFSET));
							
							//draw price
							String priceString = "";
							if (price != 0) {
								NumberFormat nf = new DecimalFormat("\u00a3#0.00");
								priceString = nf.format(price);
							}
							g2d2.drawString(priceString,PRICE_XOFFSET,PRICE_YOFFSET);

							//draw Name
							g2d2.drawString(name,NAME_XOFFSET,NAME_YOFFSET);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						if(copies > 1){
							copiesDone++;
							printingCopies = true;
						}
					}							
				}
			}
			return PAGE_EXISTS;
		}
		else
		return NO_SUCH_PAGE; 
	}

	public void setLeftMargin(float m){
		LEFT_MARGIN = m;
	}

	public float getLeftMargin(){
		return LEFT_MARGIN;
	}
}
