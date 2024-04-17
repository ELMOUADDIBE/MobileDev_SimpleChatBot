package ma.enset.mobile_chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ma.enset.mobile_chatbot.adapters.ChatBotAdapter;
import ma.enset.mobile_chatbot.api.BrainAPI;
import ma.enset.mobile_chatbot.model.BrainReponse;
import ma.enset.mobile_chatbot.model.MessageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<MessageModel> dataMessages = new ArrayList<>();
    private EditText sendMsg;
    private ImageButton buttonSend;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMsg = findViewById(R.id.sendMsg);
        buttonSend = findViewById(R.id.sendButton);
        recyclerView = findViewById(R.id.chat);
        Gson gson= new GsonBuilder().create();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://api.brainshop.ai/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        BrainAPI brainAPI = retrofit.create(BrainAPI.class);
        ChatBotAdapter chatBotAdapter=new ChatBotAdapter(dataMessages,this);
        LinearLayoutManager manager= new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(chatBotAdapter);
        recyclerView.setLayoutManager(manager);

        buttonSend.setOnClickListener(view -> {
            String msg = sendMsg.getText().toString();
            dataMessages.add(new MessageModel(msg,"user"));
            chatBotAdapter.notifyDataSetChanged();
            String msgs = sendMsg.getText().toString();
            String url = null;
            try {
                url = "get?bid=181516&key=UlgnW4ZNhjRG94fX&uid=[uid]&msg="+ URLEncoder.encode(msgs, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            Call<BrainReponse> response = brainAPI.getMessage(url);
            sendMsg.setText("");

            response.enqueue(new Callback<BrainReponse>() {
                @Override
                public void onResponse(Call<BrainReponse> call, Response<BrainReponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.i("ChatBotResponse", "Response: " + response.body().getCnt());
                        dataMessages.add(new MessageModel(response.body().getCnt(),"bot"));
                        chatBotAdapter.notifyDataSetChanged();
                    } else {
                        Log.i("ChatBotResponse", "No response received.");
                    }
                }

                @Override
                public void onFailure(Call<BrainReponse> call, Throwable throwable) {

                }
            });
        });
    }
}