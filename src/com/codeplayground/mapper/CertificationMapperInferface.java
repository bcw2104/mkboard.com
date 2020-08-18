package com.codeplayground.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.CertificationDTO;

@Mapper
public interface CertificationMapperInferface {

	public CertificationDTO selectOnebyId(String userId);

	public void insert(CertificationDTO certificationDTO);

	public void delete(CertificationDTO certificationDTO);
}
