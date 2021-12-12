package com.cc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;

//import lombok.*;
//@Data

@JsonTypeName(value = "forum")
public class Forum extends AppContent {

    private String forumId;
    private String forumName;

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "forumId='" + forumId + '\'' +
                ", forumName='" + forumName + '\'' +
                "} " + super.toString();
    }
}
