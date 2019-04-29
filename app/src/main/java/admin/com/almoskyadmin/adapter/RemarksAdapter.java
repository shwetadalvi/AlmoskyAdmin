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
import admin.com.almoskyadmin.model.Remarks;

public class RemarksAdapter extends RecyclerView.Adapter<RemarksAdapter.ViewHolder> {

    List<Remarks> RemarksList = new ArrayList<>();
    Context mContext;
    private ClickListeners.ItemClick itemClick;
    private View mView;
    private Remarks mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;

    public RemarksAdapter(List<Remarks> RemarksList, Context context, ClickListeners.ItemClick itemClick) {
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

        return RemarksList.get(position).getId();
    }
    private void showUndoSnackbar() {
//        View view = mActivity.findViewById(R.id.coordinator_layout);
//        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
//                Snackbar.LENGTH_LONG);
//        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
//        snackbar.show();
    }

    private void undoDelete() {
        RemarksList.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView remarkListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remarkListItem = itemView.findViewById(R.id.remarkListItem);
        }

        public void bind(Remarks Remarks) {
            remarkListItem.setText(Remarks.getRemarks());
            itemView.setOnClickListener(view -> itemClick.onClickedItem(Remarks));
        }
    }
}
