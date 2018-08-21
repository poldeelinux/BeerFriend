package beer.sru.ac.th.beerfriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class RegisterFragment extends Fragment{

//    Explicit
    private ImageView imageView;
    private Uri uri;
    private boolean aBoolean = true;
    private  String  nameString, userString, passwordString,pathAvatarString;
    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Avata Controller
        avataController();

    }//Main Method

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==getActivity().RESULT_OK) {

            aBoolean = false;

            try {
                uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,800,600,true);
                imageView.setImageBitmap(bitmap1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "Please Choose Photo Only One",Toast.LENGTH_SHORT).show();
        }


    }

    private void avataController() {
        imageView = getView().findViewById(R.id.imvAvata);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose Photo"),2);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.itemUpload) {

            uploadData();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadData() {

        MyAlert myAlert = new MyAlert(getActivity());
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText userEditText = getView().findViewById(R.id.edtUser);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();




        if (aBoolean) {
            myAlert.normalDialog("dont Choose Image",
                    "Please Choose Image");
        } else if (nameString.isEmpty()|| userString.isEmpty()||passwordString.isEmpty()) {
            myAlert.normalDialog("Have Space",
                    "Please fill All Every Blank");
        } else {
            uploadPhoto();
        }


    }//uploadData

    public class MyFTPDataTransferListener implements FTPDataTransferListener{

        @Override
        public void started() {
            Toast.makeText(getActivity(),"Started",Toast.LENGTH_SHORT).show();
//            progressDialog.show();
        }

        @Override
        public void transferred(int i) {
            Toast.makeText(getActivity(),"Transfered",Toast.LENGTH_SHORT).show();
//           progressDialog.dismiss();

        }

        @Override
        public void completed() {
            Toast.makeText(getActivity(),"Compleated Upload",Toast.LENGTH_SHORT).show();
//            progressDialog.dismiss();
        }

        @Override
        public void aborted() {

        }

        @Override
        public void failed() {

        }
    }


    private void uploadPhoto() {

        //   Change Police
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("Process Upload");
//        progressDialog.setMessage("Please Wait Few Minus...");


        MyConstant myConstant = new MyConstant();

//        Find Path of Photo
        String pathString = null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri,strings,
                null,null,null);

        if (cursor!=null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathString = cursor.getString(index);
        } else {
            pathString = uri.getPath();
        }

        Log.d("21AugV1", "pathString ==> " + pathString);
        File file = new File(pathString);
        FTPClient ftpClient = new FTPClient();

        try {


            ftpClient.connect(myConstant.getHostString(), myConstant.getPortAnInt());
            ftpClient.login(myConstant.getUserString(),myConstant.getPasswordString());
            ftpClient.setType(FTPClient.TYPE_BINARY);
            ftpClient.changeDirectory("beerAvatar");
            ftpClient.upload(file,new MyFTPDataTransferListener());

//            Find name of photp
            pathAvatarString = pathString.substring(pathString.lastIndexOf("/"));
            pathAvatarString = "http://androidthai.in.th/bee/beerAvatar" + pathAvatarString;


            String tag = "21AugV2";
            Log.d(tag,"name==>"+nameString);
            Log.d(tag, "üser==>" + userString);
            Log.d(tag, "pass==>" + passwordString);
            Log.d(tag, "pathPhoto==>" + pathAvatarString);
            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList.add("start");

            AddNewUser addNewUser = new AddNewUser(getActivity());
            addNewUser.execute(nameString,userString,passwordString,
                    pathAvatarString,stringArrayList.toString(),myConstant.getUrlAddUserString());

            if (Boolean.parseBoolean(addNewUser.get())) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Cannot Upload", Toast.LENGTH_SHORT).show();
            }



        } catch (Exception e) {
            Log.d("21AugV1", "e=" + e.toString());
            try {
                ftpClient.disconnect(true);
            } catch (Exception e1) {
                Log.d("21AugV1", "e1=" + e1.toString());
            }

        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);


    }

    private void createToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

//        SetUp Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.register);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill All Every Blank");

//        Setup Navigation
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }
}
