package com.example.heatsink.TenantsHeatsink;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.heatsink.PropertyHeatsink.property_activity;
import com.example.heatsink.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import maes.tech.intentanim.CustomIntent;

public class BottomSheetTenants extends BottomSheetDialogFragment {

    public BottomSheetTenants(){

    }
    private RadioGroup filter;
    private TextView done, clear;
    RadioButton button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bottomsheetowner , container , false);

        filter = view.findViewById(R.id.filter);
        done = view.findViewById(R.id.done);
        clear = view.findViewById(R.id.cancel11);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                int selectedID  = -1;
                selectedID= filter.getCheckedRadioButtonId();
                button = (RadioButton)view.findViewById(selectedID);
                String value = "";
                if(selectedID == -1){
                   value = "empty";
                }else {
                    value = button.getText().toString();

                }
                    Intent intent = new Intent(getContext(), tenants_activity.class);
                    intent.putExtra("code", value);
                    intent.addCategory("filter");
                    startActivity(intent);
                    CustomIntent.customType(getContext(), "fadein-to-fadeout");
                    dismiss();


            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return  view;
    }
}



















