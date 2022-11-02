<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="PMS" scope="request" />
<c:set var="moduleName" value="Reports" scope="request" />
<c:set var="formId" value="reports" scope="request" />
<c:set var="formName" value="reports" scope="request" />
<c:set var="customEditIncludeFile" value="../reports/reports_custom.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="Check-In" scope="request" />

<c:set var="exp_arr_isCanView" scope="request"
	value="${(curPagePerExpArrObj.isCanView() && curPagePerExpArrObj.isIs_view_applicable())?true:false}" />
<c:set var="exp_arr_isCanExecute" scope="request"
	value="${(curPagePerExpArrObj.isCanExecute() && curPagePerExpArrObj.isIs_execute_applicable())?true:false}" />
<c:set var="exp_arr_isCanExport" scope="request"
	value="${(curPagePerExpArrObj.isCanExport() && curPagePerExpArrObj.isIs_export_applicable())?true:false}" />

<c:set var="act_arr_isCanView" scope="request"
	value="${(curPagePerActArrObj.isCanView() && curPagePerActArrObj.isIs_view_applicable())?true:false}" />
<c:set var="act_arr_isCanExecute" scope="request"
	value="${(curPagePerActArrObj.isCanExecute() && curPagePerActArrObj.isIs_execute_applicable())?true:false}" />
<c:set var="act_arr_isCanExport" scope="request"
	value="${(curPagePerActArrObj.isCanExport() && curPagePerActArrObj.isIs_export_applicable())?true:false}" />

<c:set var="exp_dep_isCanView" scope="request"
	value="${(curPagePerExpDepObj.isCanView() && curPagePerExpDepObj.isIs_view_applicable())?true:false}" />
<c:set var="exp_dep_isCanExecute" scope="request"
	value="${(curPagePerExpDepObj.isCanExecute() && curPagePerExpDepObj.isIs_execute_applicable())?true:false}" />
<c:set var="exp_dep_isCanExport" scope="request"
	value="${(curPagePerExpDepObj.isCanExport() && curPagePerExpDepObj.isIs_export_applicable())?true:false}" />

<c:set var="act_dep_isCanView" scope="request"
	value="${(curPagePerActDepObj.isCanView() && curPagePerActDepObj.isIs_view_applicable())?true:false}" />
<c:set var="act_dep_isCanExecute" scope="request"
	value="${(curPagePerActDepObj.isCanExecute() && curPagePerActDepObj.isIs_execute_applicable())?true:false}" />
<c:set var="act_dep_isCanExport" scope="request"
	value="${(curPagePerActDepObj.isCanExport() && curPagePerActDepObj.isIs_export_applicable())?true:false}" />

<c:set var="inhouse_isCanView" scope="request"
	value="${(curPagePerInHouseObj.isCanView() && curPagePerInHouseObj.isIs_view_applicable())?true:false}" />
<c:set var="inhouse_isCanExecute" scope="request"
	value="${(curPagePerInHouseObj.isCanExecute() && curPagePerInHouseObj.isIs_execute_applicable())?true:false}" />
<c:set var="inhouse_isCanExport" scope="request"
	value="${(curPagePerInHouseObj.isCanExport() && curPagePerInHouseObj.isIs_export_applicable())?true:false}" />

<c:set var="occupancy_isCanView" scope="request"
	value="${(curPagePerOccupancyObj.isCanView() && curPagePerOccupancyObj.isIs_view_applicable())?true:false}" />
<c:set var="occupancy_isCanExecute" scope="request"
	value="${(curPagePerOccupancyObj.isCanExecute() && curPagePerOccupancyObj.isIs_view_applicable())?true:false}" />
<c:set var="occupancy_isCanExport" scope="request"
	value="${(curPagePerOccupancyObj.isCanExport() && curPagePerOccupancyObj.isIs_view_applicable())?true:false}" />

<c:set var="room_per_day_isCanView" scope="request"
	value="${(curPgePerRoomsPerDayObj.isCanView() && curPgePerRoomsPerDayObj.isIs_view_applicable())?true:false}" />
<c:set var="room_per_day_isCanExecute" scope="request"
	value="${(curPgePerRoomsPerDayObj.isCanExecute() && curPgePerRoomsPerDayObj.isIs_view_applicable())?true:false}" />
