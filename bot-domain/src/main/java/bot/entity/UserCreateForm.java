package bot.entity;

import lombok.Data;

@Data
public class UserCreateForm {

    //@NotEmpty
    private String email = "";

   // @NotEmpty
    private String password = "";

   // @NotEmpty
    private String passwordRepeated = "";

   // @NotNull
    private Role role = Role.USER;

}
