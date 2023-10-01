package com.h2.h2api.controller;

import com.h2.h2api.entity.Telefono;
import com.h2.h2api.service.TelefonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "api/v1/telefono")
@Validated
public class TelefonoController {
    @Autowired
    TelefonoService telefonoService;

    @PostMapping
    public ResponseEntity<Object> registraTelefono(@RequestBody Telefono telefono) throws Exception {
        return ResponseEntity.ok(this.telefonoService.newTelefono(telefono));
    }


}
