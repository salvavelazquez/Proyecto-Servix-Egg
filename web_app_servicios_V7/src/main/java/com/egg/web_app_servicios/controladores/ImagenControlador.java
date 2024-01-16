/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.controladores;

import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.service.ClienteService;
import com.egg.web_app_servicios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
   private UsuarioService usuarioService;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenCliente (@PathVariable String id){
       Usuario usuario = usuarioService.getOne(id);
        System.out.println(usuario.getEmail());
            System.out.println(usuario.getId());
       byte[] imagen= usuario.getImagen().getContenido();
       
       HttpHeaders headers;
        headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
       
        
       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }
    
     @GetMapping("/foto/{imagenId}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable String imagenId) {
        try {
            byte[] imagenBytes = usuarioService.obtenerImagenDeUsuario(imagenId); // Asegúrate de tener este método en tu servicio
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el tipo MIME de tus imágenes

            return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
        } catch (MiException e) {
            // Maneja la excepción según tus necesidades
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

