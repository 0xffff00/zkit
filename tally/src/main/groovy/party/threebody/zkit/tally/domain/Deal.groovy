package party.threebody.zkit.tally.domain

import party.threebody.skean.data.Column
import party.threebody.skean.data.PrimaryKey
import party.threebody.zkit.tally.dao.BillDao

import javax.persistence.Table
import java.time.LocalDate

// 交易合约
@Table(name = "tally_deal")
class Deal {
    @PrimaryKey Long id
    @Column Long billId     // optional
    @Column Long invoiceId  // optional
    @Column String seller
    @Column String buyer
    @Column LocalDate makeDate
    @Column BigDecimal price    // optional
    @Column BigDecimal volume   // optional
    @Column BigDecimal amount
    @Column String unit
    // @Column String type // KEY - key frame,关键帧，存全� null/MID - 中间帧，存差�

}
// 账单
@Table(name = "tally_bill")
class Bill {
    @PrimaryKey Long id
    @Column LocalDate startDate
    @Column LocalDate endDate
    @Column String mainSeller
    @Column String mainBuyer
    @Column BigDecimal baseBalance //上次结算余额
    @Column BigDecimal finalBalance //本次最终余�
    @Column String memo
    List<Deal> deals
}
// 发货� not persistent
class Invoice {
    @PrimaryKey Long id
    @Column LocalDate makeDate
    List<Deal> deals
}
