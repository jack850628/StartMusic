package com.example.startmusic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Home extends ActionBarActivity {
	private Spinner sp;
    private final static String gte_NAME="Sfgtsql.pref";
    String md;
    int st;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		TextView tv=(TextView)findViewById(R.id.textView1);
		ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton1);
		ibutton.setOnClickListener(new iButtonClickListener());
		ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton2);
		ibutton2.setOnClickListener(new iButton2ClickListener());
		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton3);
		ibutton3.setOnClickListener(new iButton3ClickListener());
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new Button2ClickListener());
		SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
		setTitle("開機音效設定");
		md=spf.getString("music2","");
		tv.setText(md.substring(md.lastIndexOf("/")+1,md.length()));
		tv.setSelected(true);
		sp = (Spinner) findViewById(R.id.spinner1);
		if(spf.getInt("music", 0)==0){
			 sp.setSelection(0);
		}else if(spf.getInt("music", 0)==1){
			sp.setSelection(1);
		}else if(spf.getInt("music", 0)==2){
			sp.setSelection(2);
		}else if(spf.getInt("music", 0)==3){
			sp.setSelection(3);
		}
		sp.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int po, long arg3) {
            	TextView tv=(TextView)findViewById(R.id.textView1);
            	ImageButton ibutton = (ImageButton) findViewById(R.id.imageButton1);
            	ImageButton ibutton2 = (ImageButton) findViewById(R.id.imageButton2);
        		ImageButton ibutton3 = (ImageButton) findViewById(R.id.imageButton3);
               switch(po){
               case 0:
            	   st=0;
            	   tv.setVisibility(View.GONE);
          			ibutton3.setVisibility(View.GONE);
          			ibutton.setVisibility(View.GONE);
       			ibutton2.setVisibility(View.GONE);
       		    mp.stop();
       		    mp.start();
            	   break;
               case 1:
            	   st=1;
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
         	    tv.setVisibility(View.GONE);
         	    ibutton3.setVisibility(View.GONE);
         	   mp.stop();
         	   mp.start();
         	   break;
               case 2:
            	   st=2;
            	   ibutton.setVisibility(View.VISIBLE);
       			   ibutton2.setVisibility(View.VISIBLE);
            	    tv.setVisibility(View.GONE);
            	    ibutton3.setVisibility(View.GONE);
            	    mp.stop();
              	   mp.start();
  			 break;
               case 3:
            	   st=3;
       			tv.setVisibility(View.VISIBLE);
       			ibutton3.setVisibility(View.VISIBLE);
       			ibutton.setVisibility(View.VISIBLE);
    			ibutton2.setVisibility(View.VISIBLE);
  			 break;
              
               }
               

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
	}
	final MediaPlayer mp = new MediaPlayer();
	MediaPlayer ump = new MediaPlayer();
	class iButtonClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	if(st==1){
	    		ump.stop();
	    		ump=MediaPlayer.create(getApplicationContext(), R.raw.start);
	            ump.start();
	            mp.stop();
			}else if(st==2){
				ump.stop();
	    		ump=MediaPlayer.create(getApplicationContext(), R.raw.start2);
	            ump.start();
	            mp.stop();
			}else{
		    	  try {
	                  mp.setDataSource(md);
	                  mp.prepare();
	               } catch (IllegalArgumentException e) {
	             } catch (IllegalStateException e) {
	             } catch (IOException e) {
	             }
		          mp.start();
		          ump.stop();
			}
	    }
}
	class iButton2ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	mp.stop();
	    	ump.stop();
	    	if(st!=1&&st!=2){
	    		mp.start();
			}
	    }
}
	class iButton3ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	   ump.stop();
	           mp.stop();
		    	if(st!=1&&st!=2){
		    		mp.start();
				}
	    	Intent intent = new Intent();
	           intent.setType("audio/*");                                   
	            intent.setAction(Intent.ACTION_GET_CONTENT); 
	            startActivityForResult(intent, 1);
	    }
}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	TextView tv=(TextView)findViewById(R.id.textView1);
    if (resultCode == RESULT_OK) {
       Uri uri = data.getData();
       String[] gh = uri.toString().split(":");
       if(gh[0].equals("content")){
           String[] proj = { MediaStore.Images.Media.DATA };
           Cursor act = managedQuery(uri,proj,null,null,null);
           int actual = act.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
           act.moveToFirst();
           md=act.getString(actual);
       }else{
    	   try {
			md=URLDecoder.decode(uri.toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       tv.setText(md.substring(md.lastIndexOf("/")+1,md.length()));
    }
      super.onActivityResult(requestCode, resultCode, data);
    }
	class ButtonClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	SharedPreferences spf =  getSharedPreferences(gte_NAME,0);
	    	SharedPreferences.Editor ed=spf.edit();
	    	ed.putString("music2", md);
     	   ed.commit();
     	  ed.putInt("music", st);
   	      ed.commit();
   	   ump.stop();
       mp.stop();
   	      finish();
	    }
}
	class Button2ClickListener implements  OnClickListener {
	    @Override
	    public void onClick(View V) {
	    	ump.stop();
            mp.stop();
	    	finish();
	    }
}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
	    	ump.stop();
            mp.stop();
	    	finish();
        }
        return false;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("關於");
	    	builder.setMessage("Start Music 開機音效\n"+"版本：1.0");
	    	builder.setNegativeButton("確定", null);
	    	AlertDialog ad = builder.create();
	    	ad.show(); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}

}
