package wang.relish.datepicker.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import wang.relish.colorpicker.ColorPickerDialog;
import wang.relish.datepicker.DatePickerDialog;
import wang.relish.datepicker.MonthStyle;

/**
 * @author Relish Wang
 * @since 2017/3/22
 */
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSelectedListener,
        ColorPickerDialog.OnColorSelectCompletedListener, View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    TextView tv;
    private int mStartYear, mStartMonth, mStartDay, mEndYear, mEndMonth, mEndDay;

    private boolean mIsWeekShown = false;

    private int mYearTextColor = MonthStyle.YEAR_TEXT_COLOR;
    private int mWeekTextColor = MonthStyle.WEEK_TEXT_COLOR;
    private int mEnabledTextColor = MonthStyle.ENABLED_TEXT_COLOR;
    private int mDisabledTextColor = MonthStyle.DISABLED_TEXT_COLOR;

    private int mTitleBackgroundColor = Color.parseColor("#FAFAFA");
    private int mTitleTextColor = Color.parseColor("#1A1A1A");
    private int mTitleLeftTextColor = Color.parseColor("#FF571A");
    private int mTitleRightTextColor = Color.parseColor("#FF571A");


    private View mYearColorView;
    private View mWeekColorView;
    private View mEnabledColorView;
    private View mDisabledColorView;

    private View mTitleBackgroundColorView;
    private View mTitleTextColorView;
    private View mTitleLeftTextColorView;
    private View mTitleRightTextColorView;


    private SwitchCompat mStWeekShown;

    private RadioGroup mRadioGroup;
    private RadioButton mRb0;
    private RadioButton mRb2;
    private RadioButton mRb3;
    private RadioButton mRb4;
    private RadioButton mRb6;


    private DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYearTextColor = Color.parseColor("#ff571a");
        mWeekTextColor = Color.parseColor("#999999");
        mEnabledTextColor = Color.parseColor("#1a1a1a");
        mDisabledTextColor = Color.parseColor("#1a1a1a");
        dialog = DatePickerDialog.newInstance(this);

        tv = (TextView) findViewById(R.id.tv);
        mStWeekShown = (SwitchCompat) findViewById(R.id.st_week_shown);
        mStWeekShown.setOnCheckedChangeListener(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.rg);
        mRb0 = (RadioButton) findViewById(R.id.rb_0);
        mRb2 = (RadioButton) findViewById(R.id.rb_2);
        mRb3 = (RadioButton) findViewById(R.id.rb_3);
        mRb4 = (RadioButton) findViewById(R.id.rb_4);
        mRb6 = (RadioButton) findViewById(R.id.rb_6);
        mRadioGroup.setOnCheckedChangeListener(this);

        mYearColorView = findViewById(R.id.year_color);
        mWeekColorView = findViewById(R.id.week_color);
        mEnabledColorView = findViewById(R.id.enabled_color);
        mDisabledColorView = findViewById(R.id.disabled_color);
        mTitleBackgroundColorView = findViewById(R.id.title_background_color);
        mTitleTextColorView = findViewById(R.id.title_text_color);
        mTitleLeftTextColorView = findViewById(R.id.title_left_text_color);
        mTitleRightTextColorView = findViewById(R.id.title_right_text_color);

        mYearColorView.setBackgroundColor(mYearTextColor);
        mWeekColorView.setBackgroundColor(mWeekTextColor);
        mEnabledColorView.setBackgroundColor(mEnabledTextColor);
        mDisabledColorView.setBackgroundColor(mDisabledTextColor);

        mTitleBackgroundColorView.setBackgroundColor(mTitleBackgroundColor);
        mTitleTextColorView.setBackgroundColor(mTitleTextColor);
        mTitleLeftTextColorView.setBackgroundColor(mTitleLeftTextColor);
        mTitleRightTextColorView.setBackgroundColor(mTitleRightTextColor);

        mYearColorView.setOnClickListener(this);
        mWeekColorView.setOnClickListener(this);
        mEnabledColorView.setOnClickListener(this);
        mDisabledColorView.setOnClickListener(this);

        mTitleBackgroundColorView.setOnClickListener(this);
        mTitleTextColorView.setOnClickListener(this);
        mTitleLeftTextColorView.setOnClickListener(this);
        mTitleRightTextColorView.setOnClickListener(this);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mStartYear == 0 && mStartMonth == 0 && mStartDay == 0)) {
                    if (mEndYear == 0 && mEndMonth == 0 && mEndDay == 0) {
                        dialog.setSelectedDate(mStartYear, mStartMonth, mStartDay);
                    } else {
                        dialog.setSelectedDate(mStartYear, mStartMonth, mStartDay, mEndYear, mEndMonth, mEndDay);
                    }
                }
                dialog.setWeekShownInMonthView(mIsWeekShown);

                dialog.setTitleBarBackgroundColor(mTitleBackgroundColor);//设置标题栏背景颜色
                dialog.setTitleBarTitleTextColor(mTitleTextColor);//设置标题栏标题文字颜色
                dialog.setTitleBarLeftTextColor(mTitleLeftTextColor);//设置标题栏左侧文字颜色
                dialog.setTitleBarRightTextColor(mTitleRightTextColor);//设置标题栏右侧文字颜色
                dialog.setTitleBarBackgroundColor(mTitleBackgroundColor);//设置标题栏背景颜色

                dialog.setYearTextColor(mYearTextColor);
                dialog.setWeekTextColor(mWeekTextColor);
                dialog.setEnabledTextColor(mEnabledTextColor);
                dialog.setDisabledTextColor(mDisabledTextColor);

                dialog.setOnDateSelectedListener(MainActivity.this);
                dialog.show();
            }
        });
    }

    @Override
    public void onSelectCompleted(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        mStartYear = startYear;
        mStartMonth = startMonth;
        mStartDay = startDay;
        mEndYear = endYear;
        mEndMonth = endMonth;
        mEndDay = endDay;
        tv.setText(mStartYear + "/" + mStartMonth + "/" + mStartDay + " - " +
                mEndYear + "/" + mEndMonth + "/" + mEndDay);
    }

    @Override
    public void onColorSelectCompleted(ColorPickerDialog dialog, int r, int g, int b) {
        switch (dialog.getTag()) {
            case "year":
                mYearTextColor = Color.rgb(r, g, b);
                mYearColorView.setBackgroundColor(mYearTextColor);
                break;
            case "week":
                mWeekTextColor = Color.rgb(r, g, b);
                mWeekColorView.setBackgroundColor(mWeekTextColor);
                break;
            case "enabled":
                mEnabledTextColor = Color.rgb(r, g, b);
                mEnabledColorView.setBackgroundColor(mEnabledTextColor);
                break;
            case "disabled":
                mDisabledTextColor = Color.rgb(r, g, b);
                mDisabledColorView.setBackgroundColor(mDisabledTextColor);
                break;
            case "title_background":
                mTitleBackgroundColor = Color.rgb(r, g, b);
                mTitleBackgroundColorView.setBackgroundColor(mTitleBackgroundColor);
                break;
            case "title_text":
                mTitleTextColor = Color.rgb(r, g, b);
                mTitleTextColorView.setBackgroundColor(mTitleTextColor);
                break;
            case "title_left":
                mTitleLeftTextColor = Color.rgb(r, g, b);
                mTitleLeftTextColorView.setBackgroundColor(mTitleLeftTextColor);
                break;
            case "title_right":
                mTitleRightTextColor = Color.rgb(r, g, b);
                mTitleRightTextColorView.setBackgroundColor(mTitleRightTextColor);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.year_color:
                showColorDialog(mYearTextColor, "year");
                break;
            case R.id.week_color:
                showColorDialog(mWeekTextColor, "week");
                break;
            case R.id.enabled_color:
                showColorDialog(mEnabledTextColor, "enabled");
                break;
            case R.id.disabled_color:
                showColorDialog(mDisabledTextColor, "disabled");
                break;
            case R.id.title_background_color:
                showColorDialog(mYearTextColor, "title_background");
                break;
            case R.id.title_text_color:
                showColorDialog(mWeekTextColor, "title_text");
                break;
            case R.id.title_left_text_color:
                showColorDialog(mEnabledTextColor, "title_left");
                break;
            case R.id.title_right_text_color:
                showColorDialog(mDisabledTextColor, "title_right");
                break;

        }
    }

    private void showColorDialog(int color, String tag) {
        int r = (color & 0xff0000) >> 16;
        int g = (color & 0x00ff00) >> 8;
        int b = (color & 0x0000ff);
        ColorPickerDialog dialog = ColorPickerDialog.getInstance(r, g, b);
        dialog.setOnColorSelectCompletedListener(this);
        dialog.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mIsWeekShown = isChecked;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_0:
                dialog = DatePickerDialog.newInstance(this);
                break;
            case R.id.rb_2:
                dialog = DatePickerDialog.newInstance(this, 2016, 2017);
                break;
            case R.id.rb_3:
                dialog = DatePickerDialog.newInstance(this, 2017, 1, 13);
                break;
            case R.id.rb_4:
                dialog = DatePickerDialog.newInstance(this, 2017, 1, 2017, 3);
                break;
            case R.id.rb_6:
                dialog = DatePickerDialog.newInstance(this, 2017, 1, 13, 2017, 3, 2);
                break;
        }
    }
}