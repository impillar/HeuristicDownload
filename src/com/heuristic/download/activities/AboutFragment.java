package com.heuristic.download.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heuristic.download.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link AboutFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link AboutFragment#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class AboutFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment AboutFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AboutFragment newInstance(String param1, String param2) {
		AboutFragment fragment = new AboutFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_about, container, false);
	}

	int mNum;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static AboutFragment newInstance(int num) {
    	AboutFragment f = new AboutFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

}
