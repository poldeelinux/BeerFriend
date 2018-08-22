package beer.sru.ac.th.beerfriend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostMessageFragment extends Fragment{
    private String urlAvatarString, idString;
    private String tag = "21AugV4";

    public static PostMessageFragment postMessageInstance(String urlAvatarString, String idString) {
        PostMessageFragment postMessageFragment = new PostMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Avatar",urlAvatarString);
        bundle.putString("id",idString);

        postMessageFragment.setArguments(bundle);
        return postMessageFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getValueFromArgument();

//        Show Avatar
        showAvatar();


    }//Main Method

    private void showAvatar() {
        CircleImageView circleImageView = getView().findViewById(R.id.circleAvatar);
        Picasso.get().load(urlAvatarString)
                .resize(100,100)
                .into(circleImageView);
    }

    private void getValueFromArgument() {
        urlAvatarString = getArguments().getString("Avatar");
        idString = getArguments().getString("id");
        Log.d(tag, "Url==>" + urlAvatarString);
        Log.d(tag, "id of Login==>" + idString);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_message, container, false);
        return view;
    }
}
