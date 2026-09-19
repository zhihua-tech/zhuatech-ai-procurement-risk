/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aiprocurementrisk.domain;
import org.springframework.stereotype.Component;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public DomainCatalog() {
        actions.put("ASSESS", new WorkflowAction("ASSESS", "提交风险评估", List.of("草稿"), "风险复核", "OPERATOR"));
        actions.put("APPROVE", new WorkflowAction("APPROVE", "批准采购申请", List.of("风险复核"), "待下单", "ADMIN"));
        actions.put("ORDER", new WorkflowAction("ORDER", "确认采购下单", List.of("待下单"), "已下单", "ADMIN"));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String systemName() { return "知华科技AI采购风险控制系统"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String scene() { return "采购申请、供应商画像、价格基准、关联关系、制裁筛查、单一来源、预算、审批与持续监测"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String initialStatus() { return "草稿"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String partyLabel() { return "采购申请/供应商"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String amountLabel() { return "采购金额"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String quantityLabel() { return "采购数量"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String dueLabel() { return "交付日期"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<ModuleDefinition> modules() { return List.of(
            new ModuleDefinition("REQUEST", "采购申请", "维护需求、规格、数量、预算和期望交期"),
            new ModuleDefinition("SUPPLIER", "供应商风险画像", "综合资质、履约、财务、诉讼和负面事件"),
            new ModuleDefinition("BENCHMARK", "AI价格基准", "对比历史、市场、区域和规格形成价格区间"),
            new ModuleDefinition("SANCTIONS", "制裁与受益人", "筛查制裁名单、实际控制人和关联关系"),
            new ModuleDefinition("SINGLE_SOURCE", "单一来源治理", "记录必要性、替代方案、期限和例外审批"),
            new ModuleDefinition("CONFLICT", "利益冲突", "执行采购人声明、关联方识别和回避"),
            new ModuleDefinition("BUDGET", "预算控制", "校验预算占用、超预算和承诺成本"),
            new ModuleDefinition("APPROVAL", "风险审批", "按金额和风险执行多级审批与职责分离"),
            new ModuleDefinition("MONITOR", "持续监测", "跟踪供应商风险变化、交付和风险处置")
        ); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ModuleDefinition(String code,String name,String description) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record WorkflowAction(String code,String label,List<String> from,String to,String requiredRole) {}
}
