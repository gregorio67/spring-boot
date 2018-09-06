<%----------------------------------------------------------------------------%>
<%-- NAME : jobschedule.jsp													--%>
<%-- DESC : The purpose of search deploy source screen		     			--%>
<%-- VER  : v1.0                                                            --%>
<%-- Copyright ⓒ 2018 D.Y KIM                                              --%>
<%-- All rights reserved.                                                   --%>
<%----------------------------------------------------------------------------%>
<%--                           Change History                               --%>
<%----------------------------------------------------------------------------%>
<%--    DATE     AUTHOR                      DESCRIPTION                    --%>
<%-- ----------  ------------  -----------------------------------------------%>
<%-- 2016.04.22  Gregorio Kim  Initial creation                             --%>
<%----------------------------------------------------------------------------%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/header.jspf" %>
<title>Source Deploy</title>
<script type="text/javascript">

/** When the grid is choosed, send request server to fetch data **/
var lc_pageIndex = 1;
var lc_systemName = '';
var lc_subSystem = '';
var lc_nodeNo = 0;

$(document).ready(function(){
	
	/** Biz Code Create**/
	fn_init();
	
	$(document).ajaxStop($.unblockUI);
	
	// click search button 
	$(".main_srch").on("click", function(event){
		event.preventDefault();
		$.blockUI({ message: '<h1><img src="/resources/image/busy.gif" /> Just a moment...</h1>' });
		
		/*Set Main Search **/
		fn_Search();
	});
	
	// search enter event
	$("#searchSource, #searchType").on("keydown", function(e){
		if(e.which == 13){
			$(".main_srch").trigger("click");
		}
	});
	
	// click list 
	$(document).on("click", "#board_list > tbody > tr > td", function(){
		var params = $(this).parents("tr");
		fn_gridClickAction(params);				
	});
	
	
	// Delete Source Deploy
	$("#btnDeploy").on("click", function(event){
		
		event.preventDefault();
		$("#board_list tbody tr").removeClass('active');
		$("#board_list").find("input[type=checkbox]").prop("checked", false);
		
		fn_deploy();		
	});
	
	// table width
	var onSampleResized = function(e){  
		var table = $(e.currentTarget);  
	};
	
	// table resizable
	$(".colwidth").colResizable({
	    liveDrag:true,
	    onResize:onSampleResized
	});
});


