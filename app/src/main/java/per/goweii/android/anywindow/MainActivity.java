package per.goweii.android.anywindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import per.goweii.anywindow.AnyWindow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_show_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyWindow.create(MainActivity.this)
                        .setView(R.layout.anywindow_icon)
                        .setViewSize(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        .show();
            }
        });
    }
}
