/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.entidades.Imagen;
import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.enumeraciones.Rol;
import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.repositorios.ClienteRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import static org.hibernate.criterion.Projections.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenService imagenService;
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Transactional
    public Usuario crearUsuario(MultipartFile archivo, String nombre, String telefono, String email, String password, String pasword2)throws MiException {
        
        validar(nombre, telefono, email, password, password);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        
        Imagen imagen = imagenService.guardar(archivo);
        
        usuario.setImagen(imagen);     
        
        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario modificarUsuario(String id, MultipartFile archivo, String nombre, String telefono, String email, String password, String pasword2) throws MiException{
        
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta != null){
        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
//        usuario.setEmail(email);
//        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
//        usuario.setRol(Rol.USER);
        
        String idImagen = null;
        if(usuario.getImagen() != null){
            idImagen = usuario.getImagen().getId();
        }
        Imagen imagen = imagenService.actualizar(archivo, idImagen);
        
        usuario.setImagen(imagen);
       
         
        
        usuarioRepositorio.save(usuario);
        return usuario;
        } else{
            throw new MiException("No se encontró un usuario con el ID proporcionado: " + id);
        }
        
    }

  
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }
    
    @Transactional(readOnly=true)
    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList();
        
        usuarios = usuarioRepositorio.findAll();
        
        return usuarios;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }
    
     public void validar (String nombre, String telefono, String email, String password, String password2) throws MiException{
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vac�o");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException ("El telefono no puede ser nulo o estar vacio");
        }else if (telefono.length() < 3) {
            throw new MiException ("El telefono debe tenes mas de 9 digitos");
        }
        
        if (email.isEmpty()|| email == null){
            throw new MiException ("El email no puede ser nulo o estar vacio");
        }
              
               
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (usuarioRepositorio.existsByEmail(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
        
    }
    
     public byte[] obtenerImagenDeUsuario(String usuarioId) throws MiException {
        Usuario usuario = usuarioRepositorio.getOne(usuarioId);

        if (usuario != null && usuario.getImagen() != null) {
            return usuario.getImagen().getContenido();
        } else {
            throw new MiException("No se pudo encontrar la imagen del usuario con ID: " + usuarioId);
        }
    }

    
      @Transactional
    public Usuario eliminarUsuario(String id, MultipartFile archivo) throws MiException{
       
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta != null){
        Usuario usuario = respuesta.get();
        
        String idImagen = null;
        if(usuario.getImagen() != null){
            idImagen = usuario.getImagen().getId();
        }
        Imagen imagen = imagenService.eliminar(archivo, idImagen);
        
        usuarioRepositorio.delete(usuario);
        return usuario;
        } else{
            throw new MiException("No se encontró un usuario con el ID proporcionado: " + id);
        }
        
    }
     
    @Transactional
    public Usuario modificarUsuarioParaAdmin(String id, MultipartFile archivo, String nombre, String telefono, String email, String password, String pasword2) throws MiException{
        
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta != null){
        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
//        usuario.setEmail(email);
//        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
//        usuario.setRol(Rol.USER);
        
//        String idImagen = null;
//        if(usuario.getImagen() != null){
//            idImagen = usuario.getImagen().getId();
//        }
//        Imagen imagen = imagenService.actualizar(archivo, idImagen);
//        
//        usuario.setImagen(imagen);
//          String idImagen = null;
//          Imagen imagen = usuario.getImagen();
//          usuario.setImagen(imagen);
         
        
        usuarioRepositorio.save(usuario);
        return usuario;
        } else{
            throw new MiException("No se encontró un usuario con el ID proporcionado: " + id);
        }
        
    }

    
}
