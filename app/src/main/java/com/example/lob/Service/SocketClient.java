package com.example.lob.Service;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.lob.DTO.FoodDTO;
import com.example.lob.Dialog.FoodDialog;
import com.example.lob.Interface.SocketCallback;
import com.example.lob.UI.setting.SettingFragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SocketClient extends Thread  implements  Runnable      {
    Context context;
    ArrayList<FoodDTO> foodDTOS = null;
    String input;
    Handler mainHandler , backHandler;
    String s= null;
    String host ="35.225.2.236";
    int port =80;
    String [] splitdata ;
    Socket socket =null;
    SettingFragment settingFragment= new SettingFragment();
    PrintWriter outputStream = null;
   public  BufferedReader inputStream = null;
   public Callable<String> task ;
    public  SocketClient(String s , Context context , Handler mainHandler , Handler backHandler){
        this.s=s;
        this.context = context;
        this.mainHandler = mainHandler;
        this.backHandler = backHandler;
    }
    public  SocketClient(String s , Context context ){
        this.s=s;
        this.context = context;
    }


    public void setSplitdata(String[] splitdata) {
        this.splitdata = splitdata;
    }

    public String[] getSplitdata() {
        return splitdata;
    }

    public ArrayList<FoodDTO> getFoodDTOS() {
        return foodDTOS;
    }

    public void setFoodDTOS(ArrayList<FoodDTO> foodDTOS) {
        this.foodDTOS = foodDTOS;
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
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputRead = inputStream.readLine();
            splitdata = inputRead.split(",");
            setSplitdata(splitdata);
            for(int i =0 ; i< splitdata.length; i ++){
                Log.e("splitdata" , splitdata[i]);

            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                socket.close();
                inputStream.close();
        //        outputStream.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

}
