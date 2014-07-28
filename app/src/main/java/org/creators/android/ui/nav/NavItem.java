package org.creators.android.ui.nav;

import android.app.Fragment;
import android.app.FragmentManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class NavItem {
  private final FragmentManager mFragmentManager;
  private final Class<? extends Fragment> mClazz;
  private final String mTitle;
  private final int mIconId;
  private final String mTag;

  private Fragment mFragment;

  public NavItem(FragmentManager fm, Class<? extends Fragment> clazz, String title, int iconId) {
    mFragmentManager = fm;
    mClazz = clazz;
    mTitle = title;
    mIconId = iconId;
    mTag = mClazz.getSimpleName();
    mFragment = null;
  }

  public NavItem(FragmentManager fm, Class<? extends Fragment> clazz, String title, int iconId, Fragment fragment) {
    this(fm, clazz, title, iconId);
    mFragment = fragment;
  }

  public Fragment getFragment() {
    if (mFragment == null) {
      mFragment = mFragmentManager.findFragmentByTag(mClazz.getSimpleName());
      if (mFragment == null) {
        try {
          mFragment = mClazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          e.printStackTrace();
        }
      }
    }
    return mFragment;
  }

  public String getTag() {
    return mTag;
  }

  public String getTitle() {
    return mTitle;
  }

  public int getIconId() {
    return mIconId;
  }
}
