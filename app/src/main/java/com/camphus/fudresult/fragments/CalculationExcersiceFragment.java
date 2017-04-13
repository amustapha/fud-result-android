package com.camphus.fudresult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.camphus.fudresult.R;
import com.camphus.fudresult.helpers.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class CalculationExcersiceFragment extends Fragment {

    String answer = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_test, parent, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JsonObjectRequest request = new JsonObjectRequest(Utilities.getUrl("test"), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            answer = response.getString("answer");
                            ((TextView)view.findViewById(R.id.question)).setText(response.getString("question"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(getContext()).add(request);

        view.findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.getInput(view, R.id.answer).equals(answer)){
                    Utilities.notify(view, "The answer was correct");
                }else{
                    Utilities.notify(view, "The answer was wrong, the correct answer is " + answer);
                }
            }
        });
    }

}
