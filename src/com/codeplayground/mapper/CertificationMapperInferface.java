package com.codeplayground.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.codeplayground.entity.CertificationDTO;

@Mapper
public interface CertificationMapperInferface {

	public CertificationDTO selectOnebyEmail(String userEmail);

	public void insert(CertificationDTO certificationDTO);

	public void update(CertificationDTO certificationDTO);

	public void delete(String userEmail);
}
