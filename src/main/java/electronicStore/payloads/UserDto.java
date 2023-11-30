package electronicStore.payloads;

//
//import electronicStore.validation.ImageNameValid;

import electronicStore.validation.ImageNameValid;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String userId;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Invalid Name !!")
    private String name;

    @NotBlank(message = "gender is required !!")
    private String gender;

    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$", message = "Email is invalid")
    @NotEmpty(message = "Email is required !!")
    private String email;

    @NotBlank(message = "Password is required !!")
    @Size(min = 3, max = 10, message = "password must be min 3 char & max of 10 chars")
    private String password;

    @NotBlank(message = "Write something about yourself !!")
    private String about;

    //    custom-validation
    @ImageNameValid
    private String imageName;
}
