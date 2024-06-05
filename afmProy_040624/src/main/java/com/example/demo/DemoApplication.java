package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Frecuencia;
import com.example.demo.domain.Residuo;
import com.example.demo.domain.Rol;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Incidencia;
import com.example.demo.services.CategoryService;
import com.example.demo.services.RutaService;
import com.example.demo.services.ResiduoService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.IncidenciaService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ResiduoService productoService,
			CategoryService categoryService,
			UsuarioService usuarioService,
			IncidenciaService valoracionesService,
			RutaService pedidoService) {
		return args -> {
			usuarioService.añadir(new Usuario(0L, "user", "1234", "00111222A", "vendedor@domain.com", 111222333L,
							"C/inventada, nº 1, 15000, Coruña", false, true, LocalDateTime.now(), Rol.USER,0d));
			usuarioService.añadir(new Usuario(0L, "negociante", "1234", "00111224A", "negociante@domain.com", 111222333L,
							"C/inventada, nº 2, 15000, Coruña", true, true, LocalDateTime.now(), Rol.NEGOCIANTE,0d));
			usuarioService.añadir(new Usuario(0L, "admin", "1234", "00111252A", "admin@domain.com", 111222333L,
							"C/inventada, nº 3, 15000, Coruña", false, true, LocalDateTime.now(), Rol.ADMIN,0d));
			categoryService.añadir(new Categoria(0L, "Plásticos"));
			categoryService.añadir(new Categoria(0L, "Metales"));
			categoryService.añadir(new Categoria(0L, "Orgánicos"));
			productoService.añadir(
					new Residuo(0L, "Tapones de botellas", "010101", 50d, "Kg", 100d, "Tapones de diferentes tamaños", "A Coruña", LocalDateTime.now(), false, true, false, Frecuencia.PUNTUAL,
							categoryService.obtenerPorNombre("Plásticos"),
							usuarioService.obtenerPorNombre("user"),
							null));
			productoService.añadir(
					new Residuo(0L, "Posos de cafe", "010102*", 50d, "Litros", 103d, "Húmedo", "A Coruña", LocalDateTime.now(), false, false, true, Frecuencia.SEMANAL,
							categoryService.obtenerPorNombre("Orgánicos"),
							usuarioService.obtenerPorNombre("user"),
							null));
			
			valoracionesService.añadir(new Incidencia(0L, 4, "Tenía todo preparado para llegar y recogerlo!",LocalDateTime.now(),
							usuarioService.obtenerPorNombre("user"),
							usuarioService.obtenerPorNombre("negociante")));
			valoracionesService.añadir(new Incidencia(0L, 5, "Vino a recogerlo al local puntualmente!!",LocalDateTime.now(),
							usuarioService.obtenerPorNombre("negociante"),
							usuarioService.obtenerPorNombre("user")));	
			
			
		};
	}

}
