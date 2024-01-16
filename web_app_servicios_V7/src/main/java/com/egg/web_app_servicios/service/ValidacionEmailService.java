
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.repositorios.ClienteRepositorio;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacionEmailService {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    public boolean isEmailUnique(String email) {
        // Verificar si el correo electrónico no está registrado ni como cliente ni como proveedor
        return !clienteRepositorio.existsByEmail(email) && !proveedorRepositorio.existsByEmail(email);
    }
    
    
}
