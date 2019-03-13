package trd.ams.dto;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class UserLoginDto {

	public UserLoginDto() {
		// TODO Auto-generated constructor stub
	}
	private String createdStamp;
	private String createdTxStamp;
	private String currentPassword;
	private String disabledDateTime;
	private String enabled;
	private String externalAuthId;
	private String hasLoggedOut;
	private String isSystem;
	private String lastCurrencyUom;
	private String lastLocale;
	private String lastTimeZone;
	private String lastUpdatedStamp;
	private String lastUpdatedTxStamp;
	private String partyId;
	private String passwordHint;
	private String requirePasswordChange;
	private String successiveFailedLogins;
	private String userLdapDn;
	private String userLoginId;

	private String deviceId;

	public String getCreatedStamp() {
		return createdStamp;
	}

	public void setCreatedStamp(String createdStamp) {
		this.createdStamp = createdStamp;
	}

	public String getCreatedTxStamp() {
		return createdTxStamp;
	}

	public void setCreatedTxStamp(String createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getDisabledDateTime() {
		return disabledDateTime;
	}

	public void setDisabledDateTime(String disabledDateTime) {
		this.disabledDateTime = disabledDateTime;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getExternalAuthId() {
		return externalAuthId;
	}

	public void setExternalAuthId(String externalAuthId) {
		this.externalAuthId = externalAuthId;
	}

	public String getHasLoggedOut() {
		return hasLoggedOut;
	}

	public void setHasLoggedOut(String hasLoggedOut) {
		this.hasLoggedOut = hasLoggedOut;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public String getLastCurrencyUom() {
		return lastCurrencyUom;
	}

	public void setLastCurrencyUom(String lastCurrencyUom) {
		this.lastCurrencyUom = lastCurrencyUom;
	}

	public String getLastLocale() {
		return lastLocale;
	}

	public void setLastLocale(String lastLocale) {
		this.lastLocale = lastLocale;
	}

	public String getLastTimeZone() {
		return lastTimeZone;
	}

	public void setLastTimeZone(String lastTimeZone) {
		this.lastTimeZone = lastTimeZone;
	}

	public String getLastUpdatedStamp() {
		return lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(String lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public String getLastUpdatedTxStamp() {
		return lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(String lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getRequirePasswordChange() {
		return requirePasswordChange;
	}

	public void setRequirePasswordChange(String requirePasswordChange) {
		this.requirePasswordChange = requirePasswordChange;
	}

	public String getSuccessiveFailedLogins() {
		return successiveFailedLogins;
	}

	public void setSuccessiveFailedLogins(String successiveFailedLogins) {
		this.successiveFailedLogins = successiveFailedLogins;
	}

	public String getUserLdapDn() {
		return userLdapDn;
	}

	public void setUserLdapDn(String userLdapDn) {
		this.userLdapDn = userLdapDn;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
