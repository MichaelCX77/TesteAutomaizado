package com.pga.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.ConfigDataEntity;

public interface ConfigDataRepository extends CrudRepository<ConfigDataEntity, String>{

	List<ConfigDataEntity> findAllByType(String type);	

	ConfigDataEntity findByTypeAndName(String type, String name);
}
