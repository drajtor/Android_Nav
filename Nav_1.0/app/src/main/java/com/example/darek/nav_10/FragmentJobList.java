package com.example.darek.nav_10;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 212449139 on 2/3/2017.
 */
public class FragmentJobList extends Fragment  {

    Context context;

    ColorPainter colorPainter;

    private ListView listView;
    private JobListAdapter jobListAdapter;
    private ListElementViewHolder currentJob = null;
    private JobManager jobManager;

    private final int MARKED_ITEM_COLOR = 0xFF33B5E5;
    private final int ITEMS_COLOR = 0xFF0099CC;

    onJobSelectedListener onJobSelectedListenerCallback;

    public FragmentJobList(Context context){
        this.context = context;
        try {
            jobManager = ((MainActivity) this.context).getJobManager();
        }catch(ClassCastException e){
            throw new ClassCastException("Job List Fragment cannot cast context to Main Activity");
        }
    }

    public interface onJobSelectedListener {
        public void onJobSelected();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_list,container,false);

        listView = (ListView) view.findViewById(R.id.ListView_JobList);
        jobListAdapter = new JobListAdapter(listView.getContext(),R.layout.job_list_item, jobManager);
        listView.setAdapter(jobListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job job =  jobManager.get(position);
                String message = job.getJobName();
                Toast.makeText(listView.getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        try {
            onJobSelectedListenerCallback = ((onJobSelectedListener)context);
        }catch (ClassCastException e){
            throw new ClassCastException ("context to onJobSelectedListenerCallback interface cast failed");
        }

        NavApplication navApplication = (NavApplication)context.getApplicationContext();
        colorPainter = navApplication.getColorPainterForTracking();

        return view;
    }

    private class JobListAdapter extends ArrayAdapter<Job> {

        int layout;
        public JobListAdapter(Context context, int resource, List<Job> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView){
                LayoutInflater inflater = LayoutInflater.from(listView.getContext());
                convertView = inflater.inflate(layout,parent,false);
                final ListElementViewHolder listElementViewHolder = new ListElementViewHolder((ImageView) convertView.findViewById(R.id.imageView_listItem),
                                                                                                (TextView) convertView.findViewById(R.id.textView_listItem),
                                                                                                (Button) convertView.findViewById(R.id.button_listItem),
                                                                                                jobManager.get(position));
                listElementViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemClicked(listElementViewHolder);
                    }
                });
                int color = colorPainter.getBackgroundColor();
                setListElementColor(listElementViewHolder,color);
                convertView.setTag(listElementViewHolder);
            }else{
//                ListElementViewHolder viewHolder = (ListElementViewHolder)convertView.getTag();
//                viewHolder.text.setText(taskList.get(position));

            }
            return convertView;
        }
    }
    private class ListElementViewHolder{
        ImageView image;
        TextView text;
        Button button;
        Job job;

        private ListElementViewHolder (ImageView imageView, TextView textView, Button button, Job job){
            this.image = imageView;
            this.text = textView;
            this.text.setText(job.getJobName());
            this.job = job;
            this.button = button;
        }
    }

    private void ItemClicked (ListElementViewHolder listElementViewHolder){
        jobManager.setActiveJob(listElementViewHolder.job);

        if (null != currentJob){
            setListElementColor(currentJob,colorPainter.getButtonColor());
        }
        currentJob = listElementViewHolder;
        setListElementColor(currentJob,colorPainter.getButtonPushedColor());

        onJobSelectedListenerCallback.onJobSelected();
    }

    private void setListElementColor (ListElementViewHolder listElementViewHolder  ,int color){
        listElementViewHolder.text.setBackgroundColor(color);
        listElementViewHolder.image.setBackgroundColor(color);
        listElementViewHolder.button.setBackgroundColor(color);
    }

    public void onJobListUpdate() {
        jobListAdapter = new JobListAdapter(listView.getContext(),R.layout.job_list_item, jobManager);
        listView.setAdapter(jobListAdapter);
    }

    public void onJobStateChanged () {
        for (int i=0; i<jobListAdapter.getCount() ;i++ ) {
            ListElementViewHolder listElementViewHolder = (ListElementViewHolder)listView.getChildAt(i).getTag();
            setListElementColor(listElementViewHolder,colorPainter.getButtonColor());
        }
        if (null != currentJob){
            setListElementColor(currentJob,colorPainter.getButtonPushedColor());
        }
    }
}
