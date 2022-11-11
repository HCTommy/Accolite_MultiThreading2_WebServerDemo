import java.io.Serializable;

public class Request implements Serializable {
    String clientName;
    String task;

    public Request() {
    }

    public Request(String clientName, String task) {
        this.clientName = clientName;
        this.task = task;
    }
}
