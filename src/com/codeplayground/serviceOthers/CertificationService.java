package com.codeplayground.serviceOthers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.CertificationDTO;
import com.codeplayground.mapper.CertificationMapperInferface;

@Service
public class CertificationService{
	@Autowired
	private CertificationMapperInferface mapper;

	public CertificationDTO findOnebyEmail(String userEmail) {

		CertificationDTO certificationDTO = mapper.selectOnebyEmail(userEmail);
		if(certificationDTO != null) {
			if(certificationDTO.getExpiry().compareTo(new Timestamp(System.currentTimeMillis())) == 1 ){
				return certificationDTO;
			}
			mapper.delete(userEmail);
		}

		return null;
	}

	public void insert(CertificationDTO certificationDTO) {
		if(mapper.selectOnebyEmail(certificationDTO.getUserEmail()) == null) {
			mapper.insert(certificationDTO);
		}
		else {
			mapper.update(certificationDTO);
		}
	}

	public void delete(String userEmail) {
		mapper.delete(userEmail);
	}
}
