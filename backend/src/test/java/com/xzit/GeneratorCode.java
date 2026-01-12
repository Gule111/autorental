package com.xzit;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author 31507
 */
public class GeneratorCode {
    private static final String AUTHOR="Gule";
    private static final String JDBC_URL="jdbc:mysql://127.0.0.1/car_project";
    private static final String JDBC_USERNAME="root";
    private static final String JDBC_PASSWORD="Aomeisjh0228!";
    private static final String OUT_DIR=".\\src\\main\\java";
    private static final String  PACKAGE_NAME="com.xzit";
    private static final String MODULE_NAME="auto";
    private static final String[] TABLES={
            "auto_maker","auto_brand","auto_info",
            "sys_dept","sys_permission","sys_role","sys_user","sys_role_permission","sys_user_role",
            "busi_customer","busi_maintain","busi_violation","busi_order","busi_rental_type"
    };
    private static final String[] PREFIX={"busi_","sys_"};
    @Test
    public void generator() {
        // 创建代码生成器实例，并配置数据库连接信息
        FastAutoGenerator.create(JDBC_URL,JDBC_USERNAME,JDBC_PASSWORD)
                .globalConfig(builder->{
                    builder.author(AUTHOR)
                            // 开启swagger支持
                            .enableSwagger()
                            .outputDir(OUT_DIR);
                })
                // 配置包信息
                .packageConfig(builder->{
                    builder.parent(PACKAGE_NAME)
                            .moduleName(MODULE_NAME)
                            // 配置xml文件输出路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml,".\\src\\main\\resources\\mapper"));
                })
                // 配置生成策略，包括要包含的表、表名前缀等
                .strategyConfig(builder->{
                    builder.addInclude(TABLES)
                            .addTablePrefix(PREFIX)
                            // 配置实体类、控制器等的生成选项
                            .entityBuilder()
                            .enableLombok()
                            .enableChainModel()
                            .controllerBuilder()
                            .enableRestStyle();
                })
                // 设置使用的模板引擎为Freemarker
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行代码生成
                .execute();
    }
    @Test
    public void test() {
        System.out.println(1%2);
    }

}
