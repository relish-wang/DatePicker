package wang.relish.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * 显示月份
 *
 * @author wangxin
 * @since 2017/3/15
 */
public class MonthView extends View {
    /**
     * 设计稿上一个格子的高度是屏幕宽度的104/750
     */
    public static final float CELL_HEIGHT_SCALE = 0.1386666667f;
    /**
     * 一行有7天
     */
    private static final int NUM_COLUMNS = 7;

    /**
     * 格子宽
     */
    private float mCellWidth;
    /**
     * 格子高
     */
    protected float mCellHeight;

    /**
     * 监听器
     */
    private OnSelectListener mListener;

    protected MonthAdapter mAdapter;
    private MonthAdapter mDefaultAdapter = new DefaultAdapter();

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        mCellWidth = widthSize * 1.0f / NUM_COLUMNS;
        mCellHeight = widthSize * CELL_HEIGHT_SCALE;

        //一个月有多少行（可能4行或5行或6行）
        //参考钉钉是固定6行的
        int numRows = 6;
        if (mAdapter != null) {
            numRows = mAdapter.getRow();
        } else {
            numRows = mDefaultAdapter.getRow();
        }
        int heightSize = (int) (numRows * mCellHeight);
        int measureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
        setMeasuredDimension(measureSpec, heightSize);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        mCellWidth = getWidth() * 1.0F / NUM_COLUMNS;

        if (mAdapter != null) {
            mAdapter.draw(canvas, mCellWidth, mCellHeight);
        } else {
            mDefaultAdapter.draw(canvas, mCellWidth, mCellHeight);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            int cellX = (int) (x / mCellWidth);
            int cellY = (int) (y / mCellHeight);

            //异常坐标
            if (cellX < 0 || cellX > 7 || cellY < 0 || cellY > 6) return true;
            if (Utils.isFastDoubleClick(new Point(cellX, cellY))) return true;

            MonthStyle monthStyle = getMonthStyle();
            int currYear = monthStyle.getYear();
            int currMonth = monthStyle.getMonth();
            int weekFirstDay = monthStyle.getWeekFirstDay();

            int preMonthTailDayCount = Utils.preMonthTailDayCount(currYear, currMonth, weekFirstDay);
            Point monthLastDayPoint = Utils.getMonthLastDayPoint(currYear, currMonth, weekFirstDay);

            if (cellY == 0 && cellX < preMonthTailDayCount) {
                int year = currMonth == 0 ? currYear - 1 : currYear;
                int month = currMonth == 0 ? 11 : currMonth - 1;
                int monthDayCount = Utils.getMonthDayCount(year, month);
                int day = monthDayCount - preMonthTailDayCount + (cellX + 1);
                // 点击到了前一个月的末尾的某个日期
                if (mListener != null) {
                    Logger.d(year + "/" + month + "/" + day);
                    mListener.onPrevMonthDateSelect(this, year, month, day);
                    invalidate();
                    return false;
                } else {
                    return true;
                }
            } else if (cellY == monthLastDayPoint.y && cellX >= monthLastDayPoint.x
                    || cellY > monthLastDayPoint.y) {
                int year = currMonth == 11 ? currYear + 1 : currYear;
                int month = currMonth == 11 ? 0 : currMonth + 1;
                int frontDayCount = preMonthTailDayCount + Utils.getMonthDayCount(currYear, currMonth);
                int day = cellY * 7 + (cellX + 1) - frontDayCount;
                // 点击到了后一个月的末尾几个日期
                if (mListener != null) {
                    mListener.onNextMonthDateSelect(this, year, month, day);
                    invalidate();
                    return false;
                } else {
                    return true;
                }
            } else {
                int day = cellY * 7 + (cellX + 1) - preMonthTailDayCount;
                // 点击到了当月的日期
                if (mListener != null) {
                    mListener.onCurrMonthDateSelect(this, currYear, currMonth, day);
                    invalidate();
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    /**
     * 设置日期选择回调的监听器
     *
     * @param listener 日期选择回调监听器
     */
    public void setOnSelectListener(OnSelectListener listener) {
        mListener = listener;
    }


    public void setAdapter(MonthAdapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            invalidate();
        }
        requestLayout();
    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        throw new UnsupportedOperationException("CANNOT do that!");
    }

    public MonthStyle getMonthStyle() {
        if (mAdapter == null) {
            return mDefaultAdapter.getMonthStyle();
        } else {
            return mAdapter.getMonthStyle();
        }
    }
}
