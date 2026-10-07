package local.epul4a.demosbt.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonForm {

    @NotBlank(message = "{error.firstName}")
    private String firstName;

    @NotBlank(message = "{error.lastName}")
    private String lastName;

    @NotBlank(message = "{error.email}")
    @Email(message = "{error.email}")
    private String email;
}