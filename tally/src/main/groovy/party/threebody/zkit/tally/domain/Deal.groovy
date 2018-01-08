package party.threebody.zkit.tally.domain

import party.threebody.skean.data.Column
import party.threebody.skean.data.PrimaryKey

import javax.persistence.Table
import java.time.LocalDate

// 交易合约
@Table(name = "tally_deal")
class Deal {
    @PrimaryKey Long id
    @Column Long invoiceId  // optional,
    @Column String seller
    @Column String buyer
    @Column LocalDate makeDate
    @Column BigDecimal price    // optional
    @Column BigDecimal volume   // optional
    @Column BigDecimal amount
    @Column String type

}
// 账单
@Table(name = "tally_bill")
class Bill {
    @PrimaryKey Long id
    @Column String mainSellor
    @Column String mainBuyer
    @Column baseBalance //上次结算余额
    @Column LocalDate createDate

    List<Deal> deals
}
// 发货单, not persistent
class Invoice {
    @PrimaryKey Long id
    @Column LocalDate makeDate
    List<Deal> deals
}
