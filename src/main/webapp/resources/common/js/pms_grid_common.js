var tableDefenition = null;

function paginationBtnFirstDiv() {

	var txt = "<div><div id='paging_left' class=''>"
		+ "<div class='prev disabled prev_btn_disable paging_btn'>"
		+ "<input type='button' onclick='pagination_First();' class='paging_button' value='<<' id='paging_first_btn'/>"
		+ "</div><div class='frst disabled prev_btn_disable paging_btn'>"
		+ "<input type='button' class='paging_button' onclick='pagination_prev();' value='<' id='paging_prev_btn' />"
		+ "</div></div><div class='paging_mid'>";

	return txt;
}

function paginationBtnLastDiv(pageCount) {

	var txt = "</div><div id='paging_right' class=''>"
		+ "<div class='next next_btn_disable paging_btn'>"
		+ "<input type='button' onclick='pagination_next();' class='pagingRightAngle paging_button' id='paging_next_btn' value='>'> </div>"
		+ "<div class='next next_btn_disable paging_btn'>"
		+ "<input type='button' onclick='pagination_Last(" + pageCount + ")' class='pagingRightAngle paging_button' value='>>' id='paging_last_btn'></div>"
		+ "</div></div>";

	return txt;
}


function pagination_no(obj) {

	var pagingStart = parseInt($('#currentpage').val());
	var limit = $('#limit').val();
	var lastPageNo = $('#lastPageNo').val();
	
	if (obj == lastPageNo) {
		
		if (limit < PAGING_MAX_DISPLAY_LIMIT) {
			
			pagingStart = 1;
		} else {
			
			pagingStart += 1;
		}
		
		if (limit == lastPageNo) {
			
			if (limit >= PAGING_MAX_DISPLAY_LIMIT) {
				
				pagingStart = parseInt(limit);
				pagingStart -= PAGING_LAST_CONST_DISPLAY;
			}
		}
		
	} else if (obj == pagingStart && pagingStart != 1) {
		
		pagingStart -= 1
	}
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid(obj, pagingStart, rowLimit, '', null, '');
}

function pagination_next() {

	var pagingStart = parseInt($('#currentpage').val());
	var current = parseInt($('#PageNo').val());
	var loopLast = $('#lastPageNo').val();
	var limit = $('#limit').val();
	
	if ((current) == (loopLast - 1) || current >= PAGING_MAX_DISPLAY_LIMIT) {
		
		if ((limit >= PAGING_MAX_DISPLAY_LIMIT) && (limit == current + 1)) {
			
			pagingStart = parseInt(limit);
			pagingStart -= PAGING_LAST_CONST_DISPLAY;
			current += 1;
		
		} else {
			
			if (limit < PAGING_MAX_DISPLAY_LIMIT) {
				
				current += 1;
			} else {
				
				pagingStart = current - PAGING_NEXT_ACTIVE_POSITION_LIMIT;
				current += 1;
			}
		}
		
	} else {
		
		pagingStart = 1;
		current += 1;
	}
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid(current, pagingStart, rowLimit, '', null, '');
}

function pagination_prev() {

	var pagingStart = parseInt($('#currentpage').val());
	var current = parseInt($('#PageNo').val());
	var loopLast = $('#lastPageNo').val();
	var limit = $('#limit').val();
	
	if ((limit >= PAGING_MAX_DISPLAY_LIMIT) && (current > PAGING_MAX_DISPLAY_LIMIT)) {
		
		if ((limit == (current + 1)) || (limit == (current))) {
			
			pagingStart = parseInt(limit);
			pagingStart -= PAGING_LAST_CONST_DISPLAY;
			current -= 1;

		} else {
			
			pagingStart = current - 2;
			current -= 1;
		}
		
	} else {
		
		pagingStart = 1;
		current -= 1;
	}

	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid(current, pagingStart, rowLimit, '', null, '');
}