<c:set var="room_per_day_isCanExport" scope="request"
	value="${(curPgePerRoomsPerDayObj.isCanExport() && curPgePerRoomsPerDayObj.isIs_view_applicable())?true:false}" />

<c:set var="plan_room_isCanView" scope="request"
	value="${(curPagePerPlanRoomObj.isCanView() && curPagePerPlanRoomObj.isIs_view_applicable())?true:false}" />
<c:set var="plan_room_isCanExecute" scope="request"
	value="${(curPagePerPlanRoomObj.isCanExecute() && curPagePerPlanRoomObj.isIs_view_applicable())?true:false}" />
<c:set var="plan_room_isCanExport" scope="request"
	value="${(curPagePerPlanRoomObj.isCanExport() && curPagePerPlanRoomObj.isIs_view_applicable())?true:false}" />

<c:set var="resv_isCanView" scope="request"
	value="${(curPagePerResvObj.isCanView() && curPagePerResvObj.isIs_view_applicable())?true:false}" />
<c:set var="resv_isCanExecute" scope="request"
	value="${(curPagePerResvObj.isCanExecute() && curPagePerResvObj.isIs_execute_applicable())?true:false}" />
<c:set var="resv_isCanExport" scope="request"
	value="${(curPagePerResvObj.isCanExport() && curPagePerResvObj.isIs_export_applicable())?true:false}" />

<c:set var="resv_cancel_isCanView" scope="request"
	value="${(curPagePerResvCancelObj.isCanView() && curPagePerResvCancelObj.isIs_view_applicable())?true:false}" />
<c:set var="resv_cancel_isCanExecute" scope="request"
	value="${(curPagePerResvCancelObj.isCanExecute() && curPagePerResvCancelObj.isIs_execute_applicable())?true:false}" />
<c:set var="resv_cancel_isCanExport" scope="request"
	value="${(curPagePerResvCancelObj.isCanExport() && curPagePerResvCancelObj.isIs_export_applicable())?true:false}" />

<c:set var="txn_isCanView" scope="request"
	value="${(curPagePerTxnObj.isCanView() && curPagePerTxnObj.isIs_view_applicable())?true:false}" />
<c:set var="txn_isCanExecute" scope="request"
	value="${(curPagePerTxnObj.isCanExecute() && curPagePerTxnObj.isIs_execute_applicable())?true:false}" />
<c:set var="txn_isCanExport" scope="request"
	value="${(curPagePerTxnObj.isCanExport() && curPagePerTxnObj.isIs_export_applicable())?true:false}" />

<c:set var="txn_trnsfr_isCanView" scope="request"
	value="${(curPagePerTxnTrnsfrObj.isCanView() && curPagePerTxnTrnsfrObj.isIs_view_applicable())?true:false}" />
<c:set var="txn_trnsfr_isCanExecute" scope="request"
	value="${(curPagePerTxnTrnsfrObj.isCanExecute() && curPagePerTxnTrnsfrObj.isIs_execute_applicable())?true:false}" />
<c:set var="txn_trnsfr_isCanExport" scope="request"
	value="${(curPagePerTxnTrnsfrObj.isCanExport() && curPagePerTxnTrnsfrObj.isIs_export_applicable())?true:false}" />

<c:set var="deleted_txn_isCanView" scope="request"
	value="${(curPagePerTxnDeltdObj.isCanView() && curPagePerTxnDeltdObj.isIs_view_applicable())?true:false}" />
<c:set var="deleted_txn_isCanExecute" scope="request"
	value="${(curPagePerTxnDeltdObj.isCanExecute() && curPagePerTxnDeltdObj.isIs_execute_applicable())?true:false}" />
<c:set var="deleted_txn_isCanExport" scope="request"
	value="${(curPagePerTxnDeltdObj.isCanExport() && curPagePerTxnDeltdObj.isIs_export_applicable())?true:false}" />


<c:set var="CashRegisterClosure_isCanView" scope="request"
	value="${(curPagePerCashRegisterClosureObj.isCanView() && curPagePerCashRegisterClosureObj.isIs_view_applicable())?true:false}" />
<c:set var="CashRegisterClosure_isCanExecute" scope="request"
	value="${(curPagePerCashRegisterClosureObj.isCanExecute() && curPagePerCashRegisterClosureObj.isIs_execute_applicable())?true:false}" />
