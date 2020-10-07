package com.example.lob.Service;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.lob.DTO.FoodDTO;
import com.example.lob.Dialog.FoodDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient extends Thread {
    Activity activity;
    String s= null;
    String host ="35.225.2.236";
    int port =80;
    Socket socket =null;
    PrintWriter outputStream = null;
    BufferedReader inputStream = null;
    public  SocketClient(String s , Activity activity){
        this.s=s;
        this.activity = activity;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

   public void run() {
        try{
            socket=new Socket(host,port);
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            outputStream.print(s);
            outputStream.flush();
            Log.d("ClientThread", outputStream.toString());
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = inputStream.readLine();
            Log.e("input ",input);
            String [] splitdata = input.split(",");
            ArrayList<FoodDTO> foodDTOS = new ArrayList<FoodDTO>() ;
            for(int i= 0; i<splitdata.length; i++){
                            foodDTOS.add(new FoodDTO(splitdata[i], ""));
            }
            Log.d("ClientThread","받은 데이터 : "+input);
            //dialg
            if(activity!=null){
                FoodDialog foodDialog = new FoodDialog( activity);
                foodDialog.setFoodDTOS(foodDTOS);
                foodDialog.show();

            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                socket.close();
                inputStream.close();
                outputStream.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }
}
