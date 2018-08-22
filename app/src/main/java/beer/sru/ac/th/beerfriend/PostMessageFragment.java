package beer.sru.ac.th.beerfriend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostMessageFragment extends Fragment{
    private String urlAvatarString, idString, currentPostMessageString;
    private String tag = "21AugV4";


    public static PostMessageFragment postMessageInstance(
            String urlAvatarString,
            String idString,
            String currentPostMessageString) {
        PostMessageFragment postMessageFragment = new PostMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Avatar",urlAvatarString);
        bundle.putString("id",idString);
        bundle.putString("PostMessage",currentPostMessageString);
        postMessageFragment.setArguments(bundle);
        return postMessageFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getValueFromArgument();

//        Show Avatar
        showAvatar();

//        Post Controller
        postController();


    }//Main Method

    private void postController() {
        Button button = getView().findViewById(R.id.btnPost);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editText = getView().findViewById(R.id.edtPost);
                String postString = editText.getText().toString().trim();

                if (postString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have Space",
                            "Please Type Post!");
                } else {
                    editPostMessage(postString);
                    editText.setText("");
                }

            }
        });

        }

    private void editPostMessage(String postString) {
        try {
            ArrayList<String> stringArrayList = new ArrayList<>();
            MyConstant myConstant = new MyConstant();

            currentPostMessageString = currentPostMessageString.substring(1, currentPostMessageString.length() - 1);
            String[] strings = currentPostMessageString.split(",");
            for (int i=0;i<strings.length;i=+1  ) {
                stringArrayList.add(strings[i].trim());
            }
            stringArrayList.add(postString.trim());

            currentPostMessageString = stringArrayList.toString();
            Log.d("22AugV1", "newPostMessage==>" + currentPostMessageString);

            EditPostMessage editPostMessage = new EditPostMessage(getActivity());
            editPostMessage.execute(idString,currentPostMessageString,
                    myConstant.getUrlEditPostMessageString());

            String resultString = editPostMessage.get();
            Log.d("22AugV1", "Result==>" + resultString);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }   //editPostMessage

    private void showAvatar() {
        CircleImageView circleImageView = getView().findViewById(R.id.circleAvatar);
        Picasso.get().load(urlAvatarString)
                .resize(100,100)
                .into(circleImageView);
    }

    private void getValueFromArgument() {
        urlAvatarString = getArguments().getString("Avatar");
        idString = getArguments().getString("id");
        currentPostMessageString = getArguments().getString("PostMessage");
        Log.d(tag, "Url==>" + urlAvatarString);
        Log.d(tag, "id of Login==>" + idString);
        Log.d(tag, "currentPostMessage of Login==>" + currentPostMessageString);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_message, container, false);
        return view;
    }
}
