package bot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChannelDto {

    @ApiModelProperty(value = "Channel Id", example = "732199841819787315")
    private String channelId;

    @ApiModelProperty(value = "Channel name", example = "Test channel")
    private String name;

}
