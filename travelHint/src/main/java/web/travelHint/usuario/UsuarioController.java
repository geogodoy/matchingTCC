package web.travelHint.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.travelHint.usuario.dto.UsuarioAuthenticateDTO;
import web.travelHint.usuario.payload.UsuarioLogin;
import web.travelHint.usuario.payload.UsuarioRequest;
import web.travelHint.usuario.service.UsuarioService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public List<Usuario> listUsuarios(){
        return usuarioService.listUsuarios();
    }

    @GetMapping("/usuario/{id}")
    public Usuario findUsuario(@PathVariable(value = "id") long id){
        return usuarioService.findUsuario(id);
    }

    @GetMapping("/usuario/token/{id}")
    public Usuario findUsuarioToken(@PathVariable(value = "id") long id){
        return usuarioService.findUsuarioToken(id);
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioAuthenticateDTO> createUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest){
        Usuario usuario = usuarioService.createUsuario(usuarioRequest);

        return new ResponseEntity<UsuarioAuthenticateDTO>(UsuarioAuthenticateDTO.toDTO(usuario, "Bearer"), HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/usuario/{id}")
    public void deleteUsuario(@PathVariable(value = "id") long id){
        Usuario usuario = usuarioService.findUsuario(id);
        usuarioService.deleteUsuario(usuario);
    }

    @PutMapping("/usuario")
    public Usuario updateUsuario(@RequestBody Usuario usuario){
        return usuarioService.updateUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioAuthenticateDTO> loginUsuario(@RequestBody UsuarioLogin usuarioLogin, @RequestHeader String Authorization ){
        Usuario usuario = usuarioService.authenticate(usuarioLogin, Authorization);

        return new ResponseEntity<UsuarioAuthenticateDTO>(UsuarioAuthenticateDTO.toDTO(usuario, "Bearer"), HttpStatus.ACCEPTED);
    }


}
