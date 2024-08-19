package gdsc.session.user.exception;

import lombok.Getter;

@Getter
public abstract class UserException extends RuntimeException{

    public UserException(String message) {
        super(message);
    }
    public abstract int getStatusCode();
}
