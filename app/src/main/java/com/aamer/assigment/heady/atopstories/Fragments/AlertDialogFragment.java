package com.aamer.assigment.heady.atopstories.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.aamer.assigment.heady.atopstories.R;


public class AlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = getActivity();
       AlertDialog.Builder builder  = new AlertDialog.Builder(context);
       builder.setTitle(getArguments().getString("error_title"))
       .setMessage(getArguments().getString("error_message"))
       .setPositiveButton(R.string.error_button_ok_text,null);

       return builder.create();

    }
}
