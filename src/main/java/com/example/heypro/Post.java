package com.example.heypro;

public class Post {

    String U_ID , Date , Time , Post_Caption , Post_Image , Profile_Image_User , UserFullName ;


    public Post(){

    }

    public Post(String u_ID, String date, String time, String post_Caption, String post_Image, String profile_Image_User, String userFullName) {
        U_ID = u_ID;
        Date = date;
        Time = time;
        Post_Caption = post_Caption;
        Post_Image = post_Image;
        Profile_Image_User = profile_Image_User;
        UserFullName = userFullName;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPost_Caption() {
        return Post_Caption;
    }

    public void setPost_Caption(String post_Caption) {
        Post_Caption = post_Caption;
    }

    public String getPost_Image() {
        return Post_Image;
    }

    public void setPost_Image(String post_Image) {
        Post_Image = post_Image;
    }

    public String getProfile_Image_User() {
        return Profile_Image_User;
    }

    public void setProfile_Image_User(String profile_Image_User) {
        Profile_Image_User = profile_Image_User;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }
}
