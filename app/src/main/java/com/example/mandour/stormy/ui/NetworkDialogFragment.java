package com.example.mandour.stormy.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class NetworkDialogFragment  extends DialogFragment{

    // this fragment is to alert the user that the internet connection is not working
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Network Failure")
                .setMessage("Sorry there is no network available")
                .setPositiveButton("OK",null);
        AlertDialog dialog = builder.create();
        return dialog;

    }
}
