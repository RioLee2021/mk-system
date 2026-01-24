package net.system.mk.front.ctrl;

import io.swagger.annotations.Api;
import net.system.mk.front.serv.HomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:53
 */
@RestController
@Api(tags = "首页")
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService service;
}
