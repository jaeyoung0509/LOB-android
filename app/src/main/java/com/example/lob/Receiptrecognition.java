package com.example.lob;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Receiptrecognition extends AppCompatActivity {
    private Button receipt_caputure, receipt_detect;
    private ImageView receipt_image;
    private TextView receipt_display;
    static final int REQUEST_RECEiPT_IMAGE = 1;
    private Bitmap imageBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiptrecognition);
        receipt_caputure = findViewById(R.id.receipt_caputure);
        receipt_image = findViewById(R.id.receipt_image);
        receipt_display = findViewById(R.id.receipt_display);
        receipt_detect = findViewById(R.id.receipt_detect);

        receipt_caputure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTaskPictureIntent();
            }
        });

        receipt_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               List<String> inputReceipt =  detectTextFromImage();
                Log.e("ssssssssssssssssssss","zzz");
                if(inputReceipt != null){
                    Log.e("sadasd","zzz");
                    Log.e("asdadasdas",String.valueOf(inputReceipt.size()));
                    for(int i =0 ; i<inputReceipt.size(); i++){
                        Log.e("asdadasdz",inputReceipt.get(i));
                    }
                }
            }
        });

    }

    private void dispatchTaskPictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_RECEiPT_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECEiPT_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            receipt_image.setImageBitmap(imageBitmap);
        }

    }

    private  synchronized  List<String> detectTextFromImage() {
        final List<String> input = new ArrayList<String>();
        FirebaseVisionImage firebaseVisionImage;
        firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer   detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer();
        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("en", "hi"))
                .build();

        final Task<FirebaseVisionText> result=
                detector.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        receipt_display.setText(firebaseVisionText.getText());
                        for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
                            for (FirebaseVisionText.Line line : block.getLines()) {
                                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                                for (FirebaseVisionText.Element element : line.getElements()) {
                                    String elementText = element.getText();
                                    Log.e("elementtext",elementText);
                                    input.add(element.getText());
                                }
                            }
                        }
                    }
                });
        return input;
    }
}

