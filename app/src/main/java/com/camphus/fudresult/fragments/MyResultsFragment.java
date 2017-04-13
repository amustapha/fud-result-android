package com.camphus.fudresult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.camphus.fudresult.R;
import com.camphus.fudresult.helpers.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyResultsFragment extends Fragment {

    ArrayList<Map<String, String>> results = new ArrayList<>();
    String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        try {
            JSONArray results = new JSONArray(bundle.getString("results"));
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonResult = results.getJSONObject(i);
                Map<String, String> result = new HashMap<>();
                name = jsonResult.getString("name");
                result.put("id", jsonResult.getString("id"));
                result.put("session", jsonResult.getString("session"));
                result.put("gpa", jsonResult.getString("gpa"));
                result.put("semester", jsonResult.getString("semester"));
                this.results.add(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_results, parent, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView resultsView  = ((ListView) view.findViewById(R.id.results));
        ((TextView) view.findViewById(R.id.name)).setText(name);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), results, R.layout.holder_result, new String[]{"semester", "session", "gpa"}, new int[]{R.id.semester, R.id.session, R.id.gpa});
        resultsView.setAdapter(adapter);
        resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final JsonObjectRequest request = new JsonObjectRequest(Utilities.getUrl("show-result/" + results.get(position).get("id")), null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray courses = response.getJSONArray("summary");
                                    if(courses.length()>0){
                                        ResultFragment resultFragment = new ResultFragment();
                                        Bundle extra = new Bundle();
                                        extra.putString("summary", courses.toString());
                                        resultFragment.setArguments(extra);
                                        getFragmentManager().beginTransaction()
                                                .addToBackStack(null)
                                                .replace(R.id.content_main, resultFragment)
                                                .commit();
                                    }
                                    else{
                                        Utilities.notify(view, "Summary could not be fetced");
                                    }
                                } catch (JSONException e) {
                                    Utilities.notify(view, "Data corrupted");
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Utilities.notify(view, "Response not fetched" + error.toString());
                            }
                        });
                Volley.newRequestQueue(getContext()).add(request);
            }
        });

    }
}
