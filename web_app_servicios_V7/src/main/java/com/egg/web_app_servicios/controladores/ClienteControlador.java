/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;



import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.excepciones.MiException;

import com.egg.web_app_servicios.service.ClienteService;
import com.egg.web_app_servicios.service.UsuarioService;
import java.util.List;


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
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private UsuarioService usuarioService;
    

    
    
   

    
    @GetMapping("/cliente_formulario")
    public String registrarUusario(){
        return "cliente_formulario.html";
    }
    
 
   
    
    @PostMapping("/registro")
public String registro(@RequestParam String nombre, 
                       @RequestParam String password, 
                       @RequestParam String password2,
                       @RequestParam String direccion, 
                       @RequestParam String barrio, 
                       @RequestParam String email, 
                       @RequestParam String telefono,
                       ModelMap modelo,
                       MultipartFile archivo) {
    
        System.out.println("nombre: " + nombre + "password: " + password + "direccion: " + direccion + "barrio: " + barrio + "email: " + email);
        
        try {
            clienteService.crearCliente(archivo, nombre, telefono, email, barrio, direccion, password, password2);
            modelo.put("exito", "El usuario fue cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "cliente_formulario.html";
        }
        
    return "index.html";
}
    
 @PostMapping("/modificar_cliente/{id}")
public String ClienteModificar(@PathVariable String id, 
                        @RequestParam String nombre, 
                        String password, 
                       String password2,
                       @RequestParam String direccion, 
                       @RequestParam String barrio, 
                       String email, 
                       @RequestParam String telefono,
                       ModelMap modelo,
                       MultipartFile archivo) {
    
       
        
        try {
//            usuarioService.modificarUsuario(id, archivo, nombre, telefono, email, password, password2);
            clienteService.modificarCliente(id, archivo, nombre, telefono, email, barrio, direccion, password, password2);
            modelo.put("exito", "El usuario fue cargado correctamente");
            return "redirect:/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "usuario_modificar.html";
        }
        
    
}
 
@GetMapping("/eliminar_cliente/{id}")
public String ClienteEliminar(@PathVariable String id,
                             MultipartFile archivo,
                              ModelMap modelo) {

    try {
        clienteService.eliminarCliente(id, archivo);
        modelo.put("exito", "Cliente eliminado");
        return "redirect:/";
    } catch (MiException ex) {
        modelo.put("error", ex.getMessage());
        return "inicio.html";
    }

    
}

    @GetMapping("/todos_clientes")
    public String mostrarTodosLosClientes(ModelMap modelo) {
        List<Cliente> clientes = clienteService.listarCliente();
        modelo.addAttribute("clientes", clientes);
        return "panel.html"; // Ajusta el nombre de la vista según tu estructura de carpetas
    }



}
