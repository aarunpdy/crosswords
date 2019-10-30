package com.crosswords

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ActionMode
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

/**
 * TODO: Write Javadoc for AnswerTextView.
 *
 * @author arun
 */
class AnswerTextView(context: Context?, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {

    private var mSpace: Float = 24f //24 dp by default, space between the lines
    private var mNumChars = 8
    private var mLineSpacing: Float = 6f //8dp by default, height of the text from our lines
    private var mMaxLength = 8
    private var mLineStroke: Float = 2f
    private var mLinesPaint: Paint? = null
    private var mRectPaint: Paint? = null
    private var mClickListener: OnClickListener? = null
    private var mAnswer: String? = null;

    init {
        val multi = context?.resources?.displayMetrics?.density
        mLineStroke *= multi!!
        mLinesPaint = Paint(paint)
        mLinesPaint!!.strokeWidth = mLineStroke
        mLinesPaint!!.color = resources.getColor(R.color.colorAccent)

        mRectPaint = Paint(paint)
        mRectPaint!!.color = resources.getColor(R.color.colorAccentbg)

        setBackgroundResource(0);
        mSpace *= multi //convert to pixels for our density
        mLineSpacing *= multi //convert to pixels for our density
        mNumChars = mMaxLength;

        super.setOnClickListener(OnClickListener {
            // When tapped, move cursor to end of text.
            setSelection(text!!.length)
            mClickListener?.onClick(it)
        })
    }

    public fun setAnswer(ans: String) {
        mNumChars = ans.length
        mAnswer = ans
    }

    public fun getAnswer(): String? {
        return mAnswer
    }

    override fun setOnClickListener(listener: OnClickListener) {
        mClickListener = listener
    }


    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.");
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val availableWidth = width - paddingRight - paddingLeft
        val availableHeight = height - paddingTop - paddingBottom
        val mCharSize: Float = if (mSpace < 0) {
            ((availableWidth / (mNumChars * 2 - 1)).toFloat())
        } else {
            (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars
        }

        var startX: Float = paddingLeft.toFloat()
        val bottom: Float = (height - paddingBottom).toFloat()

        //Text Width
        val text = text
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)

        var i = 0
        while (i < mNumChars) {
            //canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mLinesPaint)
            canvas.drawRect(
                startX - 5,
                bottom - availableHeight,
                startX + mCharSize + 5,
                bottom,
                mRectPaint
            )
            if (text.length > i) {
                val middle = startX + mCharSize / 2;
                canvas.drawText(
                    text,
                    i,
                    i + 1,
                    middle - textWidths[0] / 2,
                    bottom - mLineSpacing,
                    paint
                )
            }
            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }
            i++
        }
    }
    /*
    private float mSpace = 24; //24 dp by default, space between the lines
    private float mNumChars = 4;
    private float mLineSpacing = 8; //8dp by default, height of the text from our lines
    private int mMaxLength = 4;
    private float mLineStroke = 2;
    private Paint mLinesPaint;
    private OnClickListener mClickListener;

    public OtpEditText(Context context) {
        super(context);
    }

    public OtpEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OtpEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        float multi = context.getResources().getDisplayMetrics().density;
        mLineStroke = multi * mLineStroke;
        mLinesPaint = new Paint(getPaint());
        mLinesPaint.setStrokeWidth(mLineStroke);
        mLinesPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        setBackgroundResource(0);
        mSpace = multi * mSpace; //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing; //convert to pixels for our density
        mNumChars = mMaxLength;

        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // When tapped, move cursor to end of text.
                setSelection(getText().length());
                if (mClickListener != null) {
                    mClickListener.onClick(v);
                }
            }
        });
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickListener = l;
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        throw new RuntimeException("setCustomSelectionActionModeCallback() not supported.");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int availableWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        float mCharSize;
        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize = (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }

        int startX = getPaddingLeft();
        int bottom = getHeight() - getPaddingBottom();

        //Text Width
        Editable text = getText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(getText(), 0, textLength, textWidths);

        for (int i = 0; i < mNumChars; i++) {
            canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mLinesPaint);
            if (getText().length() > i) {
                float middle = startX + mCharSize / 2;
                canvas.drawText(text, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing, getPaint());
            }
            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }
        }
    }
     */
}
