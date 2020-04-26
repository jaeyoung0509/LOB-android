package com.example.lob.Service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import com.example.lob.UI.settings.SettingsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Storage {
   private boolean isSuccess =false;
    public void UploadProfile(Uri uri, String userId){
    final FirebaseStorage storage= FirebaseStorage.getInstance();
    final StorageReference  storageReference = storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/profile").child(userId);
    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            isSuccess=true;
        }
    });

    }

    public String getPath(Context context, Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }

}
