package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.BillDetail;
import com.cdio.petshop.services.BillService;
import com.cdio.petshop.services.BillStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/billDetail")
public class BillDetailController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillStatusService billStatusService;

    @GetMapping("/index/billId={id}")
    public String viewBillDetailPage(Model model, @PathVariable(name = "id") Long id){
        // Tính tổng tiền
        int total = 0;
        for (BillDetail billDetail : billService.findById(id).getBillDetails()){
            total += billDetail.getPrice() * billDetail.getQuantity();
        }

        model.addAttribute("billDetails",billService.findById(id).getBillDetails()); // Danh sách các sản phẩm thuộc đơn hàng id.
        model.addAttribute("billStatus",billStatusService.findAll());
        model.addAttribute("total",total);
        model.addAttribute("bill",billService.findById(id));
        return "Admin_ListBillDetailByBillId";
    }
}
