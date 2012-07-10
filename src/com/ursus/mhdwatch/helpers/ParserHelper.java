package com.ursus.mhdwatch.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ursus.mhdwatch.Time;

public class ParserHelper {

	
	private String regexWhole = ".*?pondelok - piatok(.*?)sobota(.*?)nede¾a, sviatok(.*?)Platí.*?";
	private String regexTime = "\\d\\d:\\d\\d";
	private String regexURL = "http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/(.*?)/smer/(.*?)/zastavka/(.*?)/(.*?)";
	private static ParserHelper instance;

	
	private ParserHelper() {

	}

	public static ParserHelper getInstance() {
		
		if (instance == null) {
			instance = new ParserHelper();
		}
		return instance;
	}

	public List<Time> parse(String input) {

		List<Time> list = new ArrayList<Time>();

		// ci parsovat tyzden, sobotu alebo nedelu
		int groupIndex = TimeHelper.getInstance().getDayType();

		Pattern pW = Pattern.compile(regexWhole);
		Matcher mW = pW.matcher(input);

		if (mW.matches()) {

			Pattern pT = Pattern.compile(regexTime);
			Matcher mT = pT.matcher(mW.group(groupIndex));

			while (mT.find()) {
				list.add(new Time(mT.group(0).substring(0, 2), mT.group(0).substring(3, 5)));
			}

			return list;
		} else {
			return null;
		}
	}

	public String parseURL(String url) {

		Pattern pattern = Pattern.compile(regexURL);
		Matcher matcher = pattern.matcher(url);

		if (matcher.matches()) {
			return "Linka " + matcher.group(1) + " - " + matcher.group(3) + " > " + matcher.group(2);
		} else {
			return null;
		}
	}
}
