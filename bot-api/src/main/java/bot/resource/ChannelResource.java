package bot.resource;

import bot.dto.ChannelDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/ddb/channel", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ChannelResource {

    @ApiOperation("Получить список доступных каналов на сервере")
    @GetMapping("/{guildId}")
    List<ChannelDto> getChannels(@ApiParam("Id сервера") @PathVariable String guildId);
}
