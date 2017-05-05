package ru.coyul.batterydrawable;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView batteryView;
    private ImageView flashView;
    private Button buttonUp;
    private Button buttonDown;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryView = (ImageView) findViewById(R.id.battery_view);
        flashView = (ImageView) findViewById(R.id.flash);
        buttonUp = (Button) findViewById(R.id.button_up);
        buttonDown = (Button) findViewById(R.id.button_off);

        flashView.getDrawable().setAlpha(0);


        //set animator and listener
        animator = ValueAnimator.ofInt(0,10000);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) animator.getAnimatedValue();
                batteryView.getDrawable().setLevel(value);
                flashView.getDrawable().setAlpha(value/50);
            }
        });



        //on "power up" click start animation
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animator.start();
            }
        });
        //on "power off" click do animation in reverse
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animator.reverse();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        animator.cancel();
        animator.removeAllUpdateListeners();
        animator = null;
    }
}
