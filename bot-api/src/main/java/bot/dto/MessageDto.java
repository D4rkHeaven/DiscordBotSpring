package bot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageDto {

    @ApiModelProperty(value = "Id сообщения", example = "731154486176907289")
    private String messageId;

    @ApiModelProperty(value = "Текст сообщения", example = "Test")
    private String content;

}
