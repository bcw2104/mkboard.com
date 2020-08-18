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

	public CertificationDTO findOnebyId(String userId) {
		CertificationDTO certificationDTO = mapper.selectOnebyId(userId);
		if(certificationDTO != null) {
			if(certificationDTO.getExpiry().compareTo(new Timestamp(System.currentTimeMillis())) == -1 ){
				return certificationDTO;
			}
			mapper.delete(certificationDTO);
		}
		return null;
	}

	public void insert(CertificationDTO certificationDTO) {
		try {
		mapper.insert(certificationDTO);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(CertificationDTO certificationDTO) {
		mapper.delete(certificationDTO);
	}
}
