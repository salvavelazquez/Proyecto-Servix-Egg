/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.repositorios;


import com.egg.web_app_servicios.entidades.Valoracion;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ValoracionRepositorio extends JpaRepository<Valoracion,String>  {
    
//    @Query("SELECT v FROM Valoracion v WHERE v.proveedor.nombre = :nombre")
//    public List<Valoracion> buscarPorProveedor(@Param("nombre")String nombre);
//            
}
