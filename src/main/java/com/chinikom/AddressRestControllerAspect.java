package com.chinikom;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AddressRestControllerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CounterService counterService;

	@Before("execution(public * com.chinikom.api.rest.*Controller.*(..))")
	public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
		logger.info(":::::AOP Before for Address REST call:::::" + pjp);
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.createAddress*(..))")
	public void afterCallingCreateAddress(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Create Address REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.AddressController.createAddress");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllAddressesByPage*(..))")
	public void afterCallingGetAllAddressByPage(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Addresses getAllAddressesByPage REST call:::::"
				+ pjp);

		counterService
				.increment("com.chinikom.api.rest.AddressController.getAllAddressesByPage");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllAddresses*(..))")
	public void afterCallingGetAllAddress(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Addresses getAllAddresses REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.AddressController.getAllAddresses");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAddress*(..))")
	public void afterCallingGetAddress(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Addresses getAddress REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.AddressController.getAddress");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.updateAddress*(..))")
	public void afterCallingUpdateAddress(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Addresses updateAddress REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.AddressController.updateAddress");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.deleteAddress*(..))")
	public void afterCallingDeleteAddress(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Addresses deleteAddress REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.AddressController.deleteAddress");
	}

	@AfterThrowing(pointcut = "execution(public * com.chinikom.api.rest.*Controller.*(..))", throwing = "e")
	public void afterCustomerThrowsException(NoSuchElementException e) {
		counterService.increment("counter.errors.Address.controller");
	}
}
