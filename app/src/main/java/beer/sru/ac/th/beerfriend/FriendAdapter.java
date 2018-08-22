package beer.sru.ac.th.beerfriend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends  RecyclerView.Adapter<FriendAdapter.FriendViewHolder>{

    private Context context;
    private ArrayList<String> AvatarArrayList,nameStringArrayList, postStringArrayList;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context,
                         ArrayList<String> avatarArrayList,
                         ArrayList<String> nameStringArrayList,
                         ArrayList<String> postStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        AvatarArrayList = avatarArrayList;
        this.nameStringArrayList = nameStringArrayList;
        this.postStringArrayList = postStringArrayList;
    }//Constructure

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recylcler_view_friend, parent, false);
        FriendViewHolder friendViewHolder = new FriendViewHolder(view);

        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {


        String avatarString = AvatarArrayList.get(position);
        String nameString = nameStringArrayList.get(position);
        String postString = postStringArrayList.get(position);

        holder.nameTextView.setText(nameString);
        holder.postTextView.setText(postString);

        Picasso.get()
                .load(avatarString)
                .resize(100,100)
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {


        private CircleImageView circleImageView;
        private TextView nameTextView,postTextView;


        public FriendViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.circleFriend);
            nameTextView = itemView.findViewById(R.id.txtName);
            postTextView = itemView.findViewById(R.id.txtPost);


        }
    }//Second Class


}//Main Class
