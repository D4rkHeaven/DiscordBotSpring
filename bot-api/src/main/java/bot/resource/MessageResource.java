package bot.resource;

import bot.dto.MessageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/dbd/message", produces = MediaType.APPLICATION_JSON_VALUE)
public interface MessageResource {

    @ApiOperation("Получить список из N последних сообщение из канала")
    @GetMapping("/{channelId}/last")
    List<MessageDto> getLastMessages(@ApiParam("Id канала") @PathVariable String channelId);

}