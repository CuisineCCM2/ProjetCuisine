package perso.myapplicationL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<RecyclerSimpleViewAdapter.ViewHolder> {

    private List<String> items;
    private int itemLayout;

    public RecyclerSimpleViewAdapter(List<String> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerSimpleViewAdapter.ViewHolder holder, int position) {
        String item = items.get(position);
        holder.primaryText.setText(item);
        holder.itemView.setTag(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView primaryText;
        public ViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
