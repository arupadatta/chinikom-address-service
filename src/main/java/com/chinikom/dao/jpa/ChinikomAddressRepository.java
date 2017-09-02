package com.chinikom.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinikom.domain.AddressDetails;

public interface ChinikomAddressRepository extends
		PagingAndSortingRepository<AddressDetails, Long> {
	AddressDetails findAddressByZipCode(String zipCode);

	@Override
	Page<AddressDetails> findAll(Pageable pageable);

}
