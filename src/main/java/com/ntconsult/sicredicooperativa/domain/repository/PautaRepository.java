package com.ntconsult.sicredicooperativa.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ntconsult.sicredicooperativa.domain.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>{
	
	Optional<Pauta> findByCodigo(String codigo);
	
}
