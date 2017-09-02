package com.chinikom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chinikom.dao.jpa.ChinikomAddressRepository;
import com.chinikom.domain.AddressDetails;
import com.chinikom.domain.UserDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/*
 * Service class to do CRUD for User and Address through JPS Repository
 */
@Service
public class ChinikomAddressDetailsService {

	private static final Logger log = LoggerFactory
			.getLogger(ChinikomAddressDetailsService.class);

	@Autowired
	private ChinikomAddressRepository addressRepository;

	@Autowired
	CounterService counterService;

	@Autowired
	UserDetailsClient userClient;

	// @Autowired
	// GaugeService gaugeService;

	public ChinikomAddressDetailsService() {
	}

	// @HystrixProperty(name =
	// "hystrix.command.default.execution.timeout.enabled", value = "false")
	// public AddressDetails createAddressWithUserValidation(AddressDetails
	// address)
	// throws Exception {
	// return createAddress(address);
	// }

	// @HystrixCommand(fallbackMethod = "createAddressWithoutUserValidation")
	// @HystrixProperty(name =
	// "hystrix.command.default.execution.timeout.enabled", value = "false")
	// public AddressDetails createAddressWithoutValidUser(AddressDetails
	// address)
	// throws Exception {
	// return createAddress(address);
	// }
	@HystrixCommand(fallbackMethod = "createAddressWithoutUserValidation")
	@HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
	public AddressDetails createAddressWithUserValidation(AddressDetails address)
			throws Exception {
		AddressDetails createAddress = null;
		if (address != null && address.getUser() != null) {
			log.info("In service address create" + address.getUser().getId());
			if (userClient == null) {
				log.info("In customerClient null got customer");
			} else {
				log.info("In customerClient not null got customer");
			}

			UserDetails user = userClient.getUserDetails(new Long(address
					.getUser().getId()));

			if (user != null) {
				createAddress = addressRepository.save(address);
				log.info("Valid Customer Created Account.");
			} else {
				log.info("Invalid Customer");
				throw new Exception("Invalid Customer");
			}
		} else {
			throw new Exception("Invalid Customer");
		}

		return createAddress;
	}

	public AddressDetails createAddressWithoutUserValidation(
			AddressDetails address) throws Exception {
		AddressDetails createdAddress = null;

		log.info("User Validation Failed. Creating User Address without validation.");

		createdAddress = addressRepository.save(address);

		return createdAddress;
	}

	public AddressDetails getAddress(long id) {
		return addressRepository.findOne(id);
	}

	public void updateAddress(AddressDetails address) {
		addressRepository.save(address);
	}

	public void deleteAddress(Long id) {
		addressRepository.delete(id);
	}

	public Page<AddressDetails> getAllAddressByPage(Integer page, Integer size) {
		Page<AddressDetails> pageOfCustomers = addressRepository
				.findAll(new PageRequest(page, size));
		// example of adding to the /metrics
		if (size > 50) {
			counterService.increment("com.chinikom.getAll.largePayload");
		}
		return pageOfCustomers;
	}

	public List<AddressDetails> getAllAddresses() {
		Iterable<AddressDetails> pageOfAddresses = addressRepository.findAll();
		List<AddressDetails> addresses = new ArrayList<AddressDetails>();
		for (AddressDetails address : pageOfAddresses) {
			addresses.add(address);
		}
		log.info("In Real Service getAllAddresses size :" + addresses.size());

		return addresses;
	}
}
