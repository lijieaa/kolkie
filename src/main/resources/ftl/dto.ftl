package ${pkg}.dto;
import com.jianpanmao.common.utils.StringUtil;

public class ${modelName}Dto {


<#list types?keys as key>
    <#assign property=st.beanPropertyProcess(key)>
private ${types["${key}"]} ${key} ;

public ${types["${key}"]} get${property}() {
return ${key};
}

public void set${property}(${types["${key}"]} ${key}) {
this.${key} = ${key};
}
</#list>
}