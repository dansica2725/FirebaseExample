package firebase.example.com.firebaseexample;

import java.util.ArrayList;

public class Names {

    String nameId;
    String name;
    String sessionId;

    public Names() {

    }
    public Names(String nameId, String name) {
        this.nameId = nameId;
        this.name = name;
    }
    public Names(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNameId() {
        return nameId;
    }

    public String getName() {
        return name;
    }
    public String getSessionId() {
        return sessionId;
    }
}
