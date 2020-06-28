package com.cdio.petshop.controllers;


import com.cdio.petshop.entities.Bill;
import com.cdio.petshop.entities.BillStatus;
import com.cdio.petshop.services.BillService;
import com.cdio.petshop.services.BillStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillStatusService billStatusService;

    @GetMapping("/index")
    public String viewIndexPage(Model model){
        model.addAttribute("bills",billService.findAll());
        model.addAttribute("bills1",billStatusService.findById((long) 1).getBills());
        model.addAttribute("bills2",billStatusService.findById((long) 2).getBills());
        model.addAttribute("bills3",billStatusService.findById((long) 3).getBills());
        model.addAttribute("bills4",billStatusService.findById((long) 4).getBills());
        model.addAttribute("bills5",billStatusService.findById((long) 5).getBills());
        return "Admin_BillManager";
    }

    @PostMapping
    public String updateBill(@ModelAttribute(name = "bill") Bill bill, RedirectAttributes redirectAttributes){
        BillStatus billStatus = billStatusService.findById(bill.getBillStatus().getId());
        Long id = bill.getId();
        Bill bill_final = billService.findById(id);
        bill_final.setBillStatus(billStatus);
        billService.save(bill_final);
        redirectAttributes.addFlashAttribute("message","Cập nhật trạng thái đơn hàng thành công.\nĐã chuyển đến danh sách đơn hàng : "+ bill_final.getBillStatus().getStatus());
        return "redirect:/admin/bill/index";
    }
}
