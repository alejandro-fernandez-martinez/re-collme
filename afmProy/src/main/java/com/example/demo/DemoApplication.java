package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Residuo;
import com.example.demo.domain.Rol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ResiduoService;
import com.example.demo.services.UsuarioService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ResiduoService residuoService,
			CategoryService categoryService,
			UsuarioService usuarioService) {
		return args -> {
			usuarioService.añadir(new Usuario(0L, "user", "1234", LocalDateTime.now(), "00111222A", "vendedor@domain.com", 111222333L, "C/inventada, nº 1, 15000, Coruña", Rol.USER, false, 0d, true));
			usuarioService.añadir(new Usuario(0L, "negociante", "1234", LocalDateTime.now(), "00111224A", "negociante@domain.com", 111222333L,"C/inventada, nº 2, 15000, Coruña", Rol.NEGOCIANTE, true, 0d, true));
			usuarioService.añadir(new Usuario(0L, "admin", "1234", LocalDateTime.now(), "00111252A", "admin@domain.com", 111222333L,"C/inventada, nº 3, 15000, Coruña", Rol.ADMIN, false, 0d, true));
			categoryService.añadir(new Categoria(0L, "Plásticos"));
			categoryService.añadir(new Categoria(0L, "Metales"));
			categoryService.añadir(new Categoria(0L, "Orgánicos"));
			residuoService.añadir(
					new Residuo(0L, "Tapones de botellas", "Tapones de diferentes tamaños", "010101",  LocalDateTime.now(), 50L, 100L,"Calle 1", 3L, 15000L, "A Coruña", "A Coruña", false, usuarioService.obtenerPorNombre("negociante"), false, usuarioService.obtenerPorNombre("negociante"), false, false, categoryService.obtenerPorNomCat("Plásticos"), usuarioService.obtenerPorNombre("user")));
			residuoService.añadir(
					new Residuo(0L, "Posos de cafe", "Húmedo", "010102*", LocalDateTime.now(), 51L, 103L, "Calle 2", 4L, 15000L, "A Coruña", "A Coruña", false, usuarioService.obtenerPorNombre("negociante"), false, usuarioService.obtenerPorNombre("negociante"), false, false, categoryService.obtenerPorNomCat("Orgánicos"), usuarioService.obtenerPorNombre("user")));
		};
	}

}
