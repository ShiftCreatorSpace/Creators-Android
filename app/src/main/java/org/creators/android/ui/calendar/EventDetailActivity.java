package org.creators.android.ui.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import org.creators.android.R;
import org.creators.android.data.model.Event;
import org.creators.android.data.model.EventResponse;
import org.creators.android.data.model.User;
import org.creators.android.ui.common.Util;


public class EventDetailActivity extends Activity implements AdapterView.OnItemSelectedListener {
  public static final String TAG = "EventDetailActivity";

  private Event mEvent;
  private EventResponse mEventResponse;

  private ImageView mImage;
  private Spinner mRsvpSpinner;
  private TextView mTitle;
  private TextView mDate;
  private TextView mTime;
  private TextView mDetails;

  private String[] mRsvpOptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_detail);
    getActionBar().setDisplayHomeAsUpEnabled(true);

    mImage = (ImageView) findViewById(R.id.event_image);
    mRsvpSpinner = (Spinner) findViewById(R.id.event_rsvp_spinner);
    mTitle = (TextView) findViewById(R.id.event_title);
    mDate = (TextView) findViewById(R.id.event_date);
    mTime = (TextView) findViewById(R.id.event_time);
    mDetails = (TextView) findViewById(R.id.event_details);

    mRsvpOptions = getResources().getStringArray(R.array.rsvp_options);

    try {
      mEvent = Event.query().get(getIntent().getStringExtra(Event.OBJECT_ID));
      mEventResponse = EventResponse.from(User.getCurrentUser(), mEvent);
    } catch (ParseException e) {
      e.printStackTrace();
      finish();
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    Picasso.with(this)
      .load(mEvent.getImageFile().getUrl())
      .placeholder(R.drawable.ic_launcher)
      .into(mImage);

    mRsvpSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.adapter_rsvp_spinner_item, mRsvpOptions));
    mRsvpSpinner.setSelection(mEventResponse.getStatus().ordinal());
    mRsvpSpinner.setOnItemSelectedListener(this);

    mTitle.setText(mEvent.getTitle());
    mDate.setText(Util.Time.formatDate(mEvent.getStartDate()));
    mTime.setText(Util.Time.roundTimeAndFormat(mEvent.getStartDate(), 2) + '-' + Util.Time.roundTimeAndFormat(mEvent.getEndDate(), 2));
    mDetails.setText(mEvent.getDetails());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    mEventResponse.setStatus(EventResponse.Status.values()[i]);
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
    mEventResponse.setStatus(EventResponse.Status.NO_RESPONSE);
  }
}
