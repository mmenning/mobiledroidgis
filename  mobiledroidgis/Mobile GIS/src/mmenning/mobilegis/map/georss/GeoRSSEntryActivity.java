/*
 * Copyright 2012 Mathias Menninghaus (mathias.menninghaus (at) googlemail (dot) com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmenning.mobilegis.map.georss;

import mmenning.mobilegis.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity to display and manipulate GeoRSSEntries. Requires a int in the given
 * Intent named {@link GeoRSSEntryActivity.GeoRSSEntryID} to know, information
 * from which GeoRSSEntry should be displayed.
 * 
 * @author Mathias Menninghaus
 * @version 15.10.2009
 * 
 */
public class GeoRSSEntryActivity extends Activity {

	private static final String DT = "GeoRSSEntryActivity";

	/*
	 * request
	 */
	/**
	 * Standard Request for GeoRSSEntryActivity to show all Entry informations
	 * identified by GeoRSSEntryID
	 */
	public static final int SHOW_GeoRSSEntry_INFO = 9081;
	/*
	 * response
	 */
	/**
	 * Response from GeoRSSEntryActivity to focus the Entry identified by
	 * GeoRSSEntryID in the Map
	 */
	public static final int SHOW_GeoRSSEntry_INMAP = 9082;
	/*
	 * intent extra identifiers
	 */
	/**
	 * ID of the GeoRSSEntry to be displayed by the GeoRSSEntryActivity or be
	 * focused by SHOW_GeoRSSEntry_INMAP response
	 */
	public static final String GeoRSSEntryID = "mmenning.mobilegis.map.georss/GeoRSSEntryID";

	/**
	 * LatitudeE6 to be focused by a SHOW_GeoRSSEntry_INMAP response
	 */
	public static final String LatitudeE6 = "mmenning.mobilegis.map.georss/LatitudeE6";
	/**
	 * LongitudeE6 to be focused by a SHOW_GeoRSSEntry_INMAP response
	 */
	public static final String LongitudeE6 = "mmenning.mobilegis.map.georss/LongitudeE6";

	private GeoRSSDB db;

	private int entryID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		db = new GeoRSSDB(this);
		db.open();

		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey(GeoRSSEntryID)) {
				entryID = (extras.getInt(GeoRSSEntryID));
			}
		}

		final GeoRSSEntry entry = db.getEntry(entryID);

		this.setContentView(R.layout.georssentryactivityview);
		Button originalItem = (Button) this
				.findViewById(R.id.georrssentryactivityview_showoriginalentry);
		originalItem.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				/*
				 * Start Browser to display original Item of the referring
				 * GeoRSSEntry
				 */
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(entry.link)));
			}

		});

		Button showInMap = (Button) this
				.findViewById(R.id.georrssentryactivityview_showinmap);
		showInMap.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*
				 * Go Strait back to the MainMap which called this activity.
				 */
				Intent data = new Intent();
				data.putExtra(LatitudeE6, entry.latE6);
				data.putExtra(LongitudeE6, entry.lonE6);
				data.putExtra(GeoRSSEntryID, entry.id);
				GeoRSSEntryActivity.this
						.setResult(SHOW_GeoRSSEntry_INMAP, data);
				GeoRSSEntryActivity.this.finish();
			}
		});

		TextView title = (TextView) this
				.findViewById(R.id.georssentryactivityview_title);
		title.setText(entry.title);
		TextView description = (TextView) this
				.findViewById(R.id.georssentryactivityview_description);
		description.setText(entry.description);
		TextView time = (TextView) this
				.findViewById(R.id.georssentryactivityview_time);
		time.setText(GeoRSSUtils.displayTimeFormat.format(entry.time));
		TextView location = (TextView) this
				.findViewById(R.id.georssentryactivityview_location);
		location.setText("" + (((float) entry.latE6) * 1.0f / 1E6) + " | "
				+ (((float) entry.lonE6) * 1.0f / 1E6));
		GeoRSSFeed dbFeed = db.getGeoRSS(entry.geoRSSID);
		TextView feed = (TextView) this
				.findViewById(R.id.georssentryactivityview_feed);
		feed.setText(dbFeed.title);

		db.close();

		this.setResult(RESULT_OK);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
