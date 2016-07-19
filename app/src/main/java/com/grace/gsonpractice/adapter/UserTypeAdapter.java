package com.grace.gsonpractice.adapter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.grace.gsonpractice.entity.User;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/19.
 */
public class UserTypeAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("name").value(value.getName());
        out.name("age").value(value.getAge());
        out.name("email").value(value.getEmailAddress());
        out.endObject();
    }
    @Override
    public User read(JsonReader in) throws IOException {
        User user = new User();
        in.beginObject();
        while(in.hasNext()) {
            switch(in.nextName()) {
                case "name":
                    user.setName(in.nextString());
                    break;
                case "age":
                    user.setAge(in.nextInt());
                    break;
                case "email":
                case "email_address":
                case "emailAddress":
                    String emailAddress = in.nextString();
                    user.setEmailAddress(emailAddress == "" ? "noRegister" : emailAddress);
                    break;
            }
        }
        in.endObject();
        return user;
    }
}
