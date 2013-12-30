package com.ursus.mhdwatch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ursus.mhdwatch.item.Item;
import com.ursus.mhdwatch.item.SeparatedAdapter;

public class MainActivity extends Activity {
	

	private static final int CONNECTION_FAILED = 0;

	private List<PointOfInterest> myPOIs = new ArrayList<PointOfInterest>();

	private LocationManager locationManager;
	private DownloadAndParseTask downloadAndParseTask;

	private ProgressDialog dialog;

	private Button refreshButton;
	private ListView listView;
	private TextView locationNameTextView;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Hello github

		init();
		initRoutes();
		getCurrentLocation();

	}

	private PointOfInterest findNearestPOI(Location currentLocation) {

		PointOfInterest nearestPOI = myPOIs.get(0);
		for (PointOfInterest p : myPOIs) {

			if (p.getLocation().distanceTo(currentLocation) < nearestPOI.getLocation().distanceTo(currentLocation)) {
				nearestPOI = p;
			}
		}
		return nearestPOI;
	}

	public void onContentReceived(ArrayList<Item> content) {

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		if (content != null) {
			SeparatedAdapter adapter = new SeparatedAdapter(getApplicationContext(), R.id.headerName, content);
			listView.setAdapter(adapter);
		} else {
			showDialog(CONNECTION_FAILED);
		}
	}

	private void init() {

		locationNameTextView = (TextView) findViewById(R.id.locationNameTextView);
		refreshButton = (Button) findViewById(R.id.refreshButton);
		refreshButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getCurrentLocation();
			}

		});

		listView = (ListView) findViewById(R.id.listView1);

		dialog = new ProgressDialog(this);
		dialog.setTitle("Please,wait");
		dialog.setMessage("Processing...");
	}

	private void initRoutes() {

		Location homeLocation = new Location("network");
		homeLocation.setLatitude(49.000186);
		homeLocation.setLongitude(21.274804);
		String[] homeUrls = new String[] {
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/8/smer/Sidlisko-III/zastavka/Sibirska/943718550548.html",
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/38/smer/Sidlisko-III/zastavka/Sibirska/734003350551.html",
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/N3/smer/Sebastova/zastavka/Sibirska/943718550549.html" };
		PointOfInterest home = new PointOfInterest("Doma", homeLocation, homeUrls);

		Location gymLocation = new Location("network");
		gymLocation.setLatitude(48.987057);
		gymLocation.setLongitude(21.265739);
		String[] gymUrls = new String[] {
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/8/smer/Sibirska/zastavka/Martina-Benku/419430482954.html",
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/38/smer/Sibirska/zastavka/Martina-Benku/209715282945.html" };
		PointOfInterest gym = new PointOfInterest("Posilka", gymLocation, gymUrls);

		Location stationLocation = new Location("network");
		stationLocation.setLatitude(48.984861);
		stationLocation.setLongitude(21.250193);
		String[] stationUrls = new String[] {
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/8/smer/Sibirska/zastavka/Zeleznicna-stanica/943718611979.html",
				"http://m.imhd.zoznam.sk/po/M-cestovny-poriadok/linka/38/smer/Sibirska/zastavka/Zeleznicna-stanica/419430611980.html" };
		PointOfInterest station = new PointOfInterest("Stanica", stationLocation, stationUrls);

		myPOIs.add(home);
		myPOIs.add(gym);
		myPOIs.add(station);

	}

	private void getCurrentLocation() {

		dialog.show();
		downloadAndParseTask = new DownloadAndParseTask(this);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {

					locationManager.removeUpdates(this);

					PointOfInterest nearestPOI = findNearestPOI(location);
					locationNameTextView.setText(nearestPOI.getName());

					downloadAndParseTask.execute(nearestPOI.getMHDUrls());
				}
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub

			}

		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return new AlertDialog.Builder(this).setTitle("Connection failed")
				.setMessage("Please make sure you are connected to the internet")
				.setNegativeButton("Quit", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setPositiveButton("Refresh", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						getCurrentLocation();
					}
				}).create();
	}

}
