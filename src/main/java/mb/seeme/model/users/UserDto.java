package mb.seeme.model.users;

import lombok.Getter;
import lombok.Setter;
import mb.seeme.security.validation.PasswordMatches;
import mb.seeme.security.validation.ValidEmail;
import mb.seeme.security.validation.ValidName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
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
    @Length(min = 5, max = 15)
    private String password;
    private String matchingPassword;

}
