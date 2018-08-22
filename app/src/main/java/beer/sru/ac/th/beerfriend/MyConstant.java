package beer.sru.ac.th.beerfriend;

public class MyConstant {
    private String hostString = "ftp.androidthai.in.th";
    private String userString = "bee@androidthai.in.th";
    private String passwordString = "Abc12345";
    private int portAnInt = 21;
    private String urlAddUserString = "http://androidthai.in.th/bee/addUserBeer.php";
    private String urlGetAllUserString = "http://androidthai.in.th/bee/getAllUserBeer.php";
    private String urlEditPostMessageString = "http://androidthai.in.th/bee/editPostMessageByIdBeer.php";

    private String[] columnUserStrings = new String[]{"id", "Name", "User",
            "Password", "PathAvatar", "PostMessage"};


    public String getUrlEditPostMessageString() {
        return urlEditPostMessageString;
    }

    public String[] getColumnUserStrings() {
        return columnUserStrings;
    }

    public String getUrlGetAllUserString() {
        return urlGetAllUserString;
    }

    public String getUrlAddUserString() {
        return urlAddUserString;
    }

    public String getHostString() {
        return hostString;
    }

    public String getUserString() {
        return userString;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public int getPortAnInt() {
        return portAnInt;
    }
}
