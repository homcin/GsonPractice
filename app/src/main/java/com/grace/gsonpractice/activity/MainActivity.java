package com.grace.gsonpractice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.grace.gsonpractice.R;
import com.grace.gsonpractice.adapter.UserTypeAdapter;
import com.grace.gsonpractice.api.ApiResponse;
import com.grace.gsonpractice.entity.Book;
import com.grace.gsonpractice.entity.Category;
import com.grace.gsonpractice.entity.Staff;
import com.grace.gsonpractice.entity.User;

import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvShowResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowResult = (TextView)findViewById(R.id.tvShowResult);


        /*
        //example1：序列化成Json字符串，结果为{"age":22,"emailAddress":"247102578@qq.com","name":"homcin"}，可见是按字段首字母顺序排列
        Gson gson = new Gson();
        User user = new User("homcin", 22, "247102578@qq.com");
        String jsonString = gson.toJson(user);
        tvShowResult.setText(jsonString);
        Log.i("example1", jsonString);
        */


        /*
        //example2：Json字符串反序列化为对象实例，结果为{"age":22,"name":"homcin"}，可见是按字段首字母顺序排列
        Gson gson = new Gson();
        String jsonString = "{\"name\":\"homcin\",\"age\":22,\"emailAddress\":\"247102578@qq.com\"}";
        User user = gson.fromJson(jsonString, User.class);
        tvShowResult.setText(user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        Log.i("example2", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        */

        /*
        //example3：属性重命名@SerializedName注解。为属性emailAddress添加@SerializedName注解。alternate接受String数组。
        //@SerializedName(value = "emailAddress", alternate = {"email", "email_address"})
        //private String emailAddress;
        //这样Json字符串中使用email，email_address等其他命名都能反序列为emailAddress属性的值。
        String jsonStringInJava = "{\"name\":\"homcin\",\"age\":22,\"emailAddress\":\"247102578@qq.com\"}";
        String jsonStringInPHP = "{\"name\":\"homcin\",\"age\":22,\"email_address\":\"247102578@qq.com\"}";
        String jsonStringForShort = "{\"name\":\"homcin\",\"age\":22,\"email\":\"247102578@qq.com\"}";
        Gson gson = new Gson();
        User user = gson.fromJson(jsonStringInJava, User.class);
        Log.i("example3", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        user = gson.fromJson(jsonStringInPHP, User.class);
        Log.i("example3", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        user = gson.fromJson(jsonStringForShort, User.class);
        Log.i("example3", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        */

        /*
        //example4：解析JSONArray，需借助TypeToken，它实现了对泛型的支持。TypeToken构造方法是protected修饰的，所以需要加{}。
        Gson gson = new Gson();
        String jsonArrayString = "[{\"name\":\"homcin\",\"age\":22,\"emailAddress\":\"247102578@qq.com\"}," +
                "{\"name\":\"shane\",\"age\":21,\"emailAddress\":\"281953676@qq.com\"}]";
        List<User> users = gson.fromJson(jsonArrayString, new TypeToken<List<User>>(){}.getType());
        User user0 = users.get(0);
        User user1 = users.get(1);
        Log.i("example4", user0.getName() + "," + String.valueOf(user0.getAge()) + "," + user0.getEmailAddress());
        Log.i("example4", user1.getName() + "," + String.valueOf(user1.getAge()) + "," + user1.getEmailAddress());
        */

        /*
        //example5：Gson泛型解析用于接口api，接口ApiResponse使用泛型，其成员data为泛型T。解析时，TypeToken传入data实际的类型用于Gson泛型解析。
        String response = "{\"code\":0,\"message\":\"success\",\"data\":{\"name\":\"homcin\",\"age\":22,\"emailAddress\":\"247102578@qq.com\"}}";
        String responseArray = "{\"code\":0,\"message\":\"success\",\"data\":[{\"name\":\"homcin\",\"age\":22,\"emailAddress\":\"247102578@qq.com\"}," +
                "{\"name\":\"shane\",\"age\":21,\"emailAddress\":\"281953676@qq.com\"}]}";
        Log.i("example5", response);
        Gson gson = new Gson();
        Type userType = new TypeToken<ApiResponse<User>>(){}.getType();
        ApiResponse<User> apiResponse = gson.fromJson(response, userType);
        User user = apiResponse.getData();
        Log.i("example5", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        Type userListType = new TypeToken<ApiResponse<List<User>>>(){}.getType();
        ApiResponse<List<User>> apiResponse2 = gson.fromJson(responseArray, userListType);
        List<User> users = apiResponse2.getData();
        User user0 = users.get(0);
        User user1 = users.get(1);
        Log.i("example5", user0.getName() + "," + String.valueOf(user0.getAge()) + "," + user0.getEmailAddress());
        Log.i("example5", user1.getName() + "," + String.valueOf(user1.getAge()) + "," + user1.getEmailAddress());
        */

        /*
        //example6：手动解析/反序列化，与Pull解析XML类似，基于reader字符流。
        //自动方式：fromJson内部也是通过JsonReader来实现的。手动方式可用于
        String jsonString = "{\"name\":\"homcin\",\"age\":22,\"emailAddress\":\"247102578@qq.com\"}";
        User user = new User();
        try{
            JsonReader reader = new JsonReader(new StringReader(jsonString));
            reader.beginObject();
            while(reader.hasNext()) {
                String s = reader.nextName();
                switch(s) {
                    case "name":
                        user.setName(reader.nextString());
                        break;
                    case "age":
                        user.setAge(reader.nextInt());
                        break;
                    case "emailAddress":
                        user.setEmailAddress(reader.nextString());
                        break;
                }
            }
            reader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("example6", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        */

        /*
        //example7：手动流式序列化，基于writer字符流，输出{"name":"homcin","age":22,"emaiLAddress":"247102578@qq.com"}
        //自动方式为gson.toJson(user, System.out);
        try {
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
            writer.beginObject()
                    .name("name").value("homcin")
                    .name("age").value(22)
                    .name("emaiLAddress").value("247102578@qq.com")
                    .endObject();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        /*
        //example8：借助GsonBuilder类来构建具有某些特殊功能的Gson实例。
        //如让Gson实例能导出属性emailAddress的null值，默认不导出。
        //输出：{"age":22,"emailAddress":null,"name":"homcin"}
        //还可以让Gson实例具有其他功能：如设置日期时间格式，禁此序列化内部类，禁止转义html标签等。
        User user = new User("homcin", 22);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        String jsonString = gson.toJson(user);
        tvShowResult.setText(jsonString);
        Log.i("example8", jsonString);
        */

        /*
        //example9：字段过滤，POJO由于处理业务而加入一些字段，这些字段不需要序列化，如果序列化可能带来循环引用的问题。
        //如Category类的parent字段是为处理业务而加入的，如果序列化会一直循环序列化其parent。
        //通过为Category类的成员添加@Expose（暴露）注解，而parent字段不添加@Expose注解。
        //通过GsonBuilder的excludeFieldsWithoutExposeAnnotation()方法生成具有不序列化没有@Expose注解字段功能的Gson实例。
        //输出：{"children":[{"id":100,"name":"笔记本"},{"id":100,"name":"台式机"}],"id":1,"name":"电脑"}
        Category computer = new Category(1, "电脑");
        List<Category> children = new ArrayList<Category>();
        Category laptop = new Category(100, "笔记本");
        Category desktop = new Category(100, "台式机");
        children.add(laptop);
        children.add(desktop);
        computer.setChildren(children);
        laptop.setParent(computer);
        desktop.setParent(computer);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String jsonString = gson.toJson(computer);
        tvShowResult.setText(jsonString);
        Log.i("example9", jsonString);
        */

        /*
        //example10：基于版本的字段导出使用了@Since和@Until注解和GsonBuilder.setVersion(Double)。
        //当Version>=Since的值时，该字段导出；当Version<Until的值时，该字段导出。
        //例子Version为4，Book类中rating字段使用@Since(5)注解，所以rating字段不导出。
        //输出：{"description":"郭霖","name":"第一行代码"}
        Book book = new Book("第一行代码", "郭霖", 4.5);
        Gson gson = new GsonBuilder()
                .setVersion(4)
                .create();
        String jsonString = gson.toJson(book);
        tvShowResult.setText(jsonString);
        Log.i("example10", jsonString);
        */

        /*
        //example11：基于访问修饰符：可以不序列化被指定访问修饰符修饰的字段。
        //使用GsonBuilder.excludeFieldsWithModifiers构建gson,支持int形的可变参数，值由java.lang.reflect.Modifier提供。
        //例子中Gson不序列化被final和static修饰的字段。
        //输出：{"age":2452,"duty":"AndroidProgramming","name":"homcin","salary":8000.0}
        Staff staff = new Staff("homcin", 22, 8000, "AndroidProgramming");
        staff.department = "development";
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC)
                .create();
        String jsonString = gson.toJson(staff);
        tvShowResult.setText(jsonString);
        Log.i("example11", jsonString);
        */

        /*
        //example12：基于策略（自定义规则）：借助GsonBuilder的addSerializationExclusionStrategy和addDeserializationExclusionStrategy方法。
        //实现ExclusionStrategy接口并作为参数传入。
        //输出：{"duty":"AndroidProgramming","name":"homcin","salary":8000.0}
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    //应该跳过的字段
                    public boolean shouldSkipField(FieldAttributes f) {
                        //这里跳过company字段名
                        if ("company".equals(f.getName())) return true;

                        //这里跳过带@Expose(deserialize = false)注解的字段
                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose !=null && expose.deserialize() == false) return true;

                        return false;
                    }
                    @Override
                    //应该跳过的字段的类型
                    public boolean shouldSkipClass(Class<?> clazz) {
                        //这里跳过int类和Integer类的字段
                        return (clazz == int.class || clazz == Integer.class);
                    }
                })
                .create();
        Staff staff = new Staff("homcin", 22, 8000, "AndroidProgramming");
        staff.department = "development";
        String jsonString = gson.toJson(staff);
        tvShowResult.setText(jsonString);
        Log.i("example12", jsonString);
        */

        /*
        //example13：POJO与JSON的字段映射规则/属性重命名。
        //方法一：@SerializedName注解，见example3，序列化和反序列化都适用。优先级高于FieldNamingStrategy。
        //方法二：GsonBuilder的setFieldNamingStrategy方法，用于序列化成JSON字符串时将字段名字重命名。
        //实现FieldNamingStrategy接口作为参数传入。重写translateName方法自定义转换规则。
        //例子实现序列化成JSON字符串时将名字为emailAddress的字段重命名为email_address。
        //执行例子前先注销掉example3添加的@SerializedName注解。
        //输出：{"age":22,"email_address":"247102478@qq.com","name":"homcin"}
        Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        if (f.getName() == "emailAddress") {
                            return "email_address";
                        } else {
                            return f.getName();
                        }
                    }
                })
                .create();
        User user = new User("homcin", 22, "247102478@qq.com");
        String jsonString = gson.toJson(user);
        tvShowResult.setText(jsonString);
        Log.i("example13", jsonString);
        */

        //example14：TypeAdapter用于接管某种类型的序列化和反序列过程。最大的自由度，想怎么序列化/反序列化就怎么就序列化/反序列化，很强大，优先级高于注解，能实现@SerializedName 、FieldNamingStrategy、Since、Until、Expose注解的功能。
        //使用：继承TypeAdapter抽象类，重写write方法实现序列化过程，重写read方法实现反序列化过程。
        //GsonBuilder调用registerTypeAdapter注册该TypeAdapter继承类实例。或者为某个类添加@JsonAdapter(UserTypeAdapter.class)注解。
        //输出：序列化：{"name":"homcin","age":22,"email":"247102578@qq.com"}
        //反序列化：homcin,22,247102578@qq.com
        //在TypeAdapter的write方法中规定如果邮箱地址为空字符串则设置为noRegister：shane,21,noRegister
        User user = new User("homcin", 22, "247102578@qq.com");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
        String jsonString = gson.toJson(user);
        tvShowResult.setText(jsonString);
        Log.i("example14", jsonString);
        String jsonStringInPHP = "{\"name\":\"homcin\",\"age\":22,\"email_address\":\"247102578@qq.com\"}";
        user = gson.fromJson(jsonStringInPHP, User.class);
        Log.i("example14", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
        String jsonStringWithoutEmail = "{\"name\":\"shane\",\"age\": 21,\"email_address\":\"\"}";
        user = gson.fromJson(jsonStringWithoutEmail, User.class);
        Log.i("example14", user.getName() + "," + String.valueOf(user.getAge()) + "," + user.getEmailAddress());
    }

}
