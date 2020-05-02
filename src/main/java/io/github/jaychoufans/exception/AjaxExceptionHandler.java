package io.github.jaychoufans.exception;

import io.github.jaychoufans.common.JsonData;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class AjaxExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public JsonData defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		return JsonData.fail(e.getMessage());
	}

	@ExceptionHandler(BindException.class)
	public JsonData handleBindException(BindException ex) {
		// 校验 除了 requestbody 注解方式的参数校验 对应的 bindingresult 为 BeanPropertyBindingResult
		FieldError fieldError = ex.getBindingResult().getFieldError();
		return JsonData.fail(fieldError.getDefaultMessage());
	}

}
