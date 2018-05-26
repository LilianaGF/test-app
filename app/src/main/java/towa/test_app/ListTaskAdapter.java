package towa.test_app;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ItemTaskHolder>
{
    private List<Task> listOfTasks;

    public ListTaskAdapter(List<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    //------------------------------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public ListTaskAdapter.ItemTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Boolean attachViewImmediatelyToParent = false;

        View singleItemLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_task_item,parent,attachViewImmediatelyToParent);

        ItemTaskHolder itemTaskHolder = new ItemTaskHolder(singleItemLayout);
        return itemTaskHolder;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    class ItemTaskHolder extends RecyclerView.ViewHolder {
        TextView textToShow;

        public ItemTaskHolder(View itemView) {
            super(itemView);

            textToShow = (TextView) itemView.findViewById(R.id.taskItem);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull ListTaskAdapter.ItemTaskHolder holder, int position) {

        Task task = listOfTasks.get(position);
        String strTask = task.getShortDescription() + "    " +
                String.valueOf(task.getPercentage())+ "%";
        holder.textToShow.setText(strTask);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return listOfTasks.size();
    }

    //------------------------------------------------------------------------------------------------------------------

}
/*END-ADAPTER*/