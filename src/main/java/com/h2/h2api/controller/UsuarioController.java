package com.h2.h2api.controller;

import com.h2.h2api.service.UsuarioService;
import com.h2.h2api.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path= "api/v1/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getUsuarios(){
        return this.usuarioService.getUsuarios();
    }

    @PostMapping
    public ResponseEntity<Object> registrarUsuario(@RequestBody @Valid Usuario usuario) throws Exception {
            return ResponseEntity.ok(this.usuarioService.newUsuario(usuario));
    }


    @PutMapping
    public ResponseEntity<Object> actualizarUsuario(@RequestBody Usuario usuario) throws Exception {
        return ResponseEntity.ok(this.usuarioService.newUsuario(usuario));

    }

    @DeleteMapping(path = "{usuarioId}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable("usuarioId") String id) throws Exception{
        return ResponseEntity.ok(this.usuarioService.deleteUsuario(id));

    }

}


