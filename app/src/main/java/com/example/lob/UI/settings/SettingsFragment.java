package com.example.lob.UI.settings;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.CursorLoader;

import com.example.lob.R;
import com.example.lob.Service.Storage;
import com.google.firebase.storage.FirebaseStorage;

public class SettingsFragment extends Fragment {
    private Storage firebaseStorage;
    private SettingsViewModel settingsViewModel;
    Button settingButton_userImg;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingButton_userImg=root.findViewById(R.id.settingButton_userImg);
        settingButton_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               {
                   Intent imgintent = new Intent();
                   imgintent.setType("image/*");
                   imgintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                   imgintent.setAction(Intent.ACTION_GET_CONTENT);
                 // firebaseStorage=
                }
            }
        });
        /*settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}
