package com.h2.h2api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h2.h2api.Repository.UsuarioRepository;
import com.h2.h2api.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Prueba para guardar usuario")
    public void newUsuarioTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();


        Usuario usuarioSim = new Usuario();
        usuarioSim.setId("666-666-666");
        usuarioSim.setName("Boris");
        usuarioSim.setEmail("Boris@gmail.com");
        usuarioSim.setPassword("A21213a");
        usuarioSim.setPhone("569666666");
        usuarioSim.setCreated(LocalDate.parse("2023-09-29"));
        usuarioSim.setModified(LocalDate.parse("2023-09-29"));


        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId("666-666-666");
        usuarioEsperado.setName("Boris");
        usuarioEsperado.setEmail("Boris@gmail.com");
        usuarioEsperado.setPassword("A21213a");
        usuarioEsperado.setPhone("569666666");
        usuarioEsperado.setCreated(LocalDate.parse("2023-09-29"));
        usuarioEsperado.setModified(LocalDate.parse("2023-09-29"));

        Mockito.when(usuarioRepository.findUsuarioById("666-666-666")).thenReturn(Optional.of(usuarioEsperado));
        Mockito.when(usuarioRepository.save(usuarioSim)).thenReturn(usuarioSim);

        ResponseEntity<Object> respuesta = usuarioService.newUsuario(usuarioSim);
        Usuario usuarioRespuesta =  extracted(respuesta);

        assertEquals(usuarioEsperado.getEmail(),usuarioRespuesta.getEmail());

    }
    @Test
    @DisplayName("Buscar Persona")
    public void getUsuariosIdTest() throws Exception {

        Usuario usuarioSim = new Usuario();
        usuarioSim.setId("666-666");
        usuarioSim.setName("Boris");
        usuarioSim.setEmail("Boris@gmail.com");
        usuarioSim.setPassword("A21213a");
        usuarioSim.setPhone("569666666");

        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId("666-666");
        usuarioEsperado.setName("Boris");
        usuarioEsperado.setEmail("Boris@gmail.com");
        usuarioEsperado.setPassword("A21213a");
        usuarioEsperado.setPhone("569666666");

        String esperado = "{data=Optional[Usuario(id=666-666, name=Boris, email=Boris@gmail.com, password=A21213a, phone=569666666, created=null, modified=null, telefonos=null)], message=Usuario Encontrado}";

        Mockito.when(usuarioRepository.findUsuarioById("666-666")).thenReturn(Optional.of(usuarioSim));
        ResponseEntity<Object> resultado =  usuarioService.getUsuariosId("666-666");

        //assertEquals(usuarioEsperado.getId(), usuarioResultado.getId());
        assertEquals(esperado, resultado.getBody().toString());

    }

    @Test
    @DisplayName("Prueba de Eliminacion de Usuario")
    public void deleteUsuarioTest() throws Exception {
        Usuario usuarioSim = new Usuario();
        usuarioSim.setId("666-666-666");
        usuarioSim.setName("Boris");
        usuarioSim.setEmail("Boris@gmail.com");
        usuarioSim.setPassword("A21213a");
        usuarioSim.setPhone("569666666");


        Mockito.when(usuarioRepository.existsById(usuarioSim.getId())).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(usuarioSim.getId());

        ResponseEntity<Object> usuarioEliminado = usuarioService.deleteUsuario(usuarioSim.getId());

        assertEquals(202, usuarioEliminado.getStatusCodeValue());

    }


    private Usuario extracted(ResponseEntity<Object> respuesta) {
        String json = respuesta.getBody().toString();
        Usuario usuario1 = new Usuario();
        Pattern pattern = Pattern.compile("\\{data=Usuario\\(id=(.*?), name=(.*?), email=(.*?), password=(.*?), phone=(.*?), created=(.*?), modified=(.*?)\\), message=(.*?)\\}");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            String id = matcher.group(1);
            String name = matcher.group(2);
            String email = matcher.group(3);
            String password = matcher.group(4);
            String phone = matcher.group(5);


            usuario1.setId(id);
            usuario1.setName(name);
            usuario1.setEmail(email);
            usuario1.setPassword(password);
            usuario1.setPhone(phone);
        }
        return usuario1;
    }



}
