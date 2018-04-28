package com.jianpanmao.codegen;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018/4/27.
 */
public class Main {

    @Test
    public void testHello() throws Exception {
        Context context = new Context(ModelType.FLAT);
        context.setTargetRuntime("MyBatis3");
        context.setId("test");
        context.addProperty("autoDelimitKeywords", "true");
        context.addProperty("javaFileEncoding", "utf-8");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");
        context.addProperty("javaFormatter", "org.mybatis.generator.api.dom.DefaultJavaFormatter");
        context.addProperty("xmlFormatter", "org.mybatis.generator.api.dom.DefaultXmlFormatter");
        /****************************************���***************************************************/
        PluginConfiguration pluginConfiguration = null;
        pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(pluginConfiguration);

        pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
        context.addPluginConfiguration(pluginConfiguration);

        /****************************************ע��***************************************************/
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
        /****************************************jdbc����***************************************************/
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");
        jdbcConnectionConfiguration.setConnectionURL("jdbc:mysql://47.104.144.238:6666/fc");
        jdbcConnectionConfiguration.setUserId("fc");
        jdbcConnectionConfiguration.setPassword("123456");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        /****************************************����ת��***************************************************/
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty("forceBigDecimals", "false");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);
        /****************************************����ʵ�����ַ***************************************************/
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage("com.we.fc.intermediary.entity");
        javaModelGeneratorConfiguration.setTargetProject("src/main/java");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "false");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
       /****************************************����mapxml�ļ�***************************************************/
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration=new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        sqlMapGeneratorConfiguration.setTargetProject("src/main/resources");
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        /****************************************����mapxml��Ӧclient��Ҳ���ǽӿ�dao***************************************************/
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration=new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetPackage("com.we.fc.intermediary.dao");
        javaClientGeneratorConfiguration.setTargetProject("src/main/java");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages","false");
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        //table�����ж��,ÿ�����ݿ��еı�����дһ��table��tableName��ʾҪƥ������ݿ��,Ҳ������tableName������ͨ��ʹ��%ͨ�����ƥ���������ݿ��,ֻ��ƥ��ı�Ż��Զ������ļ�
        TableConfiguration tableConfiguration=new TableConfiguration(context);
        tableConfiguration.setTableName("intermediary");
        tableConfiguration.setCountByExampleStatementEnabled(true);
        tableConfiguration.setDeleteByExampleStatementEnabled(true);
        tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setSelectByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setUpdateByExampleStatementEnabled(true);
        tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setInsertStatementEnabled(true);
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        Configuration config=new Configuration();
        config.addContext(context);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

    }
}
