package com.kikkos.alchemyapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        editText.setHint("TYPE HERE");
        textView.setHint("RESULTS HERE");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.length() > 0){
                    // pass text in for analysis
                    new AlchemyAsyncTask(new AlchemyAsyncTask.AlchemyAsyncTaskResponse() {
                        @Override
                        public void processFinish(String keywords) {
                            textView.setText("Keywords:"+keywords);
                        }
                    }).execute(editText.getText().toString());
                }
            }
        });

    }
}
