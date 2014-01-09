package com.example.yologger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yologger.Content.Category;
import com.example.yologger.Content.Event;

public class Home extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	List<Fragment> mFragments;
	EventListFragment eventList;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private StatisticsFragment statistics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mFragments = new ArrayList<Fragment>();
		eventList = new EventListFragment();
		statistics = new StatisticsFragment();
		mFragments.add(eventList);
		mFragments.add(statistics);
		mFragments.add(new DummySectionFragment());

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), mFragments);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		addActivity();
		return true;
		// switch (item.getItemId()) {
		// case R.menu.home:
		// addActivity();
		// return true;
		// default:
		// return super.onOptionsItemSelected(item);
		// }
	}

	private void addActivity() {
		Intent intent = new Intent(this, AddEventActivity.class);
		startActivity(intent);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		eventList.refresh();
	}

	private boolean skipResume = true;

	@Override
	public void onResume() {
		super.onResume();
		if (!skipResume) {
			eventList.refresh();
			statistics.refresh();
		} else
			skipResume = false;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		List<Fragment> fragmentList;

		public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			fragmentList = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = fragmentList.get(position);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_dummy,
					container, false);

			// final ListView listview = (ListView)
			// rootView.findViewById(R.id.activity_list);
			// String[] values = new String[] { "Android", "iPhone",
			// "WindowsMobile",
			// "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			// "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
			// "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			// "Android", "iPhone", "WindowsMobile" };
			// List<String> list = Arrays.asList(values);
			// listview.setAdapter(new ArrayAdapter<String>(getActivity(),
			// android.R.layout.simple_list_item_activated_1, list));

			return rootView;
		}
	}

	public static class EventListFragment extends ListFragment {

		private View rootView;

		public EventListFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_home_dummy,
					container, false);
			refresh();
			return rootView;

		}

		public void refresh() {
			final ListView listview = (ListView) rootView
					.findViewById(android.R.id.list);
			Collections.sort(Content.events);
			listview.setAdapter(new EventListArrayAdapter(getActivity()
					.getBaseContext(), Content.getEventList().toArray(
					new Event[0])));
		}
	}

	public static class StatisticsFragment extends Fragment {

		private View rootView;
		private StatisticsGraph statisticsGraph;

		public StatisticsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.statistics, container, false);
			statisticsGraph = new StatisticsGraph(rootView.getContext(), 20, 20, 180, 180, true);
			LinearLayout linear = (LinearLayout) rootView
					.findViewById(R.id.chart_layout);
			linear.addView(statisticsGraph);
			refresh();
			return rootView;
		}

		private Float[] getCategoryData() {
			List<Float> temp = new ArrayList<Float>();
			float total = 0;
			for (Category c : Content.getSortedCategories()) {
				float categoryTotal = (float) Content.getCategoryTotal(c);
				total += categoryTotal;
				temp.add(categoryTotal);
			}
			List<Float> data = new ArrayList<Float>();
			for (Float f : temp) {
				data.add(360 * f / total);
			}
			return data.toArray(new Float[0]);
		}

		public void refresh() {
			statisticsGraph.invalidate();
			statisticsGraph.degrees = getCategoryData();
			final ListView listview = (ListView) rootView
					.findViewById(R.id.categoryList);
			final List<Category> categories = Content.getSortedCategories();
			listview.setAdapter(new StatisticsListArrayAdapter(getActivity()
					.getBaseContext(), categories.toArray(
					new Category[0])));
			listview.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent i = new Intent(view.getContext(), CategoryActivity.class);
					i.putExtra("category", categories.get(position));
					startActivity(i);
				}
				
			});
		}

	}
	
	public static class StatisticsGraph extends View {
		
		private Paint style = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		public Float[] degrees;
		
		public int[] chartColors = { 
				Color.parseColor("#66FF66"),
				Color.parseColor("#66FFCC"), 
				Color.parseColor("#99CCFF"),
				Color.parseColor("#FFCCFF"),
				Color.parseColor("#FFCC99")};

		private int x;

		private int y;

		private boolean drawLabels;

		private int width;

		private int height;
		
		public StatisticsGraph(Context context, int x, int y, int height, int width, boolean drawLabels) {
			super(context);
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.drawLabels = drawLabels;
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			drawPieChart(canvas);
			if(drawLabels)
			drawLabels(canvas);
		}

		private void drawLabels(Canvas canvas) {
			int height = 25;
			int width = height;
			int pixelsBetween = 10;
			
			int index = 0;
			for (Category c : Content.getSortedCategories()) {
				style.setColor(chartColors[index]);
				style.setStyle(Paint.Style.FILL);
				canvas.drawRect(220, 
						30 + index * (height + pixelsBetween), 
						220 + width, 
						30 + height + index * (height + pixelsBetween), 
						style);
				style.setColor(Color.BLACK);
				style.setStyle(Paint.Style.STROKE);
				canvas.drawRect(220, 
						30 + index * (height + pixelsBetween), 
						220 + width, 
						30 + height + index * (height + pixelsBetween), 
						style);
				style.setTextSize(height);
				canvas.drawText(c.name + " (" + (int)(degrees[index] / 360 * 100) + "%)",
						230 + width, 
						30 + height * (float) 0.85 + index * (height + pixelsBetween), 
						style);
				index++;
			}
		}

		private void drawPieChart(Canvas canvas) {
			float degree = 0;
			RectF pieRect = new RectF(x, y, x + width, y + height);
			for (int i = 0; i < degrees.length; i++) {
				style.setColor(chartColors[i]);
				style.setStyle(Paint.Style.FILL);
				canvas.drawArc(pieRect, degree, degrees[i], true, style);
				style.setColor(Color.BLACK);
				style.setStyle(Paint.Style.STROKE);
				canvas.drawArc(pieRect, degree, degrees[i], true, style);
				degree += degrees[i];
			}
		}

	}
	
	public static class StatisticsListArrayAdapter extends
			ArrayAdapter<Category> {

		private Context context;
		private Category[] values;

		public StatisticsListArrayAdapter(Context context, Category[] values) {
			super(context, R.layout.categorylist, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.categorylist, parent,
					false);
			TextView below = (TextView) rowView.findViewById(R.id.lineBelow);
			TextView above = (TextView) rowView.findViewById(R.id.lineAbove);
			TextView right = (TextView) rowView.findViewById(R.id.lineRight);
			// TextView textView2 = (TextView)
			// rowView.findViewById(R.id.eventLine2);
			ImageView imageView = (ImageView) rowView
					.findViewById(R.id.categoryicon);

			Category c = values[position];
			above.setText(c.name);

			int minutesUsed = Content.getCategoryTotal(c);
			below.setText((minutesUsed * 100) / c.goal
					+ "% of this week's goal");
			int co = 0;
			if (c.goal == -1) {
				below.setText("");
			} else if ((double) minutesUsed / c.goal < 0.2) {
				co = Color.parseColor("#FFD1D1");
			} else if ((double) minutesUsed / c.goal < 0.4) {
				co = Color.parseColor("#FFD6AD");
			} else if ((double) minutesUsed / c.goal < 0.6) {
				co = Color.parseColor("#FFFFD6");
			} else if ((double) minutesUsed / c.goal < 0.8) {
				co = Color.parseColor("#EBFFD6");
			} else {
				co = Color.parseColor("#D6FFD6");
			}
			
			rowView.setBackgroundColor(co);

			String d = formatDuration(minutesUsed);
			right.setText(d.isEmpty() ? "0 min" : d);
			imageView.setImageResource(c.resId);

			return rowView;
		}

	}

	public static class EventListArrayAdapter extends ArrayAdapter<Event> {
		private Context context;
		private Event[] values;

		public EventListArrayAdapter(Context context, Event[] values) {
			super(context, R.layout.eventlist, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Event e = values[position];
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (e.isDivider()) {
				View rowView = inflater.inflate(R.layout.list_divider, parent,
						false);
				rowView.setOnClickListener(null);
				rowView.setOnLongClickListener(null);
				rowView.setLongClickable(false);
				TextView sectionView = (TextView) rowView
						.findViewById(R.id.list_item_section_text);
				if (e.dividerCount == 0)
					sectionView.setText("This week");
				else if (e.dividerCount == 1) {
					sectionView.setText("Last week");
				} else {
					sectionView.setText(String.valueOf(e.dividerCount)
							+ " weeks ago");
				}

				rowView.setBackgroundColor(Color.parseColor("#EDF3F3"));
				return rowView;
			}

			View rowView = inflater.inflate(R.layout.eventlist, parent, false);
			TextView below = (TextView) rowView
					.findViewById(R.id.eventLineBelow);
			TextView above = (TextView) rowView
					.findViewById(R.id.eventLineAbove);
			TextView right = (TextView) rowView
					.findViewById(R.id.eventLineRight);
			// TextView textView2 = (TextView)
			// rowView.findViewById(R.id.eventLine2);
			ImageView imageView = (ImageView) rowView
					.findViewById(R.id.eventicon);
			above.setText(values[position].description);
			right.setText(formatDuration(values[position].durationMinutes));
			below.setText(formatDate(values[position].date));
			Category c = values[position].category;
			imageView.setImageResource(c.resId);

			return rowView;
		}

	}

	public static String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm dd.MM.yyyy");
		return f.format(date);
	}

	public static String formatDuration(int durationMinutes) {
		String hours = durationMinutes / 60 > 0 ? durationMinutes / 60
				+ " hrs " : "";
		String mins = durationMinutes % 60 > 0 ? durationMinutes % 60 + " min"
				: "";
		return hours + mins;
	}

}
