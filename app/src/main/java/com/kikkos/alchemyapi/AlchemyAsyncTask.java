package com.kikkos.alchemyapi;

import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kikkos on 11/4/2016.
 */

public class AlchemyAsyncTask extends AsyncTask<String, Void, String> {

    public interface AlchemyAsyncTaskResponse {
        void processFinish(String keywords);
    }

    private AlchemyAsyncTaskResponse delegate = null;
    private final String api_key = "ALCHEMY API KEY HERE";

    public AlchemyAsyncTask(AlchemyAsyncTaskResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        // init alchemyapi service
        AlchemyLanguage service = new AlchemyLanguage();
        service.setApiKey(api_key);
        // create and set parameters
        Map<String, Object> alchemyParameters = new HashMap<String, Object>();
        alchemyParameters.put(AlchemyLanguage.TEXT, params[0]);
        // run service and get keyword extraction results
        Keywords keywords = service.getKeywords(alchemyParameters).execute();
        // Print results
        System.out.println("Total Transactions: " + keywords.getTotalTransactions());
        System.out.println("Language: " + keywords.getLanguage());
        System.out.println("Number of keywords " + keywords.getKeywords().size());
        // get text of keywords
        List<Keyword> keys = keywords.getKeywords();
        StringBuilder output = new StringBuilder();
        if (keys.size() > 0){
            for (int i = 0; i < keys.size(); i++){
                System.out.println("Keyword " + i + ": " + keys.get(i));
                output.append("\n" + keys.get(i).getText());
            }
            return output.toString();
        }else {
            return "No Keywords Found";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }
}
