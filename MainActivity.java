package com.example.smartmuseum;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity implements Runnable {

    private static final int REQUEST_ENABLE_BT = 1;
    TextView receive;
    EditText input;
    Button start,button;
    List<ScanResult> inRange_wifi;
    ScanResult node_wifi;
    WifiManager wifi;
    Timer timer;
    Thread thread;
    Wifi_timer mytimertask;
    int counter;
    int input_counter;
    int avgk=0,avga=0;

    boolean flag=true;


    Server_conn server_connection;
    public static  String reply_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=(EditText)findViewById(R.id.counter);
        start=(Button)findViewById(R.id.start);
        receive=(TextView)findViewById(R.id.recieve);
        //timer = new Timer();



        //thread = new Thread(this);
        server_connection= new Server_conn();
        server_connection=new Server_conn("192.168.43.55",6668);




        start.setOnClickListener(new View.OnClickListener(){//Wifi
            @Override
            public void onClick(View v)
            {
                input_counter=Integer.parseInt(input.getText().toString());
                System.out.println(input_counter);
                wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                if (!wifi.isWifiEnabled())
                {
                    Toast.makeText(getApplicationContext(), "Enabling Wifi", Toast.LENGTH_LONG);
                    wifi.setWifiEnabled(true);
                }
                //thread.start();
                if(mytimertask!=null&&!mytimertask.isActive())
                {
                    mytimertask.deactivate();
                    mytimertask=null;
                }
                startTask();
            }
        });
 }
    public void display() {//Client Server
        runOnUiThread(new Runnable()
        {
            @Override
            public void run() {

                receive.append("\n"+reply_message);
            }
        });
    }//ends

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void run() {//Client Server
        while (true) {
            display();
            try {
                thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }//ends


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startTask()
    {

        timer = new Timer();
        mytimertask = new Wifi_timer();
        timer.schedule(mytimertask, 0, 2000);
    }

    class Wifi_timer extends TimerTask
    {//WIFI

        void deactivate()
        {
            avga=0;
            avgk=0;
            counter=0;
            cancel();
            flag = false;
        }
        public boolean isActive()
        {
            return flag;
        }
        @Override
        public void run()
        {
            flag=true;
            if(counter==input_counter)
            {
                try
                {
                    System.out.println("Waiting for next input");
                    avgk=avgk/input_counter;
                    avga=avga/input_counter;
                    String information="Uk"+avgk+":a"+avga+"|";
                    server_connection.client.send(information);
                    Thread.sleep(2000);
                    display();
                    deactivate();
                }
                catch(InterruptedException io)
                {
                    System.out.println("Exception");
                }
            }

            inRange_wifi = wifi.getScanResults();
            Iterator i = inRange_wifi.iterator();
            while (i.hasNext())
            {
                node_wifi = (ScanResult) i.next();
                int rssi_wifi = node_wifi.level;
                String ssid_wifi = node_wifi.SSID;
                if(ssid_wifi.contains("SMG1"))
                {
                    avgk+=rssi_wifi;
                }
                if(ssid_wifi.contains("SMG2"))
                {
                    avga+=rssi_wifi;
                }
            }
            counter++;
        }
    }//ends
}
