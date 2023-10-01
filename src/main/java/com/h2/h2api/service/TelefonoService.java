package com.h2.h2api.service;

import com.h2.h2api.Repository.TelefonoRepository;
import com.h2.h2api.Repository.UsuarioRepository;
import com.h2.h2api.entity.Telefono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@Service
public class TelefonoService {
    HashMap<String, Object> datos;
    @Autowired
    private TelefonoRepository telefonoRepository;
    private UsuarioRepository usuarioRepository;



    @GetMapping

    public ResponseEntity<Object> newTelefono(Telefono telefono) throws Exception {
        datos = new HashMap<>();

        if (telefono.getUsuario().getId() ==  null) {
            datos.put("error", true);
            datos.put("message", "Debe indicar el usuario que corresponde el telefono");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );

        }

        if (telefono.getCitycode() == null || telefono.getContrycode() == null || telefono.getNumber() == null) {
            datos.put("error", true);
            datos.put("message", "Debe ingresar todos los datos");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        datos.put("Message", "Telefono creado exitosamente al usuario indicado");
        telefonoRepository.save(telefono);
        datos.put("data", telefono);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }



    public ResponseEntity<Object> getTelefonoUsuario( String usuarioId){
        List<Telefono> telefonos = this.telefonoRepository.findTelefonosByUsuarioId(usuarioId);
        datos = new HashMap<>();
        datos.put("data", telefonos);

        if ( telefonos.isEmpty()) {
            datos.put("error", true);
            datos.put("message", "No existe Telefonos asociados solicitado");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.NOT_FOUND
            );
        }else{
            datos.put("message", "Telefonos asociados");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.OK
            );
        }


    }
}