function pagination_rowLimit(obj) {

	var rowLimit = $('#rowCounter option:selected').text();
	var pagingStart = $('#currentpage').val('1');
	var rowLimit = $('#rowCounter option:selected').text();
	sessionStorage.setItem("rowLimit", rowLimit);
	
	reloadGrid('1', '1', rowLimit, '', null, '');
}

function pagination_Last(obj) {

	var pagingStart = parseInt($('#limit').val());
	
	if ($('#limit').val() >= PAGING_MAX_DISPLAY_LIMIT) {
		
		pagingStart -= PAGING_LAST_CONST_DISPLAY;
	
	} else {
		
		pagingStart = 1;
	}
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid(obj, pagingStart, rowLimit, '', null, '');
}

function pagination_First() {

	$('#currentpage').val('');
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid('1', '1', rowLimit, '', null, '');
}

function pageSearch() {

	var page = $('#pageNos').val();
	var limit = $('#limit').val();
	var pagingStart;
	
	if (parseInt(page) <= parseInt(limit) && parseInt(page) >= parseInt(1)) {
	
		if(limit != page) {
			
			if (page < PAGING_MAX_DISPLAY_LIMIT) {
				
				pagingStart = 1;
			} else {
				
				pagingStart = page - 3;
			}
			
		} else {
			
			if (page < PAGING_MAX_DISPLAY_LIMIT) {
				
				pagingStart = 1;
			} else {
				
				pagingStart = page - PAGING_LAST_CONST_DISPLAY;
			}
		}

		var rowLimit =  $('#rowCounter option:selected').text();
		reloadGrid(page, pagingStart, rowLimit, '', null, '');

	} else {
		
	
		
	}
}


function contentSearch() {

	$('#search_fom_div').hide();

	item = {};
	var tagInput = '';
	var count = 0;

	$('.searchCls').each(function() {
		var ids = $(this).attr('id');
		var tag = $(this).attr('data-tag');
		var inputType = $(this).prop('tagName');
		var val = "";

		if(inputType == "INPUT") {
			
			val = $('#' + ids).val();
		} else if(inputType == "SELECT") {
			
			val = $('#' + ids + " option:selected").text();
		}

		if ($('#' + ids).val() != '') {
			
			item[ids] = $('#' + ids).val();
			count++;
			tagInput += '<div id="'
				+ ids
				+ '-'
				+ count
				+ '" class="inputTags">'
				+ tag
				+ ' : '
				+ val
				+ '<span class="inputclose" onclick="tagInputClose(this)">&times;</span></div>';
		}
	});

	$('#simpleSearchTxt').html(tagInput);
	var stringContent = JSON.stringify(item);
	$('#loginBox').hide();
	$('#loginButton').removeClass('active');
	$('#limit').val('');
	$("#advance_search_box_div").css("display", "none");
	$('#drpdwn').removeClass('fa-angle-up').addClass('fa-angle-down');
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid('1', '1', rowLimit, stringContent, null, '');

	sessionStorage.setItem("searchData", stringContent + "~" + tagInput);
	$('#stringContent').val(stringContent);
}

function tagInputClose(obj) {

	var id = $(obj).closest('div').attr('id');
	var temp = id.split('-');
	
	$('#' + id).remove();
	$('#stringContent').val('');
	$('#' + temp[0]).val('');
	
	contentSearch();
}

function searchBoxClear() {

	$('#simpleSearchTxt').html('');
	
	advanceSearchClear();
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid('1', '1', rowLimit, '', null, '');
}

function advanceSearchClear() {

	$('.searchCls').each(function() {
		
		var ids = $(this).attr('id');
		if(ids=="nett_amount"){
	        $('#'+ids).attr("disabled", true);           


		}
		$('#'+ids).val('');
	});
	
	sessionStorage.removeItem('searchData');
	sessionStorage.removeItem('simpleSearch');
}

function sortAsc(columnName) {

	var searchCont = $('#stringContent').val();
	var pageN = $('#pageNo').val();
	
	/* Need to change */
	$('#jqGrid_list_grid').jqGrid('setGridParam', {
		postData : {
			currentPage : pageN,
			sortVal : columnName + " desc",
			advanceSearch : searchCont,
			rowLimit : $('#rowCounter option:selected').text()
		}
	});
}

