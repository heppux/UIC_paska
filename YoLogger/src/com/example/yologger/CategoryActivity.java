package com.example.yologger;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yologger.Content.Category;
import com.example.yologger.Home.StatisticsGraph;

public class CategoryActivity extends Activity {

	private StatisticsGraph statisticsGraph;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		Category c = (Category) getIntent().getSerializableExtra("category");
		TextView weekTime = (TextView) findViewById(R.id.time_this_week);
		TextView goalTime = (TextView) findViewById(R.id.goal_this_week);
		TextView cName = (TextView) findViewById(R.id.category_name_label);
		cName.setText(c.name);
		ImageView imageView = (ImageView) findViewById(R.id.category_icon);
		imageView.setImageResource(c.resId);
		weekTime.setText(Home.formatDuration(Content.getCategoryTotal(c)));
		TextView percentage = (TextView) findViewById(R.id.percentage_text);
		if(c.goal != -1){
			goalTime.setText(Home.formatDuration(c.goal));
			float part = getCategoryCompletion(c);
			statisticsGraph = new StatisticsGraph(this, 20, 20, 240, 240, false);
			statisticsGraph.degrees = new Float[]{part, 360 -part};
			percentage = (TextView) findViewById(R.id.percentage_text);
			percentage.setText((int)(part / 360 * 100) + "%");
			statisticsGraph.chartColors = new int[]{
					Color.parseColor("#CCFFCC"),
					Color.parseColor("#FF9898")
					};
			LinearLayout linear = (LinearLayout) findViewById(R.id.graph_layout);
			linear.addView(statisticsGraph);
		} else {
			goalTime.setText("");
			TextView goalLabel = (TextView) findViewById(R.id.goal_this_week_label);
			goalLabel.setText("No goal set.");
			percentage.setText("");
		}
		
			
		// Show the Up button in the action bar.
		setupActionBar();
	}

	private float getCategoryCompletion(Category c) {
		int total = Content.getCategoryTotal(c);
		float part = (float) total / c.goal * 360;
		return part > 360 ? 360 : part;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
