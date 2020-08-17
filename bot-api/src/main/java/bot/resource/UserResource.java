package bot.resource;

import bot.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "/api/dbd/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserResource {

    @ApiOperation("Получить информацию о пользователе")
    @GetMapping("/{userId}")
    UserDto getUser(@ApiParam("Id пользователя") @PathVariable String userId);

}