function sortDesc(columnName) {

	var searchCont = $('#stringContent').val();
	var pageN = $('#pageNo').val();
	
	/* Need to change */
	$('#jqGrid_list_grid').jqGrid('setGridParam', {
		postData : {
			currentPage : pageN,
			sortVal : columnName + " asc",
			advanceSearch : searchCont,
			rowLimit : $('#rowCounter option:selected').text()
		}
	});
}


function simpleSearch() {

	var serchVal = $('#simpleSearchTxt').text();
	serchVal = $.trim(serchVal);
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid('1', '1', rowLimit, '', null, serchVal);

	sessionStorage.setItem('simpleSearch',serchVal);
}

function refreshGrid() {

	$('#simpleSearchTxt').html('');
	$('#pageNos').val('');
	$('#code').val('');
	$('#name').val('');
	sessionStorage.removeItem('rowLimit');
	
	var rowLimit =  $('#rowCounter option:selected').text();
	reloadGrid('1', '1', rowLimit, '', null, '');
}

function masterFormContent() {

	var txt="<input type='hidden' id='currentpage' /> <input type='hidden' id='lastPageNo' /> "
		+ "<input type='hidden' id='PageNo' /> <input type='hidden' id='limit' /> "
		+ "<input type='hidden' id='searchTotalCount' /> <input type='hidden' id='stringContent' /> "
		+ "<input type='hidden' id='countTotal'><div id='pms_dynamic_grid'></div>";
	
	$('#form_content_div').html(txt);
}

function pagination(data) {

	var serverData = JSON.stringify(data);
	var obj = JSON.parse(serverData);
	var totalRow = obj.total;
	var currentPage = obj.page;
	var pagingStart = obj.pagingStart;
	var data = totalRow;
	sessionStorage.setItem('totalRecord', data);
	$('#PageNo').val(currentPage);
	$('#currentpage').val(pagingStart);
	var rowCountLimit = $("#rowCounter option:selected").text();
	var pageCount = Math.ceil(totalRow / rowCountLimit);
	var txt = '';
	var count = 0;
	var limit = pageCount;
	$('#limit').val(limit);
	$('#pagination_div').empty();
	$('#pagination_div').addClass('pagination_div_cls');
	
	txt = paginationBtnFirstDiv();
	
	txt = "<div><div id='paging_left' class=''>"
		+ "<div class='prev disabled prev_btn_disable paging_btn'>"
		+ "<input type='button' onclick='pagination_First();' class='paging_button' value='<<' id='paging_first_btn'/></div>"
		+ "<div class='frst disabled prev_btn_disable paging_btn'>"
		+ "<input type='button' class='paging_button' onclick='pagination_prev();' value='<' id='paging_prev_btn' /></div>"
		+ "</div><div class='paging_mid'>";
	
	if (totalRow == 0) {
		
		txt += "<div class='active paging_btn'><input type='button' class='pagingCls paging_button active' id='pageNo1'  value='1'></div>";
	}
	var endLoop = '';
	
	for (var i = pagingStart; i <= limit; i++) {
		
		count++;
		
		if (count == 6) {
			
			break;
		}
		
		endLoop = i;
		
		if (i == currentPage) {
			
			txt += "<div class='active paging_btn'><input type='button' class='pagingCls paging_button active' id='pageNo"
				+ i
				+ "' onclick='pagination_no(this)' value="+ i + "></div>";
		} else {
			
			txt += "<div class='paging_btn'><input type='button' class='pagingCls paging_button' id='pageNo"
				+ i
				+ "' onclick='pagination_no("
				+ i + ")' value=" + i + "></div>";
		}
	}
	
	$('#lastPageNo').val(endLoop);
	txt += paginationBtnLastDiv(pageCount);
	$('#pagination_div').empty();

	$('#pagination_div').append(txt);

	var pagingStarting = currentPage* $('#rowCounter  option:selected').text() - ($('#rowCounter  option:selected').text()-1);
	var endPageCount =  parseInt(pagingStarting)+parseInt($('#rowCounter  option:selected').text())-1;

	if (currentPage == limit) {
		
		endPageCount = totalRow;
		$('#paging_last_btn').prop('disabled', true);
		$('#paging_next_btn').prop('disabled', true);
		$('#paging_right').addClass('paging_right_cls');

	} else if (currentPage == 1) {
		
		endPageCount = $('#rowCounter  option:selected').text();
		pagingStarting = 1;
		$('#paging_first_btn').prop('disabled', true);
		$('#paging_prev_btn').prop('disabled', true);
		$('#paging_left').addClass('paging_left_cls');

	}
	if (totalRow == 0 ){
		
		pagingStarting=0;
		endPageCount=0;
		
	}
	if (totalRow == 0 || limit == 1 && currentPage == 1) {

		$('.paging_button').prop('disabled', true);
		$('#paging_right').addClass('paging_right_cls');
		$('#paging_left').addClass('paging_left_cls');
		$('#pagination_div').addClass('pagination_div_cls');
	
	}

	var result = '  Showing ' + pagingStarting + ' to ' +endPageCount + ' of ' + totalRow + ' entries';
	$('#result').text(result);
}

