package com.example.spannable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView spanView = findViewById(R.id.spanView);
        spanView.setMovementMethod(new ScrollingMovementMethod());

        String data = "복수초 \n img \n 이른봄 설산에서 만나는 복수초는" +
                "모든 야생화 찍사들의 로망이 아닐까 싶다.";

        SpannableStringBuilder builder = new SpannableStringBuilder(data);
        int start = data.indexOf("img");
        if(start>-1) {
            int end = start+"img".length();
            Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.img1, null);
            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(dr);
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spanView.setText(builder);
    }
}