package com.camphus.fudresult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.camphus.fudresult.R;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    ArrayList<Map<String, String>> summary = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_result, parent, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView resultsView  = ((ListView) view.findViewById(R.id.results));
        SimpleAdapter adapter = new SimpleAdapter(getContext(), summary, R.layout.holder_summary, new String[]{"course_code", "course_title", "grade", "grade_point", "remark"}, new int[]{R.id.course_code, R.id.course_title, R.id.grade, R.id.grade_point, R.id.remark});
        resultsView.setAdapter(adapter);

    }
}
