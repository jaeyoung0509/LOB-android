package com.example.lob.Service;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class Storage {
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public void UploadProfile(String filePath, final String userId)  {
        storage=FirebaseStorage.getInstance();
        Uri file =Uri.fromFile(new File(filePath));
        storageReference=storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/profile/")
                .child(userId);
        UploadTask uploadTask=storageReference.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("asadadasdasd", String.valueOf(taskSnapshot));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("asadadasdasd", String.valueOf(e));
                Log.e("asadadasdasd","gs://lobb-9ea28.appspot.com/profile/"+userId );
            }
        });
    }
}


