package com.sample.swingapp.jaxb.io;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StockCode {
	private String accountID;
	private String stockID;

	public String getAccountID() {
		return accountID;
	}

	@XmlElement
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getStockID() {
		return stockID;
	}

	@XmlElement
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
}
