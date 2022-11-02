package com.indocosmo.pms.web.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice 
public class ExceptionController {
	public static final String DEFAULT_ERROR_VIEW = "exception/exception";

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ModelAndView defaultErrorHandler(HttpServletRequest request,HttpServletResponse response,/* CustomException customException*/Exception ex){
		ModelAndView modelAndView = null;

		if(isAjax(request) ) {
			modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
			String exceptionMessage ="Please Contact Administrator";
			modelAndView.addObject("message", exceptionMessage);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			return modelAndView;
		} else {
			modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
			modelAndView.addObject("datetime", new Date());
			modelAndView.addObject("name", ex.getClass().getSimpleName());
			modelAndView.addObject("message","Please Contact Administrator");
			modelAndView.addObject("url", request.getRequestURL());

			return modelAndView;
		}
	}  

	private boolean isAjax(HttpServletRequest request){
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String handle(NoHandlerFoundException ex) {

	  return "exception/404Page";
	}

}