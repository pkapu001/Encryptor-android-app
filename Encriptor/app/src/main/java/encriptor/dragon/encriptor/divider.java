package encriptor.dragon.encriptor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class divider extends RecyclerView.ItemDecoration
{
    private Drawable mdivider;
    private int morentation;

    public divider (Context context, int orientation)
    {
        mdivider = ContextCompat.getDrawable(context,R.drawable.divider);
        if(orientation != LinearLayoutManager.VERTICAL)
        {
            throw new IllegalArgumentException("its not linat layour");
        }
        morentation =orientation;

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (morentation == LinearLayoutManager.VERTICAL)
        {
            drawHorizontalDivider(c ,parent ,state);
        }
    }



    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        int left ,right ,top ,bottom ;
        left = 0 ;
        right = parent.getWidth();
        int count = parent.getChildCount();
        for (int i = 0; i <count ; i++)
        {
            View current = parent.getChildAt(i);
            top = current.getTop();
            bottom = top + mdivider.getIntrinsicHeight();

            mdivider.setBounds(left,top,right,bottom);
            mdivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mdivider.getIntrinsicHeight();

    }
}
