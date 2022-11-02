package com.indocosmo.pms.web.transaction.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Transient;

import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.templates.model.InvoiceTemplate;
import com.mysql.fabric.xmlrpc.base.Array;

public class Transaction {
	private boolean is_adjust;
	private int qty;
	private int txn_no;
	private int receipt_no;
	private int folio_no;
	private int folio_bind_no;
	private int txn_source;
	private String txn_date;
	private String txn_time;
	private int acc_mst_id;
	private String acc_mst_code;
	private int tax_id;
	private String tax_code;
	private String tax_indicator;
	private boolean include_tax;
	private double tax1_pc;
	private double tax2_pc;
	private double tax3_pc;
	private double tax4_pc;
	private double amount;
	private double base_amount;
	private double tax1_amount;
	private double tax2_amount;
	private double tax3_amount;
	private double tax4_amount;
	private double nett_amount;
	private BigDecimal serviceChargePc;
	private BigDecimal serviceCharge;
	private int payment_mode;
	private int corporate_id;
	private String corporate_name;
	private String payment_option;
	private int txn_status;
	private int source_folio_no;
	private int dest_folio_no;
	private int source_txn_no;
	private String remarks;
	private int user_id;
	private int shift_id;
	private Date last_upd_ts;
	private Date hotelDate;
	private Byte canEditDelete;
	private String received_from;
	private int serviceTaxId;
	private boolean is_confirmed;
	private int confirm_by;
	private String receivedForm;
	private int depositFrom; // 1= reservation , 2 = reception
	private int checkInNo;
	private int resvNo;
	private double tax;
	private String sourceRoom;
	private String sourceName;
	private String transferedDate;;
	private String destinationRoom;
	private String destinationName;
	private String name;
	private String roomNumber;
	private String firstName;
	private String lastName;
	private String guestName;
	private double tax_total;
    private String invoicePrefix;
    private boolean isDiscApplied;
    private Double disc_amount;
    
	public boolean isDiscApplied() {
		return isDiscApplied;
	}

	public void setDiscApplied(boolean isDiscApplied) {
		this.isDiscApplied = isDiscApplied;
	}

	public Double getDisc_amount() {
		return disc_amount;
	}

	public void setDisc_amount(Double disc_amount) {
		this.disc_amount = disc_amount;
	}

	public double getTax_total() {
		return tax_total;
	}