<c:set var="CashRegisterClosure_isCanExport" scope="request"
	value="${(curPagePerCashRegisterClosureObj.isCanExport() && curPagePerCashRegisterClosureObj.isIs_export_applicable())?true:false}" />

<c:set var="dailyRevenue_isCanView" scope="request"
	value="${(curPageDailyRevenueObj.isCanView() && curPageDailyRevenueObj.isIs_view_applicable())?true:false}" />
<c:set var="dailyRevenue_isCanExecute" scope="request"
	value="${(curPageDailyRevenueObj.isCanExecute() && curPageDailyRevenueObj.isIs_execute_applicable())?true:false}" />
<c:set var="dailyRevenue_isCanExport" scope="request"
	value="${(curPageDailyRevenueObj.isCanExport() && curPageDailyRevenueObj.isIs_export_applicable())?true:false}" />


<c:set var="folio_isCanView" scope="request"
	value="${(curPagePerFolioObj.isCanView() && curPagePerFolioObj.isIs_view_applicable())?true:false}" />
<c:set var="folio_isCanExecute" scope="request"
	value="${(curPagePerFolioObj.isCanExecute() && curPagePerFolioObj.isIs_execute_applicable())?true:false}" />
<c:set var="folio_isCanExport" scope="request"
	value="${(curPagePerFolioObj.isCanExport() && curPagePerFolioObj.isIs_export_applicable())?true:false}" />

<c:set var="shift_isCanView" scope="request"
	value="${(curPagePerShiftObj.isCanView() && curPagePerShiftObj.isIs_view_applicable())?true:false}" />
<c:set var="shift_isCanExecute" scope="request"
	value="${(curPagePerShiftObj.isCanExecute() && curPagePerShiftObj.isIs_execute_applicable())?true:false}" />
<c:set var="shift_isCanExport" scope="request"
	value="${(curPagePerShiftObj.isCanExport() && curPagePerShiftObj.isIs_export_applicable())?true:false}" />

<c:set var="shift_txn_isCanView" scope="request"
	value="${(curPagePerShiftTxnObj.isCanView() && curPagePerShiftTxnObj.isIs_view_applicable())?true:false}" />
<c:set var="shift_txn_isCanExecute" scope="request"
	value="${(curPagePerShiftTxnObj.isCanExecute() && curPagePerShiftTxnObj.isIs_execute_applicable())?true:false}" />
<c:set var="shift_txn_isCanExport" scope="request"
	value="${(curPagePerShiftTxnObj.isCanExport() && curPagePerShiftTxnObj.isIs_export_applicable())?true:false}" />

<c:set var="shift_txn_trnsfr_isCanView" scope="request"
	value="${(curPagePerShiftTxnTransfrObj.isCanView() && curPagePerShiftTxnTransfrObj.isIs_view_applicable())?true:false}" />
<c:set var="shift_txn_trnsfr_isCanExecute" scope="request"
	value="${(curPagePerShiftTxnTransfrObj.isCanExecute() && curPagePerShiftTxnTransfrObj.isIs_execute_applicable())?true:false}" />
<c:set var="shift_txn_trnsfr_isCanExport" scope="request"
	value="${(curPagePerShiftTxnTransfrObj.isCanExport() && curPagePerShiftTxnTransfrObj.isIs_export_applicable())?true:false}" />

<c:set var="Request_isCanView" scope="request"
	value="${(curPagePerRequestObj.isCanView() && curPagePerRequestObj.isIs_view_applicable())?true:false}" />
<c:set var="Request_isCanExecute" scope="request"
	value="${(curPagePerRequestObj.isCanExecute() && curPagePerRequestObj.isIs_execute_applicable())?true:false}" />
<c:set var="Request_isCanExport" scope="request"
	value="${(curPagePerRequestObj.isCanExport() && curPagePerRequestObj.isIs_export_applicable())?true:false}" />

<c:set var="dailyRevenue_isCanView" scope="request"
	value="${(curPageDailyRevenueObj.isCanView() && curPageDailyRevenueObj.isIs_view_applicable())?true:false}" />
<c:set var="dailyRevenue_isCanExecute" scope="request"
	value="${(curPageDailyRevenueObj.isCanExecute() && curPageDailyRevenueObj.isIs_execute_applicable())?true:false}" />
