package com.aquarian.drivers.ui.jobs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aquarian.drivers.R;
import com.aquarian.drivers.ui.jobsActivity.JobsActivityExtended;

import java.util.ArrayList;
import java.util.List;

//import com.squareup.picasso.Picasso;



public class JobsAdapter extends RecyclerView.Adapter<JobsHolder> {

    private final List<Job> jobList;

    public JobsAdapter(ArrayList jobs) {
        jobList = jobs;
    }

    @Override
    public JobsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View newsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobslist, parent, false);

        return new JobsHolder(newsView);

    }

    @Override
    public void onBindViewHolder(JobsHolder holder, int position) {
        holder.nJobId.setText("Job #" + jobList.get(position).getId());
        holder.nStatus.setText("Status: " + jobList.get(position).getStatus());
        holder.nDetails.setText("Type: " + jobList.get(position).getParcelType()
                + "\nSize: " + jobList.get(position).getParcelSize()
                + "\nWeight: " + jobList.get(position).getParcelWeight());
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

}

class JobsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nJobId;
    public TextView nStatus;
    public TextView nDetails;

    public JobsHolder(View itemView) {
        super(itemView);
        nJobId = (TextView) itemView.findViewById(R.id.jobId);
        nStatus = (TextView) itemView.findViewById(R.id.jobStatus);
        nDetails = (TextView) itemView.findViewById(R.id.jobDetails);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        Intent intent = new Intent(view.getContext(), JobsActivityExtended.class);
        String[] ida = nJobId.getText().toString().split("#");
        String id = ida[1];

        intent.putExtra("JobID", id);
        view.getContext().startActivity(intent);

    }
}
