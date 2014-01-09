package com.example.yologger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yologger.Content.Event;

public class AddEventActivity extends Activity {

	private int durationMinutes = 15;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		// Show the Up button in the action bar.
		
		setupActionBar();
		initDropdownMenu();
	}
	
	 public void initDropdownMenu() {
		 	
		 
			Spinner dropdown = (Spinner) findViewById(R.id.dropdown_menu);
			ArrayList<String> entries = new ArrayList<String>();
			entries.addAll(Content.categories.keySet());
			Collections.sort(entries);
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.dropdown, entries);
			dropdown.setAdapter(dataAdapter);
			ImageView i = (ImageView) findViewById(R.id.social_media_images);
			i.setAlpha(60);
			
			String name = (String) getIntent().getSerializableExtra("category");
			if(name != null)
				dropdown.setSelection(entries.indexOf(name));
		  }
	 
	public void durationSelect(View view){
		 TimePickerDialog mTimePicker;
         mTimePicker = new EventDurationPickerDialog(this, null, 0, 0);
         mTimePicker.setTitle("Select Duration");
         mTimePicker.show();
	}
	
	public void shareSelected(View view){
		CheckBox check = (CheckBox) findViewById(R.id.share_check);
		ImageView i = (ImageView) findViewById(R.id.social_media_images);
		if(check.isChecked()){
			i.setAlpha(255);
		} else {
			i.setAlpha(60);
		}
	}
	
	public void changeDuration(int hours,int minutes){
		TextView durationDisplay = (TextView) findViewById(R.id.duration_display);
		String d = Home.formatDuration(minutes + hours * 60);
		durationDisplay.setText(d);
		durationMinutes = minutes + hours * 60;
	}
	
	 public class EventDurationPickerDialog extends TimePickerDialog {

		    public EventDurationPickerDialog(Context context, int theme,
		            OnTimeSetListener callBack, int hour, int minute) {
		        super(context, theme, callBack, hour, minute, true);
		        updateTitle(hour, minute);
		    }

		    public EventDurationPickerDialog(Context context, OnTimeSetListener callBack,
		            int hour, int minute) {
		        super(context, callBack, hour, minute, true);
		        updateTitle(hour, minute);
		    }

		    @Override
		    public void onTimeChanged(TimePicker view, int hour, int minute) {
		        super.onTimeChanged(view, hour, minute);
		        updateTitle(hour, minute);
		        changeDuration(hour, minute);
		    }

		    public void updateTitle(int hour, int minute) {
		        setTitle("Duration: " + Home.formatDuration(minute + hour * 60));
		    }

		 
		}
		 
	
	public void submitEvent(View view){
		TextView t = (TextView) findViewById(R.id.event_text);
		Spinner s = (Spinner) findViewById(R.id.dropdown_menu);
		String c = (String) s.getSelectedItem();
		String desc = t.getText().toString().trim();
		if(durationMinutes == 0)
			displayAlert("You forgot to set the duration!");
		else if(desc.isEmpty())
			displayAlert("You forgot to describe the activity!");
		else {
		Content.addEvent(new Event(desc, new Date(), durationMinutes, Content.categories.get(c)));
		finish();
		Toast.makeText(getApplicationContext(), "Activity logged!",
				   Toast.LENGTH_LONG).show();
		}
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
