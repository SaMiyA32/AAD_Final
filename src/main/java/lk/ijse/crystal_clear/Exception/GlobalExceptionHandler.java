package lk.ijse.crystal_clear.Exception;

import lk.ijse.crystal_clear.Util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<APIResponse> handleCustomException(CustomException ex) {

        APIResponse response = new APIResponse(ex.getStatusCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGlobalException(Exception ex) {
        APIResponse response = new APIResponse(500, "Internal Server Error: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