function simpleSearchEnter() {

	$('#simpleSearchTxt').keypress(function(e) {
		
		if (e.keyCode == 10 || e.keyCode == 13) {
			
			simpleSearch();
			e.preventDefault();
		}
	});
}

function totalCount(serverUrl) {

	var advanceSearchData = sessionStorage.getItem('searchData');
	var simpleSearchData = sessionStorage.getItem('simpleSearch');
	var returnCount;
	
	if(simpleSearchData != null || advanceSearchData != null) {
		
		$.ajax({
			url:serverUrl,
			data:{
				simpleSearch:simpleSearchData,
				advanceSearch:advanceSearchData,
			},
			async:false,
			type:"POST",

			success : function(response){
				$('#searchTotalCount').val(response);
			}
		});
	}
}

function generateGrid(tableDef) {
 
	
	var actionVal = sessionStorage.getItem('action');
	var rowLimit = sessionStorage.getItem('rowLimit');
	
	if(rowLimit == '' || rowLimit == null) {
		
		rowLimit = $('#rowCounter option:selected').text();
	}	  
	
	$('#rowCounter').val(rowLimit);
	var activePage = Math.ceil($('#totalCount').val() / rowLimit);
	var pagingStart = parseInt($('#currentpage').val());
	var totalRecord = sessionStorage.getItem('totalRecord');
	var totalLimit = Math.ceil(totalRecord / rowLimit);

	if($('#searchTotalCount').val()!='' && actionVal != SAVE_ACTION_INS) {
		
		activePage = Math.ceil($('#searchTotalCount').val() / rowLimit);
	}

	var page = 1;
	var cookieVal = sessionStorage.getItem('currentPage');
	var cookieTempValue = activePage;
	
	if(actionVal == SAVE_ACTION_INS) {
		
		sessionStorage.removeItem('searchData');
		sessionStorage.removeItem('simpleSearch');
		cookieVal = "";
	} else if(actionVal == SAVE_ACTION_UPD) {
		
		activePage = "";
	}
	
	var searchData = sessionStorage.getItem('searchData');
	var advanceSearchSplit = '';
	
	if(searchData != null) {
		
		advanceSearchSplit=searchData.split('~');
		$('#simpleSearchTxt').html(advanceSearchSplit[1]);
	}
	
	var simpleSearchData = sessionStorage.getItem('simpleSearch');
	
	if(simpleSearchData != null) {
		
		$('#simpleSearchTxt').text(simpleSearchData);
	}
	
	if(activePage != 0 && cookieVal == '') {
		
		page = activePage;
	
	} else if(cookieVal == '' && activePage == '') {
		
		page = "1";
	
	} else if(cookieVal !='' && activePage == 0) {
		
		page = cookieVal;

		if(page >= 5) {
			
			if(cookieVal == totalLimit) {
				
				pagingStart = page - 4;
			
			} else {
				
				pagingStart = page - 3;
			}
		}

	} else if (cookieVal >= activePage) {
		
		page = activePage;
		
		if(page >= 5) {
			
			pagingStart = page - 4;
			
		} else {
			
			pagingStart = 1;
		}
		
	} else if(cookieVal <= activePage) {
		
		page = cookieVal;
		
		if(page >= 5) {
			
			pagingStart = page - 3;
		
		} else {
			
			pagingStart = 1;
		}
	}    

	if(actionVal == SAVE_ACTION_INS) {
		
		if(page >= 5) {
			
			pagingStart = page - 4;
		
		} else {
			
			pagingStart = 1;
		}

		advanceSearchSplit[0] = '';  
		$('#simpleSearchTxt').html(' ');

	} else if(actionVal == SAVE_ACTION_UPD) {
		
		if(page >= 5) {
			
			if(cookieVal == totalLimit) {	 
				
				pagingStart = page - 4;
			
			} else {
				
				pagingStart = page - 3;
			}
		}
	}

	jsonObj = [];
	
	$('.searchCls').each(function() {
		
		var ids = $(this).attr('id');
		
		if ($('#' + ids).val() != '') {
			
			item = {};
			item[ids] = $('#' + ids).val();
			jsonObj.push(item);
		}
		
		var stringContent = JSON.stringify(jsonObj);

	});

	$('#pagination_div ul li').remove();
	
	if(tableDef.postData === undefined) {
		tableDef.postData = {};
	}
	
	tableDef.postData.currentPage = page;
	tableDef.postData.rowLimit = rowLimit;
	tableDef.postData.pagingStart = pagingStart;
	tableDef.postData.advanceSearch = advanceSearchSplit[0];
	tableDef.postData.simpleSearh = simpleSearchData;
	
	/** Table Generation **/
	var tHead = "<thead class='gd_thead'><tr>";
	var cellCss = '';
	var cellClass = '';

	$.each(tableDef.colModel, function(idx, obj) {
		
		if(obj.hidden) {
			
			cellClass = "d_grid_hidden_col";
		} else if(obj.width != '') {
			
			cellCss = "width:" + obj.width + ";";
		}

		if(!obj.hidden && obj.sort) {
			
			if(cellClass != '') {
				
				cellClass += ',';
			}

			cellClass += 'd_grid_sortable_col d_grid_sortable_col_none';
		}

		tHead += "<th data-colmodel='" + obj.name + "' data-sortstatus='none'" 
			+ " class='" + cellClass + "' style='" + cellCss + "'>" + obj.label + "</th>";

		cellClass = '';
		cellCss = '';
	});

	tHead += "</tr></thead>";
	
	var tableWidthCss = '';

	if(tableDef.tableWidth != '') {
	
		tableWidthCss = "style='width:" + tableDef.tableWidth + "'";
	}
	
	cellCss = (tableDef.tableHeight != '') ? " style='height:" + tableDef.tableHeight + "; overflow: auto;'" : "";
	var table = "<div class='d_grid_div cmn_tbl' " + cellCss + "><table id='" + tableDef.id + "' class='gd_table' " + tableWidthCss + ">" + tHead 
		+ "<tbody class='gd_tbody'></tbody></table></div>";

	$('#' + tableDef.bindingDivId).html(table);

	$('.d_grid_sortable_col').click(function() {
		
		sortClick(tableDef, this);
	});
	
	 
	fillGrid(tableDef);
	 
	
	sessionStorage.removeItem('action');
	sessionStorage.removeItem('currentPage');
	
	
	
}

