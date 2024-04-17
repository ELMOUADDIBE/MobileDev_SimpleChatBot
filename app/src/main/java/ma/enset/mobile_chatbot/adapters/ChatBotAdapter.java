package ma.enset.mobile_chatbot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ma.enset.mobile_chatbot.R;
import ma.enset.mobile_chatbot.model.MessageModel;

public class ChatBotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MessageModel> dataMessages;
    private Context context;

    public ChatBotAdapter(List<MessageModel> dataMessages, Context context) {
        this.dataMessages = dataMessages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 0) {
            View view = inflater.inflate(R.layout.user_msg, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.bot_msg, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = dataMessages.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).textViewMsg.setText(message.getMessage());
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).textViewMsg.setText(message.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return "user".equals(dataMessages.get(position).getSender()) ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return dataMessages.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMsg;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMsg = itemView.findViewById(R.id.userMsg);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMsg;

        BotViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMsg = itemView.findViewById(R.id.botMsg);
        }
    }
}