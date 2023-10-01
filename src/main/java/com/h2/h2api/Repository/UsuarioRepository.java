package com.h2.h2api.Repository;

import com.h2.h2api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findUsuarioByName(String name);

    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> findUsuarioById(String id);



    }
