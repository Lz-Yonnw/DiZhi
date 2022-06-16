package com.ruoyi;

import com.ruoyi.netty.server.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动程序
 *
 * @author liaozan8888@163.com
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableSwagger2
public class RuoYiApplication
{
    public static void main(String[] args) throws Exception {
        // System.setProperty("spring.devtools.restart.enabled", "false");

        try {
            ConfigurableApplicationContext context = SpringApplication.run(RuoYiApplication.class, args);
            System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ ");

            //启动netty服务
            TCPServer tcpServer = context.getBean(TCPServer.class);
            tcpServer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
