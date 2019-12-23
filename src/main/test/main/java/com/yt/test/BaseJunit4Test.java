package com.yt.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 自定义junit单元测试注解
 * RunWith——参数化运行器，用于指定JUnit运行环境为SpringJUnit4ClassRunner.class
 * ContextConfiguration——加载配置文件applicationContext.xml
 * @author yt
 * @date 2019/11/30 - 22:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BaseJunit4Test {
}
