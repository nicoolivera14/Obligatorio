import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tuorganizacion.backend.entity.Usuario;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarUsuario(
            @RequestParam String nombre,
            @RequestParam String email) {
        
        try {
            Usuario usuarioGuardado = usuarioService.guardarUsuario(nombre, email);
            if (usuarioGuardado != null) {
                return ResponseEntity.ok("Usuario guardado con éxito");
            } else {
                return ResponseEntity.status(400).body("El usuario ya existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar el usuario");
        }
    }

    @GetMapping("/obtener")
    public ResponseEntity<Usuario> obtenerUsuario(@RequestParam String email) {
        try {
            Usuario usuario = usuarioService.obtenerUsuario(email);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(404).body(null);  // Si no existe, devolver 404
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
