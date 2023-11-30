package electronicStore.entities;




import jakarta.persistence.*;

import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    @Id
    private String userId;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String about;
    private String imageName;


}