/*----------------------------------------------------------------------------*/
/* NAME : fn_init()															  */
/* DESC : Main Business Code Search											  */
/* DATE : 2018.05.11                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_init() {
	debugger;
	sendParam = {};
	sendParam.command="getBizCode";
	restAjax.setUrl("<c:url value='/deploy/searchCodeList' />");
	restAjax.setCallback("fn_initCallBack");
	restAjax.setAsync(false);
	restAjax.setParam(sendParam);
	restAjax.call();
	
}
/*----------------------------------------------------------------------------*/
/* NAME : fn_initCallBack()													  */
/* DESC : Main Business Code Search	callback								  */
/* DATE : 2018.05.11                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_initCallBack(result) {
	debugger;
	
	if (gfn_showErrMessage(result.message)) {
		return;
	}
	
	$("#systemNameList").empty();
	$("#subSystemList").empty();
	
	$('#systemNameList').append($('<option>', {
	    value: 1,
	    text: 'ALL'
	}));
	
	$.each(result.systemNameList, function(i, val){
		debugger;
		$("#systemNameList").append($('<option/>', {
			value: val.systemName,
			text: val.systemName
		}))
	});

	$('#subSystemList').append($('<option>', {
	    value: 1,
	    text: 'ALL'
	}));
	
	$.each(result.subSystemList, function(i, val){
		debugger;
		$("#subSystemList").append($('<option/>', {
			value: val.subSystem,
			text: val.subSystem
		}))
	});
	
	$("#btnStop").hide();
	$("#btnDeploy").hide();
	$("#btnStart").hide();
	$("#btnBuild").hide();
	$("#btnUpload").hide();
	
}
/*----------------------------------------------------------------------------*/
/* NAME : fn_Search()														  */
/* DESC : Main list search													  */
/* DATE : 2018.05.11                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_Search(pageIndex){

	debugger;

	var sendParam = {};
	if (typeof pageIndex == "undefined") {
		sendParam.currentPage = 1;
		lc_pageIndex = 1;
	}	
	else {
		sendParam.currentPage = pageIndex;
		lc_pageIndex = pageIndex;
		
	}	
	
	if ($("#systemNameList").val() != 1) {
		sendParam.systemName= $("#systemNameList").val();		
	}
	else {
		sendParam.systemName = "";
	}
	if ($("#subSystemList").val() != 1 ) {
		sendParam.subSystem=$("#subSystemList").val();		
	}
	else {
		sendParam.subSystem = "";
	}
	
	
	
	restAjax.setUrl("<c:url value='/deploy/searchDeployList' />");
	restAjax.setCallback("fn_searchCallBack");
	restAjax.setParam(sendParam);
	restAjax.call();

}
/*----------------------------------------------------------------------------*/
/* NAME : fn_searchCallBack()												  */
/* DESC : Main list search callback			  			 					  */
/* DATE : 2018.05.11                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_searchCallBack(result){
	
	debugger;
	
	if (gfn_showErrMessage(result.message)) {
		return;
	}
	
	$("#board_list tbody").empty();
	var dataList = "";
	if(result.page.totalRowCount == 0){
		var size = $("#board_list thead tr th").size();
		$("#board_list tbody").append("<tr class='list_row'><td class='list_td' colspan='7'>" + "There is no data" + "</td></tr>");
		
 		var msg = "There is no data";
		gfn_messageDialog(msg);
		return;
		
	}
	
	$.each(result.deployTarget, function(i, val){
		dataList = '<tr class="list_row">';			
		dataList += '<td class="list_td">' + '<input type="checkbox"/>' + '</td>';
			
		dataList += '<td class="list_td align-left" id="seq">' + gfn_nvl(val.seq) + '</td>';
		dataList += '<td class="list_td align-left" id="systemName">' + gfn_nvl(val.systemName) + '</td>';
		dataList += '<td class="list_td align-left" id="subSystem">' + gfn_nvl(val.subSystem) + '</td>';
		dataList += '<td class="list_td align-left" id="nodeNo">' + gfn_nvl(val.nodeNo) + '</td>';
		dataList += '<td class="list_td align-center" id="ipAddr">' + gfn_nvl(val.ipAddr) + '</td>';
		dataList += '<td class="list_td align-center" id="listenPort">' + gfn_nvl(val.listenPort) + '</td>';
		dataList += '</tr>';
		
		$("#board_list tbody").append(dataList);
	});
	
	$("#btnStop").hide();
	$("#btnDeploy").hide();
	$("#btnStart").hide();
	$("#btnBuild").show();
	$("#btnUpload").show();

	$.unblockUI();
	/** Pagination **/
	var params = {
		divId : "PAGE_NAVI",
		pageIndex : result.page.currentPage,
		recordCount : result.page.pageRowCount,
		totalCount : result.page.totalRowCount,
		pageGroupCount : result.page.pageGroupCount,
		eventName : "fn_selectPage"	
	};
	
	gfn_renderPaging(params);	
	
}


/*----------------------------------------------------------------------------*/
/* NAME : fn_selectPage()											  */
/* DESC : Click on the bottom of the list page								  */
/* DATE : 2016.04.26                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_selectPage(pageIndex){
	fn_Search(pageIndex);
}


/*----------------------------------------------------------------------------*/
/* NAME : fn_stop()									   						  */
/* DESC : Setting the detailed information at the bottom of the list		  */
/* DATE : 2016.04.26                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_stop() {
	
	debugger;
	sendParam = {};
	sendParam.hostName = lc_systemName;	
	sendParam.subSystem = lc_subSystem;
	sendParam.nodeNo = lc_nodeNo;

	/** Call Service **/
	restAjax.setUrl("<c:url value='/deploy/searchTarget' />");
	restAjax.setCallback("fn_messageCallBack");
	restAjax.setParam(sendParam);
	restAjax.call();
}

/*----------------------------------------------------------------------------*/
/* NAME : fn_deploy()								  						  */
/* DESC : Setting the detailed information at the bottom of the list		  */
/* DATE : 2016.04.26                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_deploy() {
	
	debugger;
	sendParam = {};
	sendParam.systemName = lc_systemName;	
	sendParam.subSystem = lc_subSystem;
	sendParam.nodeNo = lc_nodeNo;
	sendParam.command="deploy";

	/** Call Service **/
	restAjax.setUrl("<c:url value='/deploy/deployTarget' />");
	restAjax.setCallback("fn_messageCallBack");
	restAjax.setParam(sendParam);
	restAjax.call();
}

function fn_jobBuild() {
	debugger;
	sendParam = {};
	sendParam.hostName = lc_systemName;	
	sendParam.subSystem = lc_subSystem;
	sendParam.nodeNo = lc_nodeNo;
	sendParam.command="build";
	
	/** Call Service **/
	restAjax.setUrl("<c:url value='/deploy/jobBuild' />");
	restAjax.setCallback("fn_messageCallBack");
	restAjax.setParam(sendParam);
	restAjax.call();
}

