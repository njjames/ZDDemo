package com.nj.zddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.nj.zddemo.R;

/**
 * Created by Administrator on 2018-08-08.
 */

public class RoundImageView extends AppCompatImageView {
    private static final String TAG = "RoundImageView";
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private static final float BODER_RADIUS_DEFAULT = 10f;
    private Matrix matrix;
    private Paint paint;
    private int type;
    private int borderRadius;
    private int radius;
    private RectF rectF;
    private int width;
    private float dx;
    private float dy;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        matrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundAngelImageView);
//        borderRadius = dp2px(BODER_RADIUS_DEFAULT);
        // 在xml中设置属性需要在这里读取，下面的set方法是在代码中调用时起作用
        // 注意是R.styleable.RoundAngelImageView_属性名
        borderRadius = dp2px((int) array.getDimension(R.styleable.RoundAngelImageView_borderRadius, BODER_RADIUS_DEFAULT));
        type = array.getInt(R.styleable.RoundAngelImageView_type, TYPE_CIRCLE);
        array.recycle();
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果类型数圆形，就去宽和高中的最小值作为view的宽和高
        if (type == TYPE_CIRCLE) {
            width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            radius = width / 2;
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 如果是圆角，就创建圆角矩形
        if (type == TYPE_ROUND) {
            rectF = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getDrawable() == null) {
            return;
        }
        setBitmapShader();
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(rectF, borderRadius, borderRadius, paint);
        } else {
            canvas.drawCircle(radius, radius, radius, paint);
        }
    }

    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        // 将drawable转化成bitmap对象
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) {
            return;
        }
        //android.graphics.drawable.AdaptiveIconDrawable cannot be cast to android.graphics.drawable.BitmapDrawable
        // 报上面的错误是因为src取的是mipmap资源下的，应该取drawable下的
        // 将bitmap作为着色器，就是在指定区域内绘制bmp
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int viewwidth = getWidth();
        int viewheight = getHeight();
        int drawablewidth = bitmap.getWidth();
        int drawableheight = bitmap.getHeight();
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
            // 这里的width已经是宽和高中的最小值了
            scale = width * 1.0f / size;
        } else if(type == TYPE_ROUND) {
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
        }
        // 计算截取的偏移量
        if (drawablewidth * viewheight > viewwidth * drawableheight) {
            dx = (viewwidth - drawablewidth * scale) * 0.5f;
        } else {
            dy = (viewheight - drawableheight * scale) * 0.5f;
        }
        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
    }

    public void setBorderRadius(int borderRadius) {
        int px = dp2px(borderRadius);
        if (this.borderRadius != px) {
            this.borderRadius = px;
            invalidate();
        }
    }

    public void setType(int type) {
        Log.d(TAG, "type: " + type);
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }
    }
}
