/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;



import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Usuario;

import com.egg.web_app_servicios.enumeraciones.Rol;



import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorService {
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Autowired
    private ValidacionEmailService validacionEmailService;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private UsuarioService usuarioService;
    
//    @Autowired
//    private ImagenService imagenService;
    
  
    @Transactional
    public void crearProveedor(MultipartFile archivo, String nombre, String telefono, String email, String password, String password2, String tipo_servicio, String descripcion) throws MiException{
        
        validar(nombre, telefono, email, tipo_servicio);
        
        Usuario usuario = usuarioService.crearUsuario(archivo, nombre, telefono, email, password, password2);
       
        Proveedor proveedor = new Proveedor();
        
        
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        
        proveedor.setUsuario(usuario);
        
        proveedorRepositorio.save(proveedor);        
    }
    
    public List<Proveedor> listarProveedor(){
        
        List<Proveedor> proveedores = new ArrayList();
        
        proveedores = proveedorRepositorio.findAll();
        
        return proveedores;
    }
    
    public List<Proveedor> listarProveedorPorTipoServicio(String tipo_servicio) {
        
       
        List<Proveedor> proveedores = proveedorRepositorio.findByTipoServicio(tipo_servicio);

        return proveedores;
    }
    
    @Transactional
    public void modificarProveedor(String id, MultipartFile archivo, String nombre, String telefono, String email, String tipo_servicio, String descripcion, String password, String password2) throws MiException{
        
         
        
        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
        Proveedor proveedor =respuesta.getProveedor();
        Usuario usuario = usuarioService.modificarUsuario(id, archivo, nombre, telefono, email, password, password2);
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        proveedor.setUsuario(usuario);
       
       
        proveedorRepositorio.save(proveedor);
        }
    } 
    
    private void validar(String nombre, String telefono, String email, String tipo_servicio) throws MiException{
        
       
        
       if (!validacionEmailService.isEmailUnique(email)) {
        throw new MiException("El correo electrónico ya está registrado.");
    }
       
    
    }
    
    
    
    
    @Transactional(readOnly=true)
    public List<Proveedor> listarUsuarios() {

        List<Proveedor> proveedores = new ArrayList();

        proveedores = proveedorRepositorio.findAll();

        return proveedores;
    }
    
    
    public Proveedor getOne(String id){
        return proveedorRepositorio.getOne(id);
    }
    
    
    public Proveedor obtenerPorEmailProveedor(String email) {
        return proveedorRepositorio.buscarPorEmailProveedor(email);
    }
    
    
    @Transactional
    public void eliminarProveedor(String id, MultipartFile archivo) throws MiException{
        
        
        
        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
        Proveedor proveedor =respuesta.getProveedor();
        Usuario usuario = usuarioService.eliminarUsuario(id, archivo);
              
       
        proveedorRepositorio.delete(proveedor);
        }
    } 
    
    @Transactional
    public void modificarProveedorParaAdmin(String id, MultipartFile archivo, String nombre, String telefono, String email, String tipo_servicio, String descripcion, String password, String password2) throws MiException{
        
         
        
        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
        Proveedor proveedor =respuesta.getProveedor();
        Usuario usuario = usuarioService.modificarUsuarioParaAdmin(id, archivo, nombre, telefono, email, password, password2);
        proveedor.setTipo_servicio(tipo_servicio);
        proveedor.setDescripcion(descripcion);
        proveedor.setUsuario(usuario);
       
       
        proveedorRepositorio.save(proveedor);
        }
    } 
}