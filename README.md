# tfc
- v 0.19.0
    * Implantación limitada de xestión de erros mediante excepcións e páxinas personalizadas
        - Creación de páxinas personalizadas para os erros 404, 403 e 500
        - Lanzamento de excepción ResiduoNotFoundException no servizo do residuo para o método Residuo obtenerPorId(Long id) no caso de que non se atope o residuo solicitado
- v 0.18.0
    * Implantación limitada de solución de integración mediante API.
        - GET -> /api/residuos
- v 0.17.0
    * Implantación limitada de validacións no lado cliente
      - Revisión de campos no formulario de contacto
      - Credenciais correctas no inicio de sesión
      - Revisión de campos no rexistro dun novo residuo
      - Confirmar a nova contrasinal no apartado de editar a contrasinal
      - Revisión de campos na edición do perfil de usuario
- v 0.16.0
    * Mellora da interface web segundo as indicacións da Guía  Básica de Estilo (Pdte facer a memoria coa guia de stilo)
      - Mellora da usabilidade (Usability) ao ter un menú superior fixo que concentra todas as interaccións posibles do usuario en función dos seus permisos, un footer con acceso a toda a información sobre a plataforma así como a licencia de privacidade en uso e o emprego de JavaScript para otorgar a posibilidade de filtrar as filas pulsando nos cabeceiros das columnas das diferentes táboas, axudar a completar os formularios correctamente así como guiar dende un banner dinámico ao usuario para que se rexistre enviando un email.
      - Mellora da adaptabilidade (Responsive Design) ao variar o tamaño do contido en función do tamaño da pantalla.
      - Mellora da accesibilidade (Accessibility) ao empregar cores e símbolos para identificar os diferentes estados nos que pode estar un residuo ou reserva, así como os botóns que interactúan e varían os mesmos.
- v 0.15.0
    * Completado o código da arquitectura MVC adaptándoa ao proxecto
- v 0.14.0 
    * Readme con versions en forma de lista
    * Refactorizado o código para adaptar o PoC á idea do proxecto
- v 0.13.4 Correccion de erros detectados no PoC
    * Paxina de Login indica que aconteceu un erro se as credenciais son incorrectas
    * Paxina de Logout xa non indica que o carrito vai ser baleirado cando saias da aplicacion
    * Limitouse a puntuacion das valoracions a un valor maximo de 5 e puxose obligatoria xunto co usuario a ser valorado
- v 0.13.3 Proof of concept (PoC). Punto de partida para o TFC


Futuras versións:

- v 0.20.0
    * Implantación limitada de comunicacións vía correo electrónico
- v 0.21.0
    * Cambiar o Sistema de Xestión de Base de Datos
- v 0.22.0
    * Ampliar o xogo de datos de demostracións
- v 0.23.0
    * Despregar a aplicación
- v 0.24.0
    * Mellorar a experiencia na creación de rexistros de residuos
- v 0.25.0
    * Implantar a visualización dos residuos nun mapa
