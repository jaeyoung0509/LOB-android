package com.example.lob;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient extends Thread {
    int count ;
    String s= null;
    String host ="192.168.0.103";
    int port =9999;
    Socket socket =null;
    PrintWriter outputStream = null;
    BufferedReader inputStream = null;
    public SocketClient(int count , String s){
        Log.e("123123122aaaaa","123");
        this.count=count;
        this.s=s;
    }

    public void run(){
        Log.e("1a","123");
        try{
            Log.d("ClientThread3", "서버로 보냄.");

            socket=new Socket(host,port);
            Log.d("ClientThread2", "서버로 보냄.");

            outputStream = new PrintWriter(socket.getOutputStream(), true);
            Log.d("ClientThread666", "서버로 보냄.");

            outputStream.print("count" + String.valueOf(count) + "s" + s);
            Log.d("ClientThread7777", "서버로 보냄.");

            outputStream.flush();
            Log.d("ClientThread", outputStream.toString());
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = inputStream.readLine();
            String [] splitdata = input.split("\t");
            input = splitdata[splitdata.length-1];
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
