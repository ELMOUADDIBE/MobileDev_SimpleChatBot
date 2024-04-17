package ma.enset.mobile_chatbot.api;

import ma.enset.mobile_chatbot.model.BrainReponse;
import ma.enset.mobile_chatbot.model.MessageModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BrainAPI {
    @GET
    Call <BrainReponse> getMessage(@Url String msg);

}
