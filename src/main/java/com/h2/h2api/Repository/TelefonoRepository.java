package com.h2.h2api.Repository;

import com.h2.h2api.entity.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, String> {

    Optional<Telefono> findTelefonoById(String id);
    @Query("SELECT t.id,t.contrycode,t.number FROM Telefono t  WHERE t.usuario = :idUsuario")
    List<Telefono> findTelefonosByUsuarioId(@Param("idUsuario") String idUsuario);

}
