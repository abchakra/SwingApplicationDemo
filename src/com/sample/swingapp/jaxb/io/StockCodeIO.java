package com.sample.swingapp.jaxb.io;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class StockCodeIO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String jaxbXMLFile = System.getProperty("user.home")
			+ "\\StockApp\\Stock.xml";

	private static class StockCodeIOHolder {
		public static final StockCodeIO INSTANCE = new StockCodeIO();

	}

	public static StockCodeIO getInstance() {
		return StockCodeIOHolder.INSTANCE;
	}

	protected Object readResolve() {
		return getInstance();
	}

	public void StockCodeJavaToXML(StockCodes stockCodes) {
		try {
			File file = new File(jaxbXMLFile);
			File parent = file.getParentFile();

			if (!parent.exists() && !parent.mkdirs()) {
				throw new IllegalStateException("Couldn't create dir: "
						+ parent);
			}
			// create JAXB context and instantiate marshaller
			JAXBContext jaxbContext = JAXBContext.newInstance(StockCodes.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			// Write to System.out
			jaxbMarshaller.marshal(stockCodes, System.out);
			// Write to File
			jaxbMarshaller.marshal(stockCodes, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public StockCodes StockCodeXMLToJava() {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(StockCodes.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StockCodes stockCodeList = (StockCodes) jaxbUnmarshaller
					.unmarshal(new File(jaxbXMLFile));
			return stockCodeList;

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void loadStocks() {

	}

}