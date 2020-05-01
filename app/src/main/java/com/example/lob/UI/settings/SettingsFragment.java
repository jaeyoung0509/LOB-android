
package com.example.lob.UI.settings;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;
        import android.content.Intent;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

public class SettingsFragment extends Fragment {
    private Storage storage;
    private FirebaseAuth googleAuth = null;
    private FirebaseUser currentUser=null;
    private SettingsViewModel settingsViewModel;
    private String pathUri;
    private static final int PICK_FROM_ALBUM=1;
    private Uri imageUri;
    Button settingButton_userImg;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        googleAuth = FirebaseAuth.getInstance();
        currentUser = googleAuth.getCurrentUser();
        ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingButton_userImg=root.findViewById(R.id.settingButton_userImg);
        settingButton_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent imgintent = new Intent(Intent.ACTION_PICK);
                    imgintent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(imgintent,PICK_FROM_ALBUM);
                }
            }
        });
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PICK_FROM_ALBUM: { // 코드 일치
                // Uri
                Log.e("asdasdasdas", String.valueOf(  getPath(data.getData())));
                Log.e("asdasdasdasd",currentUser.getUid());
                storage=new Storage();
                storage.UploadProfile( getPath(data.getData()),currentUser.getUid());
            }
        }
    }

    public String getPath(Uri uri){
        String [] proj ={MediaStore.Images.Media.DATA};
        Cursor cursor =null;
        int index;
        String name = null;
        CursorLoader cursorLoader= new CursorLoader(this.getContext(),uri,proj,null,null,null);
        if(cursorLoader!=null) {
            cursor = cursorLoader.loadInBackground();
            index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            name=cursor.getString(index);
            cursor.close();
        }
        return name;
    }
}
