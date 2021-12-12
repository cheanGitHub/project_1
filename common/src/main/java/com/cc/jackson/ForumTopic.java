package com.cc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;

//import lombok.*;
//@Data

@JsonTypeName(value = "forumTopic")
public class ForumTopic extends AppContent {

    private String topicId;
    private String topicName;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "topicId='" + topicId + '\'' +
                ", topicName='" + topicName + '\'' +
                '}';
    }
}
