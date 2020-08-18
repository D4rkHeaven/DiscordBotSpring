package bot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {

    @ApiModelProperty(value = "User Id", example = "731154486176907289")
    private String userId;

    @ApiModelProperty(value = "User Tag", example = "TestUser#2196")
    private String tag;

}
