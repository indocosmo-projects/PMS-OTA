function sortPaymentList(com) {
	var sortCol = $(com).attr('data-col');
	var sortStat = '';
	var resvId = $('#resvNo').val();

	if($(com).attr('data-sort') == '') {
		sortStat = 'desc';
	}

	$('#tblTxnLst .d_grid_sortable_col').removeClass('d_grid_sortable_col_asc').removeClass('d_grid_sortable_col_desc');
	
	if($(com).attr('data-sort') == 'asc') {
		sortStat = 'desc';
		$(com).removeClass('d_grid_sortable_col_asc').addClass('d_grid_sortable_col_desc');
	} else {
		sortStat = 'asc';
		$(com).removeClass('d_grid_sortable_col_desc').addClass('d_grid_sortable_col_asc');
	}

	$(com).attr('data-sort', sortStat);
	depositList(resvId, sortCol, sortStat);
}
