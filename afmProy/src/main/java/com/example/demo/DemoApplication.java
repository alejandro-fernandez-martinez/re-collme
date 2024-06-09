package com.example.demo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Incidencia;
import com.example.demo.domain.Residuo;
import com.example.demo.domain.Rol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.CategoryService;
import com.example.demo.services.IncidenciaService;
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
			UsuarioService usuarioService,
			IncidenciaService incidenciaService) {
		return args -> {
			usuarioService.añadir(new Usuario(0L, "user", "1234", LocalDateTime.now(), "00111222A", "vendedor@domain.com", 111222333L, "C/inventada, nº 1, 15000, Coruña", Rol.USER, false, 0, true));
			usuarioService.añadir(new Usuario(0L, "negociante", "1234", LocalDateTime.now(), "00111224A", "negociante@domain.com", 111222333L,"C/inventada, nº 2, 15000, Coruña", Rol.NEGOCIANTE, true, 0, true));
			usuarioService.añadir(new Usuario(0L, "admin", "1234", LocalDateTime.now(), "00111252A", "admin@domain.com", 111222333L,"C/inventada, nº 3, 15000, Coruña", Rol.ADMIN, false, 0, true));
			usuarioService.añadir(new Usuario(0L, "gestor", "1234", LocalDateTime.now(), "00111252A", "gestor@domain.com", 111222333L,"C/inventada, nº 3, 15000, Coruña", Rol.NEGOCIANTE, true, 0, true));
			// Crear la incidencia con un único comentario inicial
// Crear dos incidencias creadas por el usuario contra el negociante
incidenciaService.añadirData(new Incidencia(
    0L,
    LocalDateTime.now(),
    "Comentario de la incidencia 1 del usuario al negociante",
    Arrays.asList(
        "Este es el primer comentario de la incidencia 1 del usuario al negociante.",
        "El usuario está reportando un problema con el producto.",
        "Es importante solucionar esta incidencia lo antes posible."
    ),
    false,
    0,
    usuarioService.obtenerPorNombre("user"),
    usuarioService.obtenerPorNombre("negociante")
));

incidenciaService.añadirData(new Incidencia(
    0L,
    LocalDateTime.now(),
    "Comentario de la incidencia 2 del usuario al negociante",
    Arrays.asList(
        "Aquí está el primer comentario de la incidencia 2 del usuario al negociante.",
        "El usuario necesita ayuda con el servicio ofrecido.",
        "La situación parece ser urgente."
    ),
    false,
    0,
    usuarioService.obtenerPorNombre("user"),
    usuarioService.obtenerPorNombre("negociante")
));

// Crear dos incidencias creadas por el negociante contra el usuario
incidenciaService.añadirData(new Incidencia(
    0L,
    LocalDateTime.now(),
    "Comentario de la incidencia 1 del negociante al usuario",
    Arrays.asList(
        "Este es el primer comentario de la incidencia 1 del negociante al usuario.",
        "El negociante está respondiendo a una consulta del usuario.",
        "Se está proporcionando una solución al problema planteado por el usuario."
    ),
    false,
    0,
    usuarioService.obtenerPorNombre("negociante"),
    usuarioService.obtenerPorNombre("user")
));

incidenciaService.añadirData(new Incidencia(
    0L,
    LocalDateTime.now(),
    "Comentario de la incidencia 2 del negociante al usuario",
    Arrays.asList(
        "Aquí está el primer comentario de la incidencia 2 del negociante al usuario.",
        "El negociante está informando sobre el estado de un pedido.",
        "Se está ofreciendo una compensación por la demora en la entrega."
    ),
    false,
    0,
    usuarioService.obtenerPorNombre("negociante"),
    usuarioService.obtenerPorNombre("user")
));



			categoryService.añadir(new Categoria(0L, "Plásticos"));
			categoryService.añadir(new Categoria(0L, "Metales"));
			categoryService.añadir(new Categoria(0L, "Orgánicos"));
			// Residuos "negociante"
			residuoService.añadirData(
    			new Residuo(0L, "Mondas de platano", "Húmedo", "010102*", LocalDateTime.of(2024, Month.JANUARY, 5, 10, 0), 51L, 103L, 
							"Calle 2", 4L, 15000L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null, categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			residuoService.añadirData(
				new Residuo(0L, "Chatarra de hierro", "Metálico", "020202*", LocalDateTime.of(2024, Month.FEBRUARY, 12, 11, 0), 100L, 200L, 
							"Calle 3", 5L, 15001L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			residuoService.añadirData(
				new Residuo(0L, "Restos de poda", "Vegetal", "030303*", LocalDateTime.of(2024, Month.MARCH, 20, 14, 30), 75L, 150L, 
							"Calle 6", 8L, 15004L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			residuoService.añadirData(
				new Residuo(0L, "Botellas de vidrio", "Vidrio", "040404*", LocalDateTime.of(2024, Month.APRIL, 15, 9, 45), 120L, 250L, 
							"Calle 7", 9L, 15005L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			residuoService.añadirData(
				new Residuo(0L, "Electrónica obsoleta", "Electrónico", "050505*", LocalDateTime.of(2024, Month.MAY, 8, 16, 0), 200L, 100L, 
							"Calle 8", 10L, 15006L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			// Añadir residuos para el usuario "user"
			residuoService.añadirData(
				new Residuo(0L, "Restos de comida", "Orgánico", "030303*", LocalDateTime.of(2024, Month.JANUARY, 25, 12, 0), 60L, 120L, 
							"Calle 4", 6L, 15002L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Botellas de plástico", "Plástico", "040404*", LocalDateTime.of(2024, Month.FEBRUARY, 28, 13, 0), 30L, 90L, 
							"Calle 5", 7L, 15003L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Papel y cartón", "Papel", "060606*", LocalDateTime.of(2024, Month.MARCH, 18, 11, 15), 45L, 80L, 
							"Calle 9", 11L, 15007L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Aceite usado", "Aceite", "070707*", LocalDateTime.of(2024, Month.APRIL, 22, 17, 30), 25L, 70L, 
							"Calle 10", 12L, 15008L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Ropa vieja", "Textil", "080808*", LocalDateTime.of(2024, Month.JUNE, 2, 15, 0), 80L, 160L, 
							"Calle 11", 13L, 15009L, "A Coruña", "A Coruña", false, null, false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("user"),null));
			// Añadir residuos para el usuario "negociante" con solicitado = true y nombreSolicitante = "user"
			residuoService.añadirData(
				new Residuo(0L, "Maderas (se lo autoasignó el negociante)", "Seco", "090909*", LocalDateTime.of(2024, Month.JANUARY, 10, 14, 0), 150L, 300L, 
							"Calle 12", 14L, 15010L, "A Coruña", "A Coruña", true, "negociante", false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			residuoService.añadirData(
				new Residuo(0L, "Neumáticos usados (se lo autoasignó el negociante)", "Goma", "100101*", LocalDateTime.of(2024, Month.FEBRUARY, 20, 10, 30), 80L, 140L, 
							"Calle 13", 15L, 15011L, "A Coruña", "A Coruña", true, "negociante", false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			residuoService.añadirData(
				new Residuo(0L, "Vidrios rotos (se lo autoasignó el negociante)", "Vidrio", "110202*", LocalDateTime.of(2024, Month.MARCH, 25, 16, 0), 110L, 220L, 
							"Calle 14", 16L, 15012L, "A Coruña", "A Coruña", true, "negociante", false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("negociante"),null));

			// Añadir residuos para el usuario "user" con solicitado = true y nombreSolicitante = "user"
			residuoService.añadirData(
				new Residuo(0L, "Restos de construcción (se lo asignó el user)", "Construcción", "120303*", LocalDateTime.of(2024, Month.APRIL, 18, 11, 45), 300L, 500L, 
							"Calle 15", 17L, 15013L, "A Coruña", "A Coruña", true, "negociante", false, null, 
							false, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Residuos electrónicos (se lo asignó el user)", "Electrónico", "130404*", LocalDateTime.of(2024, Month.JUNE, 5, 9, 15), 250L, 300L, 
							"Calle 16", 18L, 15014L, "A Coruña", "A Coruña", true, "negociante", false, null, 
							false, false,null, categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("user"),null));
			// Añadir residuos para el usuario "user" con solicitado = true, nombreSolicitante = "negociante", reservado = true, nombreGestor = "negociante"
			residuoService.añadirData(
				new Residuo(0L, "Residuos de papel (solicitado y asignado al negociante)", "Papel y cartón", "060606*", LocalDateTime.of(2024, Month.JANUARY, 15, 10, 0), 120L, 250L, 
							"Calle 17", 19L, 15015L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							false, false, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Botellas de plástico (solicitado y asignado al negociante)", "Plástico", "040404*", LocalDateTime.of(2024, Month.FEBRUARY, 10, 14, 30), 200L, 300L, 
							"Calle 18", 20L, 15016L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							false, false, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Residuos de comida (solicitado y asignado al negociante)", "Orgánico", "030303*", LocalDateTime.of(2024, Month.MARCH, 5, 11, 0), 150L, 200L, 
							"Calle 19", 21L, 15017L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							false, false, null,categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("user"),null));
			// Añadir residuos para el usuario "user" con solicitado = true, nombreSolicitante = "negociante", reservado = true, nombreGestor = "negociante", y recogido = true
			residuoService.añadirData(
				new Residuo(0L, "Residuos de vidrio (recogido)", "Vidrio", "070707*", LocalDateTime.of(2024, Month.APRIL, 20, 10, 0), 130L, 270L, 
							"Calle 20", 22L, 15018L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							true, false,null, categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Chatarra metálica (recogido)", "Metálico", "080808*", LocalDateTime.of(2024, Month.MAY, 10, 15, 30), 220L, 340L, 
							"Calle 21", 23L, 15019L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							true, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Desechos textiles (recogido)", "Textil", "090909*", LocalDateTime.of(2024, Month.JUNE, 25, 12, 0), 180L, 290L, 
							"Calle 22", 24L, 15020L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							true, false, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("user"),null));
			// Añadir residuos para el usuario "user" con solicitado = true, nombreSolicitante = "negociante", reservado = true, nombreGestor = "negociante", recogido = true y bloqueado = true
			residuoService.añadirData(
				new Residuo(0L, "Residuos electrónicos (bloqueado)", "Electrónico", "100101*", LocalDateTime.of(2024, Month.APRIL, 30, 14, 0), 150L, 300L, 
							"Calle 23", 25L, 15021L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							true, true, null,categoryService.obtenerPorNomCat("Metales"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Residuos plásticos (bloqueado)", "Plástico", "110202*", LocalDateTime.of(2024, Month.MAY, 15, 16, 30), 200L, 350L, 
							"Calle 24", 26L, 15022L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							true, true, null,categoryService.obtenerPorNomCat("Plásticos"), 
							usuarioService.obtenerPorNombre("user"),null));

			residuoService.añadirData(
				new Residuo(0L, "Restos de poda (bloqueado)", "Vegetal", "120303*", LocalDateTime.of(2024, Month.JUNE, 10, 10, 0), 180L, 250L, 
							"Calle 25", 27L, 15023L, "A Coruña", "A Coruña", true, "negociante", true, "negociante", 
							true, true, null,categoryService.obtenerPorNomCat("Orgánicos"), 
							usuarioService.obtenerPorNombre("user"),null));

						};
	}

}