	public void setTax_total(double tax_total) {
		this.tax_total = tax_total;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	private String loginId;
	private int hsnCode;
	private String accMstName;
	private String invoiceNo;
	private Date invoiceDate;
	private int pax;
	private InvoiceTemplate headerFooter;
	private SystemSettings systemSettings;

	private String room_type;
	private String numNights;

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public SystemSettings getSystemSettings() {
		return systemSettings;
	}

	public void setSystemSettings(SystemSettings systemSettings) {
		this.systemSettings = systemSettings;
	}

	public InvoiceTemplate getHeaderFooter() {
		return headerFooter;
	}

	public void setHeaderFooter(InvoiceTemplate headerFooter) {
		this.headerFooter = headerFooter;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	@Transient
	private double invoiceAmount;
	
	public double getActualBaseAmount() {
		return actualBaseAmount;
	}

	public void setActualBaseAmount(double actualBaseAmount) {
		this.actualBaseAmount = actualBaseAmount;
	}

	@Transient
	private double actualBaseAmount;
	
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Transient
	private String discount;
	
	@Transient
	private double invoiceBaseAmount;
	
	public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

	@Transient
	private double totalInvoiceAmount;
	
	@Transient
	private double invoiceNett_amount;
	@Transient
	private String dateFormat;
	@Transient
	private String ShiftCode;
	@Transient
	private String username;
	@Transient
	private String rate_description;
	@Transient
	private int total;
	
	@Transient
	private int countInvoice;
	
	@Transient
	private int countGst;
	
	@Transient
	private Double totalTaxableVal;
	
	
	public Double getTotalTaxableVal() {
		return totalTaxableVal;
	}

	public void setTotalTaxableVal(Double totalTaxableVal) {
		this.totalTaxableVal = totalTaxableVal;
	}

	public int getCountInvoice() {
		return countInvoice;
	}

	public void setCountInvoice(int countInvoice) {
		this.countInvoice = countInvoice;
	}

	public int getCountGst() {
		return countGst;
	}

	public void setCountGst(int countGst) {
		this.countGst = countGst;
	}

	@Transient
	private String maxDate;
	
	@Transient
	private String receiver_name;
	
	@Transient
	private String invoiceDateField;
	
	public String getInvoiceDateField() {
		return invoiceDateField;
	}

	public void setInvoiceDateField(String invoiceDateField) {
		this.invoiceDateField = invoiceDateField;
	}

	@Transient
	private double totalTaxPc;
	
	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public double getTotalTaxPc() {
		return totalTaxPc;
	}

	public void setTotalTaxPc(double totalTaxPc) {
		this.totalTaxPc = totalTaxPc;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Transient
	private String gstNo;
	
	
	public Object getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(Object billDetails) {
		this.billDetails = billDetails;
	}

	@Transient
	private Object billDetails;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAcc_mst_code() {
		return acc_mst_code;
	}

	public int getAcc_mst_id() {
		return acc_mst_id;
	}

	public double getAmount() {
		return amount;
	}

	public double getBase_amount() {
		return base_amount;
	}

	public Byte getCanEditDelete() {
		return canEditDelete;
	}

	public int getCheckInNo() {
		return checkInNo;
	}

	public int getConfirm_by() {
		return confirm_by;
	}

	public int getDepositFrom() {
		return depositFrom;
	}

	public int getDest_folio_no() {
		return dest_folio_no;
	}

	/**
	 * @return the destinationRoom
	 */
	public String getDestinationRoom() {
		return destinationRoom;
	}

	public int getFolio_bind_no() {
		return folio_bind_no;
	}

	public int getFolio_no() {
		return folio_no;
	}

	public Date getHotelDate() {
		return hotelDate;
	}

	public boolean getIs_adjust() {
		return is_adjust;
	}

	public Date getLast_upd_ts() {
		return last_upd_ts;
	}

	public double getNett_amount() {
		return nett_amount;
	}

	public int getPayment_mode() {
		return payment_mode;
	}

	public String getReceived_from() {
		return received_from;
	}

	public String getReceivedForm() {
		return receivedForm;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getResvNo() {
		return resvNo;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public BigDecimal getServiceChargePc() {
		return serviceChargePc;
	}

	public int getServiceTaxId() {
		return serviceTaxId;
	}

	public int getSource_folio_no() {
		return source_folio_no;
	}

	/**
	 * @return the source_txn_no
	 */
	public int getSource_txn_no() {
		return source_txn_no;
	}

	/**
	 * @return the sourceRoom
	 */
	public String getSourceRoom() {
		return sourceRoom;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	public String getTax_code() {
		return tax_code;
	}

	public int getTax_id() {
		return tax_id;
	}

	public String getTax_indicator() {
		return tax_indicator;
	}

	public double getTax1_amount() {
		return tax1_amount;
	}

	public double getTax1_pc() {
		return tax1_pc;
	}

	public double getTax2_amount() {
		return tax2_amount;
	}

	public double getTax2_pc() {
		return tax2_pc;
	}

	public double getTax3_amount() {
		return tax3_amount;
	}

	public double getTax3_pc() {
		return tax3_pc;
	}

	public double getTax4_amount() {
		return tax4_amount;
	}

	public double getTax4_pc() {
		return tax4_pc;
	}

	public String getTxn_date() {
		return txn_date;
	}

	public int getTxn_no() {
		return txn_no;
	}

	public int getTxn_source() {
		return txn_source;
	}

	public int getTxn_status() {
		return txn_status;
	}

	public String getTxn_time() {
		return txn_time;
	}

	public int getUser_id() {
		return user_id;
	}

	public boolean isInclude_tax() {
		return include_tax;
	}

	public boolean isIs_confirmed() {
		return is_confirmed;
	}

	public void setAcc_mst_code(String acc_mst_code) {
		this.acc_mst_code = acc_mst_code;
	}

	public void setAcc_mst_id(int acc_mst_id) {
		this.acc_mst_id = acc_mst_id;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setBase_amount(double base_amount) {
		this.base_amount = base_amount;
	}

	public void setCanEditDelete(Byte canEditDelete) {
		this.canEditDelete = canEditDelete;
	}

	public void setCheckInNo(int checkInNo) {
		this.checkInNo = checkInNo;
	}

	public void setConfirm_by(int confirm_by) {
		this.confirm_by = confirm_by;
	}

	public void setDepositFrom(int depositFrom) {
		this.depositFrom = depositFrom;
	}

	public void setDest_folio_no(int dest_folio_no) {
		this.dest_folio_no = dest_folio_no;
	}

	/**
	 * @param destinationRoom
	 *            the destinationRoom to set
	 */
	public void setDestinationRoom(String destinationRoom) {
		this.destinationRoom = destinationRoom;
	}

	public void setFolio_bind_no(int folio_bind_no) {
		this.folio_bind_no = folio_bind_no;
	}

	public void setFolio_no(int folio_no) {
		this.folio_no = folio_no;
	}

	public void setHotelDate(Date hotelDate) {
		this.hotelDate = hotelDate;
	}

	public void setInclude_tax(boolean include_tax) {
		this.include_tax = include_tax;
	}

	public void setIs_adjust(boolean is_adjust) {
		this.is_adjust = is_adjust;
	}

	public void setIs_confirmed(boolean is_confirmed) {
		this.is_confirmed = is_confirmed;
	}

	public void setLast_upd_ts(Date last_upd_ts) {
		this.last_upd_ts = last_upd_ts;
	}

	public void setNett_amount(double nett_amount) {
		this.nett_amount = nett_amount;
	}

	public void setPayment_mode(int payment_mode) {
		this.payment_mode = payment_mode;
	}

	public void setReceived_from(String received_from) {
		this.received_from = received_from;
	}

	public void setReceivedForm(String receivedForm) {
		this.receivedForm = receivedForm;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setResvNo(int resvNo) {
		this.resvNo = resvNo;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public void setServiceChargePc(BigDecimal serviceChargePc) {
		this.serviceChargePc = serviceChargePc;
	}

	public void setServiceTaxId(int serviceTaxId) {
		this.serviceTaxId = serviceTaxId;
	}

	public void setSource_folio_no(int source_folio_no) {
		this.source_folio_no = source_folio_no;
	}

	/**
	 * @param source_txn_no
	 *            the source_txn_no to set
	 */
	public void setSource_txn_no(int source_txn_no) {
		this.source_txn_no = source_txn_no;
	}

	/**
	 * @param sourceRoom
	 *            the sourceRoom to set
	 */
	public void setSourceRoom(String sourceRoom) {
		this.sourceRoom = sourceRoom;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	public void setTax_code(String tax_code) {
		this.tax_code = tax_code;
	}

	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}

	public void setTax_indicator(String tax_indicator) {
		this.tax_indicator = tax_indicator;
	}

	public void setTax1_amount(double tax1_amount) {
		this.tax1_amount = tax1_amount;
	}

	public void setTax1_pc(double tax1_pc) {
		this.tax1_pc = tax1_pc;
	}

	public void setTax2_amount(double tax2_amount) {
		this.tax2_amount = tax2_amount;
	}

	public void setTax2_pc(double tax2_pc) {
		this.tax2_pc = tax2_pc;
	}

	public void setTax3_amount(double tax3_amount) {
		this.tax3_amount = tax3_amount;
	}

	public void setTax3_pc(double tax3_pc) {
		this.tax3_pc = tax3_pc;
	}

	public void setTax4_amount(double tax4_amount) {
		this.tax4_amount = tax4_amount;
	}

	public void setTax4_pc(double tax4_pc) {
		this.tax4_pc = tax4_pc;
	}

	public void setTxn_date(String hotelDate2) {
		this.txn_date = hotelDate2;
	}

	public void setTxn_no(int txn_no) {
		this.txn_no = txn_no;
	}

	public void setTxn_source(int txn_source) {
		this.txn_source = txn_source;
	}

	public void setTxn_status(int txn_status) {
		this.txn_status = txn_status;
	}

	public void setTxn_time(String txn_time) {
		this.txn_time = txn_time;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the transferedDate
	 */
	public String getTransferedDate() {
		return transferedDate;
	}

	/**
	 * @param transferedDate
	 *            the transferedDate to set
	 */
	public void setTransferedDate(String transferedDate) {
		this.transferedDate = transferedDate;
	}

	public int getShift_id() {
		return shift_id;
	}

	public void setShift_id(int shift_id) {
		this.shift_id = shift_id;
	}

	public String getShiftCode() {
		return ShiftCode;
	}

	public void setShiftCode(String shiftCode) {
		ShiftCode = shiftCode;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public double getInvoiceBaseAmount() {
		return invoiceBaseAmount;
	}

	public void setInvoiceBaseAmount(double invoiceBaseAmount) {
		this.invoiceBaseAmount = invoiceBaseAmount;
	}

	public double getInvoiceNett_amount() {
		return invoiceNett_amount;
	}

	public void setInvoiceNett_amount(double invoiceNett_amount) {
		this.invoiceNett_amount = invoiceNett_amount;
	}

	public String getRate_description() {
		return rate_description;
	}

	public void setRate_description(String rate_description) {
		this.rate_description = rate_description;
	}

	public String getPayment_option() {
		return payment_option;
	}

	public void setPayment_option(String payment_option) {
		this.payment_option = payment_option;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public int getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(int hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getAccMstName() {
		return accMstName;
	}

	public void setAccMstName(String accMstName) {
		this.accMstName = accMstName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(int receipt_no) {
		this.receipt_no = receipt_no;
	}

	/**
	 * @return the numNights
	 */
	public String getNumNights() {
		return numNights;
	}

	/**
	 * @param numNights
	 *            the numNights to set
	 */
	public void setNumNights(String numNights) {
		this.numNights = numNights;
	}

	public int getCorporate_id() {
		return corporate_id;
	}

	public void setCorporate_id(int corporate_id) {
		this.corporate_id = corporate_id;
	}

	public String getCorporate_name() {
		return corporate_name;
	}

	public void setCorporate_name(String corporate_name) {
		this.corporate_name = corporate_name;
	}

	public String getInvoicePrefix() {
		return invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}

}