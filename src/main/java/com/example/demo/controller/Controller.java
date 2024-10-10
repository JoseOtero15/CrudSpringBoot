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
        int resultCode = repo.validar_contrasena( r.getContrasena() );
        System.out.println("r.getContrasena() = " + r.getContrasena());
        if( resultCode == 1 ) {
            repo.save(r);
            return "Usuario Guardado";
        }
        return mensajeValidacionContrasena( resultCode );
    };

    @PutMapping("/editar/{id}")
    public String editarRegistro(@PathVariable Long id, @RequestBody Registros r){
        Optional<Registros> registro = repo.findById(id);

        if (registro.isPresent()) {
            Registros registroExistente = registro.get();
            registroExistente.setUsuario(r.getUsuario());

            int resultCode = repo.validar_contrasena( r.getContrasena() );

            if ( resultCode == 1 ){
                registroExistente.setContrasena( r.getContrasena() );
            }else{
                return mensajeValidacionContrasena( resultCode );
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

    public String mensajeValidacionContrasena(int resultCode){
        String mensaje;
        switch ( resultCode ){
            case 1:
                mensaje = "validado";
                break;
            case 2:
                mensaje = "Longitud de la contraseña invalida";
                break;
            case 3:
                mensaje = "La contraseña no contiene minusculas";
                break;
            case 4:
                mensaje = "La contraseña no contiene mayusculas";
                break;
            case 5:
                mensaje = "No contiene al menos un digito";
                break;
            case 6:
                mensaje = "No contiene al menos un caracter especial";
                break;
            default:
                mensaje = "Ocurrio un error";
                break;
        }
        return mensaje;
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
