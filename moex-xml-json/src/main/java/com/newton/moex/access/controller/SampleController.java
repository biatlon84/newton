package com.newton.moex.access.controller;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.newton.moex.access.pojo.Resp;

@RestController
public class SampleController {
	String request = "";
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp getLast(@RequestParam(name = "SECID", required = false, defaultValue = "MOEX") String ticker) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document res = null;
		Resp resp = new Resp(ticker, "0", false);
		String last = null;
		String secid = null;

		request = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities/" + ticker
				+ ".xml?iss.meta=off";
		try {
			builder = factory.newDocumentBuilder();

			URL url = new URL(request);

			URLConnection con = url.openConnection();

			con.setConnectTimeout(1000);

			res = builder.parse(con.getInputStream());

			last = res.getDocumentElement().getElementsByTagName("row").item(1).getAttributes().getNamedItem("LAST")
					.getNodeValue();

			secid = res.getDocumentElement().getElementsByTagName("row").item(1).getAttributes().getNamedItem("SECID")
					.getNodeValue();

			resp = new Resp(secid, last, true);
		} catch (SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {

			LOGGER.info("{} Couldn't be reached", "iss.moex.com");

		}
		// System.out.println(res.getDocumentElement().getElementsByTagName("row").item(1).getAttributes()
		// .getNamedItem("LAST").getNodeValue());

		return resp;
	}
}
