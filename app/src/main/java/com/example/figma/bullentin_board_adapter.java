package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class bullentin_board_adapter extends RecyclerView.Adapter<bullentin_board_adapter.ViewHolder> {

    private ArrayList<bullentin_DB> arrayList;
    private Context context;
    private ArrayList<bullentin_DB> filteredarrayList;


    public bullentin_board_adapter(ArrayList<bullentin_DB> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.filteredarrayList = new ArrayList<>(arrayList);
    }

    public void filter(String query){
        filteredarrayList.clear();
        if(query.isEmpty()){
            filteredarrayList.addAll(arrayList);
        } else {
            for(bullentin_DB db : arrayList){
                if(db.getTitle().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))){
                    filteredarrayList.add(db);
                }
            }
        }
        notifyDataSetChanged();
        if(filteredarrayList.isEmpty()){
            Toast.makeText(context,"검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public bullentin_board_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        bullentin_board_adapter.ViewHolder holder = new bullentin_board_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bullentin_board_adapter.ViewHolder holder, int position) {
        holder.item_title_text.setText(arrayList.get(position).getTitle());
        holder.item_name_text.setText(arrayList.get(position).getUserName());
        holder.item_studentNumber.setText((arrayList.get(position).getStudentNumber()));


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton iv_profile;
        Button item_title_text;
        Button item_name_text;
        Button item_studentNumber;
        EditText editText;
        Button searchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item_title_text = itemView.findViewById(R.id.item_title_text);
            this.item_name_text = itemView.findViewById(R.id.item_name_text);
            this.item_studentNumber = itemView.findViewById(R.id.item_studentNumber);

        }
    }
//    private ArrayList<bullentin_DB> arrayList;
//    private ArrayList<bullentin_DB> items = null;
//    private Context context;
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        Button item_title_text;
//        Button item_name_text;
//        Button item_studentNumber;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            this.item_title_text = itemView.findViewById(R.id.item_title_text);
//            this.item_name_text = itemView.findViewById(R.id.item_name_text);
//            this.item_studentNumber = itemView.findViewById(R.id.item_studentNumber)
//
//        }
//    }
//
//    // 생성자
//    bullentin_board_adapter(ArrayList<item_bullentin_board> list){
//        items = list;
//    }
//
//    @NonNull
//    @Override
//    public bullentin_board_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService()
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    public bullentin_board_adapter(ArrayList<bullentin_DB> arrayList, Context context ){
//        this.arrayList = arrayList;
//        this.context =context;
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull bullentin_board_adapter.ViewHolder holder, int position) {
//        holder.item_title_text.setText(arrayList.get(position).getItem_title_text());
//        holder.item_name_text.setText(arrayList.get(position).getItem_name_text());
//        holder.item_studentNumber.setText(arrayList.get(position).getItem_name_text());
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return (arrayList != null ? arrayList.size():0);
//    }

}
