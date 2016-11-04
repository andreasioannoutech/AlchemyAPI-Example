package com.kikkos.alchemyapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    final String api_key = "ALCHEMY API KEY HERE";

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
                    AlchemyAsyncTask task = new AlchemyAsyncTask();
                    // pass text in for analysis
                    task.execute(editText.getText().toString());
                }
            }
        });

    }

    private class AlchemyAsyncTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {
            // call alchemy function to start analysis of input text
            return callAlchemyKeywords(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            // print keywords in TextView for viewing
            textView.setText("Keywords:"+s);
        }
    }

    // ALCHEMY API FUNCTION
    public String callAlchemyKeywords(String input){
        // init alchemyapi service
        AlchemyLanguage service = new AlchemyLanguage();
        service.setApiKey(api_key);
        // create and set parameters
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AlchemyLanguage.TEXT, input);
        // run service and get keyword extraction results
        Keywords keywords = service.getKeywords(params).execute();
        // Print results
        System.out.println("Total Transactions: " + keywords.getTotalTransactions());
        System.out.println("Language: " + keywords.getLanguage());
        System.out.println("Number of keywords " + keywords.getKeywords().size());
        // get text of keywords
        List<Keyword> keys = keywords.getKeywords();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < keys.size(); i++){
            System.out.println("Keyword " + i + ": " + keys.get(i));
            output.append("\n" + keys.get(i).getText());
        }
        return output.toString();
    }
}
