package com.chinikom.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinikom.domain.AddressDetails;
import com.chinikom.exception.HTTP400Exception;
import com.chinikom.service.ChinikomAddressDetailsService;
import com.chinikom.service.ServiceEvent;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */
@RestController
@RequestMapping(value = "/chinikom-address-service/v1/address")
public class AddressServiceController extends AbstractRestController {

	@Autowired
	private ChinikomAddressDetailsService addressDetailsService;

	@Autowired
	CounterService counterService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createAddressDetailsWithValidation(
			@RequestBody AddressDetails address, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// UserDetails userDtls =
		AddressDetails createdAddressDetail = this.addressDetailsService
				.createAddressWithUserValidation(address);
		if (createdAddressDetail != null) {
			counterService
					.increment("com.chinikom.addressdetails.created.success");
			eventPublisher.publishEvent(new ServiceEvent(this,
					createdAddressDetail, "AddressCreated"));
		} else {
			counterService
					.increment("com.chinikom.addressdetails.created.failure");
		}
		response.setHeader("Location", request.getRequestURL().append("/")
				.append(createdAddressDetail.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Page<AddressDetails> getAllAddressDetailsByPage(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.addressDetailsService.getAllAddressByPage(page, size);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<AddressDetails> getAllAddressDtails(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.addressDetailsService.getAllAddresses();
	}

	// @RequestMapping("/simple/{emailId}")
	// public UserLogin getSimpleUserLoginByEmail(
	// @PathVariable("emailId") String emailId) {
	// UserLogin userLogin = this.userLoginService
	// .getUserLoginByEmail(emailId);
	// checkResourceFound(userLogin);
	// return userLogin;
	// }
	//

	// @RequestMapping(value = "/byemail/{emailId}", method = RequestMethod.GET,
	// produces = {
	// "application/json", "application/xml" })
	// @ResponseStatus(HttpStatus.OK)
	// public @ResponseBody
	// AddressDetails getUserLoginByEmail(@PathVariable("emailId") String
	// emailId,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// UserLogin userLogin = this.addressDetailsService
	// .getUserLoginByEmail(emailId);
	// checkResourceFound(userLogin);
	// return userLogin;
	// }

	@RequestMapping("/simple/{id}")
	public AddressDetails getSimpleAddressDeatilsById(
			@PathVariable("id") Long id) {
		AddressDetails addressDetails = this.addressDetailsService
				.getAddress(id);
		checkResourceFound(addressDetails);
		return addressDetails;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	AddressDetails getAddressDetailsById(@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AddressDetails addressDetails = this.addressDetailsService
				.getAddress(id);
		checkResourceFound(addressDetails);
		return addressDetails;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateAddressDetails(@PathVariable("id") Long id,
			@RequestBody AddressDetails addressDetails,
			HttpServletRequest request, HttpServletResponse response) {
		AddressDetails addressDtls = this.addressDetailsService.getAddress(id);
		checkResourceFound(addressDtls);
		if (id != addressDtls.getId())
			throw new HTTP400Exception("ID doesn't match!");
		counterService.increment("com.chinikom.addressdetails.updated.success");

		this.addressDetailsService.updateAddress(addressDtls);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAddressDetails(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.addressDetailsService.getAddress(id));
		counterService.increment("com.chinikom.addressdetails.deleted.success");
		this.addressDetailsService.deleteAddress(id);
	}
}
