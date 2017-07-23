package com.example.smartmuseum;

/**
 * Created on 05/03/2016.
 */
public class Server_conn implements Runnable {
    Thread thread;
    Chat client;
    int port;
    String ip_addr;


    public Server_conn()
    {
        thread = new Thread(this);
        thread.start();

    }

    public Server_conn(String a,int b){
        thread = new Thread(this);
        thread.start();
        port=b;
        ip_addr=a;
    }


    @Override
    public synchronized void run() {

        if(client == null) {
            try {
                client = new Chat(ip_addr,port);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (client !=null){
            if(client != null && Chat.isconnected)
            {
                client.recieve();
            }
        }

    }
}
