package com.jianpanmao.codegen.plugin;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.exception.ShellException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by Administrator on 2018/4/29.
 */
public class BaseMapperGeneratorPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable){

        Properties properties = this.getProperties();
        String pkg = (String) properties.get("pkg");
        System.out.println(pkg);


        String project = (String) properties.get("project");
        System.out.println(project);

        String modelPkg=introspectedTable.getBaseRecordType();
        String modelExamplePkg=introspectedTable.getExampleType();

        int modelIndexOf = modelPkg.lastIndexOf(".");
        String modelName = modelPkg.substring(modelIndexOf + 1);
        System.out.println(modelName);


        int modelExampleIndexOf = modelExamplePkg.lastIndexOf(".");
        String modelExampleName = modelExamplePkg.substring(modelExampleIndexOf + 1);
        System.out.println(modelExampleName);




        String servicePkg=pkg+".service";


        Map root = new HashMap();
        root.put("servicePkg",servicePkg);
        root.put("modelPkg", modelPkg);
        root.put("modelExamplePkg",modelExamplePkg);
        root.put("modelName",modelName);
        root.put("modelExampleName", modelExampleName);
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        try {
            configuration.setDirectoryForTemplateLoading(new File("src\\main\\resources\\ftl"));
            configuration.setObjectWrapper(new DefaultObjectWrapper(freemarker.template.Configuration.VERSION_2_3_28));


            Template temp = configuration.getTemplate("service.ftl");
            String servicefileName = modelName+"Service.java";
            File directory = this.getDirectory(project, servicePkg);
            System.out.println(directory);
            File targetFile = new File(directory,servicefileName);
            FileWriter fw = new FileWriter(targetFile);
            BufferedWriter bw = new BufferedWriter(fw);
            temp.process(root, bw);
            bw.flush();
            fw.close();


            Template serviceImpltemplate = configuration.getTemplate("service_impl.ftl");
            String serviceImplfileName = modelName+"ServiceImpl.java";
            File serviceImplDirectory = this.getDirectory(project, servicePkg+".impl");
            System.out.println(directory);
            File serviceImplTargetFile = new File(serviceImplDirectory,serviceImplfileName);
            FileWriter serviceImplFw = new FileWriter(serviceImplTargetFile);
            BufferedWriter serviceImplbw = new BufferedWriter(serviceImplFw);
            serviceImpltemplate.process(root, serviceImplbw);
            serviceImplbw.flush();
            serviceImplFw.close();


        } catch (Exception e) {
            e.printStackTrace();
        }





        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        /**
         * 主键默认采用java.lang.Integer
         */
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseDao<"
                + introspectedTable.getBaseRecordType() + ","
                + introspectedTable.getExampleType() + ","
                + "java.lang.Integer" + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(
                "com.jianpanmao.common.dao.BaseDao");
        /**
         * 添加 extends MybatisBaseMapper
         */
        interfaze.addSuperInterface(fqjt);

        /**
         * 添加import my.mabatis.example.base.MybatisBaseMapper;
         */
        interfaze.addImportedType(imp);

        interfaze.addAnnotation("@Mapper");

        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        /**
         * 方法不需要
         */
        interfaze.getMethods().clear();


        interfaze.getImportedTypes().removeIf(new Predicate<FullyQualifiedJavaType>() {
            @Override
            public boolean test(FullyQualifiedJavaType fullyQualifiedJavaType) {
                if (fullyQualifiedJavaType.getFullyQualifiedName().equals("java.util.List")) {
                    return true;
                } else if (fullyQualifiedJavaType.getFullyQualifiedName().equals("org.apache.ibatis.annotations.Param")) {
                    return true;
                }
                return false;
            }
        });

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("javax.validation.constraints.NotNull");
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //主键不添加验证注解
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn primaryKeyColumn : primaryKeyColumns) {
            if (!field.getName().equals(primaryKeyColumn.getJavaProperty())){
                if(!introspectedColumn.isNullable()){
                    field.addAnnotation("@NotNull(message = \""+field.getName()+"不能为空！\")");
                }
            }
        }

        field.addJavaDocLine("//"+introspectedColumn.getRemarks());
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        XmlElement selectAll = new XmlElement("select");
        Attribute id = new Attribute("id","selectAll");
        selectAll.addAttribute(id);

        Attribute resultMap = new Attribute("resultMap","BaseResultMap");
        selectAll.addAttribute(resultMap);


        Attribute parameterType = new Attribute("parameterType",introspectedTable.getBaseRecordType());
        selectAll.addAttribute(parameterType);



        TextElement textElement=new TextElement("select * from "+introspectedTable.getTableConfiguration().getTableName());
        selectAll.addElement(textElement);

        rootElement.addElement(selectAll);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }


    private File getDirectory(String targetProject, String targetPackage)
            throws ShellException {
        // targetProject is interpreted as a directory that must exist
        //
        // targetPackage is interpreted as a sub directory, but in package
        // format (with dots instead of slashes). The sub directory will be
        // created
        // if it does not already exist

        File project = new File(targetProject);
        if (!project.isDirectory()) {
            throw new ShellException(getString("Warning.9", //$NON-NLS-1$
                    targetProject));
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) {
                throw new ShellException(getString("Warning.10", //$NON-NLS-1$
                        directory.getAbsolutePath()));
            }
        }

        return directory;
    }

}
