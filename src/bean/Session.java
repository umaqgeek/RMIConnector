/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;

/**
 *
 * @author End User
 */
public class Session {
    private ArrayList<String> sessionData;
    public Session() {
        sessionData = new ArrayList<String>();
    }

    public ArrayList<String> getSessionData() {
        return sessionData;
    }

    public void setSessionData(ArrayList<String> sessionData) {
        this.sessionData = sessionData;
    }
}
