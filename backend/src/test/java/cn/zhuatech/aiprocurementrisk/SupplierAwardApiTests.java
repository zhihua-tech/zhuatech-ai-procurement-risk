/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aiprocurementrisk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest @AutoConfigureMockMvc class SupplierAwardApiTests {
 @Autowired MockMvc mvc;
 private static final String BODY="""
  {"quantity":1000,"budget":100000,"maxSupplierShare":0.6,"weights":{"price":0.4,"quality":0.25,"delivery":0.2,"risk":0.15},
   "bids":[{"supplierCode":"SUP-A","unitPrice":90,"qualityScore":92,"deliveryDays":7,"riskScore":8,"compliant":true,"sanctioned":false,"capacity":800},
           {"supplierCode":"SUP-B","unitPrice":85,"qualityScore":80,"deliveryDays":10,"riskScore":15,"compliant":true,"sanctioned":false,"capacity":700},
           {"supplierCode":"SUP-X","unitPrice":70,"qualityScore":99,"deliveryDays":3,"riskScore":2,"compliant":true,"sanctioned":true,"capacity":1000}]}
  """;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void ranksBidsAppliesConcentrationLimitAndRejectsSanctionedSupplier() throws Exception {
  mvc.perform(post("/api/domain/supplier-award").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content(BODY))
   .andExpect(status().isOk()).andExpect(jsonPath("$.data.status").value("RECOMMENDED"))
   .andExpect(jsonPath("$.data.awards.length()").value(2)).andExpect(jsonPath("$.data.rejectedBids[0].supplierCode").value("SUP-X"))
   .andExpect(jsonPath("$.data.unallocatedQuantity").value(0));
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void rejectsInvalidWeights() throws Exception {
  mvc.perform(post("/api/domain/supplier-award").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content(BODY.replace("\"risk\":0.15","\"risk\":0.25")))
   .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("评标权重合计必须等于1"));
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void awardRequiresAuthentication() throws Exception {mvc.perform(post("/api/domain/supplier-award").contentType(MediaType.APPLICATION_JSON).content(BODY)).andExpect(status().isUnauthorized());}
}
