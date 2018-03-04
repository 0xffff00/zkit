package party.threebody.zkit.tally.domain

import party.threebody.skean.data.Column
import party.threebody.skean.data.PrimaryKey

import javax.persistence.Table
import java.time.LocalDate

//
/**
 * 交易合约流水
 * 不含lastBalance（上次余额），currBalance（本次余额）。这些数据是派生的，往往会有有错会导致历史流水全部算错。
 */
@Table(name = "tally_deal")
class Deal {
    @PrimaryKey String seller   // 卖方，就是自己啦，在简单情形下一般是常量
    @PrimaryKey String buyer    // 买方，即客户
    @PrimaryKey Integer sn     //往往有交易日期自动生成，没有交易日期则随机生成(date的数字化+3位序号，每天最多1000条流水)
    //@Column Long invoiceId  // 发货单id,optional
    @Column LocalDate date // 交易日期,optional
    @Column BigDecimal price    // 单价,optional
    @Column BigDecimal volume   // 数量,optional
    @Column BigDecimal amount   // 成交额=借方=-贷方
    @Column String unit // 单位,optional
    @Column String desc // 描述,optional
    @Column String type // KEY - key frame,关键帧; null/MID - 中间帧
}

// 发货单， not persistent
class Invoice {
    @PrimaryKey Long id
    @Column LocalDate date
    List<Deal> deals
}
