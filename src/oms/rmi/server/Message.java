/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oms.rmi.server;

import bean.VTSBean;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public interface Message extends Remote {
    
    ArrayList<ArrayList<String>> getEhrCentral(String pmino) throws RemoteException;
    ArrayList<ArrayList<String>> getEhrCentral2(String pmino) throws RemoteException;
    boolean addEhrCentral_vts(String pmino, String vts_data) throws RemoteException;
    ArrayList<String> getBioPDI(String pmiNo) throws RemoteException;
    
    boolean isLogin(String username, String password) throws RemoteException;
    ArrayList<String> getLoginData(String username, String password) throws RemoteException;
    boolean insertData(ArrayList<String> data_user) throws RemoteException;
    
    // fast rmi query
    ArrayList<ArrayList<String>> getQuery(String query, int col, String data[]) throws RemoteException;
    boolean setQuery(String query, String data[]) throws RemoteException;
    
    // fast sql query
    boolean setQuerySQL(String query) throws RemoteException;
    ArrayList<ArrayList<String>> getQuerySQL(String query) throws RemoteException;
}
