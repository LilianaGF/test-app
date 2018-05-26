package towa.test_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    //                                                      //tag for Log.d() method.
    final static String tag = "LGF";



    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
        //                                                  //Called when the activity is first created.
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //                                                  //Debugging purpose.
        Log.d(tag, "The onCreate() event");

        //                                                  //To register a receiver of the broadcast
        BroadcastReceiver updateTaskCountReceiver = new UpdateTaskCountReceiver();
        IntentFilter intentFilter = new IntentFilter("com.LGF.CUSTOM_INTENT.TasksCountReady");
        this.registerReceiver(updateTaskCountReceiver, intentFilter);

        //                                                  //Initialization of the DB
        //TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        //DBUtil.DBinitialize(taskDBInstance);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onStart()
        //                                                  //Called when the activity is about to become visible.
    {
        super.onStart();
        //                                                  //Debugging purpose.
        Log.d(tag, "The onStart() event");
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume()
        //                                                  //Called when the activity has become visible.
    {
        super.onResume();
        //                                                  //Debugging purpose.
        Log.d(tag, "The onResume() event");
        //                                                  //Toast test code.
        Toast toast1 = Toast.makeText(getApplicationContext(), "onResume() event", Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0,0 );
        toast1.show();

        updateTaskCount();
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void updateTaskCount()
    {
        //                                                  //broadcast com.LGF.CUSTOM_INTENT.TaskReady
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBTaskCount(taskDBInstance, getApplicationContext());
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onPause()
        //                                              //Called when another activity is taking focus.
    {
        super.onPause();
        //                                                  //Debugging purpose.
        Log.d(tag, "The onPause() event");
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onStop()
        //                                                  //Called when the activity is no longer visible.
    {
        super.onStop();
        //                                                  //Debugging purpose.
        Log.d(tag, "The onStop() event");
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void onDestroy()
        //                                                  //Called just before the activity is destroyed.
    {
        super.onDestroy();
        //                                                  //Debugging purpose.
        Log.d(tag, "The onDestroy() event");
        //                                                  //Destroy de DB INSTANCE.
        TaskDB.destroyInstance();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void ShowNewTaskForm(View view)
        //                                                  //onClick-btnNewTask (New button)
    {
        //                                                  //To verify the click get the method.
        Log.d(tag, "click btnNewTask");

        //                                                  //Explicit Intent to start NewTaskFormActivity.
        Intent intent = new Intent(getApplicationContext(), NewTaskFormActivity.class);
        startActivity(intent);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void ShowAllTasks(View view)
        //                                                  //onClick-btnShowAll (Show All button)
    {
        //                                                  //To verify the click get the method.
        Log.d(tag, "click btnShowAll");

        //                                                  //Explicit Intent to start TaskListActivity.
        Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
        startActivity(intent);
    }

    //------------------------------------------------------------------------------------------------------------------
    private class UpdateTaskCountReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //                                              //When com.LGF.CUSTOM_INTENT.ToDoTaskCountReady occurs

            int intToDoTask = DBUtil.getToDoTaskCount();
            TextView outtxtTaskToDo = findViewById(R.id.outtxtTaskToDo);
            outtxtTaskToDo.setText(String.valueOf(intToDoTask) + " Task To Do");

            int intDoingTask = DBUtil.getDoingTaskCount();
            TextView outtxtTaskDoing = findViewById(R.id.outtxtTaskDoing);
            outtxtTaskDoing.setText(String.valueOf(intDoingTask) + " Task Doing");

            int intDoneTask = DBUtil.getDoneTaskCount();
            TextView outtxtTaskDone = findViewById(R.id.outtxtTaskDone);
            outtxtTaskDone.setText(String.valueOf(intDoneTask) + " Task Done");
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
/*END-ACTIVITY*/


/*EXAMPLE CODE*/
//------------------------------------------------------------------------------------------------------------------
//                                                          //To broadcast a custom intent
/*
public void broadcastIntent(View view)
{
    Log.d(tag, "Sending com.LGF.CUSTOM_INTENT...");
    Intent intent = new Intent();
    intent.setAction("com.LGF.CUSTOM_INTENT");
    sendBroadcast(intent);
}
*/

//------------------------------------------------------------------------------------------------------------------
//                                                          //Logging
//Log.e(tag, "...");
//Log.w(tag, "...");
//Log.i(tag, "...");
//Log.d(tag, "...");
//Log.v(tag, "...");
//Log.wtf(tag, "...");

//------------------------------------------------------------------------------------------------------------------
//                                                          //Toasting
/*
Toast toast1 = Toast.makeText(context, "...", Toast.LENGTH_SHORT);
toast1.setGravity(Gravity.CENTER, 0,0 );
toast1.show();
*/
