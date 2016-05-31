package com.pexlabs.customrecycleswipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ArrayList<String> mItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private Paint mPaint = new Paint();

    private static float sFirstSwipeThreshold = .3f;
    private static float sSwipeThreshold = .6f;

    public MainActivityFragment() {
        mItems.add("Roland");
        mItems.add("Dan");
        mItems.add("Andy");
        mItems.add("Tony");
        mItems.add("Ross");
        mItems.add("Jason");
        mItems.add("Parag");
        mItems.add("Yuichi");
        mItems.add("Dave");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View returnView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) returnView.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CustomAdapter(mItems, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        initSwipe();

        return returnView;
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Some methods to implement/override
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                // Could do something specific depending on the direction?
//                if (direction == ItemTouchHelper.LEFT) {
                    mAdapter.removeItem(position);
//                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                    RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                    boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float)itemView.getBottom() - (float)itemView.getTop();
                    float width = height / 3;
                    if (dX > 0) {
                        // The item is being swiped to the right
                        // Color the background up to where the row has been swiped indicated
                        // by dX
                        mPaint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float)itemView.getLeft(),
                                (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, mPaint);

                        // Now draw the icon
                        icon = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_event_white_24dp);
                        RectF icon_dest = new RectF((float)itemView.getLeft() + width,
                                (float)itemView.getTop() + width,
                                (float)itemView.getLeft() + 2 * width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, mPaint);
                    } else {
                        // The item is being swiped to the left
                        // Draw the background with the exposed rect.
                        mPaint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float)itemView.getRight() + dX,
                                (float)itemView.getTop(),
                                (float)itemView.getRight(),
                                (float)itemView.getBottom());
                        c.drawRect(background, mPaint);

                        // Now draw the icon
                        icon = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_event_white_24dp);
                        RectF icon_dest = new RectF((float)itemView.getRight() - 2 * width,
                                (float)itemView.getTop() + width,
                                (float)itemView.getRight() - width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, mPaint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                        isCurrentlyActive);
            }

            @Override
            public float getSwipeVelocityThreshold (float defaultValue) {
                return defaultValue * 10;
            }

            @Override
            public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
                return super.getSwipeThreshold(viewHolder);
            }
        };

        // Attachment this to the recycler view.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
