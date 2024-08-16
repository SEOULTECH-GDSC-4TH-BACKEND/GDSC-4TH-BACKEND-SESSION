package gdsc.session.user.service;


import gdsc.session.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup() {}

    public void login() {}

    public void logout() {}
}
