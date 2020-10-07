package com.example.lob.Service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient extends Thread {
    String s= null;
    String host ="35.225.2.236";
    int port =80;
    Socket socket =null;
    PrintWriter outputStream = null;
    BufferedReader inputStream = null;
    public  SocketClient(String s){
        this.s=s;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    public void run(){
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
            Log.d("ClientThread","받은 데이터 : "+input);
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
