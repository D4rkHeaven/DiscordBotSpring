package bot.resource.impl;

import bot.dto.ChannelDto;
import bot.resource.ChannelResource;
import bot.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChannelResourceImpl implements ChannelResource {

    private final MapperFacade mapperFacade;
    private final ChannelService channelService;

    @Override
    public List<ChannelDto> getChannels(String guildId) {
        log.info("getTextChannels() - start, guildId = {}", guildId);
        List<ChannelDto> channelDtos = mapperFacade.mapAsList(channelService.getChannels(guildId), ChannelDto.class);
        log.info("getTextChannels() - end, channelDtoList = {}", channelDtos);
        return channelDtos;
    }
}
