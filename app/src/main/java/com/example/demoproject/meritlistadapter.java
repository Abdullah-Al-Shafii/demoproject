package com.example.demoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class meritlistadapter  extends RecyclerView.Adapter<meritlistadapter.viewholder>{
    private Context context;
    private List<MeritListStudents> muploadList;

    public meritlistadapter(Context context, List<MeritListStudents> muploadList) {
        this.context = context;
        this.muploadList = muploadList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public meritlistadapter.viewholder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.meritlistsample,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull meritlistadapter.viewholder holder, int position) {
        MeritListStudents upload=muploadList.get(position);
        holder.meritlistid.setText(upload.getId());
        holder.meritlistmerit.setText(upload.getMerit());
    }


    @Override
    public int getItemCount() {
        return muploadList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView meritlistid,meritlistmerit;
        public viewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            meritlistid=itemView.findViewById(R.id.MeritListIdViewId);
            meritlistmerit=itemView.findViewById(R.id.MeritListMeritViewId);
        }

    }
}
