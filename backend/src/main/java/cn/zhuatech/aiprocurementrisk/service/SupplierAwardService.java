/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aiprocurementrisk.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;

import java.math.*;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class SupplierAwardService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AwardResult evaluate(@Valid AwardRequest request) {
        BigDecimal weightTotal = request.weights().price().add(request.weights().quality())
            .add(request.weights().delivery()).add(request.weights().risk());
        if (weightTotal.subtract(BigDecimal.ONE).abs().compareTo(new BigDecimal("0.001")) > 0)
            throw new IllegalArgumentException("评标权重合计必须等于1");
        Set<String> supplierCodes = new HashSet<>();
        for (Bid bid : request.bids()) if (!supplierCodes.add(bid.supplierCode())) throw new IllegalArgumentException("供应商编码不能重复: " + bid.supplierCode());
        List<Bid> eligible = request.bids().stream().filter(bid -> bid.compliant() && !bid.sanctioned()).toList();
        if (eligible.isEmpty()) return new AwardResult("NO_ELIGIBLE_BID", List.of(), request.quantity(), BigDecimal.ZERO,
            List.of("全部报价因合规或制裁规则被否决"), rejected(request.bids()));
        BigDecimal minPrice = eligible.stream().map(Bid::unitPrice).min(BigDecimal::compareTo).orElseThrow();
        int minDelivery = eligible.stream().mapToInt(Bid::deliveryDays).min().orElseThrow();
        List<ScoredBid> ranking = eligible.stream().map(bid -> {
            BigDecimal priceScore = minPrice.divide(bid.unitPrice(), 8, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
            BigDecimal deliveryScore = BigDecimal.valueOf(minDelivery).divide(BigDecimal.valueOf(bid.deliveryDays()), 8, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
            BigDecimal score = priceScore.multiply(request.weights().price())
                .add(BigDecimal.valueOf(bid.qualityScore()).multiply(request.weights().quality()))
                .add(deliveryScore.multiply(request.weights().delivery()))
                .add(BigDecimal.valueOf(100 - bid.riskScore()).multiply(request.weights().risk()))
                .setScale(2, RoundingMode.HALF_UP);
            return new ScoredBid(bid, score);
        }).sorted(Comparator.comparing(ScoredBid::score).reversed().thenComparing(value -> value.bid().supplierCode())).toList();
        BigDecimal remaining = request.quantity();
        BigDecimal supplierLimit = request.quantity().multiply(request.maxSupplierShare()).setScale(2, RoundingMode.DOWN);
        List<AwardLine> awards = new ArrayList<>(); BigDecimal total = BigDecimal.ZERO;
        for (int i=0; i<ranking.size() && remaining.signum()>0; i++) {
            ScoredBid item = ranking.get(i);
            BigDecimal quantity = remaining.min(item.bid().capacity()).min(supplierLimit);
            if (quantity.signum() <= 0) continue;
            BigDecimal amount = quantity.multiply(item.bid().unitPrice()).setScale(2, RoundingMode.HALF_UP);
            awards.add(new AwardLine(i+1, item.bid().supplierCode(), item.score(), quantity, item.bid().unitPrice(), amount,
                "综合价格、质量、交付与风险评分择优"));
            remaining = remaining.subtract(quantity); total = total.add(amount);
        }
        List<String> warnings = new ArrayList<>();
        if (remaining.signum()>0) warnings.add("合格供应商产能不足，尚缺 " + remaining + " 单位");
        if (total.compareTo(request.budget())>0) warnings.add("建议授标金额超出预算 " + total.subtract(request.budget()).setScale(2, RoundingMode.HALF_UP));
        if (awards.size()==1 && request.maxSupplierShare().compareTo(BigDecimal.ONE)>=0) warnings.add("单一供应商集中度为100%，建议准备备选供应源");
        String status = remaining.signum()>0 ? "CAPACITY_GAP" : total.compareTo(request.budget())>0 ? "OVER_BUDGET" : "RECOMMENDED";
        return new AwardResult(status, awards, remaining, total, warnings, rejected(request.bids()));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private List<RejectedBid> rejected(List<Bid> bids) { return bids.stream().filter(bid -> bid.sanctioned() || !bid.compliant())
        .map(bid -> new RejectedBid(bid.supplierCode(), bid.sanctioned()?"制裁名单命中":"合规检查未通过")).toList(); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record AwardRequest(@NotNull @DecimalMin("0.01") BigDecimal quantity, @NotNull @DecimalMin("0.00") BigDecimal budget,
                               @NotNull @DecimalMin("0.01") @DecimalMax("1.00") BigDecimal maxSupplierShare,
                               @NotNull @Valid Weights weights, @NotEmpty List<@Valid Bid> bids) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Weights(@NotNull @DecimalMin("0") BigDecimal price, @NotNull @DecimalMin("0") BigDecimal quality,
                          @NotNull @DecimalMin("0") BigDecimal delivery, @NotNull @DecimalMin("0") BigDecimal risk) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Bid(@NotBlank String supplierCode, @NotNull @DecimalMin("0.01") BigDecimal unitPrice,
                      @Min(0) @Max(100) int qualityScore, @Min(1) int deliveryDays, @Min(0) @Max(100) int riskScore,
                      boolean compliant, boolean sanctioned, @NotNull @DecimalMin("0.01") BigDecimal capacity) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private record ScoredBid(Bid bid, BigDecimal score) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record AwardLine(int rank, String supplierCode, BigDecimal score, BigDecimal awardedQuantity,
                            BigDecimal unitPrice, BigDecimal amount, String reason) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record RejectedBid(String supplierCode, String reason) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record AwardResult(String status, List<AwardLine> awards, BigDecimal unallocatedQuantity,
                              BigDecimal recommendedAmount, List<String> warnings, List<RejectedBid> rejectedBids) {}
}
