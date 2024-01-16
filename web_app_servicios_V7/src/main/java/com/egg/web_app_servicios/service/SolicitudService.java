/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios.service;

import com.egg.web_app_servicios.entidades.Cliente;
import com.egg.web_app_servicios.entidades.Proveedor;
import com.egg.web_app_servicios.entidades.Solicitud;
import com.egg.web_app_servicios.entidades.Usuario;
import com.egg.web_app_servicios.excepciones.MiException;
import com.egg.web_app_servicios.repositorios.ClienteRepositorio;
import com.egg.web_app_servicios.repositorios.ProveedorRepositorio;
import com.egg.web_app_servicios.repositorios.SolicitudRepositorio;
import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SolicitudService {
    
    
    @Autowired
    private SolicitudRepositorio solicitudRepositorio;
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    
    @Transactional
    public void crearSolicitud(String proveedorId, String clienteId,String estado, String mensaje, Date fecha){
        
        Cliente cliente = clienteRepositorio.getOne(clienteId);
        Proveedor proveedor = proveedorRepositorio.getOne(proveedorId);
        
        
        
        Solicitud solicitud = new Solicitud();
        
        solicitud.setEstado(estado);
        solicitud.setMensaje(mensaje);
        solicitud.setFecha_solicitud(fecha);
        solicitud.setCliente(cliente);
        solicitud.setProveedor(proveedor);
       
        
        solicitudRepositorio.save(solicitud);
        
    }
    
    @Transactional
    public void modificarSolicitud(String id, String proveedorId, String clienteId,String estado, String mensaje, Date fecha) throws MiException{

        Usuario respuesta = usuarioRepositorio.getOne(id);
        
        if(respuesta != null){
            Solicitud solicitud = respuesta.getSolicitud();
            solicitud.setEstado(estado);
        
        solicitudRepositorio.save(solicitud);
        }
    }

//    public List<Solicitud> obtenerSolicitudesPorProveedor(String proveedorId) {
//        
//        List<Solicitud> solicitudes = new ArrayList();
//        
//        solicitudes = solicitudRepositorio.findSolicitudPorProveedor(proveedorId);
//        
//        
//        
//        
//        
//       
//        
//        return solicitudes;
//    }
    
    
}
