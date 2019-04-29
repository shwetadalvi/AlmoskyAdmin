package admin.com.almoskyadmin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.interfaces.ClickListeners;
import admin.com.almoskyadmin.model.Driverdto;
import admin.com.almoskyadmin.model.Remarks;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ViewHolder> {

    List<Driverdto.Result> RemarksList = new ArrayList<>();
    Context mContext;
    private ClickListeners.ItemClick itemClick;
    private View mView;
    private Remarks mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;

    public DriverListAdapter(List<Driverdto.Result> RemarksList, Context context, ClickListeners.ItemClick itemClick) {
        this.RemarksList = RemarksList;
        mContext = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.remarks_item, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(RemarksList.get(i));
    }

    @Override public int getItemCount() {
        return RemarksList.size();
    }


    public int getRemarksId(int position) {

        return RemarksList.get(position).getID();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        TextView remarkListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remarkListItem = itemView.findViewById(R.id.remarkListItem);
        }

        public void bind(Driverdto.Result Remarks) {
            remarkListItem.setText(Remarks.getFullName());
            itemView.setOnClickListener(view -> itemClick.onClickedItem(Remarks));
        }
    }
}
