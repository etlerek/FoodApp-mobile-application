package com.example.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.entity.Step;

import java.util.List;


public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final Context context;
    private List<Step> steps;

    public StepsAdapter(Context context, List<Step> steps){
        super();
        this.context = context;
        this.steps = steps;
    }

    public void setItems(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        StepViewHolder stepVH = (StepViewHolder) holder;
        Step step = steps.get(position);

        stepVH.tvTitle.setText(step.getStepNumber() + ": " + step.getTitle());
        stepVH.tvContent.setText(step.getContent());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvContent;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.step_title);
            tvContent = itemView.findViewById(R.id.step_content);
        }
    }
}

