package com.example.lob.Service;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.lob.UserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class Storage {

       public void UploadProfile2(String filePath, final String userId)  {
            FirebaseStorage storage;
            StorageReference storageReference;
           storage=FirebaseStorage.getInstance();
        Uri file =Uri.fromFile(new File(filePath));
        storageReference=storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/")
                .child("profile/"+userId);
        UploadTask uploadTask=storageReference.putFile(file);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("asdasdad2", String.valueOf(taskSnapshot));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    public void  UploadProfile(Uri filePath, final String userId)  {
        FirebaseStorage storage;
        StorageReference storageReference;

        storage=FirebaseStorage.getInstance();
        Uri file = filePath;
        storageReference=storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/")
                .child("profile/"+userId);
        UploadTask uploadTask=storageReference.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("asdasdad211111", String.valueOf(taskSnapshot));
            }
        });
    }

    public void ModifyUpload(final String filePath , final String userId){
        FirebaseStorage storage;
        StorageReference storageReference;
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference().child("profile/"+userId);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseStorage storage;
                StorageReference storageReference;
                storage=FirebaseStorage.getInstance();
                Uri file =Uri.fromFile(new File(filePath));
                Log.e("여긴되나2","ㅁㄴ");

                storageReference=storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/")
                        .child("profile/"+userId);
                UploadTask uploadTask=storageReference.putFile(file);
                Log.e("여긴되나1","ㅁㄴ");

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("true","true");
                    }
                });
            }
        });
    }

    }



