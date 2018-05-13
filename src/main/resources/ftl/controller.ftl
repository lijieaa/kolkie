package ${pkg}.controller;

import ${pkg}.service.${modelName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("${modelName?lower_case}")
public class ${modelName}Controller {


@Autowired
${modelName}Service ${modelName?lower_case}Service;


@RequestMapping(method = RequestMethod.GET,value = "list")
public String list(){
return "${modelName?lower_case}/${modelName?lower_case}_list";
}

@RequestMapping(method = RequestMethod.GET,value = "add")
public String add(){
return "${modelName?lower_case}/${modelName?lower_case}_add";
}

@RequestMapping(method = RequestMethod.GET,value = "edit")
public String edit(@RequestParam("id") Integer id, Model model){
model.addAttribute("id",id);
return "${modelName?lower_case}/${modelName?lower_case}_edit";
}


@RequestMapping(method = RequestMethod.GET,value = "detail")
public String detail(@RequestParam("id") Integer id, Model model){
model.addAttribute("id",id);
return "${modelName?lower_case}/${modelName?lower_case}_detail";
}
}
