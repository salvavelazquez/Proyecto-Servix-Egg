/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.repositorios;

import com.egg.web_app_servicios.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
    @Query("SELECT n FROM Cliente n WHERE n.nombre = :nombre")
    public List<Cliente> buscarPorNombre(@Param("nombre")String nombre);

    @Query("SELECT e FROM Cliente e WHERE e.email = :email")
    List <Cliente> buscarPorEmail(@Param("email")String email);
    
    @Query("SELECT e FROM Cliente e WHERE e.email = :email")
    public Cliente buscarPorEmailCliente(@Param("email")String email);
    
    public boolean existsByEmail(String email);
    
}
