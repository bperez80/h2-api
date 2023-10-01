package com.h2.h2api.service;

import com.h2.h2api.Repository.UsuarioRepository;
import com.h2.h2api.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    HashMap<String, Object> datos;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
    }

    @GetMapping
    public List<Usuario> getUsuarios(){
        return this.usuarioRepository.findAll();
    }

    @GetMapping
    public ResponseEntity<Object> getUsuariosId(String id){
        Optional <Usuario> respuesta = this.usuarioRepository.findUsuarioById(id);
        datos = new HashMap<>();
        datos.put("data", respuesta);

        if ( respuesta.isEmpty()) {
            datos.put("error", true);
            datos.put("message", "No existe usuario solicitado");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.NOT_FOUND
            );
        }else{
            datos.put("message", "Usuario Encontrado");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.OK
            );
        }



    }

    public ResponseEntity<Object> newUsuario(@Valid Usuario usuario) throws Exception {
        Optional <Usuario> res = usuarioRepository.findUsuarioByName((usuario.getName()));
        Optional <Usuario> resEmail = usuarioRepository.findUsuarioByEmail(usuario.getEmail());
        datos = new HashMap<>();

        if (usuario.getName() == null || usuario.getName().isEmpty()) {
            datos.put("error", true);
            datos.put("message", "El nombre no puede ser nulo o vacío");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );

        }

        if (usuario.getPassword() == null || usuario.getPassword().isEmpty() || !usuario.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]*$")) {
            datos.put("error", true);
            datos.put("message", "La contraseña no puede ser nula o vacía, el formato de la contraseña debe cumplir con:  Una Mayuscula mas letras minúsculas y dos numeros ");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (!usuario.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            datos.put("error", true);
            datos.put("message", "El correo electrónico no es válido");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        if(resEmail.isPresent() && usuario.getId() == null){
            datos.put("error", true);
            datos.put("message", "Ya existe el correo");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );

        }

        if(res.isPresent() && usuario.getId() == null){
            datos.put("error", true);
            datos.put("message", "Ya existe un usuario con este nombre");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );

        }

        if(usuario.getId() != null){
            datos.put("message", "Usuario actualizado con exitosamente");
            usuario.setModified(LocalDate.now());
            usuario.setCreated(usuarioRepository.findUsuarioById(usuario.getId()).get().getCreated());
        }else {
            datos.put("Message", "Usuario agregado exitosamente");
            usuario.setCreated(LocalDate.now());
        }
        usuarioRepository.save(usuario);
        datos.put("data", usuario);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public  ResponseEntity<Object> deleteUsuario(String id) throws Exception {
        datos = new HashMap<>();
        boolean existe = this.usuarioRepository.existsById(id);

        if(!existe){
            datos.put("data", id);
            datos.put("message", "Id de usuario no encontrado");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        usuarioRepository.deleteById(id);
        datos.put("message", "Usuario Eliminado");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );

    }

}
