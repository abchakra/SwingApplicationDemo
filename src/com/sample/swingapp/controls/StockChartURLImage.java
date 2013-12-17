package com.sample.swingapp.controls;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class StockChartURLImage {

	public static ImageIcon getStockChartURLImage(String symbol) {
		ImageIcon imageIcon = null;
		try {

			String path = "http://chart.finance.yahoo.com/z?s=" + symbol
					+ "&t=6m&q=l";
			// System.out.println("Get Image from " + path);
			URL url = new URL(path);
			BufferedImage image = ImageIO.read(url);
			// System.out.println("Load image into frame...");
			imageIcon = new ImageIcon(image);
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return imageIcon;
	}

	public static String searchStockCodeByCode(String symbol) {
		String searchURL = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="
				+ symbol + "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";
		return searchURL;
	}
}
