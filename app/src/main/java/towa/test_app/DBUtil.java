package towa.test_app;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    static List<Task> tasks = new ArrayList<>();
    static int ToDoTaskCount = 0;
    static int DoingTaskCount = 0;
    static int DoneTaskCount = 0;

    public static List<Task> getTasks() {
        return tasks;
    }

    public static int getToDoTaskCount() {
        return ToDoTaskCount;
    }

    public static int getDoingTaskCount() {
        return DoingTaskCount;
    }

    public static int getDoneTaskCount() {
        return DoneTaskCount;
    }

    public static void DBinitialize(TaskDB taskDBInstance){
        Initialize initialize = new Initialize(taskDBInstance);
        initialize.execute();
    }

    public static List<Task> DBGetAllTask(TaskDB taskDBInstance, Context context){
        GetAllTask getAllTask = new GetAllTask(taskDBInstance, context);
        getAllTask.execute();
        return tasks;
    }

    public static void DBSaveNewTask(TaskDB taskDBInstance, Task task){
        SaveNewTask saveNewTask = new SaveNewTask(taskDBInstance, task);
        saveNewTask.execute();
    }

    public static void DBGetToDoTaskCount(TaskDB taskDBInstance, Context context){
        GetToDoTaskCount getToDoTaskCount = new GetToDoTaskCount(taskDBInstance, context);
        getToDoTaskCount.execute();
    }

    public static void DBGetDoingTaskCount(TaskDB taskDBInstance){
        GetDoingTaskCount getDoingTaskCount = new GetDoingTaskCount(taskDBInstance);
        getDoingTaskCount.execute();
    }

    public static void DBGetDoneTaskCount(TaskDB taskDBInstance){
        GetDoneTaskCount getDoneTaskCount = new GetDoneTaskCount(taskDBInstance);
        getDoneTaskCount.execute();
    }








    private static class Initialize extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        public Initialize(TaskDB taskDBInstance) {
            this.taskDBInstance = taskDBInstance;
        }
        @Override
        protected Void doInBackground(final Void...params){

            Task task = new Task();
            task.setShortDescription("Task 1");
            task.setLongDescription("Description for Task 1");
            task.setPercentage(0);
            taskDBInstance.taskDAO().insertTask(task);

            tasks = taskDBInstance.taskDAO().getAll();
            Log.d("LGF ", tasks.size() + " Tasks in DB");
            return null;
        }
    }

    private static class GetAllTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetAllTask(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            tasks = taskDBInstance.taskDAO().getAll();
            Log.d("LGF ", tasks.size() + " Tasks in DB");
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d("LGF Broadcast ", "AllTasksReady");
            Intent intent = new Intent();
            intent.setAction("com.LGF.CUSTOM_INTENT.AllTasksReady");
            context.sendBroadcast(intent);
        }

    }

    private static class SaveNewTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Task task;

        public SaveNewTask(TaskDB taskDBInstance, Task task) {
            this.taskDBInstance = taskDBInstance;
            this.task = task;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            taskDBInstance.taskDAO().insertTask(task);
            Log.d("LGF ", "Saving new task ");
            return null;
        }
    }

    private static class GetToDoTaskCount extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetToDoTaskCount(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            ToDoTaskCount = taskDBInstance.taskDAO().getToDo().size();
            Log.d("ToDoTaskCount", "" + ToDoTaskCount);
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d("LGF Broadcast ", "ToDoTaskCountReady");
            Intent intent = new Intent();
            intent.setAction("com.LGF.CUSTOM_INTENT.ToDoTaskCountReady");
            context.sendBroadcast(intent);
        }

    }

    private static class GetDoingTaskCount extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;

        public GetDoingTaskCount(TaskDB taskDBInstance) {
            this.taskDBInstance = taskDBInstance;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            DoingTaskCount = taskDBInstance.taskDAO().getDoing().size();
            Log.d("DoingTaskCount", "" + DoingTaskCount);
            return null;
        }
    }

    private static class GetDoneTaskCount extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;

        public GetDoneTaskCount(TaskDB taskDBInstance) {
            this.taskDBInstance = taskDBInstance;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            DoneTaskCount = taskDBInstance.taskDAO().getDone().size();
            Log.d("DoneTaskCount", "" + DoneTaskCount);
            return null;
        }
    }






}
