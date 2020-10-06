package com.example.lob.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lob.Adapter.AdapterFoodInsert;
import com.example.lob.DTO.FoodDTO;
import com.example.lob.R;

import java.util.ArrayList;
import java.util.List;

public class FoodDialog extends Dialog {
    AdapterFoodInsert adapterFoodInsert;
    Button food_add ,food_insert;
    ListView foodIndexList;
    List<FoodDTO> foodDTOS = new ArrayList<FoodDTO>();
    FoodInsertDialogListener foodInsertDialogListener;
    public interface FoodInsertDialogListener{
        void onPositiveClicked(List<FoodDTO> foodDTOList);
    }

    public void setDialogListener(FoodInsertDialogListener foodInsertDialogListener){
        this.foodInsertDialogListener = foodInsertDialogListener;
    }

    public FoodDialog(Context context){
        super(context);
        setContentView(R.layout.dialog_food);
        food_insert = findViewById(R.id.food_insert);
        adapterFoodInsert = new AdapterFoodInsert(context);
        foodIndexList = findViewById(R.id.foodIndexList);
        foodIndexList.setAdapter(adapterFoodInsert);
        food_add = findViewById(R.id.food_add);

        food_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterFoodInsert.addItem(new FoodDTO("",""));
                foodDTOS=adapterFoodInsert.getItems();
            }
        });
        food_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodDTOS = new ArrayList<>();
                for(int i =0 ; i < adapterFoodInsert.getCount(); i++){
                    if(!adapterFoodInsert.getItem(i).getFood_name().equals("")){
                        foodDTOS.add(adapterFoodInsert.getItem(i));
                    }
                }
                if (foodDTOS.size() > 0) {
                } else {
                    foodDTOS=null;
                }
                foodInsertDialogListener.onPositiveClicked(foodDTOS);
                dismiss();
            }
        });
    }
    public ArrayList<FoodDTO> getFoodDTOS(){
        return (ArrayList<FoodDTO>) foodDTOS;
    }
}