function fillGrid(tableDef) {
	
	tableDefenition = tableDef;
	
	var rows = {};
	
	
	$.ajax({
		url: tableDef.url,
		type: 'POST',
		async:false,
		data: tableDef.postData,
		success: function(data) {
			
			rows = data.rows;
			pagination(data);
		},
		error: function(response) {
			
			alert("Error occured.");
		}
	});
	
	var cellClass = '';
	var cellCss = '';
	var tBody = "";

	if(rows.length == 0) {
		
		$('#' + tableDef.bindingDivId + ' tbody').html("<tr><td style='text-align:center;' colspan='" 
				+ tableDef.colModel.length + "'>No records found</td></tr>");
	} else {

		var cellValue = '';
		
		$.each(rows, function(idx, obj) {
			 
			if(obj.system || obj.is_System || obj.is_system || obj.isSystem){
				tBody += "<tr id='desabletr' data-rownum='" + (idx + 1) + "'>";
			}else{
				
				tBody += "<tr   data-rownum='" + (idx + 1) + "'>";
			}
			
			
			
			 
			

		//	tBody += "<tr   data-rownum='" + (idx + 1) + "'>";

			$.each(tableDef.colModel, function(i, col) {

				if(col.hidden) {
					
					cellClass = "d_grid_hidden_col";
				} else if(col.align != '') {
					
					cellCss = "text-align:" + col.align + ";";
				}
				
				if(typeof col.name !== "undefined" && col.name != null && col.name != '') {
					
					cellValue = eval('obj.' + col.name);
					
					
				}
				
				if(typeof col.formatter !== "undefined" && col.formatter != null) {
					
					cellValue = col.formatter(cellValue, obj);
				}

				tBody += "<td class='" + cellClass + "' style='" + cellCss + "'>" + cellValue + "</td>";

				cellClass = '';
				cellCss = '';
			});

			tBody += "</tr>";
		});

		$('#' + tableDef.bindingDivId + ' tbody').html(tBody);
		
		$('html, body').animate({scrollTop: 0}, 500);
	}
}

