package gdsc.session.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {

    private Long id;

    private String email;

    private String password;

    private String username;

}
