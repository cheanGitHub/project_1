package com.cc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.Set;

public class JacksonTest {

    public static void main(String[] args) throws IOException {

        Reflections reflections = new Reflections("com.huawei.jackson.");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(JsonTypeName.class);
        System.out.println(classSet);

        ObjectMapper mapper = new ObjectMapper();
//        classSet.parallelStream().forEach(clazz -> mapper.registerSubtypes(clazz));

//        mapper.registerSubtypes(AppContent.class);
        mapper.registerSubtypes(Forum.class);
        mapper.registerSubtypes(ForumTopic.class);




//        ObjectMapper objectMapper = new ObjectMapper();
//        String forumStr = "{\" \": \" \"}";
//        Forum forum = objectMapper.readValue(forumStr, Forum.class);

//        Forum forum = new Forum();
//        forum.setCategory("forum");
//        forum.setExpId("expId-1");
//        forum.setForumId("fid-1");
//        forum.setForumName("fname-1");
//        String string = mapper.writeValueAsString(forum);
//        System.out.println(string);

        String s = "{\"category\":\"forum\",\"expId\":\"expId-1\",\"forumId\":\"fid-1\",\"forumName\":\"fname-1\"}";
        AppContent appContent = mapper.readValue(s, AppContent.class);
        System.out.println(appContent);
        System.out.println(appContent.getClass().getName());

    }
}
