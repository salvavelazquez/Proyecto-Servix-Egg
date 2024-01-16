/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.repositorios;

import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Solicitud;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepositorio extends JpaRepository<Solicitud, String> {
  

    
//   @Query("SELECT s FROM Solicitud s WHERE s.proveedor = :proveedor")
//    List<Solicitud> findSolicitudPorProveedor(@Param("proveedor") Proveedor proveedor);
    
}
