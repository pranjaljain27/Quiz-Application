package com.example.trivia.data;

import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class QuestionBank {
    ArrayList<Question> questionArrayList=new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Resp","Response"+response);
                        for(int i=0;i<response.length();i++)
                        {
                            try {
                                Question question=new Question();
                                question.setques(response.getJSONArray(i).get(0).toString());
                                question.setCorrectAns(response.getJSONArray(i).getBoolean(1));
                                questionArrayList.add(question);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (null != callBack) callBack.processFinished(questionArrayList);
                        for(int i=0;i<questionArrayList.size();i++)
                        {
                            Log.d("Ques","This is  " + questionArrayList.get(i).getques());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