function reloadGrid(currentPage, pagingStart, rowLimit, advanceSearch, sortVal, simpleSearh) {
	
	var tableDef = tableDefenition;
	
	if(currentPage != null) {
		
		tableDef.postData.currentPage = currentPage;
	}
	
	if(pagingStart != null) {
		
		tableDef.postData.pagingStart = pagingStart;
	}
	
	if(rowLimit != null) {
		
		tableDef.postData.rowLimit = rowLimit;
	}
	
	if(advanceSearch != null) {
		
		tableDef.postData.advanceSearch = advanceSearch;
	}
	
	if(sortVal != null) {
		
		tableDef.postData.sortVal = sortVal;
	}
	
	if(simpleSearh != null) {
		
		tableDef.postData.simpleSearh = simpleSearh;
	}
	
	fillGrid(tableDef);
}

function sortClick(tableDef, cell) {
	
	var sortField = $(cell).attr('data-colmodel');
	var sortStatus = $(cell).attr('data-sortstatus');
	
	$('.d_grid_sortable_col').removeClass('d_grid_sortable_col_asc d_grid_sortable_col_desc');
	$('.d_grid_sortable_col').addClass('d_grid_sortable_col_none');
	
	
	if(sortStatus == 'none') {
		
		$(cell).addClass('d_grid_sortable_col_asc');
		$(cell).attr('data-sortstatus', 'asc');
		sortStatus = 'asc';
	
	} else if(sortStatus == 'asc') {
		
		$(cell).addClass('d_grid_sortable_col_desc');
		$(cell).attr('data-sortstatus', 'desc');
		sortStatus = 'desc';
	
	} else {
		
		$(cell).addClass('d_grid_sortable_col_asc');
		$(cell).attr('data-sortstatus', 'asc');
		sortStatus = 'asc';
	}
	
	tableDef.postData.sortVal = sortField + " " + sortStatus;
	
	fillGrid(tableDef);
}

function backToTop() {
	
	var offset = 220;
	var duration = 500;
	
	jQuery(window).scroll(function() {
		
		if (jQuery(this).scrollTop() > offset) {
			
			jQuery('.back-to-top').fadeIn(duration);
		} else {
			
			jQuery('.back-to-top').fadeOut(duration);
		}
	});
	
	jQuery('.back-to-top').click(function(event) {
		
		event.preventDefault();
		jQuery('html, body').animate({scrollTop: 0}, duration);
		return false;

	});
}