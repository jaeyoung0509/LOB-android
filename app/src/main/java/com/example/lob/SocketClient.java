package com.example.lob;

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
    public SocketClient(String s){
        Log.e("123123122aaaaa","123");
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

            outputStream.print(s);
            Log.d("ClientThread7777", "서버로 보냄.");

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
