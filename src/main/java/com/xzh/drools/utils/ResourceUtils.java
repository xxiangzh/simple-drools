package com.xzh.drools.utils;

import org.apache.poi.util.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @author 向振华
 * @date 2022/07/25 10:59
 */
public class ResourceUtils {

    /**
     * 读取配置文件并转换成字符串
     *
     * @param path 路径，eg: classpath:META-INF/xxx/spring.factories
     * @return
     */
    public static String read(String path) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourcePatternResolver.getResource(path);
        try {
            byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
            return new String(bytes);
        } catch (IOException e) {
            return null;
        }
    }
}