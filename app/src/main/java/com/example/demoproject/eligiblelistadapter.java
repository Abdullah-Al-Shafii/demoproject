package com.example.demoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class eligiblelistadapter  extends RecyclerView.Adapter<eligiblelistadapter.viewholder> {
    private Context context;
    private List<EligibleListStudents> uploadList;

    public eligiblelistadapter(Context context, List<EligibleListStudents> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public viewholder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.eligiblelistsample,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull eligiblelistadapter.viewholder holder, int position) {
       EligibleListStudents upload=uploadList.get(position);
       holder.eligiblelistsl.setText(upload.getSl());
       holder.eligiblelistid.setText(upload.getId());
       holder.eligiblelistname.setText(upload.getName());
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView eligiblelistsl,eligiblelistid,eligiblelistname;
        public viewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            eligiblelistsl=itemView.findViewById(R.id.EligibleListSLId);
            eligiblelistid=itemView.findViewById(R.id.EligibleListIdViewId);
            eligiblelistname=itemView.findViewById(R.id.EligibleListNameViewId);
        }

    }
}
