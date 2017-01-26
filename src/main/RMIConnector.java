/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import bean.EHR_Central;
import bean.NetConfig;
import bean.Session;
import bean.VTSBean;
import helper.J;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.ArrayList;
import oms.rmi.server.Message;

/**
 *
 * @author End User
 */
public class RMIConnector {
    private static EHR_Central ec = new EHR_Central();
    private static NetConfig nc = new NetConfig();
    private static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    private static ArrayList<String> pdidata = new ArrayList<String>();
    private static Session dataUser = new Session();
    private static boolean loggedIn = false;
    
    public static void setConfig(String host, int port) {
        nc.setHost(host);
        nc.setPort(port);
    }
    
    public static ArrayList<ArrayList<String>> getEhrCentral(String pmino) {
        try {
            Registry myRegistry = LocateRegistry.getRegistry(nc.getHost(), nc.getPort());
            Message impl = (Message) myRegistry.lookup("myMessage");
            data = impl.getEhrCentral(pmino);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static ArrayList<ArrayList<String>> getEhrCentral2(String pmino) {
        try {
            Registry myRegistry = LocateRegistry.getRegistry(nc.getHost(), nc.getPort());
            Message impl = (Message) myRegistry.lookup("myMessage");
            data = impl.getEhrCentral2(pmino);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static boolean addEhrCentral_vts(String pmino, 
            String height, String weight, String sys, String dis, String pul, 
            String blg) {
        boolean stat = false;
        try {
            Registry myRegistry = LocateRegistry.getRegistry(nc.getHost(), nc.getPort());
            Message impl = (Message) myRegistry.lookup("myMessage");
            ArrayList<String> pdi = impl.getBioPDI(pmino);
            String PMI = "";
            String name = "";
            String IC = "";
            String race = "";
            String sex = "";
            String DOB = "";
            String blood = "";
            String pstatus = "";
            if (pdi.size() > 0) {
                PMI = pdi.get(0);
                name = pdi.get(2);
                IC = pdi.get(4);
                race = pdi.get(13);
                sex = pdi.get(11);
                DOB = pdi.get(10);
                blood = pdi.get(16);
                pstatus = pdi.get(12);
            }
            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
            VTSBean vts = new VTSBean();
            vts.setHeight(height);
            vts.setWeight(weight);
            vts.setBpSittingSys(sys);
            vts.setBpSittingDiag(dis);
            vts.setPulseRate(pul);
            vts.setEpisodeDate(String.valueOf(timestamp));
            vts.setEncounterDate(String.valueOf(timestamp));
            vts.setBloodGlucose(blg);
            String header = "MSH|^~|CIS^T12109|" + "<cr>" + "\n";
            String patientInfo = "PDI|" + PMI + "|" + name + "^" + IC + "^" 
                    + race + "^" + sex + "^" + DOB + "^" + blood + "^" 
                    + pstatus + "^" + "|" + "<cr>" + "\n";
            String vts_data = "VTS|" 
                    + vts.getEpisodeDate() + "|" 
                    + vts.getTempature() + "^" 
                    + vts.getBpSittingSys() + "^" 
                    + vts.getBpSittingDiag() + "^" 
                    + vts.getBpSupineSys() + "^" 
                    + vts.getBpSupineDiag() + "^" 
                    + vts.getBpStandingSys() + "^" 
                    + vts.getBpStandingDiag() + "^" 
                    + vts.getWeight() + "^" 
                    + vts.getHeight() + "^" 
                    + vts.getHeadCircumference() + "^" 
                    + vts.getRespiratoryRate() + "^" 
                    + vts.getGsc() + "^" 
                    + vts.getPulseRate() + "^" 
                    + vts.getLeftPupilCondition() + "^" 
                    + vts.getLeftPupilOption() + "^" 
                    + vts.getLeftPupilSize() + "^" 
                    + vts.getLeftLightReflex() + "^" 
                    + vts.getRightLightReflex() + "^" 
                    + vts.getLeftAccomReflex() + "^" 
                    + vts.getRightAccomReflex() + "^" 
                    + vts.getHeartRate() + "^" 
                    + vts.getEncounterDate() + "^" 
                    + vts.getHfcCode() + "^" 
                    + vts.getDoctorId() + "^" 
                    + vts.getDoctorName() + "^"
                    + vts.getGcsPoints() + "^"
                    + vts.getGcsResult() + "^"
                    + vts.getPgcsPoints() + "^"
                    + vts.getPgcsResult() + "^"
                    + vts.getOxygenSaturation() + "^"
                    + vts.getPainScale() + "^"
                    + vts.getBloodGlucose() +"^"
                    + "null^|<cr>";
            String ehr_data = header + patientInfo + vts_data;
            stat = impl.addEhrCentral_vts(pmino, ehr_data);
        } catch (Exception e) {
            stat = false;
            e.printStackTrace();
        }
        return stat;
    }
    
    public static ArrayList<String> getPatientBiodata(String pmino) {
        try {
            Registry myRegistry = LocateRegistry.getRegistry(nc.getHost(), nc.getPort());
            Message impl = (Message) myRegistry.lookup("myMessage");
            pdidata = impl.getBioPDI(pmino);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdidata;
    }
    
    public static ArrayList<String> getDataUser() {
        return dataUser.getSessionData();
    }

    public static void setLogin(String username, String password) {
        boolean stat = false;
        try {
            Registry myRegistry = LocateRegistry.getRegistry(nc.getHost(), nc.getPort());
            Message impl = (Message) myRegistry.lookup("myMessage");
            stat = impl.isLogin(username, password);
            if (stat) {
                dataUser.setSessionData(impl.getLoginData(username, password));
            }
            setLoggedIn(stat);
        } catch (Exception e) {
            setLoggedIn(stat);
            J.o("Error!", "Error while Login!\n" + e.getMessage(), 0);
            e.printStackTrace();
        }
    }

    public static boolean signUp(ArrayList<String> data_user) {
        boolean stat = false;
        try {
            Registry myRegistry = LocateRegistry.getRegistry(nc.getHost(), nc.getPort());
            Message impl = (Message) myRegistry.lookup("myMessage");
            stat = impl.insertData(data_user);
        } catch (Exception e) {
            stat = false;
            J.o("Error!", "Error while sign up!\n" + e.getMessage(), 0);
            e.printStackTrace();
        }
        return stat;
    }

    public static void setLogout() {
        setLoggedIn(false);
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    private static void setLoggedIn(boolean LoggedIn) {
        loggedIn = LoggedIn;
    }
    
    public static void main(String[] args) {
        RMIConnector.setConfig("10.73.32.200", 1099);
        RMIConnector rmic = new RMIConnector();
        String pminew = rmic.getPMI("10.73.32.200", 1099, "asbasd");
        System.out.println("new pmi: "+pminew);
    }
    
//    public static void main(String[] args) {
//        String username = "khanapi";
//        String password = "abc123";
//        RMIConnector.setConfig("10.73.32.200", 1099);
//        RMIConnector.setLogin(username, password);
//        System.out.println(""+RMIConnector.isLoggedIn());
//        if(RMIConnector.isLoggedIn()) {
//            System.out.print("Hai..");
//        } else {
//            System.out.print("Hoi!");
//        }
//    }
    
//    public static void main(String[] args) {
//        String host = "10.73.32.200";
//        int port = 1099;
//        String pmino = "9001280450427";
//        
//        RMIConnector.setConfig(host, port);
//        ArrayList<ArrayList<String>> d = RMIConnector.getEhrCentral(pmino);
//        for(int i = 0; i < d.size(); i++) {
//            for(int j = 0; j < d.get(i).size(); j++) {
//                System.out.print(d.get(i).get(j)+"\n");
//            }
//            System.out.println("-----");
//        }
//        
//        String height = "175";
//        String weight = "75";
//        String sys = "130";
//        String dis = "93";
//        
//        boolean stat = RMIConnector.addEhrCentral_vts(pmino, height, weight, sys, dis);
//        System.out.println(stat);
//    }
    
//    public static void main(String[] args) {
//        String sql = "SELECT * FROM EHR_Central";
//        RMIConnector rmi = new RMIConnector();
//        ArrayList<ArrayList<String>> data = rmi.getQuerySQL("10.73.32.200", 1099, sql);
//        System.out.println(data);
//    }
    
    public String getPMI(String host, int port, String ic) {
        String pmi = "";
        try {
            Registry myRegistry = LocateRegistry.getRegistry(host, port);
            Message impl = (Message) myRegistry.lookup("myMessage");
            pmi = impl.getPMI(ic);
        } catch (Exception e) {
            pmi = "";
            J.o("Error!", "Error! Something wrong while execute the query!!\n" + e.getMessage(), 0);
            e.printStackTrace();
        }
        return pmi;
    }
    
    public boolean setQuerySQL(String host, int port, String query) {
        boolean stat = false;
        try {
            Registry myRegistry = LocateRegistry.getRegistry(host, port);
            Message impl = (Message) myRegistry.lookup("myMessage");
            stat = impl.setQuerySQL(query);
        } catch (Exception e) {
            stat = false;
            J.o("Error!", "Error! Something wrong while execute the query!!\n" + e.getMessage(), 0);
            e.printStackTrace();
        }
        return stat;
    }
    
    public ArrayList<ArrayList<String>> getQuerySQL(String host, int port, String query) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            Registry myRegistry = LocateRegistry.getRegistry(host, port);
            Message impl = (Message) myRegistry.lookup("myMessage");
            data = impl.getQuerySQL(query);
        } catch (Exception e) {
            J.o("Error!", "Error! Something wrong while execute the query!!\n" + e.getMessage(), 0);
            e.printStackTrace();
        }
        return data;
    }
}
