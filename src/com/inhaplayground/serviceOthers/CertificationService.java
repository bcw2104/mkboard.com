package com.inhaplayground.serviceOthers;

import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.CertificationDTO;
import com.inhaplayground.mapper.CertificationMapperInferface;

@Service
public class CertificationService{
	@Autowired
	private CertificationMapperInferface mapper;

	public CertificationDTO findOne(String userEmail, String key) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userEmail", userEmail);
		hashMap.put("key", key);

		CertificationDTO certificationDTO = mapper.selectOne(hashMap);
		if(certificationDTO != null) {
			if(certificationDTO.getExpiry().compareTo(new Timestamp(System.currentTimeMillis())) == 1 ){
				return certificationDTO;
			}
			mapper.delete(certificationDTO.getUserEmail());
		}

		return null;
	}

	public void insert(CertificationDTO certificationDTO) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userEmail", certificationDTO.getUserEmail());

		if(mapper.selectOne(hashMap) == null) {
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