<c:set var="dailyRevenue_isCanExport" scope="request"
	value="${(curPageDailyRevenueObj.isCanExport() && curPageDailyRevenueObj.isIs_export_applicable())?true:false}" />

<c:set var="monthly_closure_isCanView" scope="request"
	value="${(curPageMonthlyReportObj.isCanView() && curPageMonthlyReportObj.isIs_view_applicable())?true:false}" />
<c:set var="monthly_closure_isCanExecute" scope="request"
	value="${(curPageMonthlyReportObj.isCanExecute() && curPageMonthlyReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="monthly_closure_isCanExport" scope="request"
	value="${(curPageMonthlyReportObj.isCanExport() && curPageMonthlyReportObj.isIs_export_applicable())?true:false}" />


<c:set var="nationality_statistics_isCanView" scope="request"
	value="${(curPageNationalityReportObj.isCanView() && curPageNationalityReportObj.isIs_view_applicable())?true:false}" />
<c:set var="nationality_statistics_isCanExecute" scope="request"
	value="${(curPageNationalityReportObj.isCanExecute() && curPageNationalityReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="nationality_statistics_isCanExport" scope="request"
	value="${(curPageNationalityReportObj.isCanExport() && curPageNationalityReportObj.isIs_export_applicable())?true:false}" />

<c:set var="Customer_isCanView" scope="request"
	value="${(curPageCustomerObj.isCanView() && curPageCustomerObj.isIs_view_applicable())?true:false}" />
<c:set var="Customer_isCanExecute" scope="request"
	value="${(curPageCustomerObj.isCanExecute() && curPageCustomerObj.isIs_execute_applicable())?true:false}" />
<c:set var="Customer_isCanExport" scope="request"
	value="${(curPageCustomerObj.isCanExport() && curPageCustomerObj.isIs_export_applicable())?true:false}" />

<c:set var="Income_isCanView" scope="request"
	value="${(curPageIncomeObj.isCanView() && curPageIncomeObj.isIs_view_applicable())?true:false}" />
<c:set var="Income_isCanExecute" scope="request"
	value="${(curPageIncomeObj.isCanExecute() && curPageIncomeObj.isIs_execute_applicable())?true:false}" />
<c:set var="Income_isCanExport" scope="request"
	value="${(curPageIncomeObj.isCanExport() && curPageIncomeObj.isIs_export_applicable())?true:false}" />

<c:set var="Room_isCanView" scope="request"
	value="${(curPageRoomBooking.isCanView() && curPageRoomBooking.isIs_view_applicable())?true:false}" />
<c:set var="Room_isCanExecute" scope="request"
	value="${(curPageRoomBooking.isCanExecute() && curPageRoomBooking.isIs_execute_applicable())?true:false}" />
<c:set var="Room_isCanExport" scope="request"
	value="${(curPageRoomBooking.isCanExport() && curPageRoomBooking.isIs_export_applicable())?true:false}" />

<c:set var="CustomerOutstanding_isCanView" scope="request"
	value="${(curPageCustomerOutstanding.isCanView() && curPageCustomerOutstanding.isIs_view_applicable())?true:false}" />
<c:set var="CustomerOutstanding_isCanExecute" scope="request"
	value="${(curPageCustomerOutstanding.isCanExecute() && curPageCustomerOutstanding.isIs_execute_applicable())?true:false}" />
<c:set var="CustomerOutstanding_isCanExport" scope="request"
	value="${(curPageCustomerOutstanding.isCanExport() && curPageCustomerOutstanding.isIs_export_applicable())?true:false}" />

<c:set var="CustomerGrading_isCanView" scope="request"
	value="${(curPageCustomerGrading.isCanView() && curPageCustomerGrading.isIs_view_applicable())?true:false}" />
<c:set var="CustomerGrading_isCanExecute" scope="request"
	value="${(curPageCustomerGrading.isCanExecute() && curPageCustomerGrading.isIs_execute_applicable())?true:false}" />
<c:set var="CustomerGrading_isCanExport" scope="request"
	value="${(curPageCustomerGrading.isCanExport() && curPageCustomerGrading.isIs_export_applicable())?true:false}" />

<c:set var="BookingFrequency_isCanView" scope="request"
	value="${(curPageBookingFrequency.isCanView() && curPageBookingFrequency.isIs_view_applicable())?true:false}" />
