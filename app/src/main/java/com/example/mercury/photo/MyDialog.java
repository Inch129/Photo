package com.example.mercury.photo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by mercury on 14.08.2015.
 */
public class MyDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "Meow";
    EditText edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Save picture");
        View v = inflater.inflate(R.layout.dlg, null);
        v.findViewById(R.id.btnCancel).setOnClickListener(this);
        v.findViewById(R.id.btnSave).setOnClickListener(this);
        edit = (EditText) v.findViewById(R.id.editText);
        return v;
    }



   /* @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Title!").setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.Saving, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setMessage(R.string.message_text);

        .setPositiveButton(R.string.Saving, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                        Main.saveFile();
                    }
                })

                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }*/


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClickDialog");
        final String to_save = "";
        switch (v.getId()) {
            case R.id.btnSave:
                String text = edit.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    Log.d(TAG, "Button Save");
                    Main.saveFile(text);

                }
                dismiss();
                 break;
            default:
                Log.d(TAG, "Default");
                return;
        }


    }
}

