package org.creators.android.ui.members;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Function;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.squareup.picasso.Picasso;

import org.creators.android.R;
import org.creators.android.data.model.User;
import org.creators.android.ui.MainActivity;
import org.creators.android.ui.common.CircleTransform;
import org.creators.android.ui.common.ParseAdapter;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class MembersFragment extends Fragment implements ParseAdapter.ListCallbacks<User>, AdapterView.OnItemClickListener {
  public static final String TAG = "MembersFragment";

  private ListView mListView;
  private SwipeRefreshLayout mLayout;
  private ParseAdapter<User> mAdapter;

  public MembersFragment() {
    super();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    ParseQueryAdapter.QueryFactory<User> factory = new ParseQueryAdapter.QueryFactory<User>() {
      @Override
      public ParseQuery<User> create() {
        return User.query().orderByAscending(User.FIRST_NAME);
      }
    };
    mAdapter = new ParseAdapter<>(getActivity(), R.layout.adapter_user, this, factory);
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
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_members, menu);

    mAdapter.prepareFilter(menu, R.id.menu_search, new Function<User, String>() {
      @Override
      public String apply(User input) {
        return input.getFullName().toLowerCase();
      }
    });
  }

  @Override
  public void fillView(ParseAdapter.ViewHolder holder, User user) {
    ImageView selfie = holder.get(R.id.user_selfie);
    TextView fullName = holder.get(R.id.user_full_name);

    fullName.setText(user.getFullName());
    Picasso.with(getActivity())
      .load(user.getSelfieFile().getUrl())
      .transform(new CircleTransform())
      .placeholder(user.getSex() == User.Sex.M ? R.drawable.ic_member_male : R.drawable.ic_member_female)
      .into(selfie);
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
    intent.putExtra(User.OBJECT_ID, mAdapter.getItem(i).getObjectId());
    startActivity(intent);
  }
}