<c:set var="BookingFrequency_isCanExecute" scope="request"
	value="${(curPageBookingFrequency.isCanExecute() && curPageBookingFrequency.isIs_execute_applicable())?true:false}" />
<c:set var="BookingFrequency_isCanExport" scope="request"
	value="${(curPageBookingFrequency.isCanExport() && curPageBookingFrequency.isIs_export_applicable())?true:false}" />

<c:set var="TallyExport_isCanView" scope="request"
	value="${(curPageTallyExport.isCanView() && curPageTallyExport.isIs_view_applicable())?true:false}" />
<c:set var="TallyExport_isCanExecute" scope="request"
	value="${(curPageTallyExport.isCanExecute() && curPageTallyExport.isIs_execute_applicable())?true:false}" />
<c:set var="TallyExport_isCanExport" scope="request"
	value="${(curPageTallyExport.isCanExport() && curPageTallyExport.isIs_export_applicable())?true:false}" />

<c:set var="CorporateList_isCanView" scope="request"
	value="${(curPageCorporateList.isCanView() && curPageCorporateList.isIs_view_applicable())?true:false}" />
<c:set var="CorporateList_isCanView" scope="request"
	value="${(curPageCorporateList.isCanExecute() && curPageCorporateList.isIs_execute_applicable())?true:false}" />
<c:set var="CorporateList_isCanView" scope="request"
	value="${(curPageCorporateList.isCanExport() && curPageCorporateList.isIs_export_applicable())?true:false}" />

<c:set var="GuestAnalysis_isCanView" scope="request"
	value="${(curPageGuestAnalysis.isCanView() && curPageGuestAnalysis.isIs_view_applicable())?true:false}" />
<c:set var="GuestAnalysis_isCanView" scope="request"
	value="${(curPageGuestAnalysis.isCanExecute() && curPageGuestAnalysis.isIs_execute_applicable())?true:false}" />
<c:set var="GuestAnalysis_isCanView" scope="request"
	value="${(curPageGuestAnalysis.isCanExport() && curPageGuestAnalysis.isIs_export_applicable())?true:false}" />

<c:set var="PettyList_isCanView" scope="request"
	value="${(curPagePettyList.isCanView() && curPagePettyList.isIs_view_applicable())?true:false}" />
<c:set var="PettyList_isCanView" scope="request"
	value="${(curPagePettyList.isCanExecute() && curPagePettyList.isIs_execute_applicable())?true:false}" />
<c:set var="PettyList_isCanView" scope="request"
	value="${(curPagePettyList.isCanExport() && curPagePettyList.isIs_export_applicable())?true:false}" />

<c:set var="ContraList_isCanView" scope="request"
	value="${(curPageContraList.isCanView() && curPageContraList.isIs_view_applicable())?true:false}" />
<c:set var="curContraList_isCanView" scope="request"
	value="${(curPageContraList.isCanExecute() && curPageContraList.isIs_execute_applicable())?true:false}" />
<c:set var="curContraList_isCanView" scope="request"
	value="${(curPageContraList.isCanExport() && curPageContraList.isIs_export_applicable())?true:false}" />

<c:set var="PaymentList_isCanView" scope="request"
	value="${(curPagePaymentList.isCanView() && curPagePaymentList.isIs_view_applicable())?true:false}" />
<c:set var="PaymentList_isCanView" scope="request"
	value="${(curPagePaymentList.isCanExecute() && curPagePaymentList.isIs_execute_applicable())?true:false}" />
<c:set var="PaymentList_isCanView" scope="request"
	value="${(curPagePaymentList.isCanExport() && curPagePaymentList.isIs_export_applicable())?true:false}" />
	
	<c:set var="PettyLedger_isCanView" scope="request"
	value="${(curPagePettyLedgerList.isCanView() && curPagePettyLedgerList.isIs_view_applicable())?true:false}" />
<c:set var="PettyLedger_isCanView" scope="request"
	value="${(curPagePettyLedgerList.isCanExecute() && curPagePettyLedgerList.isIs_execute_applicable())?true:false}" />
<c:set var="PettyLedger_isCanView" scope="request"
	value="${(curPagePettyLedgerList.isCanExport() && curPagePettyLedgerList.isIs_export_applicable())?true:false}" />

