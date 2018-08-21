package beer.sru.ac.th.beerfriend;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AddNewUser extends AsyncTask<String,Void,String>{

    private Context context;

    public AddNewUser(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", strings[0])
                    .add("User", strings[1])
                    .add("Password", strings[2])
                    .add("PathAvatar", strings[3])
                    .add("PostMessage", strings[4])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[5]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
