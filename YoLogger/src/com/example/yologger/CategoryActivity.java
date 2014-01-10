package com.example.yologger;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yologger.Content.Category;
import com.example.yologger.Home.StatisticsGraph;

public class CategoryActivity extends Activity {

	private StatisticsGraph statisticsGraph;
	private Category c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		String name = (String) getIntent().getSerializableExtra("category");
		c = Content.categories.get(name); 
		TextView weekTime = (TextView) findViewById(R.id.time_this_week);
		TextView goalTime = (TextView) findViewById(R.id.goal_this_week);
		TextView cName = (TextView) findViewById(R.id.category_name_label);
		cName.setText(c.name);
		ImageView imageView = (ImageView) findViewById(R.id.category_icon);
		imageView.setImageResource(c.resId);
		weekTime.setText(Home.formatDuration(Content.getCategoryTotal(c)));
		TextView percentage = (TextView) findViewById(R.id.percentage_text);
		
		LinearLayout chart = (LinearLayout) findViewById(R.id.chart_layout);
		statisticsGraph = new StatisticsGraph(this, 20, 20, 330, 330, false);
		chart.addView(statisticsGraph);
		if(c.goal > 0){
			goalTime.setText(Home.formatDuration(c.goal));
			refreshStatistics();
		} else {
			RelativeLayout linear = (RelativeLayout) findViewById(R.id.graph_layout);
			linear.setVisibility(View.GONE);
			LinearLayout goalRow = (LinearLayout) findViewById(R.id.goal_row);
			goalRow.setVisibility(View.GONE);
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
	
	public void setGoal(View v){
		TimePickerDialog mTimePicker;
        mTimePicker = new CategoryGoalPickerDialog(this, null, c.goal / 60, c.goal % 60);
        mTimePicker.setTitle("Select Goal");
        mTimePicker.show();
	}
	
	public void addActivity(View v) {
		Intent intent = new Intent(this, AddEventActivity.class);
		intent.putExtra("category", c.name);
		startActivity(intent);
	}
	
	private boolean skipResume = true;

	@Override
	public void onResume() {
		super.onResume();
		if (!skipResume) {
			refreshStatistics();
			TextView weekTime = (TextView) findViewById(R.id.time_this_week);
			weekTime.setText(Home.formatDuration(Content.getCategoryTotal(c)));
		} else
			skipResume = false;
	}
	
	public void refreshStatistics(){
		float part = getCategoryCompletion(c);
		statisticsGraph.degrees = new Float[]{part, 360 -part};
		TextView percentage = (TextView) findViewById(R.id.percentage_text);
		String p = (int)(part / 360 * 100) + "%";
		percentage.setText(p);
		
		statisticsGraph.chartColors = new int[]{
				Color.parseColor("#00AA00"),
				Color.parseColor("#FFFFFF")
				};
		statisticsGraph.invalidate();
	}
	
	public void changeGoal(int hours,int minutes){
		LinearLayout goalRow = (LinearLayout) findViewById(R.id.goal_row);
		
		TextView goalDisplay = (TextView) findViewById(R.id.goal_this_week);
		
		c.goal = minutes + hours * 60;
		
		String d = Home.formatDuration(minutes + hours * 60);
		RelativeLayout linear = (RelativeLayout) findViewById(R.id.graph_layout);
		if(c.goal <= 0){
			linear.setVisibility(View.GONE);
			goalRow.setVisibility(View.GONE);
		} else {
			goalRow.setVisibility(View.VISIBLE);
			linear.setVisibility(View.VISIBLE);
			goalDisplay.setText(d);
		}
		
		refreshStatistics();
	}
	
	 public class CategoryGoalPickerDialog extends TimePickerDialog {

		    public int minute;
		    public int hour;

			public CategoryGoalPickerDialog(Context context, int theme,
		            OnTimeSetListener callBack, int hour, int minute) {
		        super(context, theme, callBack, hour, minute, true);
		        updateTitle(hour, minute);
		    }

		    public CategoryGoalPickerDialog(Context context, OnTimeSetListener callBack,
		            int hour, int minute) {
		        super(context, callBack, hour, minute, true);
		        updateTitle(hour, minute);
		    }

		    @Override
		    public void onTimeChanged(TimePicker view, int hour, int minute) {
		        super.onTimeChanged(view, hour, minute);
		        updateTitle(hour, minute);
		        this.hour = hour;
		        this.minute = minute;
		    }
		    
		    @Override
		    protected void onStop() {
		    	changeGoal(hour, minute);
		    };

		    public void updateTitle(int hour, int minute) {
		        setTitle("Goal: " + Home.formatDuration(minute + hour * 60));
		    }

		 
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
