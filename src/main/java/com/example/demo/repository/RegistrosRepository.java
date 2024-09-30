package com.example.demo.repository;

import com.example.demo.model.Registros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.query.Procedure;

@Repository
public interface RegistrosRepository extends JpaRepository<Registros, Long> {

    @Query(value = "SELECT validar_registro(:contrasena) FROM dual", nativeQuery = true)
    Integer validar_contrasena(String contrasena);

    @Query(value = "SELECT prueba() FROM dual", nativeQuery = true)
    String pruebaPostman();

    @Query( value = "SELECT asignar_ranking(:puntuacion) FROM dual", nativeQuery = true)
    String asignar_ranking(String puntuacion);

    @Query( value = "SELECT n_ejemplo(:num_ventas) FROM dual", nativeQuery = true)
    String comision( int num_ventas );

}
