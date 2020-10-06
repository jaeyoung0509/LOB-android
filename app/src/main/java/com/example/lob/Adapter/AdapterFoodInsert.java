package com.example.lob.Adapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lob.DTO.FoodDTO;
import com.example.lob.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public  class AdapterFoodInsert extends BaseAdapter{
    private  Context context;
    List<FoodDTO> temporaryItems = new ArrayList<FoodDTO>();
    List<FoodDTO> realItems;
    DatePickerDialog.OnDateSetListener daateListener ;
    public AdapterFoodInsert(Context context){
        this.context= context;
    }
    @Override
    public int getCount() {
        return temporaryItems.size();
    }
    public void addItem(FoodDTO foodDTO){
        temporaryItems.add(foodDTO);
        notifyDataSetChanged();
    }
    @Override
    public FoodDTO getItem(int i) {
        return temporaryItems.get(i);
    }
    public  List<FoodDTO> getItems(){
        realItems= new ArrayList<FoodDTO>();
        for (int i =0 ;i<temporaryItems.size();i++){
            realItems.set(i,temporaryItems.get(i));
        }
        return  realItems;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    public void removeItem(int position) {
        temporaryItems.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_foodinsert, parent, false);
        EditText food_text = (EditText) view.findViewById(R.id.food_text);
        final TextView food_date = (TextView) view.findViewById(R.id.food_date);
        Button food_del = (Button) view.findViewById(R.id.food_del);
        final FoodDTO foodDTO = temporaryItems.get(position);
        food_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });
        food_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                temporaryItems.get(position).setFood_name(editable.toString());
            }
        });
        food_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(context , daateListener,2020,10,01);
                dateDialog.show();
            }
        });
        daateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year , int month, int dayOfMonth) {
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(year, month, dayOfMonth);
                cal.set(Calendar.HOUR_OF_DAY,6);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND,0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String calString = sdf.format(cal.getTime());
                try{
                    Date calDate = sdf.parse(calString);
                    food_date.setText(year + "Y " + month + "M " + dayOfMonth + "d");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        food_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                temporaryItems.get(position).setExpirationDate(editable.toString());

            }
        });
    return  view;
    }
}