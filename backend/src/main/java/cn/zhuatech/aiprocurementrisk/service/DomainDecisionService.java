/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aiprocurementrisk.service;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service public class DomainDecisionService {
 public DecisionResult assess(DecisionRequest request) { int score=request.supplierRiskScore();List<String>actions=new ArrayList<>();if(request.priceVarianceRate()>10){score+=25;actions.add("复核价格偏离和市场基准");}if(request.singleSource()){score+=20;actions.add("补充单一来源必要性与替代方案");}if(!request.sanctionsClear()){score+=80;actions.add("制裁筛查未通过，冻结供应商和采购申请");}if(!request.beneficialOwnerVerified()){score+=35;actions.add("完成受益所有人与关联关系核验");}if(!request.conflictDeclared()){score+=35;actions.add("取得采购参与人的利益冲突声明");}if(!request.budgetAvailable()){score+=50;actions.add("取得预算或调整采购范围");}if(request.purchaseAmount()>1000000)actions.add("执行大额采购双人审批");return riskResult(score,actions,"APPROVE","ENHANCED_REVIEW","BLOCKED",Map.of("supplierRiskScore",request.supplierRiskScore(),"adjustedRiskScore",Math.min(100,score),"priceVarianceRate",request.priceVarianceRate(),"singleSource",request.singleSource())); }
 private DecisionResult result(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=80?good:score>=50?warn:bad;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 private DecisionResult riskResult(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=70?bad:score>=40?warn:good;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 public record DecisionRequest(
        @NotBlank String requestNo,
        @Positive double purchaseAmount,
        @Min(0) @Max(100) int supplierRiskScore,
        @DecimalMin("0") double priceVarianceRate,
        boolean singleSource,
        boolean sanctionsClear,
        boolean beneficialOwnerVerified,
        boolean conflictDeclared,
        boolean budgetAvailable) {}
 public record DecisionResult(String decision,int score,Map<String,Object> metrics,List<String> actions) {}
}
