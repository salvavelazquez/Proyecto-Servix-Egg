/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.entidades.Imagen;

import com.egg.web_app_servicios.entidades.Usuario;


import com.egg.web_app_servicios.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.web_app_servicios.repositorios.ClienteRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import java.util.Optional;




import org.springframework.web.multipart.MultipartFile;


@Service
public class ClienteService{
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private ValidacionEmailService validacionEmailService;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ImagenService imagenService;
   
    @Transactional
    public void crearCliente(MultipartFile archivo, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
        Usuario usuario = usuarioService.crearUsuario(archivo, nombre, telefono, email, password, password2);
        
        validar(nombre, telefono, email, barrio, direccion, password, password2);
        
        Cliente cliente = new Cliente();
       
        cliente.setDireccion(direccion);
        cliente.setBarrio(barrio);
        cliente.setUsuario(usuario);
                   
        clienteRepositorio.save(cliente);
    }
    
    public List<Cliente> listarCliente(){
        
        List<Cliente> clientes = new ArrayList();
        
        clientes = clienteRepositorio.findAll();
        
        return clientes;
    }
    
    @Transactional
    public void modificarCliente(String id, MultipartFile archivo, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
               
        
        
        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
        Cliente cliente =respuesta.getCliente();
        Usuario usuario = usuarioService.modificarUsuario(id, archivo, nombre, telefono, email, password, password2);
        cliente.setBarrio(barrio);
        cliente.setDireccion(direccion);
        cliente.setUsuario(usuario);
        
       
        clienteRepositorio.save(cliente);
        }
    }
    
    
    public void eliminarClientePorEmail(String email) throws MiException {
    // Buscar el cliente por su email
    List<Cliente> clientes = clienteRepositorio.buscarPorEmail(email);

    if (!clientes.isEmpty()) {
        // Si se encontraron clientes con ese email, eliminar el primero (o manejar según tus necesidades)
        Cliente cliente = clientes.get(0);
        clienteRepositorio.delete(cliente);
    } else {
        // Si la lista está vacía, el cliente no existe, lanzar una excepción o manejar de acuerdo a tus necesidades
        throw new MiException("Cliente con email " + email + " no encontrado");
    }

}
    
    
    public void validar (String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
        
       
        
       if (!validacionEmailService.isEmailUnique(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
     
    }
    
 
    
    
    @Transactional(readOnly=true)
    public List<Cliente> listarUsuarios() {

        List<Cliente> clientes = new ArrayList();

        clientes = clienteRepositorio.findAll();

        return clientes;
    }

    public Cliente getOne(String id){
        return clienteRepositorio.getOne(id);
    }
    
    
    public Cliente obtenerPorEmailCliente(String email) {
        return clienteRepositorio.buscarPorEmailCliente(email);
    }

    @Transactional
    public void eliminarCliente(String id, MultipartFile archivo) throws MiException{
        
        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
        Cliente cliente =respuesta.getCliente();
        
        Usuario usuario = usuarioService.eliminarUsuario(id, archivo);
            byte[] imagen = usuarioService.obtenerImagenDeUsuario(id);
        imagenService.eliminar(archivo, id);
       clienteRepositorio.delete(cliente);
        
        }
    }
    
    @Transactional
    public void modificarClienteParaAdmin(String id, MultipartFile archivo, String nombre, String telefono, String email, String barrio, String direccion, String password, String password2) throws MiException{
               
        
        
        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
        Cliente cliente =respuesta.getCliente();
        Usuario usuario = usuarioService.modificarUsuarioParaAdmin(id, archivo, nombre, telefono, email, password, password2);
        cliente.setBarrio(barrio);
        cliente.setDireccion(direccion);
        cliente.setUsuario(usuario);
        
       
        clienteRepositorio.save(cliente);
        }
    }
    
}
