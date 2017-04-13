package com.camphus.fudresult.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;

/**
 * Created by m_bryo on 2/8/17.
 */

public class Utilities {
    public static String getInput(View v, int id){
        return ((EditText) v.findViewById(id)).getText().toString();
    }

    public static String getSelected(View v, int id){
        return ((Spinner) v.findViewById(id)).getSelectedItem().toString();
    }

    public static boolean isEmpty(View v, int id){
        return ((EditText) v.findViewById(id)).getText().toString().equals("");
    }

    public static boolean isEmpty(View v, int ...ids){
        for (int id :
                ids) {
            if(isEmpty(v, id)){
                return true;
            }
        }
        return false;
    }

    public static boolean noneSelected(View v, int id) {
        return (((Spinner) v.findViewById(id)).getSelectedItemPosition() == 0);
    }


    public static boolean noneSelected(View v, int ...ids){

        for (int id :
                ids) {
            if(noneSelected(v, id)){
                return true;
            }
        }

        return false;
    }

    public static void notify(View v, String message){
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
    }

    public static String getUrl(String path){
        return "http://192.168.173.1/fud-result/" + path;
//        return "http://192.168.43.103/fud-result/" + path;
    }

    public static String encodeBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 80, baos);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static ProgressDialog standardProgressDialog(Context context, String message, String title){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        if(title.isEmpty()){
            title = "Loading";
        }
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public static ProgressDialog standardProgressDialog(Context context, String message){
        return standardProgressDialog(context, message, null);
    }

    public static void log(String type, String key, String value){
        String pckg = "com.camphus.fudresult.";
        switch (type){
            case "e":
                Log.e(pckg + key, value);
                break;
            case "v":
                Log.v(pckg + key, value);
                break;
            case "i":
                Log.v(pckg + key, value);
                break;
        }
    }

}