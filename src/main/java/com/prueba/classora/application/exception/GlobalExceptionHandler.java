package com.prueba.classora.domain.exception;

import org.springframework.core.Ordered;
import org.springframework.core.codec.DecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /** JSON mal formado o cuerpo ilegible. */
    @ExceptionHandler(DecodingException.class)
    public Mono<ResponseEntity<String>> handleDecode(DecodingException ex) {
        log.warn("DecoderException: {}", ex.getMessage());
        return badRequest("Cuerpo JSON no válido: " + ex.getMessage());
    }

    /** Error de validación/binding en WebFlux (incluye @Valid, @RequestBody, etc.). */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleWebBind(WebExchangeBindException ex) {
        log.warn("WebExchangeBindException: {}", ex.getMessage());
        return badRequest("Error de validación: " + ex.getMessage());
    }

    /** Error de validación en escenarios no reactivos (por si apareciera). */
    @ExceptionHandler(BindException.class)
    public Mono<ResponseEntity<String>> handleBind(BindException ex) {
        log.warn("BindException: {}", ex.getMessage());
        return badRequest("Error de validación: " + ex.getMessage());
    }

    /**
     * JSON mal formado: lo lanza Jackson wrapped en ServerWebInputException o
     * en HttpMessageReadException antes de llegar al controller.
     */
    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<String>> handleInvalidJson(ServerWebInputException ex) {
        String reason = ex.getReason();  // suele contener el mensaje de Jackson
        log.warn("JSON inválido: {}", reason, ex);
        return badRequest("Cuerpo JSON no válido: " + reason);
    }

    /** Parámetros ilegales o reglas de negocio. */
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<String>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        return badRequest("Parámetros incorrectos: " + ex.getMessage());
    }

    /* -------------------------------------------------------------------------
     * 403 – FORBIDDEN
     * ---------------------------------------------------------------------- */

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<String>> handleForbidden(AccessDeniedException ex) {
        log.warn("AccessDeniedException");
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado"));
    }

    /* -------------------------------------------------------------------------
     * 500 – INTERNAL SERVER ERROR
     * ---------------------------------------------------------------------- */

    /** Problemas de IO genéricos (conexiones, ficheros, etc.). */
    @ExceptionHandler(IOException.class)
    public Mono<ResponseEntity<String>> handleIO(IOException ex) {
        log.error("IOException", ex);
        return internal("Error de entrada/salida: " + ex.getMessage());
    }

    /** Fallo TLS/SSL específico. */
    @ExceptionHandler(SSLHandshakeException.class)
    public Mono<ResponseEntity<String>> handleSSL(SSLHandshakeException ex) {
        log.error("SSLHandshakeException", ex);
        return internal("Error SSL al conectar con el servicio externo: " + ex.getMessage());
    }

    /** Fallback para cualquier otra excepción no controlada. */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return internal("Error interno");
    }

    /* --------------------------------------------------------------------- */

    /* Utilidades privadas para crear respuestas. */
    private Mono<ResponseEntity<String>> badRequest(String body) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    private Mono<ResponseEntity<String>> internal(String body) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }
}