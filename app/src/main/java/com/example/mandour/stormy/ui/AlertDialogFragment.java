package com.example.mandour.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class AlertDialogFragment  extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Sorry")
                .setMessage("There was an error!")
                .setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
