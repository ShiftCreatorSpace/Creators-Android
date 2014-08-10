package org.creators.android.ui.announcements;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import org.creators.android.R;
import org.creators.android.data.model.Announcement;


public class AnnouncementDetailActivity extends Activity {
  public static final String TAG = "AnnouncementDetailActivity";

  private Announcement mAnnouncement;

  private TextView mTitle;
  private TextView mDetails;
  private ImageView mImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_announcement_detail);
    getActionBar().setDisplayHomeAsUpEnabled(true);

    mTitle = (TextView) findViewById(R.id.announcement_title);
    mDetails = (TextView) findViewById(R.id.announcement_details);
    mImage = (ImageView) findViewById(R.id.announcement_image);

    try {
      mAnnouncement = Announcement.query().get(getIntent().getStringExtra(Announcement.OBJECT_ID));
    } catch (ParseException e) {
      e.printStackTrace();
      finish();
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    mTitle.setText(mAnnouncement.getTitle());
    mDetails.setText(mAnnouncement.getDetails());

    if (mAnnouncement.hasImage()) {
      Picasso.with(this)
        .load(mAnnouncement.getImageFile().getUrl())
        .placeholder(R.drawable.ic_launcher)
        .into(mImage);
    } else {
      mImage.setVisibility(View.GONE);
    }

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