<c:set var="PaymenExporttList_isCanView" scope="request"
	value="${(curPagePaymentExportList.isCanView() && curPagePaymentExportList.isIs_view_applicable())?true:false}" />		
<c:set var="PaymenExporttList_isCanExecute" scope="request"
	value="${(curPagePaymentExportList.isCanExecute() && curPagePaymentExportList.isIs_execute_applicable())?true:false}" />
<c:set var="PaymenExporttList_isCanExport" scope="request"
	value="${(curPagePaymentExportList.isCanExport() && curPagePaymentExportList.isIs_export_applicable())?true:false}" />

<c:set var="CardExporttList_isCanView" scope="request"
	value="${(curPageCardExportList.isCanView() && curPageCardExportList.isIs_view_applicable())?true:false}" />		
<c:set var="CardExporttList_isCanExecute" scope="request"
	value="${(curPageCardExportList.isCanExecute() && curPageCardExportList.isIs_execute_applicable())?true:false}" />
<c:set var="CardExporttList_isCanExport" scope="request"
	value="${(curPageCardExportList.isCanExport() && curPageCardExportList.isIs_export_applicable())?true:false}" />

<c:set var="CreditCardList_isCanView" scope="request"
	value="${(curPageCreditCardReportObj.isCanView() && curPageCreditCardReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="CreditCardList_isCanExecute" scope="request"
	value="${(curPageCreditCardReportObj.isCanExecute() && curPageCreditCardReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="CreditCardList_isCanExport" scope="request"
	value="${(curPageCreditCardReportObj.isCanExport() && curPageCreditCardReportObj.isIs_export_applicable())?true:false}" />
	
	<c:set var="DetailRevenueList_isCanView" scope="request"
	value="${(curPageDetailRevenueReportObj.isCanView() && curPageDetailRevenueReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="DetailRevenueList_isCanExecute" scope="request"
	value="${(curPageDetailRevenueReportObj.isCanExecute() && curPageDetailRevenueReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="DetailRevenueList_isCanExport" scope="request"
	value="${(curPageDetailRevenueReportObj.isCanExport() && curPageDetailRevenueReportObj.isIs_export_applicable())?true:false}" />
	
	<c:set var="FoodRevenueList_isCanView" scope="request"
	value="${(curPageFoodRevenueReportObj.isCanView() && curPageFoodRevenueReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="FoodRevenueList_isCanExecute" scope="request"
	value="${(curPageFoodRevenueReportObj.isCanExecute() && curPageFoodRevenueReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="FoodRevenueList_isCanExport" scope="request"
	value="${(curPageFoodRevenueReportObj.isCanExport() && curPageFoodRevenueReportObj.isIs_export_applicable())?true:false}" />
	
	<c:set var="PosRevenueList_isCanView" scope="request"
	value="${(curPagePosRevenueReportObj.isCanView() && curPagePosRevenueReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="PosRevenueList_isCanExecute" scope="request"
	value="${(curPagePosRevenueReportObj.isCanExecute() && curPagePosRevenueReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="PosRevenueList_isCanExport" scope="request"
	value="${(curPagePosRevenueReportObj.isCanExport() && curPagePosRevenueReportObj.isIs_export_applicable())?true:false}" />
	
	<c:set var="B2Breport_isCanView" scope="request"
	value="${(curPageB2BReportObj.isCanView() && curPageB2BReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="B2Breport_isCanExecute" scope="request"
	value="${(curPageB2BReportObj.isCanExecute() && curPageB2BReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="B2Breport_isCanExport" scope="request"
	value="${(curPageB2BReportObj.isCanExport() && curPageB2BReportObj.isIs_export_applicable())?true:false}" />
	
	<c:set var="B2Creport_isCanView" scope="request"
	value="${(curPageB2CReportObj.isCanView() && curPageB2CReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="B2Creport_isCanExecute" scope="request"
	value="${(curPageB2CReportObj.isCanExecute() && curPageB2CReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="B2Creport_isCanExport" scope="request"
	value="${(curPageB2CReportObj.isCanExport() && curPageB2CReportObj.isIs_export_applicable())?true:false}" />

<c:set var="DAYENDreport_isCanView" scope="request"
	value="${(curPageDAYENDReportObj.isCanView() && curPageDAYENDReportObj.isIs_view_applicable())?true:false}" />		
