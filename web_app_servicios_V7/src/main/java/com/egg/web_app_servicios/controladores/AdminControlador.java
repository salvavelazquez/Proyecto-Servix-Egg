/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.service.ClienteService;
import com.egg.web_app_servicios.service.ProveedorService;
import com.egg.web_app_servicios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProveedorService proveedorService;
    
    @Autowired
    private ClienteService clienteService;
    
    
    
    
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
     return "panel.html";   
    }
    
   @PostMapping("/modificar_cliente/{id}")
public String ClienteModificar(@PathVariable String id, 
                       String nombre, 
                        String password, 
                       String password2,
                       String direccion, 
                       String barrio, 
                       String email, 
                       String telefono,
                       ModelMap modelo,
                       MultipartFile archivo) {
    
       
        
        try {
//            usuarioService.modificarUsuario(id, archivo, nombre, telefono, email, password, password2);
            clienteService.modificarClienteParaAdmin(id, archivo, nombre, telefono, email, barrio, direccion, password, password2);
            modelo.put("exito", "El usuario fue cargado correctamente");
            return "redirect:/cliente/todos_clientes";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "usuario_modificar.html";
        }
        
}

@PostMapping("/modificar_proveedor/{id}")
public String ProveedorModificar(@PathVariable String id, 
                       String nombre, 
                       String telefono, 
                       String tipo_servicio,
                       String descripcion,
                       String email,
                       String password,
                       String password2,
                       ModelMap modelo,                       
                       MultipartFile archivo) {
    
        
        try {
//            usuarioService.modificarUsuario(id, archivo, nombre, telefono, email, password, password2);
            proveedorService.modificarProveedorParaAdmin(id, archivo, nombre, telefono, email, tipo_servicio, descripcion, password, password2);
            modelo.put("exito", "El proveedor fue cargado exitosamente");
            return "redirect:/proveedor/todos_proveedores";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "usuario_modificar.html";
        }
        
    
}

@GetMapping("/eliminar_proveedor/{id}")
public String ProveedorEliminar(@PathVariable String id,
                                MultipartFile archivo,
                                ModelMap modelo) {                     

        try {
            proveedorService.eliminarProveedor(id, archivo);
            modelo.put("exito", "Proveedor eliminado");
            return "redirect:/proveedor/todos_proveedores";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "inicio.html";
        }
        
    
}

@GetMapping("/eliminar_cliente/{id}")
public String ClienteEliminar(@PathVariable String id,
                             MultipartFile archivo,
                              ModelMap modelo) {

    try {
        clienteService.eliminarCliente(id, archivo);
        modelo.put("exito", "Cliente eliminado");
        return "redirect:/cliente/todos_clientes";
    } catch (MiException ex) {
        modelo.put("error", ex.getMessage());
        return "inicio.html";
    }


}
}