package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class bullentin_board_adpter extends RecyclerView.Adapter<bullentin_board_adpter.ViewHolder> {
    //layout 객체화 시키기 위해 Context 객체의 변수생성
    Context context;
    ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>(); //List 데이터 관리하기 위해 ArrayList 객체의 변수 생성

    public bullentin_board_adpter (Context context){
        this.context = context;
    }

    @NonNull
    @Override
    //reycyclerview에서 사용할 viewholder 생성할때 사용되는 메서드
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recyclerview_bullentin_board, parent,false);

        return new ViewHolder(itemView); //viewholder 반환
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size(); //표시할 아이템의 갯수
    }

    public void addItem(HashMap<String, String> stringStringHashMap) {
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
        }

        public void setItem(HashMap<String,String> item) {
            title.setText(item.get("card_title"));
            name.setText(item.get("card_name"));
        }
    }
}
