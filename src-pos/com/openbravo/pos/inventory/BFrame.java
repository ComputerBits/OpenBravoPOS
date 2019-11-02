package com.openbravo.pos.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.sourceforge.barbecue.*;
import net.sourceforge.barbecue.linear.ean.*;

public class BFrame extends JFrame {

	private JButton butt_print, butt_genRandom, butt_lookup;
	private JTextField text_x, text_y, text_barcode, text_leftMargin, text_tax;
	private JLabel label_x, label_y, label_barcode, label_name, label_price, label_tax, label_tax_pc;
	private JLabel label_name_val, label_price_val, label_leftMargin, label_copies;
	private JSpinner spinner_copies;
	private SpinnerNumberModel snm = new SpinnerNumberModel(1,1,65,1);
	private ProductsEditor pe = null;

	public BFrame(ProductsEditor p){
		this();
		pe = p;
		//init vals
		if(pe != null && !"".equals(pe.getCode())){
			text_barcode.setText(pe.getCode());
			lookupDetails();
		}
	}

	public BFrame() {
		super("Barcode Printer");

		this.setPreferredSize(new Dimension(300,200));

		butt_print = new JButton("Print");
		butt_genRandom = new JButton("Generate Barcode");
		butt_lookup = new JButton("Lookup");

		text_x = new JTextField();
		text_y = new JTextField();
		text_barcode = new JTextField();
		text_leftMargin = new JTextField();
		text_tax = new JTextField("20");

		spinner_copies = new JSpinner(snm);

		label_x = new JLabel("X coord:");
		label_y = new JLabel("Y coord:");
		label_barcode = new JLabel("Barcode:");
		label_name = new JLabel("Product name:");
		label_price = new JLabel("Product price:");
		label_name_val = new JLabel();
		label_price_val = new JLabel();
		label_leftMargin = new JLabel("Left print margin:");
		label_copies = new JLabel("Copies:");
		label_tax = new JLabel("Tax");
		label_tax_pc = new JLabel("%");

		Barcodes temp = new Barcodes();

		text_leftMargin.setText(String.valueOf(temp.getLeftMargin()));

		text_barcode.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent ke){
			}
			public void keyReleased(KeyEvent ke){
			}
			public void keyTyped(KeyEvent ke){
				if(text_barcode.getText().length() == 13){
					lookupDetails();
				}
			}
		});

		this.setLayout(new GridLayout(8,3));

		this.add(label_x);
		this.add(text_x);
		this.add(new JLabel());

		this.add(label_y);
		this.add(text_y);
		this.add(new JLabel());

		this.add(label_barcode);
		this.add(text_barcode);
		this.add(butt_genRandom);

		this.add(label_name);
		this.add(label_name_val);
		this.add(butt_lookup);

		this.add(label_price);
		this.add(label_price_val);
		this.add(new JLabel());

		this.add(label_tax);
		this.add(text_tax);
		this.add(label_tax_pc);

		this.add(label_copies);
		this.add(spinner_copies);
		this.add(new JLabel());

		this.add(label_leftMargin);
		this.add(text_leftMargin);
		this.add(butt_print);

		butt_genRandom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String newCode = generateBarcode();
				if(pe != null){
					pe.setBarcodeAndRef(newCode);
					setVisible(false);
				}
				text_barcode.setText(newCode);
			}
		});
		butt_lookup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				lookupDetails();
			}
		});
		butt_print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int xco=0, yco=0, barcode=0;
				String usableBarcode = text_barcode.getText();
				try{
					xco = new Integer(text_x.getText());
					yco = new Integer(text_y.getText());
					new Long(text_barcode.getText());

				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null,"Invalid Number");
					return;
				}
				if(xco < 1 || xco > 5){
					JOptionPane.showMessageDialog(null,"X coord out of range (1-5)");
					return;
				}
				if(yco < 1 || yco > 13){
					JOptionPane.showMessageDialog(null,"y coord out of range (1-13)");
					return;
				}
				if(usableBarcode.length() == 13){
					usableBarcode = usableBarcode.substring(0,12);
				}
				if(label_name_val.getText().length() == 0){
					JOptionPane.showMessageDialog(null,"Please enter a barcode and use the 'Lookup' button before printing");
					return;
				}
				Barcodes b = new Barcodes(xco-1,yco-1,label_name_val.getText(),new Float(label_price_val.getText()),usableBarcode);
				b.setLeftMargin(new Float(text_leftMargin.getText()));
				b.doPrint(snm.getNumber().intValue());
			}
		});

		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private String generateBarcode(){
		java.util.Random rand = new java.util.Random();
		String wholeCode = "333";
		for (int n = 0; n < 9; n++){
			wholeCode += (new Integer(rand.nextInt(9))).toString();
		}
		try{
			EANCode b = new EANCode(wholeCode);
			wholeCode += b.checksum().getSymbol();
		}catch(Exception e){
			e.printStackTrace();
		}

		return wholeCode;
	}
	private void lookupDetails(){

		String REGEX = "0{0,2}"+text_barcode.getText()+"{0,1}";
		String query = "SELECT * FROM products, barcode_table WHERE products.code REGEXP '"+REGEX+"' OR (barcode_table.pid=products.id and barcode_table.code REGEXP '"+REGEX+"') GROUP BY products.id";
		SQLQueryer sql = new SQLQueryer(pe.session, query);
		try{
			label_name_val.setText(sql.getAttribute("NAME"));
			float price = (new Float(sql.getAttribute("PRICESELL"))) * (1+(new Float(text_tax.getText()))/100);
			price = ((float)((int)(price*100)))/100;
			if(price < 0.01){
				label_price_val.setText("-");
			}
			else{
				label_price_val.setText(String.valueOf(price));
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"ERROR:Barcode not found in database\nHas it been added yet?");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"ERROR:Probably tax number formatting");
		}
	}

	public static void main(String[] args)	{
		new BFrame();
	}
}
