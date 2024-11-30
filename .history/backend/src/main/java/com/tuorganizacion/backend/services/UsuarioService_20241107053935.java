import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tuorganizacion.backend.entity.Usuario;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(String nombre, String email) {
        // Verificar si el usuario ya existe por email
        Usuario usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente != null) {
            return usuarioExistente;  // Si el usuario ya existe, lo devolvemos
        }

        // Crear un nuevo usuario y guardarlo
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);

        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuario(String email) {
        return usuarioRepository.findByEmail(email);  // Buscar usuario por email
    }
}
