package com.sample.swingapp.jaxb.io;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StockCodes {

	private List<StockCode> stockCodeList;
	private String stockCodeID;

	public List<StockCode> getStockCodeList() {
		return stockCodeList;
	}

	@XmlElement(name = "StockEntry")
	public void setStockCodeList(List<StockCode> stockCodeList) {
		this.stockCodeList = stockCodeList;
	}

	public String getStockCodeID() {
		return stockCodeID;
	}

	@XmlAttribute
	public void setStockCodeID(String stockCodeID) {
		this.stockCodeID = stockCodeID;
	}

}
