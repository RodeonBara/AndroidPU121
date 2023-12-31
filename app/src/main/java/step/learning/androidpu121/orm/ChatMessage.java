package step.learning.androidpu121.orm;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class ChatMessage {
    public String id;
    public String author;
    public String text;
    public String moment;
    public static ChatMessage fromJson(JSONObject jsonObject) {
        ChatMessage chatMessage = new ChatMessage();
        try {
            for (Field field : ChatMessage.class.getDeclaredFields()) {
                if (jsonObject.has(field.getName())) {
                    field.set(chatMessage,
                            jsonObject.getString(field.getName())
                    );
                }
            }
        } catch (Exception ignored) { }
        return chatMessage;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
