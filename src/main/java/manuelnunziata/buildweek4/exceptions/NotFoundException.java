package manuelnunziata.buildweek4.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id) {
        super("Risorsa con id " + id + " non trovata");
    }
}