function fn_systemPerf() {
	commonSubmit.setUrl("<c:url value='/system/showPerfPage.do'/>");
	commonSubmit.setMethod("GET");
	commonSubmit.submit();
}

/*----------------------------------------------------------------------------*/
/* NAME : fn_messageCallBack()								   				  */
/* DESC : Insert, Update, Delete call back									  */
/* DATE : 2016.04.26                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_messageCallBack(result) {

	debugger;
	/** When error occurred, show message **/
	gfn_showErrMessage(result.message);
	if (result.status != "S") {
		var msg = '<H2 style="color=red">' + result.message + '<H2>';
	}
	$("#result").val(result.message);
	
}
/*----------------------------------------------------------------------------*/
/* NAME : fn_gridClickAction()												  */
/* DESC : Click the list 													  */
/* DATE : 2018.05.10                                                          */
/* AUTH : Gregorio Kim														  */
/*----------------------------------------------------------------------------*/
function fn_gridClickAction(params){
	
	debugger;
	/** Checkbox all unchecked **/
	$("#board_list tbody tr input[type='checkbox']").prop("checked", false);
	$("#board_list tbody tr").removeClass("active");
	
	/** Selected Row checked **/
	if($(params).hasClass("active")){
		$(params).removeClass('active');
	}else{
		$(params).addClass('active');
	}


	if($(params).find("input[type=checkbox]").prop("checked") && !($(params).hasClass("active"))){
		$(params).find("input[type=checkbox]").prop("checked", false);
	}
	if(!($(params).find("input[type=checkbox]").prop("checked")) && $(params).hasClass("active")){
		$(params).find("input[type=checkbox]").prop("checked", true);
	}
	/** Set local golbal varialbe **/
	lc_systemName = $(params).find("#systemName").text();
	lc_subSystem = $(params).find("#subSystem").text();
	lc_nodeNo = $(params).find("#nodeNo").text();
	

	$("#btnDeploy").show();
	$("#result").val("");

	/** Check deploy type, if deploy type is was then show all button **/
	/**
	if (lc_deployType == "WAS") {
		$("#btnStop").show();
		$("#btnDeploy").show();
		$("#btnStart").show();
	}
	else {
		$("#btnStop").hide();
		$("#btnDeploy").show();
		$("#btnStart").hide();		
	}
	**/
	$("#btnResource").show();
}

</script>
</head>
<body>
<%@ include file="/WEB-INF/include/navigation.jsp" %>

	<div class="container_wrap">
		<div class="sub_title">
			<h3>Deploy Target List</h3>
		</div>
		<!-- Search View -->
		<div class="search_Tblock">
			<table class="search_table">
				<colgroup>
					<col width="100px"/>
					<col width="100px"/>
					<col width="100px"/>
					<col width="100px"/>
					<col width="100px"/>
				</colgroup>
				<tbody>
					<tr class="search_row">
						<th class="list_th">Deploy Target</th>
						<td>
							<select class="select_file" id="systemNameList">
							</select>
						</td>
						<th class="list_th">Source Type</th>
						<td>
							<select class="select_file" id="subSystemList">
							</select>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="search_submit">
				<input class="main_srch" type="button" value="Search">
			</div>
		</div>
		<!-- Detail View -->
		<div class="block_list">
			<table class="list_table colwidth" id="board_list">
				<thead>
					<tr class="list_thead">
						<th width="3%" class="select_check"><input type="checkbox"/></th>
						<th width="10%">SEQ</th>
						<th width="20%">System Name</th>
						<th width="20%">Sub System</th>
						<th width="20%">Node No</th>
						<th width="20%">IP Address</th>
						<th width="20%">Port</th>						
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="7">There is no data</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="PAGE_NAVI" class="list_page">
		</div>
		
<!-- --------------------------------------------------------------------------------- -->
		<div class="list_btn_group">
			<button class="btn_default" id="btnDeploy">Deploy</button>
		</div>
		<div class="sub_title">
			<h3>Result Log</h3>
		</div>		
		<div class="block_list">
			<table class="list_table">
				<colgroup>
					<col width="15%"/>
					<col width="35%"/>
					<col width="0%"/>							
				</colgroup>
				<tbody>
					<tr class="detail_view">
						<th class="list_th align-center" >Result</th>
						<!-- td class="list_td_textarea align-left"-->
						<td class="list_td align-left" colspan="2">
							<textarea id="result" style="margin: 3px; width: 1040px; height: 80px;" class="floatL" readonly="readOnly"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>							
	</div>
	<%@ include file="/WEB-INF/include/tailer.jspf" %>
</body>
</html>