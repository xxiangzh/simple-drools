package com.xzh.drools.utils;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * drools架构规则工具
 *
 * @author 向振华
 * @date 2020/06/30 17:29
 */
public class KieUtils {

    /**
     * 执行全部规则
     *
     * @param entity  规则文件中的实体类
     * @param globals 全局变量
     * @param drl     规则文件
     */
    public static void fireAllRules(Object entity, Map<String, Object> globals, String drl) {
        //会话对象，用于和规则引擎交互
        KieSession kieSession = getKieSession(entity, globals, drl);
        //激活规则引擎，如果规则匹配成功则执行规则
        kieSession.fireAllRules();
        //关闭会话
        kieSession.dispose();
    }

    /**
     * 执行全部规则
     *
     * @param kSession
     */
    public static void fireAllRules(KieSession kSession) {
        //激活规则引擎，如果规则匹配成功则执行规则
        kSession.fireAllRules();
        //关闭会话
        kSession.dispose();
    }

    /**
     * 获取KieSession
     *
     * @param entity  规则文件中的实体类
     * @param globals 全局变量
     * @param drl     规则文件
     * @return
     */
    public static KieSession getKieSession(Object entity, Map<String, Object> globals, String drl) {
        KieSession kieSession = getKieSession(drl);
        kieSession.insert(entity);
        if (!CollectionUtils.isEmpty(globals)) {
            for (String key : globals.keySet()) {
                kieSession.setGlobal(key, globals.get(key));
            }
        }
        return kieSession;
    }

    /**
     * 获取KieSession
     *
     * @param drls 规则文件
     * @return
     */
    public static KieSession getKieSession(String... drls) {
        KieHelper kieHelper = new KieHelper();
        for (String drl : drls) {
            kieHelper.addContent(drl, ResourceType.DRL);
        }
        verify(kieHelper);
        KieBaseConfiguration config = kieHelper.ks.newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kieHelper.build(config);
        //创建KieSession
        return kieBase.newKieSession();
    }

    /**
     * 校验规则文件
     *
     * @param drl 规则文件
     */
    public static void verify(String drl) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        verify(kieHelper);
    }

    /**
     * 校验
     *
     * @param kieHelper
     */
    private static void verify(KieHelper kieHelper) {
        Results results = kieHelper.verify();
        List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
        if (!CollectionUtils.isEmpty(messages)) {
            StringBuilder error = new StringBuilder("规则文件错误 ");
            for (Message message : messages) {
                error.append(message.getText()).append("；");
            }
            throw new RuntimeException(error.toString());
        }
    }
}
