package com.example.yologger;

import java.util.Date;

import com.example.yologger.Content.Event;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class AddEventActivity extends Activity {

	private int durationMinutes = 15;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	public void submitEvent(View view){
		TextView t = (TextView) findViewById(R.id.event_text);
		if(durationMinutes == 0){
			TextView hourInput = (TextView) findViewById(R.id.hourInput);
			TextView minuteInput = (TextView) findViewById(R.id.minuteInput);
			int hours = !hourInput.getText().toString().isEmpty() ? Integer.parseInt(hourInput.getText().toString()) : 0;
			int mins = !minuteInput.getText().toString().isEmpty() ? Integer.parseInt(minuteInput.getText().toString()) : 0;
			durationMinutes = hours * 60 + mins;
		}
		String desc = t.getText().toString();
		if(durationMinutes == 0)
			displayAlert("Event duration not set!");
		else if(desc.isEmpty())
			displayAlert("Event description not set!");
		else {
		Content.events.add(new Event(desc, new Date(), durationMinutes, "dummy"));
		
		finish();}
	}
	
	private void displayAlert(String message) {
		AlertDialog.Builder b = new AlertDialog.Builder(
				this);
		b.setTitle("Whoops!");
		b.setMessage(message);
		b.setCancelable(true)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				dialog.cancel();
			}
		  });
		AlertDialog d = b.create();
		d.show();
	}

	private boolean customDurationVisible = false;
	public void durationValueChange(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		if(checked)
		switch (view.getId()) {
		case R.id.radio_quarter_hour:
			this.durationMinutes = 15;
			if(customDurationVisible)
				hideCustomDuration();
			break;
		case R.id.radio_half_hour:
			this.durationMinutes = 30;
			if(customDurationVisible)
				hideCustomDuration();
			break;
		case R.id.radio_hour:
			this.durationMinutes = 60;
			if(customDurationVisible)
				hideCustomDuration();
			break;
		case R.id.radio_custom:
			this.durationMinutes = 0;
			showCustomDuration();
			break;
		}
	}

	private void hideCustomDuration() {
		this.customDurationVisible = false;
		TextView hourInput = (TextView) findViewById(R.id.hourInput);
		TextView minuteInput = (TextView) findViewById(R.id.minuteInput);
		TextView hourLabel = (TextView) findViewById(R.id.hourLabel);
		TextView minuteLabel = (TextView) findViewById(R.id.minuteLabel);
		Button button = (Button) findViewById(R.id.submitButton);
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) button.getLayoutParams();
		params.addRule(RelativeLayout.BELOW, R.id.radioGroup1);
		hourInput.setVisibility(View.GONE);
		minuteInput.setVisibility(View.GONE);
		hourLabel.setVisibility(View.GONE);
		minuteLabel.setVisibility(View.GONE);
	}
	
	private void showCustomDuration() {
		this.customDurationVisible = true;
		TextView hourInput = (TextView) findViewById(R.id.hourInput);
		TextView minuteInput = (TextView) findViewById(R.id.minuteInput);
		TextView hourLabel = (TextView) findViewById(R.id.hourLabel);
		TextView minuteLabel = (TextView) findViewById(R.id.minuteLabel);
		Button button = (Button) findViewById(R.id.submitButton);
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) button.getLayoutParams();
		params.addRule(RelativeLayout.BELOW, R.id.minuteLabel);
		hourInput.setVisibility(View.VISIBLE);
		minuteInput.setVisibility(View.VISIBLE);
		hourLabel.setVisibility(View.VISIBLE);
		minuteLabel.setVisibility(View.VISIBLE);
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
		getMenuInflater().inflate(R.menu.add_event, menu);
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
