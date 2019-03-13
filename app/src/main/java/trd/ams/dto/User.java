package trd.ams.dto;

/**
 * Created by hanu on 30-10-2017.
 */

public class User {


   // private String id;
    private String userLoginId;
    private String fromDate;
    private String visitId;
    private String currentPassword;
    private String successfulLogin;
    private String partyId;
    private String passwordHint;
    private String enabled;
    private String hasLoggedOut;
    private String isSystem;
    private int successiveFailedLogins;
    private String deviceIMEI;
    private String passwordUsed;
    private String createdStamp;

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitIdt) {
        this.visitId = visitId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getSuccessfulLogin() {
        return successfulLogin;
    }

    public void setSuccessfulLogin(String successfulLogin) {
        this.successfulLogin = successfulLogin;
    }

    public int getSuccessiveFailedLogins() {
        return successiveFailedLogins;
    }

    public void setSuccessiveFailedLogins(int successiveFailedLogins) {
        this.successiveFailedLogins = successiveFailedLogins;
    }


    public String getHasLoggedOut() {
        return hasLoggedOut;
    }

    public void setHasLoggedOut(String hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }

    public String getPasswordUsed() {
        return passwordUsed;
    }

    public void setPasswordUsed(String passwordUsed) {
        this.passwordUsed = passwordUsed;
    }

    public String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(String createdStamp) {
        this.createdStamp = createdStamp;
    }









}
