/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : BaseExceptionHandler.java
 * @Description : 
 *
 * @Author : LGCNS
 * @Since : 2017. 4. 20.
 *
 * @Copyright (c) 2018 EX All rights reserved.
 *-------------------------------------------------------------
 *              Modification Information
 *-------------------------------------------------------------
 * 날짜            수정자             변경사유 
 *-------------------------------------------------------------
 * 2018. 8. 7.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.exception;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;

import dymn.demo.base.BaseConstants;

//@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

	@ResponseBody
	public ResponseEntity<?> handleUnauthenticationException(Exception e) {
		return errorResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class })
	@ResponseBody
	public ResponseEntity<?> handleConflictException(Exception e) {
		return errorResponse(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler({ SQLException.class, DataAccessException.class, RuntimeException.class })
	@ResponseBody
	public ResponseEntity<?> handleSQLException(Exception e) {
		return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ IOException.class, ParseException.class, JsonParseException.class, JsonMappingException.class })
	@ResponseBody
	public ResponseEntity<?> handleParseException(Exception e) {
		return errorResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ InvalidKeyException.class, NoSuchAlgorithmException.class })
	@ResponseBody
	public ResponseEntity<?> handleHashException(Exception e) {
		return errorResponse(new Exception("Encrypt/Decrypt key is requested"), HttpStatus.LOCKED);
	}

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public ResponseEntity<?> handleAnyException(Exception e) {
		return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<ExceptionMessage> errorResponse(Throwable throwable, HttpStatus status) {
		if (null != throwable) {
			return response(new ExceptionMessage(throwable), status);
		} else {
			return response(null, status);
		}
	}

	protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
		return new ResponseEntity<T>(body, new HttpHeaders(), status);
	}

//	@ExceptionHandler(value = { NoHandlerFoundException.class})
//	// @ResponseStatus(value = HttpStatus.NOT_FOUND)
//	public ModelAndView handleNotFoundError(HttpServletRequest request, Exception ex) throws Exception {
//
//		if (ex instanceof NoHandlerFoundException) {
//			LOGGER.error("{} not found", request.getRequestURI());
//			Map<String, Object> retMsg = new HashMap<String, Object>();
//			ModelAndView mav = new ModelAndView();
//			mav.setViewName("jsonView");
//
//			retMsg.put("message", "Page not found.");
//			retMsg.put("status", BaseConstants.STATUS_ERROR);
//			return mav;
//		} else {
//			return null;
//		}
//	}
	
	public class ExceptionMessage {
		private Throwable throwable;
		
		private String errMessage;
		
		public ExceptionMessage(Throwable throwable) {
			this.throwable = throwable;
		}

		public String getErrMessage() {
			errMessage = this.throwable.getMessage();
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}
		
		
	}

}
