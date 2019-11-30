package br.com.smartconn.mine;

/**
 * @author Henrique Nascimento
 *
 * @version 1.0.0
 */

public class Notification {
    
    public Notification(String body, String when){
        this.body = body;
        this.when = when;
    }
    
    public Notification(){}
    
    private String body;
    private String when;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }
    
    
}
