/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.repositorios;

import com.egg.web_app_servicios.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("SELECT e FROM Usuario e WHERE e.email = :email")
    public Usuario buscarPorEmail(@Param("email")String email);
    
    public boolean existsByEmail(String email);
}

//@Query("SELECT p FROM Persona p WHERE (:nombre IS NULL OR p.nombre LIKE %:nombre%) AND(:edad IS NULL OR p.edad = :edad)")
//    public List<Persona> findAllByNombreOrEdad(String nombre, Integer edad);