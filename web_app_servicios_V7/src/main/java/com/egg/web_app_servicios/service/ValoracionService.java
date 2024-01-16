/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;


import com.egg.web_app_servicios.entidades.Valoracion;

import com.egg.web_app_servicios.repositorios.ValoracionRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValoracionService {
    
   @Autowired 
   private ValoracionRepositorio valoracionRepositorio;
  
    @Transactional
    public void crearValoracion(String comentario, Integer puntuacion){
           
      Valoracion valoracion = new Valoracion();
      
     
      valoracion.setComentario(comentario);
      valoracion.setComentario(comentario);
      
      valoracionRepositorio.save(valoracion);
     
  }
    
    @Transactional
    public void modificarValoracion(String id, String comentario, Integer puntuacion){
        
       
        Optional<Valoracion> respuesta = valoracionRepositorio.findById(id);
        
        if(respuesta.isPresent()){
           Valoracion valoracion = respuesta.get();
           
           valoracion.setComentario(comentario);
           valoracion.setPuntuacion(puntuacion);
                     
           valoracionRepositorio.save(valoracion);           
        }        
    }
    
    //OJO METODO VALIDAR RESPECTO DE LA PUNTUACION EJEMPLO NO MAYOR A 5 
    // EN EL CASO DE QUE SEA POR NUMERACION
    
}

