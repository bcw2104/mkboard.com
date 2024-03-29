package com.mkboard.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.mkboard.entity.CertificationDTO;

@Mapper
public interface CertificationMapperInferface {

	public CertificationDTO selectOne(HashMap<String, Object>hashMap);

	public void insert(CertificationDTO certificationDTO);

	public void update(CertificationDTO certificationDTO);

	public void delete(String userEmail);
}
