package com.example.demo.controller;

import com.example.demo.model.Registros;
import com.example.demo.repository.RegistrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private RegistrosRepository repo;

    @GetMapping()
    public String listar() {
        //return service.listar();
        return "CONECTADO";
    };

    @GetMapping("/registros")
    public List<Registros> obtenerRegistros(){
      return repo.findAll();
    };

    @PostMapping("/guardar")
    public String guardarRegistro(@RequestBody Registros r){
        if( validacionContrasena( r.getContrasena() ) ){
            repo.save(r);
            return "Usuario Guardado";
        }
        return "Usuario No Guardado, Contraseña No Cumple con los Requisitos";
    };

    @PutMapping("/editar/{id}")
    public String editarRegistro(@PathVariable Long id, @RequestBody Registros r){
        Optional<Registros> registro = repo.findById(id);

        if (registro.isPresent()) {
            Registros registroExistente = registro.get();
            registroExistente.setUsuario(r.getUsuario());

            if ( validacionContrasena( r.getContrasena() )){

                registroExistente.setContrasena( r.getContrasena() );
            }else{
                return "Contraseña No Cumple con los Requisitos";
            }

            repo.save(registroExistente);
            return "Usuario editado";
        } else {
            return "Registro no encontrado";
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarRegistro(@PathVariable Long id) {
        Optional<Registros> delete = repo.findById(id);

        if (delete.isPresent()) {
            repo.delete( delete.get() );
            return "Usuario eliminado";
        } else {
            return "Registro no encontrado";
        }
    }

    public boolean validacionContrasena(String contrasena) {
        return repo.validar_contrasena(contrasena) == 1;
    }

    @GetMapping("/registros/prueba")
    public String obtenerSaldo() {
        return repo.pruebaPostman();
    }

    @GetMapping("/ranking/{letter}")
    public String obtenerRanking(@PathVariable String letter){
        return repo.asignar_ranking( letter.toUpperCase() );
    }

    @GetMapping("/comision/{num_ventas}")
    public String obtenerComision(@PathVariable int num_ventas){
        return repo.comision( num_ventas );
    }
}
