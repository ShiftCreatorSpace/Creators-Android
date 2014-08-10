package org.creators.android.ui.calendar;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.R;
import org.creators.android.data.model.Event;
import org.creators.android.data.model.EventResponse;
import org.creators.android.data.model.User;
import org.creators.android.ui.MainActivity;
import org.creators.android.ui.common.ParseAdapter;
import org.creators.android.ui.common.Util;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class CalendarFragment extends Fragment implements ParseAdapter.ListCallbacks<Event>, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
  public static final String TAG = "CalendarFragment";

  private ListView mListView;
  private SwipeRefreshLayout mLayout;
  private ParseAdapter<Event> mAdapter;

  private String[] mRsvpOptions;
  private LoadingCache<Event, EventResponse> mAttendance;

  public CalendarFragment() {
    super();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ParseQueryAdapter.QueryFactory<Event> factory = new ParseQueryAdapter.QueryFactory<Event>() {
      @Override
      public ParseQuery<Event> create() {
        return Event.query().orderByDescending(Event.CREATED_AT);
      }
    };
    mAdapter = new ParseAdapter<>(getActivity(), R.layout.adapter_event, this, factory);
    mRsvpOptions = getResources().getStringArray(R.array.rsvp_options);

    mAttendance = CacheBuilder.newBuilder().build(new CacheLoader<Event, EventResponse>() {
      private User mmUser = User.getCurrentUser();
      @Override
      public EventResponse load(Event event) throws Exception {
        return EventResponse.from(mmUser, event);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_calendar, null);

    mListView = (ListView) mLayout.findViewById(R.id.events_list);
    mListView.setAdapter(mAdapter);
    mListView.setOnItemClickListener(this);

    mAdapter.bindSync(mLayout);
    if (getArguments().getBoolean(MainActivity.SHOULD_SYNC, false)) mAdapter.onRefresh();

    return mLayout;
  }

  @Override
  public void fillView(ParseAdapter.ViewHolder holder, Event event) {
    TextView title = holder.get(R.id.event_title);
    TextView details = holder.get(R.id.event_details);
    TextView date = holder.get(R.id.event_date);
    TextView time = holder.get(R.id.event_time);
    Spinner rsvp = holder.get(R.id.event_rsvp_spinner);


    title.setText(event.getTitle());
    details.setText(event.getDetails());
    date.setText(Util.Time.formatDate(event.getStartDate()));
    time.setText(Util.Time.roundTimeAndFormat(event.getStartDate(), 2) + '-' + Util.Time.roundTimeAndFormat(event.getEndDate(), 2));

    if (rsvp.getAdapter() == null) {
      rsvp.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.adapter_rsvp_spinner_item, mRsvpOptions));
    }

    EventResponse response = mAttendance.getUnchecked(event);
    rsvp.setTag(response);
    rsvp.setSelection(response.getStatus().ordinal());
    rsvp.setOnItemSelectedListener(this);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    ((EventResponse) adapterView.getTag()).setStatus(EventResponse.Status.values()[i]);
  }

  @Override
  public void onStart() {
    super.onStart();
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
    ((EventResponse) adapterView.getTag()).setStatus(EventResponse.Status.NO_RESPONSE);
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    Intent intent = new Intent(getActivity(), EventDetailActivity.class);
    intent.putExtra(Event.OBJECT_ID, mAdapter.getItem(i).getObjectId());
    startActivity(intent);
  }
}
