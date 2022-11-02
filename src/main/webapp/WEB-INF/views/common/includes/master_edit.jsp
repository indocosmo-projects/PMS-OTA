 <jsp:directive.include file="page_directives.jsp" />

 
  <div class="main_wrappper">
		<div class="main_wrapper_in">
		</div></div>  
<div>
					<c:import url="../menu/topMenu.jsp" />
				
						
					</div>
			<div class="rgt_content_head_top_div content_wrapper_white">
					<div class="main_wrappper">
		<div class="main_wrapper_in " >
				<h2>${moduleName}</h2></div>
				</div></div>
			<div class="wrapper">
	<div class="con_wrapper inner_content">
		<div class="rgt_content_main_div">
		
	<div class="content_wrapper inner_content_wrapper content_wrapper_white">
					<div class="pms_form_sub_div">
						<c:import url="${customEditIncludeFile}" />
					</div>
				</div>
		</div>
	</div>
</div>		