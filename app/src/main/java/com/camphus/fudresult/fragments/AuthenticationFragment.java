package com.camphus.fudresult.fragments;

import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;
import com.camphus.fudresult.R;
import com.camphus.fudresult.helpers.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthenticationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_authentication, parent, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regNo = Utilities.getInput(view, R.id.regno);
                if(Utilities.isEmpty(view, R.id.regno) || regNo.length() != 15){
                    Utilities.notify(view, "Invalid Registration number");
                    return;
                }

                if(Utilities.isEmpty(view, R.id.pass)){
                    Utilities.notify(view, "Password not entered");
                    return;
                }

                JSONObject data = new JSONObject();
                try {
                    data.put("matric_number", regNo);
                    data.put("password", Utilities.getInput(view, R.id.pass));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Utilities.notify(view, "System Error");
                    return;
                }
                JsonObjectRequest request = new JsonObjectRequest(Utilities.getUrl("my-results/" + regNo), data,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray results = response.getJSONArray("results");
                                    if(results.length() > 0){
                                        Bundle bundle = new Bundle();
                                        bundle.putString("results", results.toString());
                                        MyResultsFragment myResults = new MyResultsFragment();
                                        myResults.setArguments(bundle);
                                        getFragmentManager().beginTransaction()
                                                .addToBackStack(null)
                                                .replace(R.id.content_main, myResults)
                                        .commit();
                                    }else{
                                        Utilities.notify(view, "No results available");
                                    }

                                } catch (JSONException e) {
                                    Utilities.notify(view, "Currupt data");
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Utilities.notify(view, "Could not check for results");
                            }
                        });

                customRequestQueue().add(request);
            }
        });
    }

    public RequestQueue customRequestQueue(){
        HttpStack stack;
        String userAgent = "app/loader";

        if (Build.VERSION.SDK_INT >= 9) {
            stack = new HurlStack();
        } else {
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new NoCache(), network);
        queue.start();
        return queue;
    }
}
