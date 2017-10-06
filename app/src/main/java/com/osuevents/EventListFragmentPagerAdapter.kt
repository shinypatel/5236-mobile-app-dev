package com.osuevents

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by sufianlatif on 10/4/2017.
 */
class EventListFragmentPagerAdapter(fm: FragmentManager, private val fragments: ArrayList<EventListFragment>) : FragmentPagerAdapter(fm) {
    private val TAG: String = javaClass.simpleName

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments[position].title
    }
}
