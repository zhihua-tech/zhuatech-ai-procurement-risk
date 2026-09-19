/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aiprocurementrisk.controller;
import cn.zhuatech.aiprocurementrisk.common.ApiResponse;
import cn.zhuatech.aiprocurementrisk.service.DomainDecisionService;
import cn.zhuatech.aiprocurementrisk.service.SupplierAwardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/domain") public class DomainDecisionController {
 private final DomainDecisionService service; private final SupplierAwardService supplierAwardService;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public DomainDecisionController(DomainDecisionService service, SupplierAwardService supplierAwardService){this.service=service;this.supplierAwardService=supplierAwardService;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/decision") public ApiResponse<DomainDecisionService.DecisionResult> assess(@Valid @RequestBody DomainDecisionService.DecisionRequest request){return ApiResponse.ok(service.assess(request));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/supplier-award") public ApiResponse<SupplierAwardService.AwardResult> award(@Valid @RequestBody SupplierAwardService.AwardRequest request){return ApiResponse.ok(supplierAwardService.evaluate(request));}
}
