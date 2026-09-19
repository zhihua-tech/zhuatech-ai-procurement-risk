# ZhuaTech AIPR｜AI采购风险控制系统

> 将供应商、价格、制裁、关联关系、单一来源和预算风险纳入采购审批

ZhuaTech AIPR 是知华科技（上海如静知华信息科技有限公司）发布的企业级源码项目，面向“采购申请、供应商画像、价格基准、关联关系、制裁筛查、单一来源、预算、审批与持续监测”提供管理端与响应式业务端。工程采用前后端分离架构，所有示例数据均为虚构数据。

[知华科技官网](https://www.zhuatech.cn/) · [架构说明](docs/ARCHITECTURE.md) · [API 文档](docs/API.md) · [企业能力](docs/ENTERPRISE.md) · [测试说明](docs/TESTING.md)

![AI采购风险控制系统产品界面示意](docs/images/product-overview.svg)

## 业务模块

| 模块 | 核心能力 |
| --- | --- |
| 采购申请 | 维护需求、规格、数量、预算和期望交期 |
| 供应商风险画像 | 综合资质、履约、财务、诉讼和负面事件 |
| AI价格基准 | 对比历史、市场、区域和规格形成价格区间 |
| 制裁与受益人 | 筛查制裁名单、实际控制人和关联关系 |
| 单一来源治理 | 记录必要性、替代方案、期限和例外审批 |
| 利益冲突 | 执行采购人声明、关联方识别和回避 |
| 预算控制 | 校验预算占用、超预算和承诺成本 |
| 风险审批 | 按金额和风险执行多级审批与职责分离 |
| 持续监测 | 跟踪供应商风险变化、交付和风险处置 |

![AI采购风险控制系统业务闭环](docs/images/workflow.svg)

## 企业级控制

- 新增供应商评标与授标引擎：合规/制裁硬拦截，价格、质量、交期、风险加权评分，预算和集中度控制；
- ADMIN / OPERATOR 角色边界和管理员接口隔离；
- 服务端字段、模块、唯一编号和状态迁移校验；
- 组织、期间、责任人、风险等级、到期日和 SLA 统计；
- 幂等创建、JPA 乐观锁、重复提交保护和职责分离；
- 附件 SHA-256 元数据、业务凭证完整性与全流程审计；
- 组合检索、分页、逾期筛选、UTF-8 CSV 导出和协作时间线；
- 外部系统仅预留适配器，使用方自行配置地址与凭据；
- prod profile 拒绝默认密码、弱数据库口令和本地跨域来源。

## 技术架构

- 后端：Java 21、Spring Boot、Spring Security、JPA、Bean Validation、Actuator
- 前端：Vue 3、Vite、Axios，支持桌面端与移动端响应式布局
- 数据库：MySQL 8；自动化测试使用 H2
- 交付：Docker Compose、Nginx、环境变量、GitHub Actions
- Java 包名：`cn.zhuatech.aiprocurementrisk`

## 启动与测试

```bash
cd backend && mvn test
cd ../frontend && npm install && npm run build
cd .. && cp .env.example .env && docker compose up --build
```

开发演示账号：`admin / admin123`、`operator / operator123`。生产环境必须通过环境变量替换全部默认凭据。

## 许可与商业授权

Copyright © 2026 上海如静知华信息科技有限公司。

本工程仅允许个人学习、研究和非商业技术交流，**不得用于商业用途**。企业内部使用、生产部署、SaaS运营、项目交付、品牌替换、收费培训、咨询实施或再分发，均须事先获得上海如静知华信息科技有限公司书面授权，详见 [LICENSE](LICENSE)。

深度开发、私有化部署、系统集成与企业数字化咨询，请访问[知华科技官网](https://www.zhuatech.cn/)或扫码联系：

| 微信咨询一 | 微信咨询二 |
| --- | --- |
| ![微信咨询二维码一](docs/images/zhuatech-wechat-consulting.png) | ![微信咨询二维码二](docs/images/zhuatech-wechat-consulting-2.png) |

SEO：AI采购风险控制系统、AIPR系统源码、企业数字化、Java企业系统、Vue管理系统、知华科技、上海如静知华信息科技有限公司。
