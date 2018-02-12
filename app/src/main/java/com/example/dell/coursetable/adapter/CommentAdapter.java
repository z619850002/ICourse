package com.example.dell.coursetable.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.coursetable.R;
import com.example.dell.coursetable.gson.Remark;

import java.util.List;

/**
 * Created by 田雍恺 on 2018/2/12.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<Remark> commentDataList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userComment;
        public ViewHolder(View view){
            super(view);
            userComment = (TextView) view.findViewById(R.id.user_comment);
        }
    }

    public CommentAdapter(List<Remark> commentDataList){
        this.commentDataList = commentDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Remark commentData = commentDataList.get(position);
        holder.userComment.setText(commentData.getContent());
    }

    @Override
    public int getItemCount(){
        return commentDataList.size();
    }
}
