package vishnugt.deltatask1timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String counterstring = "0:00:000";
	Integer counterint = 0;
	Integer currentfunction = 0;//0 for no function, 1 for start, 2 for pause, 3 for reset.
	Integer seconds=0,minutes=0;
	long milliseconds=0;
	TextView countertextview= null;
	//TextView result = null;
	ListView m_listview;
	List<String> finalresult = new ArrayList<String>();
	String[] resultlist = new String[50];
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		countertextview = (TextView)findViewById(R.id.textView1);
		countertextview.setText("0:00:000");
		//result = (TextView)findViewById(R.id.textView2);
		m_listview = (ListView) findViewById(R.id.id_list_view);
		
		
	    	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;	
	}

	public void startfunction(View va)//buttonclick calls this function this function creates a new thread 
	{
		if(currentfunction==1)
		{
			Toast.makeText(getApplicationContext(), "You have already started the time", Toast.LENGTH_SHORT).show();
		}
		final View vv= va;
		new Thread(new Runnable() {
			    public void run() {
			    try {
					startfunction1(vv);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
			    }
			  }).start();
	}
	int sysseconds=0;
	Long nowmillisecs=Long.MIN_VALUE;
	Calendar c2;
	
	public void startfunction1(View v) throws InterruptedException
	{	

		if(nowmillisecs==Long.MIN_VALUE && sysseconds==0)
		{
			
			Calendar c = Calendar.getInstance(); 
			sysseconds = c.get(Calendar.SECOND);
			nowmillisecs=c.getTimeInMillis();
		}
			
		if(currentfunction==1 )
		{

		}
		else
		{
			
			currentfunction =1;
			
			while(currentfunction==1 && nowmillisecs != Long.MAX_VALUE)
			{
				c2=Calendar.getInstance();
				milliseconds=c2.getTimeInMillis()- nowmillisecs;
				milliseconds=milliseconds%1000;
				seconds = c2.get(Calendar.SECOND)- sysseconds;
				minutes = seconds/60;
				seconds = seconds%60;
				if(seconds<0)
					seconds = 60-seconds;
				if(milliseconds<1000)
					milliseconds = 1000 - milliseconds;
				Message msg = Message.obtain(uiHandler);
                msg.obj = "new value...";
                uiHandler.sendMessage(msg);
                if(currentfunction==3)
                {

                	resettime();
                	Thread.currentThread().interrupt();
                	return;
                }
			}
		}
	}
	private Handler uiHandler = new UIHandler();

	@SuppressLint("HandlerLeak")
	class UIHandler extends Handler {
	    @Override
	    public void handleMessage(Message msg) {
	    	countertextview.setText(minutes + ":"+ String.format("%02d", seconds) + ":"+ String.format("%03d", milliseconds));
			super.handleMessage(msg);
	    }
	}	String resulttext;
		public void pausefunction(View v)
		{
			
			if(currentfunction!=2 && currentfunction !=3)
			{
			countertextview.setText(minutes + ":"+ String.format("%02d", seconds) + ":"+ String.format("%03d", milliseconds));
			currentfunction = 2;
			}
			else
			{
			if(currentfunction==2)
				//Toast.makeText(getApplicationContext(), "You have already paused the time", Toast.LENGTH_SHORT).show();
			if(currentfunction==3)
				Toast.makeText(getApplicationContext(), "You had the 	time reset, so start a new one", Toast.LENGTH_SHORT).show();
			}
		}
		String resultatlast;
		int nooflaps=0;
		public void resetfunction(View v)
		{
			
			if(currentfunction!=3)
			{
				Thread[] threads = new Thread[Thread.activeCount()];
				Thread.enumerate(threads);
				for (Thread t : threads) 
				{
					t.interrupt();
				}
			pausefunction(v);
			nooflaps++;
			currentfunction = 3;
			resulttext=minutes + ":"+ String.format("%02d", seconds) + ":"+ String.format("%03d", milliseconds);
			resultatlast=("Lap Number	" + nooflaps + "	:	" + resulttext + "\n\n");
			finalresult.add(resultatlast);
		    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, finalresult);
		    m_listview.setAdapter(adapter);  
			nowmillisecs=Long.MIN_VALUE;
			sysseconds=0;
			for(int i=0;i<1000;i++){}//creating time lag so as to give time to stop the other thread
			resettime();
			countertextview.setText("0:00:000");
			resetfunction(v);
			}
			else 
			{
				resettime();
				//Toast.makeText(getApplicationContext(), "You have already reset the time", Toast.LENGTH_SHORT).show();
			}
			resettime();
			
		}
		public void resettime()
		{

			countertextview.setText("0:00:000");	
		}
}