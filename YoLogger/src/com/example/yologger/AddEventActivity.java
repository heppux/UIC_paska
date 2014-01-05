package com.example.yologger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.yologger.Content.Event;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

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
	
	@Override
	public void onStop(){
		super.onStop();
		Toast.makeText(getApplicationContext(), "Activity logged!",
				   Toast.LENGTH_LONG).show();
	}
	
	 public void initDropdownMenu() {
		 	
			Spinner dropdown = (Spinner) findViewById(R.id.dropdown_menu);
			ArrayList<String> entries = new ArrayList<String>();
			entries.addAll(Content.categories.keySet());
			Collections.sort(entries);
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.dropdown, entries);
//			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dropdown.setAdapter(dataAdapter);
			ImageView i = (ImageView) findViewById(R.id.social_media_images);
			i.setAlpha(60);
//			
//			TableRow row = (TableRow) findViewById(R.id.category_row);
//			row.setBackgroundColor(getResources().getColor(R.color.grey_color));
//			
//			row = (TableRow) findViewById(R.id.duration_row);
//			row.setBackgroundColor(getResources().getColor(R.color.grey_color));
		  }
	 
	public void durationSelect(View view){
		 TimePickerDialog mTimePicker;
         mTimePicker = new DurationPickerDialog(this, null, 0, 0);
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
		String d = Home.EventListArrayAdapter.formatDuration(minutes + hours * 60);
		durationDisplay.setText(d);
		durationMinutes = minutes + hours * 60;
	}
	
	 public class DurationPickerDialog extends TimePickerDialog {

		    public DurationPickerDialog(Context context, int theme,
		            OnTimeSetListener callBack, int hour, int minute) {
		        super(context, theme, callBack, hour, minute, true);
		        updateTitle(hour, minute);
		    }

		    public DurationPickerDialog(Context context, OnTimeSetListener callBack,
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
		        setTitle("Duration: " + Home.EventListArrayAdapter.formatDuration(minute + hour * 60));
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
		Content.events.add(new Event(desc, new Date(), durationMinutes, Content.categories.get(c)));
		
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
