package bot.resource.impl;

import bot.config.converter.MessageConverter;
import bot.dto.MessageDto;
import bot.resource.MessageResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageResourceImpl implements MessageResource {

    private final JDA jda;
    private final MapperFacade mapperFacade;
    private final MessageConverter messageConverter;

    @Override
    public List<MessageDto> getLastMessages(String channelId, Integer quantity) {
        log.info("getLastMessages() - start, channelId = {}, quantity = {}", channelId, quantity);
        GuildChannel channel = jda.getGuildChannelById(channelId);
        if (Objects.nonNull(channel) && channel.getType().equals(ChannelType.TEXT)) {
            MessageChannel messageChannel = (MessageChannel) channel;
            List<Message> messages = messageChannel.getIterableHistory().stream()
                    .limit(quantity)
                    .collect(Collectors.toList());
            if (messages.size() > 0) {
                List<MessageDto> messageDtos = mapperFacade.mapAsList(messages, MessageDto.class);
                log.info("getLastMessages() - end, messages = {}", messageDtos);
                return messageDtos;
            }
        }
        log.info("getLastMessages() - end");
        return null;
    }

    @Override
    public void deliverMessage(String messageText) {
        log.info("deliverMessage() - start, message = {}", messageText);
        Message message = new MessageBuilder().setContent(messageText).build();
        MessageDto messageDto = messageConverter.convertTo(message);
        if (Objects.nonNull(messageDto)) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject("http://localhost:8080/api/sbd/channel/text/send/732199841819787315", messageDto, String.class);
            log.info("deliverMessage() - end");
        }
    }
}
