package com.example.startmusic;

import java.io.IOException;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Start extends Service {
	private final static String gte_NAME="Sfgtsql.pref";
	boolean start=true;
    int nl1,nl2;
    AudioManager am; 
    @Override
    public IBinder onBind(Intent intent) {
          return null;
    }

    @Override
    public void onCreate() {
          super.onCreate();
              // do something when the service is created
      	SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
      	if(spf.getInt("music", 0)!=0){
      		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			nl1 = am.getStreamVolume( AudioManager.STREAM_SYSTEM )*2;
            nl2 = am.getStreamVolume( AudioManager.STREAM_MUSIC );
			am.setStreamVolume(AudioManager.STREAM_MUSIC, nl1, 0);
    		if(spf.getInt("music", 0)==1){
        		MediaPlayer ump=MediaPlayer.create(getApplicationContext(), R.raw.start);
                ump.start();
                ump.setOnCompletionListener(new OnCompletionListener()   
	            {  
	                public void onCompletion(MediaPlayer arg0)  
	                {  
	                	stop(); 
	                }  
	            }); 
    		}else if(spf.getInt("music", 0)==2){
    			MediaPlayer ump=MediaPlayer.create(getApplicationContext(), R.raw.start2);
                ump.start();
                ump.setOnCompletionListener(new OnCompletionListener()   
	            {  
	                public void onCompletion(MediaPlayer arg0)  
	                {  
	                	stop(); 
	                }  
	            }); 
			}else{
              MediaPlayer mp = new MediaPlayer();
    	    	  try {
                      mp.setDataSource(spf.getString("music2",""));
                      mp.prepare();
                   } catch (IllegalArgumentException e) {
                 } catch (IllegalStateException e) {
                 } catch (IOException e) {
                 }
    	          mp.start();
    	          mp.setOnCompletionListener(new OnCompletionListener()   
    	            {  
    	                public void onCompletion(MediaPlayer arg0)  
    	                {  
    	                	stop(); 
    	                }  
    	            });  
    		}
    	}else{
    		stop(); 
    	}
    }
    public void stop(){
    	am.setStreamVolume(AudioManager.STREAM_MUSIC, nl2, 0);
      	Intent Service=new Intent(this,Start.class);
        Service.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.stopService(Service); 
    }
}