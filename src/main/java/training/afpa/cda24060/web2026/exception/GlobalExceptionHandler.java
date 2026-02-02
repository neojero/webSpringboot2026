package training.afpa.cda24060.web2026.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {

        model.addAttribute("error", "une erreur est survenue !");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", 500);
        return "error";
    }

    // Exemple pour une exception spécifique (ex : ressource non trouvée)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException e, Model model) {
        model.addAttribute("error", "Ressource non trouvée");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", 404);
        return "error";
    }

    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.class)
    public String handleHttpClientError(HttpClientErrorException e, Model model) {
        model.addAttribute("error", "Erreur client HTTP");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", e.getStatusCode().value());
        return "error";
    }
}

