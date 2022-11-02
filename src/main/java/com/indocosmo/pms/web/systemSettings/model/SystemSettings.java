package com.indocosmo.pms.web.systemSettings.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "system_setting")
public class SystemSettings {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "production_mode")
	private boolean productionMode;

	@Column(name = "service_charge")
	private boolean serviceCharge;

	@Column(name = "service_tax_incl")
	private boolean serviceTaxIncl;

	public boolean isServiceTaxIncl() {
		return serviceTaxIncl;
	}

	public void setServiceTaxIncl(boolean serviceTaxIncl) {
		this.serviceTaxIncl = serviceTaxIncl;
	}

	public boolean isServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(boolean serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	@Column(name = "fin_year_start_in")
	private byte finYearStartIn;

	@Column(name = "week_start_on")
	private String weekStartOn;

	@Column(name = "base_currency_id")
	private int baseCurrencyId;

	@Column(name = "weekly_special_days")
	private String weeklySpecialDays;

	@Column(name = "tariff_include_tax")
	private boolean tariffIncludeTax;

	@Column(name = "date_format")
	private String dateFormat;

	@Column(name = "time_format")
	private String timeFormat;

	@Column(name = "tax1_name")
	private String tax1Name = "";

	@Column(name = "tax2_name")
	private String tax2Name = "";

	@Column(name = "tax3_name")
	private String tax3Name = "";

	@Column(name = "tax4_name")
	private String tax4Name = "";

	@Column(name = "bill_rounding")
	private int billRounding;

	@Column(name = "confirm_before")
	private short confirmBefore;

	@Column(name = "max_rooms_per_booking")
	private Byte maxRoomsPerBooking = 0;

	@Column(name = "max_nights_per_booking")
	private Byte maxNightsPerBooking = 0;

	@Column(name = "mail_notify_reservation")
	private boolean mailNotifyReservation;

	@Column(name = "mail_notify_cutoff_date")
	private boolean mailNotifyCutoffDate;

	@Column(name = "mail_notify_cancellation")
	private boolean mailNotifyCancellation;

	@Column(name = "mail_notify_confirmation")
	private boolean mailNotifyConfirmation;

	@Column(name = "mail_notify_noshow")
	private boolean mailNotifyNoShow;

	@Column(name = "mail_notify_checkin")
	private boolean mailNotifyCheckIn;

	@Column(name = "mail_notify_checkout")
	private boolean mailNotifyCheckOut;

	@Column(name = "sms_notify_reservation")
	private boolean smsNotifyReservation;

	@Column(name = "sms_notify_cutoff_date")
	private boolean smsNotifyCutoffDate;

	@Column(name = "sms_notify_cancellation")
	private boolean smsNotifyCancellation;

	@Column(name = "sms_notify_confirmation")
	private boolean smsNotifyConfirmation;

	@Column(name = "sms_notify_noshow")
	private boolean smsNotifyNoShow;

	@Column(name = "sms_notify_checkin")
	private boolean smsNotifyCheckIn;

	@Column(name = "sms_notify_checkout")
	private boolean smsNotifyCheckOut;

	@Column(name = "smtp_server")
	private String smtpServer;

	@Column(name = "smtp_port")
	private String smtpPort;

	@Column(name = "smtp_suserid")
	private String smtpSUserId;

	@Column(name = "smtp_password")
	private String smtpPassword;

	@Column(name = "sms_web_service")
	private String smsWebService;

	@Column(name = "sms_userid")
	private String smsUserId;

	@Column(name = "sms_password")
	private String smsPassword;

	@Column(name = "check_in_time")
	private Date checkInTime;

	@Column(name = "check_out_time")
	private Date checkOutTime;

	@Column(name = "hotel_date")
	private Date hotelDate;

	@Column(name = "night_audit_stage")
	private byte nightAuditStage;

	@Column(name = "last_upd_ts")
	private Date lastUpdTs;

	/** Transient Fields **/
	@Transient
	private String fmtdCheckInTime;

	@Transient
	private String fmtdCheckOutTime;

	@Transient
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	@Transient
	private boolean sun, mon, tue, wed, thu, fri, sat;

	@Transient
	private String currencySymbol;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isProductionMode() {
		return productionMode;
	}

	public void setProductionMode(boolean productionMode) {
		this.productionMode = productionMode;
	}

	public byte getFinYearStartIn() {
		return finYearStartIn;
	}

	public void setFinYearStartIn(byte finYearStartIn) {
		this.finYearStartIn = finYearStartIn;
	}

	public String getWeekStartOn() {
		return weekStartOn;
	}

	public void setWeekStartOn(String weekStartOn) {
		this.weekStartOn = weekStartOn;
	}

	public int getBaseCurrencyId() {
		return baseCurrencyId;
	}

	public void setBaseCurrencyId(int baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}

	public String getWeeklySpecialDays() {
		return weeklySpecialDays;
	}

	public void setWeeklySpecialDays(String weeklySpecialDays) {
		this.weeklySpecialDays = weeklySpecialDays;
	}

	public boolean isTariffIncludeTax() {
		return tariffIncludeTax;
	}

	public void setTariffIncludeTax(boolean tariffIncludeTax) {
		this.tariffIncludeTax = tariffIncludeTax;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getTax1Name() {
		return tax1Name;
	}

	public void setTax1Name(String tax1Name) {
		this.tax1Name = tax1Name;
	}

	public String getTax2Name() {
		return tax2Name;
	}

	public void setTax2Name(String tax2Name) {
		this.tax2Name = tax2Name;
	}

	public String getTax3Name() {
		return tax3Name;
	}

	public void setTax3Name(String tax3Name) {
		this.tax3Name = tax3Name;
	}

	public String getTax4Name() {
		return tax4Name;
	}

	public void setTax4Name(String tax4Name) {
		this.tax4Name = tax4Name;
	}

	public int getBillRounding() {
		return billRounding;
	}

	public void setBillRounding(int billRounding) {
		this.billRounding = billRounding;
	}

	public short getConfirmBefore() {
		return confirmBefore;
	}

	public void setConfirmBefore(short confirmBefore) {
		this.confirmBefore = confirmBefore;
	}

	public Byte getMaxRoomsPerBooking() {
		return maxRoomsPerBooking;
	}

	public void setMaxRoomsPerBooking(Byte maxRoomsPerBooking) {
		this.maxRoomsPerBooking = maxRoomsPerBooking;
	}

	public Byte getMaxNightsPerBooking() {
		return maxNightsPerBooking;
	}

	public void setMaxNightsPerBooking(Byte maxNightsPerBooking) {
		this.maxNightsPerBooking = maxNightsPerBooking;
	}

	public boolean isMailNotifyReservation() {
		return mailNotifyReservation;
	}

	public void setMailNotifyReservation(boolean mailNotifyReservation) {
		this.mailNotifyReservation = mailNotifyReservation;
	}

	public boolean isMailNotifyCancellation() {
		return mailNotifyCancellation;
	}

	public void setMailNotifyCancellation(boolean mailNotifyCancellation) {
		this.mailNotifyCancellation = mailNotifyCancellation;
	}

	public boolean isMailNotifyConfirmation() {
		return mailNotifyConfirmation;
	}

	public void setMailNotifyConfirmation(boolean mailNotifyConfirmation) {
		this.mailNotifyConfirmation = mailNotifyConfirmation;
	}

	public boolean isSmsNotifyReservation() {
		return smsNotifyReservation;
	}

	public void setSmsNotifyReservation(boolean smsNotifyReservation) {
		this.smsNotifyReservation = smsNotifyReservation;
	}

	public boolean isSmsNotifyCancellation() {
		return smsNotifyCancellation;
	}

	public void setSmsNotifyCancellation(boolean smsNotifyCancellation) {
		this.smsNotifyCancellation = smsNotifyCancellation;
	}

	public boolean isSmsNotifyConfirmation() {
		return smsNotifyConfirmation;
	}

	public void setSmsNotifyConfirmation(boolean smsNotifyConfirmation) {
		this.smsNotifyConfirmation = smsNotifyConfirmation;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpSUserId() {
		return smtpSUserId;
	}

	public void setSmtpSUserId(String smtpSUserId) {
		this.smtpSUserId = smtpSUserId;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmsWebService() {
		return smsWebService;
	}

	public void setSmsWebService(String smsWebService) {
		this.smsWebService = smsWebService;
	}

	public String getSmsUserId() {
		return smsUserId;
	}

	public void setSmsUserId(String smsUserId) {
		this.smsUserId = smsUserId;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Date getHotelDate() {
		return hotelDate;
	}

	public void setHotelDate(Date hotelDate) {
		this.hotelDate = hotelDate;
	}

	public byte getNightAuditStage() {
		return nightAuditStage;
	}

	public void setNightAuditStage(byte nightAuditStage) {
		this.nightAuditStage = nightAuditStage;
	}

	public Date getLastUpdTs() {
		return lastUpdTs;
	}

	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public String getFmtdCheckInTime() {
		fmtdCheckInTime = sdf.format(checkInTime);
		return fmtdCheckInTime;
	}

	public void setFmtdCheckInTime(String fmtdCheckInTime) {
		this.fmtdCheckInTime = fmtdCheckInTime;

		try {
			checkInTime = sdf.parse(fmtdCheckInTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getFmtdCheckOutTime() {
		fmtdCheckOutTime = sdf.format(checkOutTime);
		return fmtdCheckOutTime;
	}

	public void setFmtdCheckOutTime(String fmtdCheckOutTime) {
		this.fmtdCheckOutTime = fmtdCheckOutTime;
		try {
			checkOutTime = sdf.parse(fmtdCheckOutTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public boolean isMailNotifyCutoffDate() {
		return mailNotifyCutoffDate;
	}

	public void setMailNotifyCutoffDate(boolean mailNotifyCutoffDate) {
		this.mailNotifyCutoffDate = mailNotifyCutoffDate;
	}

	public boolean isMailNotifyNoShow() {
		return mailNotifyNoShow;
	}

	public void setMailNotifyNoShow(boolean mailNotifyNoShow) {
		this.mailNotifyNoShow = mailNotifyNoShow;
	}

	public boolean isMailNotifyCheckIn() {
		return mailNotifyCheckIn;
	}

	public void setMailNotifyCheckIn(boolean mailNotifyCheckIn) {
		this.mailNotifyCheckIn = mailNotifyCheckIn;
	}

	public boolean isMailNotifyCheckOut() {
		return mailNotifyCheckOut;
	}

	public void setMailNotifyCheckOut(boolean mailNotifyCheckOut) {
		this.mailNotifyCheckOut = mailNotifyCheckOut;
	}

	public boolean isSmsNotifyCutoffDate() {
		return smsNotifyCutoffDate;
	}

	public void setSmsNotifyCutoffDate(boolean smsNotifyCutoffDate) {
		this.smsNotifyCutoffDate = smsNotifyCutoffDate;
	}

	public boolean isSmsNotifyNoShow() {
		return smsNotifyNoShow;
	}

	public void setSmsNotifyNoShow(boolean smsNotifyNoShow) {
		this.smsNotifyNoShow = smsNotifyNoShow;
	}

	public boolean isSmsNotifyCheckIn() {
		return smsNotifyCheckIn;
	}

	public void setSmsNotifyCheckIn(boolean smsNotifyCheckIn) {
		this.smsNotifyCheckIn = smsNotifyCheckIn;
	}

	public boolean isSmsNotifyCheckOut() {
		return smsNotifyCheckOut;
	}

	public void setSmsNotifyCheckOut(boolean smsNotifyCheckOut) {
		this.smsNotifyCheckOut = smsNotifyCheckOut;
	}

	public boolean isSun() {
		sun = isContainsDayInWklySpclDays("SUNDAY");
		return sun;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
	}

	public boolean isMon() {
		mon = isContainsDayInWklySpclDays("MONDAY");
		return mon;
	}

	public void setMon(boolean mon) {
		this.mon = mon;
	}

	public boolean isTue() {
		tue = isContainsDayInWklySpclDays("TUESDAY");
		return tue;
	}

	public void setTue(boolean tue) {
		this.tue = tue;
	}

	public boolean isWed() {
		wed = isContainsDayInWklySpclDays("WEDNSDAY");
		return wed;
	}

	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public boolean isThu() {
		thu = isContainsDayInWklySpclDays("THURSDAY");
		return thu;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public boolean isFri() {
		fri = isContainsDayInWklySpclDays("FRIDAY");
		return fri;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public boolean isSat() {
		sat = isContainsDayInWklySpclDays("SATURDAY");
		return sat;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	private boolean isContainsDayInWklySpclDays(String day) {
		if (weeklySpecialDays != null && weeklySpecialDays.toUpperCase().contains(day))
			return true;
		return false;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

}
