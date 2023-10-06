package org.iclass.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.iclass.dto.PageRequestDTO;
import org.iclass.dto.PageResponseDTO;
import org.iclass.dto.TodoDto;
import org.iclass.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
@Log4j2

public class TodoController {

    private final TodoService todoService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerAction(TodoDto dto, BindingResult bindingResult,
                                 RedirectAttributes re){
        if (bindingResult.hasErrors()){
            re.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }

        todoService.register(dto);
        return "redirect:/todo/list";
    }


    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, BindingResult bindingResult,
                     Model model){
        if (bindingResult.hasErrors()){
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        pageRequestDTO = PageRequestDTO.of(pageRequestDTO.getPage(), pageRequestDTO.getSize());
        model.addAttribute("responseDto",todoService.getList(pageRequestDTO));
    }

    @GetMapping({"/read","/modify"})
    public void read(long tno, PageRequestDTO pageRequestDTO, Model model){
        TodoDto dto= todoService.getOne(tno);
        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long tno,PageRequestDTO pageRequestDTO
                         ,RedirectAttributes re){
        todoService.remove(tno);
        re.addAttribute("page",pageRequestDTO.getPage());
        re.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/todo/list";
    }

    @PostMapping("/modify")
    public String modifyAction(TodoDto dto, PageRequestDTO pageRequestDTO
                               ,BindingResult bindingResult
    ,RedirectAttributes re){
        if (bindingResult.hasErrors()){
            re.addFlashAttribute("tno",dto.getTno());
            re.addFlashAttribute("error",bindingResult.getAllErrors());
            log.info(">>>>>>>>>>에러 : {}",bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }
        todoService.modify(dto);

        re.addAttribute("page",pageRequestDTO.getPage());
        re.addAttribute("size",pageRequestDTO.getSize());

        return "redirect:/todo/list";
    }


}
