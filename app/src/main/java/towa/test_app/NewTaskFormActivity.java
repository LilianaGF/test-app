package towa.test_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.view.View;
import android.content.Intent;

import java.util.List;

public class NewTaskFormActivity extends AppCompatActivity {
    String tag = "LGF";

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_form);

        initiateTheSeekbarPercentage();
        addListenerToTheSeekbarPercentage();

        initiateTheSwitchDone();
        addListenerToTheSwitchDone();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void initiateTheSeekbarPercentage(){
        SeekBar seekbarPercentage = findViewById(R.id.seekbarPercentage);

        seekbarPercentage.setMax(100);

        int defaultTaskPercentage = 10;
        seekbarPercentage.setProgress(defaultTaskPercentage);
        defaultTaskPercentage = seekbarPercentage.getProgress();
        Log.d(tag, "Progress" + defaultTaskPercentage);

        TextView TheouttxtPercentage = findViewById(R.id.outtxtPercentage);
        String strDefaultTaskPercentage = defaultTaskPercentage + "%";
        TheouttxtPercentage.setText(strDefaultTaskPercentage);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void addListenerToTheSeekbarPercentage(){
        SeekBar seekbarPercentage = findViewById(R.id.seekbarPercentage);

        seekbarPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(tag, "Percentage changed: " + progressChangedValue);

                Switch switchDone = findViewById(R.id.switchDone);
                TextView outtxtPercentage = findViewById(R.id.outtxtPercentage);
                if (switchDone.isChecked()){
                    outtxtPercentage.setText("100%");
                }
                else{
                    String strTaskPercentage = progressChangedValue + "%";
                    outtxtPercentage.setText(strTaskPercentage);
                }
            }
        });
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void initiateTheSwitchDone(){
        Switch switchDone = findViewById(R.id.switchDone);
        if (switchDone.isChecked()){
            TextView outtxtPercentage = findViewById(R.id.outtxtPercentage);
            outtxtPercentage.setText("100%");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void addListenerToTheSwitchDone(){
        Switch switchDone = findViewById(R.id.switchDone);

        switchDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView outtxtPercentage = findViewById(R.id.outtxtPercentage);
                SeekBar seekbarPercentage = findViewById(R.id.seekbarPercentage);
                if(isChecked){

                    outtxtPercentage.setText("100%");
                }
                else {
                    int TaskPercentage = seekbarPercentage.getProgress();
                    String strTaskPercentage = TaskPercentage + "%";
                    outtxtPercentage.setText(strTaskPercentage);
                }
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------
    public void CancelNewTask(View view) {
        Log.d(tag, "click btnCancel");

        // Explicit Intent by specifying its class name
        android.content.Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void SaveNewTask(View view) {
        Log.d(tag, "click btnSave");

        Task task = new Task();

        //                                                  //Reading the short description.
        TextView intxtShortDescription = findViewById(R.id.intxtShortDescription);
        String strShortDescription = intxtShortDescription.getText().toString();
        task.setShortDescription(strShortDescription);

        //                                                  //Reading the long description.
        TextView intxtLongtDescription = findViewById(R.id.intxtLongDescription);
        String strLongDescription = intxtLongtDescription.getText().toString();
        task.setLongDescription(strLongDescription);

        //                                                  //Reading the percentage.
        Switch switchDone = findViewById(R.id.switchDone);
        if (switchDone.isChecked())
            task.setPercentage(100);
        else{
            SeekBar seekbarPercentage = findViewById(R.id.seekbarPercentage);
            task.setPercentage(seekbarPercentage.getProgress());
        }

        //                                                  //Asking to the DB to save.
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBSaveNewTask(taskDBInstance, task);

        //                                                  //Back to main activity.
        android.content.Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

   //------------------------------------------------------------------------------------------------------------------
    public void DeleteTask(View view, Task task) {

        //                                                  //Asking to the DB to save.
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBDeleteTask(taskDBInstance, task);

        //                                                  //Back to TaskList.
        android.content.Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
        startActivity(intent);
    }

    //------------------------------------------------------------------------------------------------------------------


}
/*END-ACTIVITY*/