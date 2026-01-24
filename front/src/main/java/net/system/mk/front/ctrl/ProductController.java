package net.system.mk.front.ctrl;

import io.swagger.annotations.Api;
import net.system.mk.front.serv.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:54
 */
@RestController
@Api(tags = "产品")
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService service;
}
