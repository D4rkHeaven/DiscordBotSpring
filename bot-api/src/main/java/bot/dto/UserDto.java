package bot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {

    @ApiModelProperty(value = "Id пользователя", example = "731154486176907289")
    private String userId;

    @ApiModelProperty(value = "Tag пользователя", example = "TestUser#2196")
    private String tag;

}
