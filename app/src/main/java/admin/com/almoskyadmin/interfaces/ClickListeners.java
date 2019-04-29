package admin.com.almoskyadmin.interfaces;

import android.view.View;


import java.util.List;

import admin.com.almoskyadmin.model.time;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public interface ClickListeners {
    interface CategoryItemEvents<T> {
        void onClickedEdit(T items);

        void onDeletedItem(T items);
    }

    interface ItemClick<T> {
        void onClickedItem(T item);
    }

    interface getTimeList {
        void onClickedTimeItem(List<time> timelist);
    }
   /* interface ItemTimeClick<T> {
        void onClickedTimeItem(T item);
    }*/
}
