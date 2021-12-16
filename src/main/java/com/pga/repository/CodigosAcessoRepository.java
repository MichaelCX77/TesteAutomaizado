package com.pga.repository;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.AccessCodesEntity;
import com.pga.entity.UsersEntity;

public interface CodigosAcessoRepository extends CrudRepository<AccessCodesEntity, String>{

//	CodigosAcessoEntity findByUsuario(UsuarioEntity usuario);

	AccessCodesEntity findByUserAndType(UsersEntity user, char type);

	AccessCodesEntity findByUserAndCodeAndStatus(UsersEntity user, String code, char status);
	
}
