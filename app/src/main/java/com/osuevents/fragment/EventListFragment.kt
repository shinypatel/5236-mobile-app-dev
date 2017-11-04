package com.osuevents.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.osuevents.R
import com.osuevents.dummy.DummyContent.DummyItem

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
open class EventListFragment : Fragment() {
    private val TAG: String = javaClass.simpleName

    private var mListener: OnListFragmentInteractionListener? = null
    var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG, "onCreate() invoked on fragment " + title)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        Log.d(TAG, "onCreateView() invoked on fragment " + title)
        val view = inflater!!.inflate(R.layout.fragment_event_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
//            view.adapter = EventListRecyclerAdapter(DummyContent.ITEMS, mListener)
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        Log.d(TAG, "onAttach() invoked on fragment " + title)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
//        Log.d(TAG, "onDetach() invoked on fragment " + title)
        mListener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        Log.d(TAG, "onActivityCreated() invoked on fragment " + title)
    }

    override fun onStart() {
        super.onStart()
//        Log.d(TAG, "onStart() invoked on fragment " + title)
    }

    override fun onResume() {
        super.onResume()
//        Log.d(TAG, "onResume() invoked on fragment " + title)
    }

    override fun onPause() {
        super.onPause()
//        Log.d(TAG, "onPause() invoked on fragment " + title)
    }

    override fun onStop() {
        super.onStop()
//        Log.d(TAG, "onStop() invoked on fragment " + title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        Log.d(TAG, "onDestroyView() invoked on fragment " + title)
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.d(TAG, "onDestroyView() invoked on fragment " + title)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem)
    }
}
