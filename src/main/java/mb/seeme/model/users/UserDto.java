package mb.seeme.model.users;

import lombok.Getter;
import lombok.Setter;
import mb.seeme.security.validation.ValidEmail;
import mb.seeme.security.validation.ValidName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    @ValidName
    @NotNull
    @NotEmpty
    private String name;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String role;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

}
