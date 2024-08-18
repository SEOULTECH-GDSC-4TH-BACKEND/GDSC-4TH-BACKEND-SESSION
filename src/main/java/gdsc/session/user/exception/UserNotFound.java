package gdsc.session.user.exception;

public class UserNotFound extends UserException {
    private static final String MESSAGE = "사용자를 찾을 수 없습니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
