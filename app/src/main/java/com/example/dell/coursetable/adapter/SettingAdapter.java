package com.example.dell.coursetable.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dell.coursetable.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tyk on 2018/2/25.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> implements View.OnClickListener {
    private List<String> list;
    private Context context;
    private Map<Integer, Boolean> map = new HashMap<>();
    private RecyclerViewOnItemClickListener onItemClickListener;

    public SettingAdapter(List<String> list, Context context){
        this.context = context;
        this.list = list;
        initMap();
    }

    private void initMap(){
        for(int i = 0; i<list.size(); i++){
            map.put(i,false);
        }
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private CheckBox checkBox;
        private View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            type = (TextView)view.findViewById(R.id.type_name);
            checkBox = (CheckBox) view.findViewById(R.id.choose);
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.type.setText(list.get(position));
        holder.view.setTag(position);
        holder.checkBox.setClickable(false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_anim);
        holder.checkBox.startAnimation(animation);
        if(map.get(position)==null){
            map.put(position,false);
        }
        holder.checkBox.setChecked(map.get(position));
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item,null,false);
        ViewHolder viewHolder = new ViewHolder(root);
        root.setOnClickListener(this);
        return viewHolder;
    }
    @Override
    public void onClick(View view){
        if(onItemClickListener != null){
            onItemClickListener.onItemClickListener(view,(Integer) view.getTag());
        }
    }
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setSelectItem(int position){
        if(map.get(position)){
            map.put(position,false);
        }else {
            map.put(position,true);
        }
        notifyItemChanged(position);
    }
    public Map<Integer, Boolean> getMap(){
        return map;
    }

    public interface RecyclerViewOnItemClickListener{
        void onItemClickListener(View view, int position);
    }
}
