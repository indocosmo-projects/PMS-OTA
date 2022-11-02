package com.indocosmo.pms.web.reservation;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;


//@Entity
//@Table(name="txn")
public class Txn {
	
	private int txn_no ;
	


	private int folio_no ;
	private int folio_bind_no; 	
	private int txn_source;
	private Date txn_date;
	private Date txn_time;
	private Date acc_mst_id ; //datetime
 	private int acc_mst_code;
	private int  tax_id;
	
	private String tax_code;
	private boolean include_tax;
	private BigDecimal tax1_pc;
	private BigDecimal tax2_pc;
	private BigDecimal tax3_pc;
	private BigDecimal tax4_pc;
	private BigDecimal amount;
	private BigDecimal tax1_amount;
	private BigDecimal tax2_amount;
	private BigDecimal tax3_amount;
	private BigDecimal tax4_amount;
	private BigDecimal nett_amount;
	private int payment_mode;
	private int txn_status;
	private int source_folio_no;
	private int dest_folio_no;
	private String remarks;
	private int  user_id;
	private Date last_upd_ts; //timestmp


}
