package ma.enset.mobile_chatbot.model;

public class BrainReponse {
    public BrainReponse(String cnt) {
        this.cnt = cnt;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public BrainReponse() {
    }

    private String cnt;
}
