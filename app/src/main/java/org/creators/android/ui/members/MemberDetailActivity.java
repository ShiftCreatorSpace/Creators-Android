package org.creators.android.ui.members;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import org.creators.android.R;
import org.creators.android.data.model.User;
import org.creators.android.ui.common.CircleTransform;


public class MemberDetailActivity extends Activity {
  public static final String TAG = "MemberDetailActivity";

  private User mMember;

  private ImageView mSelfie;
  private TextView mFullName;
  private TextView mMajor;
  private TextView mBio;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_member_detail);
    getActionBar().setDisplayHomeAsUpEnabled(true);

    mSelfie = (ImageView) findViewById(R.id.member_selfie);
    mFullName = (TextView) findViewById(R.id.member_name);
    mMajor = (TextView) findViewById(R.id.member_major);
    mBio = (TextView) findViewById(R.id.member_bio);

    try {
      mMember = User.query().get(getIntent().getStringExtra(User.OBJECT_ID));
    } catch (ParseException e) {
      e.printStackTrace();
      finish();
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    Picasso.with(this)
      .load(mMember.getSelfieFile().getUrl())
      .transform(new CircleTransform())
      .placeholder(mMember.getSex() == User.Sex.M ? R.drawable.ic_member_male : R.drawable.ic_member_female)
      .into(mSelfie);

    mFullName.setText(mMember.getFullName());
    mMajor.setText(mMember.getMajor());
    mBio.setText(mMember.getBio());
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

}
