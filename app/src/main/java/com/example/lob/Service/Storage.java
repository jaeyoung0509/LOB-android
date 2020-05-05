package com.example.lob.Service;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class Storage {
    private boolean istDeleted ;
    private boolean isStored ;
    private FirebaseStorage storage;
    private StorageReference storageReference;
       public void UploadProfile(String filePath, final String userId)  {
        storage=FirebaseStorage.getInstance();
        Uri file =Uri.fromFile(new File(filePath));
        storageReference=storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/")
                .child("profile/"+userId);
        UploadTask uploadTask=storageReference.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("asadadasdasd", String.valueOf(taskSnapshot));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    public void UploadProfile(Uri filePath, final String userId)  {
        storage=FirebaseStorage.getInstance();
        Uri file = filePath;
        storageReference=storage.getReferenceFromUrl("gs://lobb-9ea28.appspot.com/")
                .child("profile/"+userId);
        UploadTask uploadTask=storageReference.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("asadadasdasd", String.valueOf(taskSnapshot));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }


    public boolean confirmFile(String userId){
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference().child("profile/"+userId);
        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        isStored=true;

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isStored=false;
            }
        });
        return isStored;
    }

    public boolean defeteFile(String userId){
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference().child("profile/"+userId);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                istDeleted=true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                istDeleted=false;
            }
        });
        return  istDeleted;
    }

    }



