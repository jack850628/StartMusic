package com.example.startmusic;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
  
public class Receive_BootCompleted extends BroadcastReceiver{
	boolean start=true;
     @Override
     public void onReceive(Context context, Intent intent) {
        //we double check here for only boot complete event
    	 if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
         {
            //here we start the service            
            Intent serviceIntent = new Intent(context, Start.class);
            context.startService(serviceIntent);
        }
    }
}
