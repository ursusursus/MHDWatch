package com.ursus.mhdwatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

import com.ursus.mhdwatch.helpers.ParserHelper;
import com.ursus.mhdwatch.helpers.TimeHelper;
import com.ursus.mhdwatch.item.HeaderItem;
import com.ursus.mhdwatch.item.Item;
import com.ursus.mhdwatch.item.ListItem;

public class DownloadAndParseTask extends AsyncTask<String, Void, ArrayList<Item>> {

	
	private ParserHelper parserHelper;
	private TimeHelper timeHelper;
	private MainActivity mainActivity;
	private Exception e = null;
	private ArrayList<Item> list;

	
	public DownloadAndParseTask(MainActivity mainActivity) {

		this.mainActivity = mainActivity;
		this.parserHelper = ParserHelper.getInstance();
		this.timeHelper = TimeHelper.getInstance();
		
	}

	@Override
	protected ArrayList<Item> doInBackground(String... urls) {

		list = new ArrayList<Item>();
		this.e = null;

		for (String url : urls) {
			try {

				String downloadedContent = downloadAndParse(url);
				List<Time> parsedTimes = parserHelper.parse(downloadedContent);

				list.add(new HeaderItem(parserHelper.parseURL(url)));
				addOnlyRemainingTimes(parsedTimes);

			} catch (IOException e) {
				this.e = e;
			}
		}

		return list;
	}

	private String downloadAndParse(String url) throws IOException {

		String html;
		html = Jsoup.connect(url).get().html();
		Document doc = Jsoup.parse(html);
		Elements headings = doc.getElementsByTag("p");
		return headings.text();
		
	}

	private void addOnlyRemainingTimes(List<Time> parsedTimes) {

		Time currentTime = timeHelper.getCurrentTime();

		int counter = 0;
		for (int i = 0; i < parsedTimes.size() && counter < 3; i++) {
			Time t = parsedTimes.get(i);
			if (t.isAfter(currentTime)) {
				counter++;
				list.add(new ListItem(t.toString(), "o " + currentTime.calculateDifference(t)));
			}
		}

		if (counter == 0) { 
			list.add(new ListItem("Nepremava", " - "));
		}
		
	}

	@Override
	protected void onPostExecute(ArrayList<Item> result) {

		if (e == null) {
			mainActivity.onContentReceived(result);
		} else {
			mainActivity.onContentReceived(null);
		}
		
	}

}
