package step.learning.androidpu121;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import step.learning.androidpu121.orm.ChatMessage;
import step.learning.androidpu121.orm.ChatResponse;

public class ChatActivity extends AppCompatActivity {
    private final String chatUrl = "https://chat.momentfor.fun/";
    private LinearLayout chatContainer;
    private  ChatResponse chatResponse;
    private final List<ChatMessage> chatMessages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatContainer = findViewById(R.id.chat_layout_container);
        new Thread(this::loadChatMessages).start();
    }

    private void showChatMessage()
    {
        for(ChatMessage chatMessage : chatMessages){
            chatContainer.addView(chatMessageView(chatMessage));
        }
    }

    private View chatMessageView(ChatMessage chatMessage)
    {
        LinearLayout messageContainer = new LinearLayout(ChatActivity.this);
        messageContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        messageContainer.setLayoutParams(containerParams);
        TextView tv = new TextView( ChatActivity.this);
        tv.setText(chatMessage.getAuthor());
        tv.setTypeface(null, Typeface.BOLD);
        messageContainer.addView(tv);

        tv = new TextView( ChatActivity.this);
        tv.setText(chatMessage.getText());
        tv.setTypeface(null, Typeface.ITALIC);
        messageContainer.addView(tv);

        return messageContainer;
    }
    private void loadChatMessages() {
        try( InputStream inputStream = new URL( chatUrl ).openStream() ) {
            ChatResponse chatResponse = ChatResponse.fromJsonString(
                    streamToString(inputStream)
            );

            //Проверяем на новые сообщения, обновляем (за надобностью) колекцию chatMessages
            boolean wasNewMessages = false;
            for(ChatMessage message : chatResponse.getData()){
                if( chatMessages.stream().noneMatch(m->m.getId().equals(message.getId())));
                //Новое сообщение (нету в колекции)
                chatMessages.add(message);
                wasNewMessages = true;

            }
            if(wasNewMessages){
                runOnUiThread(this::showChatMessage);
            }

        }
        catch( NetworkOnMainThreadException ignored ) {
            Log.e( "loadChatMessages", "NetworkOnMainThreadException" ) ;
        }
        catch( MalformedURLException ex ) {
            Log.e( "loadChatMessages", "URL parse error: " + ex.getMessage() ) ;
        }
        catch( IOException ex ) {
            Log.e( "loadChatMessages", "IO error: " + ex.getMessage() ) ;
        }

    }

    private String streamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream builder = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRecievid;
        while((bytesRecievid = inputStream.read(buffer)) > 0) {
            builder.write(buffer, 0, bytesRecievid);
        }
        return builder.toString();

    }
}