package pe.com.entel.sincrespago.exception;

/**
 * Created by Erika Rumiche on 21/09/2018.
 */
public class RepositoryException extends Exception{

    public RepositoryException(String message){
        super(message);
    }

    public RepositoryException(String message, Throwable cause){
        super(message,cause);
    }

}