<c:set var="DAYENDreport_isCanExecute" scope="request"
	value="${(curPageDAYENDReportObj.isCanExecute() && curPageDAYENDReportObj.isIs_execute_applicable())?true:false}" />
<c:set var="DAYENDreport_isCanExport" scope="request"
	value="${(curPageDAYENDReportObj.isCanExport() && curPageDAYENDReportObj.isIs_export_applicable())?true:false}" />


<html ng-app="pmsApp">
<head>
<script type="text/javascript" language="javascript">
window.exp_arr_isCanView=${exp_arr_isCanView};
window.act_arr_isCanView=${act_arr_isCanView};
window.exp_dep_isCanView=${exp_dep_isCanView};
window.act_dep_isCanView=${act_dep_isCanView};
window.inhouse_isCanView=${inhouse_isCanView};
window.occupancy_isCanView=${occupancy_isCanView};
window.room_per_day_isCanView=${room_per_day_isCanView};
window.plan_room_isCanView=${plan_room_isCanView};
window.resv_isCanView=${resv_isCanView};
window.dailyRevenue_isCanView=${dailyRevenue_isCanView};
window.resv_cancel_isCanView=${resv_cancel_isCanView};
window.txn_isCanView=${txn_isCanView};
window.txn_trnsfr_isCanView=${txn_trnsfr_isCanView};
window.deleted_txn_isCanView=${deleted_txn_isCanView};
window.CashRegisterClosure_isCanView=${CashRegisterClosure_isCanView};
window.dailyCashClosure_isCanView=${dailyRevenue_isCanView};
window.folio_isCanView=${folio_isCanView};
window.shift_isCanView=${shift_isCanView};
window.shift_txn_isCanView=${shift_txn_isCanView};
window.shift_txn_trnsfr_isCanView=${shift_txn_trnsfr_isCanView};
window.Request_isCanView=${Request_isCanView};
window.monthly_closure_isCanView=${monthly_closure_isCanView};
window.nationality_statistics_isCanView=${nationality_statistics_isCanView};
window.Customer_isCanView=${Customer_isCanView};
window.Income_isCanView=${Income_isCanView};
window.Room_isCanView=${Room_isCanView};
window.CustomerOutstanding_isCanView=${CustomerOutstanding_isCanView};
window.CustomerGrading_isCanView=${CustomerGrading_isCanView};
window.BookingFrequency_isCanView=${BookingFrequency_isCanView};
window.TallyExport_isCanView=${TallyExport_isCanView};
window.CorporateList_isCanView=${CorporateList_isCanView};
window.GuestAnalysis_isCanView=${GuestAnalysis_isCanView};
//window.PettyList_isCanView=${PettyList_isCanView};
window.PettyList_isCanView=true;
window.ContraList_isCanView=${ContraList_isCanView};
window.PaymentList_isCanView=${PaymentList_isCanView};
window.PettyLedger_isCanView=${PettyLedger_isCanView};
window.PaymenExporttList_isCanView=${PaymenExporttList_isCanView};
window.CreditCardList_isCanView=${CreditCardList_isCanView};
window.CardExporttList_isCanView=${CardExporttList_isCanView};
window.DetailRevenueList_isCanView=${DetailRevenueList_isCanView};
window.FoodRevenueList_isCanView=${FoodRevenueList_isCanView};
window.PosRevenueList_isCanView=${PosRevenueList_isCanView};
window.B2Breport_isCanView=${B2Breport_isCanView};
window.B2Creport_isCanView=${B2Creport_isCanView};
window.DAYENDreport_isCanView =${DAYENDreport_isCanView}
function DisableBackButton() {
window.history.forward()
}
DisableBackButton();
window.onload = DisableBackButton;
window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
window.onunload = function() { void (0) }
</script>
<title>${moduleTitle}</title>
<c:import url="../common/includes/master_includes.jsp" />
<link href="<c:url value="/resources/pms/css/room_rate.css" />"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reports.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reports.js' />"></script>
<script
	src="<c:url value='/resources/common/js/download/download.js?n=1' />"></script>
<script
	src="<c:url value='/resources/common/js/download/download.min.js?n=1' />"></script>
</head>
<body ng-controller="reportsCtrl" class="full-width checkinbody"
	id="reports" ng-cloak>
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>
