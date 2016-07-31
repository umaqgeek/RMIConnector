/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author End User
 */
public class EHR_Central {
    private int central_code;
    private String pmi_no;
    private String c_txndate;
    private String c_txndata;
    private int status;

    /**
     * @return the central_code
     */
    public int getCentral_code() {
        return central_code;
    }

    /**
     * @param central_code the central_code to set
     */
    public void setCentral_code(int central_code) {
        this.central_code = central_code;
    }

    /**
     * @return the pmi_no
     */
    public String getPmi_no() {
        return pmi_no;
    }

    /**
     * @param pmi_no the pmi_no to set
     */
    public void setPmi_no(String pmi_no) {
        this.pmi_no = pmi_no;
    }

    /**
     * @return the c_txndate
     */
    public String getC_txndate() {
        return c_txndate;
    }

    /**
     * @param c_txndate the c_txndate to set
     */
    public void setC_txndate(String c_txndate) {
        this.c_txndate = c_txndate;
    }

    /**
     * @return the c_txndata
     */
    public String getC_txndata() {
        return c_txndata;
    }

    /**
     * @param c_txndata the c_txndata to set
     */
    public void setC_txndata(String c_txndata) {
        this.c_txndata = c_txndata;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
