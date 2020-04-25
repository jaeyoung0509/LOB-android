package com.example.lob.DTO;

import java.io.Serializable;

public class UserDto implements Serializable {
    private String userMail;
    private String userUid;

    public UserDto(String userMail, String userUid) {
        this.userMail = userMail;
        this.userUid = userUid;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
