package net.andoridsrc.piechartmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aman on 29/8/16.
 */
public class PieChartMenu extends View {
    String TAG = PieChartMenu.class.getSimpleName();
    Paint mPaint, solidLinePaint;
    Paint innerSolidCircle;
    Paint innerSolidStroke;
    Paint pieBackgroundSelected, pieBackgroundNormal;
    RectF rect;
    Rect imageBounds = new Rect();

    private Drawable mCustomImage;

    //partitions will be created using no. of icons.
    int[] icons = {R.drawable.ic_account_balance_wallet_black_18dp, R.drawable.ic_account_circle_black_18dp,
            R.drawable.ic_account_circle_black_18dp,R.drawable.ic_account_circle_black_18dp,R.drawable.ic_account_balance_black_18dp, R.drawable.ic_add_alarm_black_18dp,};

    public PieChartMenu(Context context) {
        super(context);
        init(context);
    }

    public PieChartMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieChartMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff4b4642);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        innerSolidCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerSolidCircle.setColor(0xffdbd380);
        innerSolidCircle.setStyle(Paint.Style.FILL);

        pieBackgroundSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        pieBackgroundSelected.setColor(0xff615c56);
        pieBackgroundSelected.setStyle(Paint.Style.FILL);

        pieBackgroundNormal = new Paint(pieBackgroundSelected);
        pieBackgroundNormal.setColor(0xff8a8c81);

        innerSolidStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerSolidStroke.setColor(0xff433741);
        innerSolidStroke.setStyle(Paint.Style.STROKE);
        innerSolidStroke.setStrokeWidth(15);

        solidLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        solidLinePaint.setColor(0xff292b28);
        solidLinePaint.setStyle(Paint.Style.STROKE);
        solidLinePaint.setStrokeWidth(2);

        rect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        float radius = getWidth() / 3;
        float angle = 360f / icons.length;
        //arc padding so that it can start drawing from start
        float arcPadding = 180f;
        imageBounds.set(center - 110, center - 25, center - 60, center + 25);
        rect.set(getWidth() / 2 - radius, getWidth() / 2 - radius, getWidth() / 2 + radius, getWidth() / 2 + radius);
        //drawing background for selected pie
        canvas.drawArc(rect, arcPadding, angle, true, pieBackgroundSelected);
        //draw background for icon
        for (int i = 1; i < icons.length; i++) {
            canvas.drawArc(rect, angle * i+arcPadding, angle, true, pieBackgroundNormal);
        }
        //draw inner solid circle
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 12, innerSolidCircle);
        //inner solid stroke
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 12, innerSolidStroke);
        //draw solid division lines

        canvas.save();
        for (int i = 0; i < icons.length; i++) {
            canvas.rotate(angle, getWidth() / 2, getWidth() / 2);
            canvas.drawLine(getWidth() / 2.5f, getWidth() / 2, getWidth() / 6, getWidth() / 2, solidLinePaint);
        }
        canvas.restore();
        //draw the outer circle
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 3, mPaint);


        //draw icons
        for (int i = 0; i < icons.length; i++) {
            canvas.save();
            canvas.rotate(angle / 2 + angle * i, center, center);
            canvas.rotate(-angle / 2 - angle * i,imageBounds.centerX(),imageBounds.centerY());
            mCustomImage = getContext().getResources().getDrawable(icons[i]);
            mCustomImage.setBounds(imageBounds);
            mCustomImage.draw(canvas);
            canvas.restore();
        }


    }
}
