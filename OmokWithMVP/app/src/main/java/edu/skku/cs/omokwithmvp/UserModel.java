package edu.skku.cs.omokwithmvp;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    private String name;
    private String passwd;
    private String nickname;
    private String easyscore;
    private String hardscore;

    protected UserModel(Parcel in) {
        name = in.readString();
        passwd = in.readString();
        nickname = in.readString();
        easyscore = in.readString();
        hardscore = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel parcel) {
            return new UserModel(parcel);
        }

        @Override
        public UserModel[] newArray(int i) {
            return new UserModel[i];
        }
    };

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEasyscore(String easyscore) {
        this.easyscore = easyscore;
    }

    public void setHardscore(String hardscore) {
        this.hardscore = hardscore;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getEasyscore() {
        return easyscore;
    }

    public String getHardscore() {
        return hardscore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(passwd);
        parcel.writeString(nickname);
        parcel.writeString(easyscore);
        parcel.writeString(hardscore);
    }
}
