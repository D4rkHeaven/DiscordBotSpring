package bot.resource;

import bot.dto.MessageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/dbd/message", produces = MediaType.APPLICATION_JSON_VALUE)
public interface MessageResource {

    @ApiOperation("Get the list of N last messages in channel")
    @PostMapping("/{channelId}/last")
    List<MessageDto> getLastMessages(@ApiParam("Channel Id") @PathVariable String channelId,
                                     @ApiParam("Number of messages") @RequestBody Integer quantity);

}
