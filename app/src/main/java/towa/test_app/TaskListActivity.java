package towa.test_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //                                                  //To register a receiver of the broadcast
        BroadcastReceiver showAllTaskReceiver = new ShowAllTaskReceiver();
        IntentFilter intentFilter = new IntentFilter("com.LGF.CUSTOM_INTENT.AllTasksReady");
        this.registerReceiver(showAllTaskReceiver, intentFilter);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume()
    //                                                  //Called when the activity has become visible.
    {
        super.onResume();

        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance, getApplicationContext());
    }

    //------------------------------------------------------------------------------------------------------------------
    private class ShowAllTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //                                              //When com.LGF.CUSTOM_INTENT.AllTasksReady occurs

            List<Task> tasks = DBUtil.getTasks();
            for (Task task: tasks){
                Log.d("LGF - Tasks ", task.getShortDescription() + ", " +
                        String.valueOf(task.getPercentage()));
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
/*END-ACTIVITY*/