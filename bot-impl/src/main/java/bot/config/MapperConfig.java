package bot.config;

import bot.dto.ChannelDto;
import bot.dto.MessageDto;
import bot.dto.UserDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.metadata.Type;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig implements OrikaMapperFactoryConfigurer {
    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(GuildChannel.class, ChannelDto.class)
                .customize(new ChannelMapper())
                .register();

        mapperFactory.classMap(User.class, UserDto.class)
                .customize(new UserMapper())
                .register();
    }

    private static class ChannelMapper extends CustomMapper<GuildChannel, ChannelDto> {
        @Override
        public void mapAtoB(GuildChannel guildChannel, ChannelDto channelDto, MappingContext context) {
            channelDto.setChannelId(guildChannel.getId());
            channelDto.setName(guildChannel.getName());
        }
    }

    private static class UserMapper extends CustomMapper<User, UserDto> {
        @Override
        public void mapAtoB(User user, UserDto userDto, MappingContext context) {
            userDto.setUserId(user.getId());
            userDto.setTag(user.getAsTag());
        }
    }
}
