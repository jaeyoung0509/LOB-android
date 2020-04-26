package com.example.lob.Service;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Storage {
   private boolean isSuccess =false;
    public boolean UploadProfile(Uri uri, String userId){
    final FirebaseStorage storage= FirebaseStorage.getInstance();
    final StorageReference  storageReference = storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/profile").child(userId);
    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            isSuccess=true;
        }
    });

        return isSuccess;
    }
}
