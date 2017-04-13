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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment {
    ArrayList<Map<String, String>> summary = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getArguments();
        try {
            JSONArray array = new JSONArray(extra.getString("summary"));
            for (int i = 0; i < array.length(); i++) {
                Map<String, String> course = new HashMap<>();
                JSONObject sObject = array.getJSONObject(i);
                course.put("course_code", sObject.getString("course_code"));
                course.put("course_title", sObject.getString("course_title"));
                course.put("grade", sObject.getString("grade"));
                course.put("grade_point", sObject.getString("grade_point"));
                course.put("remark", sObject.getString("remark"));
                summary.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
