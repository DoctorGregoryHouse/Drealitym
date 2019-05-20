package com.example.ruben.drealitym.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruben.drealitym.Data.DreamEntry;
import com.example.ruben.drealitym.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DreamDiaryAdapter extends RecyclerView.Adapter<DreamDiaryAdapter.DreamViewHolder> {

    private List<DreamEntry> Dreams = new ArrayList<>();

    @NonNull
    @Override
    public DreamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dream_entry_card_view_item, parent, false);
        return new DreamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DreamViewHolder holder, int position) {
        DreamEntry currentDream = Dreams.get(position);
        holder.textViewId.setText(String.valueOf(currentDream.getId()));
        holder.textViewDate.setText(currentDream.getDate());
        holder.textViewTitle.setText(currentDream.getTitle());
        holder.textViewText.setText(currentDream.getText());

    }

    @Override
    public int getItemCount() {
        return Dreams.size();
    }

    public void setDreams(List<DreamEntry> Dreams){
        this.Dreams = Dreams;
        notifyDataSetChanged();
        //TODO: update @method notifyDataSetChanged();
    }


    class DreamViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewId;
        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewText;
        //private Button buttonPlayAudio; TODO: button need corresponding layout depending whether the is an audio file or not
        private ImageView imageViewCircle;

        public DreamViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.diary_card_view_text_view_id);
            textViewDate = itemView.findViewById(R.id.diary_card_view_text_view_date);
            textViewTitle = itemView.findViewById(R.id.diary_card_view_text_view_title);
            textViewText = itemView.findViewById(R.id.diary_card_view_text_view_text);
        }
    }
}
