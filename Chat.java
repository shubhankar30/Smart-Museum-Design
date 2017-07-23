package com.example.smartmuseum;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created on 05/03/2016.
 */

public class Chat {
    private Socket socket              = null;
    private DataInputStream console    = null;
    private DataOutputStream streamOut = null;
    private DataInputStream streamIn = null;
    public static boolean isconnected = false;

    public Chat(String serverName, int serverPort){
        //System.out.println("Please wait ...");
        try{
            socket = new Socket(serverName, serverPort);
            //  Log.d("DEBUG", "Connected: " + socket);
            if(socket != null) {
                start();
            }
        }
        catch(UnknownHostException uhe){
            Log.d("DEBUG", "Host unknown: " + uhe.getMessage());
        }
        catch(IOException ioe){
            Log.d("DEBUG","Unexpected exception: " + ioe.getMessage());
        }

    }

    public void start() throws IOException
    {
        console   = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
        isconnected = true;

    }

    public void stop(){
        try{
            if (console   != null)  console.close();
            if (streamOut != null)  streamOut.close();
            if (socket    != null)  socket.close();
        }
        catch(IOException ioe){
            System.out.println("Error closing ...");
        }
    }
    public void open() throws IOException{

        streamIn  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }
    public void close() throws IOException{
        if (socket != null)    socket.close();
        if (console != null)  console.close();
    }

    public void send(String msg)
    {
        try
        {
            if(msg != null) {
                streamOut.writeUTF(msg);
                streamOut.flush();
            }
        }
        catch(IOException ioe)
        {
            Log.d("DEBUG","Sending error: " + ioe.getMessage());
        }
    }
    public void recieve(){
        try{
            open();
            boolean done = false;

            while (!done){
                try{
                    String line = streamIn.readUTF();
                    if(!(line.equals("")))
                    {
                        MainActivity.reply_message =line;
                        //MainActivity.puffer = line;
                    }
                    //System.out.println(" " + line);
                    done = line.equals(".bye");
                }
                catch(IOException ioe){
                    done = true;
                    ioe.printStackTrace();
                }
            }
            close();
        }
        catch(IOException ie){
            System.out.println("Acceptance Error: " + ie);
        }
    }
}
