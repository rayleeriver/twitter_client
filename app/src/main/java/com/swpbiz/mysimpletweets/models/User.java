package com.swpbiz.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name="users")
public class User extends Model implements Parcelable {

    @Column(name="uid", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;

    @Column(name="name")
    private String name;

    @Column(name="screenName")
    private String screenName;

    @Column(name="profileImageUrl")
    private String profileImageUrl;

    @Column(name="description")
    private String tagLine;

    @Column(name="followersCount")
    private String followersCount;

    @Column(name="friendsCount")
    private String friendsCount;

    public User() {
        super();
    }

    public static User fromJson(JSONObject json) {
        User user = new User();

        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.tagLine = json.getString("description");
            user.followersCount = json.getString("followers_count");
            user.friendsCount = json.getString("friends_count");
            user.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public String getFriendsCount() {
        return friendsCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(uid);
        dest.writeString(screenName);
        dest.writeString(profileImageUrl);
    }

    public static final Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screenName = in.readString();
        profileImageUrl = in.readString();
    }

}
