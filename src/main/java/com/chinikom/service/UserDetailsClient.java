package com.chinikom.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinikom.domain.UserDetails;

@FeignClient("chinikom-user-service")
interface UserDetailsClient {

	@RequestMapping(method = RequestMethod.GET, value = "/chinikom-user-service/v1/users/all")
	List<UserDetails> getUsersDetails();

	@RequestMapping(method = RequestMethod.GET, value = "/chinikom-user-service/v1/users/simple/{id}")
	UserDetails getUserDetails(@PathVariable("id") Long id);

}