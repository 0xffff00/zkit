package party.threebody.zkit.tally.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO
import party.threebody.zkit.tally.domain.Deal

@Repository
class DealDao extends SinglePKJpaCrudDAO<Deal, Integer> {
    @Autowired ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }

    @Override
    Deal createAndGet(Deal deal) {
        if (deal.id == null) {
            deal.setId(System.currentTimeMillis())
        }
        super.create(deal)
        return deal
    }

    int deleteByBillId(Long billId) {
        fromTable().by('billId').val(billId).delete()
    }

    List<Deal> listByBillId(Long billId) {
        fromTable().by('billId').val(billId).list(Deal.class)
    }

    List<String> listAllBuyers() {
        cjt.sql("SELECT DISTINCT buyer FROM tally_deal WHERE buyer IS NOT NULL ORDER BY buyer ")
                .listOfSingleCol(String.class)
    }

    List countByBuyer() {
        cjt.sql("SELECT buyer,COUNT(id) deal_cnt,COUNT(DISTINCT bill_id) bill_cnt FROM tally_deal d GROUP BY buyer")
                .list()
        // .listOfTripleCols(String.class, Integer.class, Integer.class)
    }

    def sql_listLastKeyIds = '''
SELECT a1.buyer,MAX(a1.id) last_key_id FROM (
 SELECT buyer,id FROM tally_deal WHERE TYPE='key' 
 UNION ALL 
 SELECT DISTINCT buyer,0 id FROM tally_deal
) a1 GROUP BY a1.buyer'''

    def sql_listLatestBalance = """
SELECT a.buyer,SUM(a.amount) latest_balance
FROM tally_deal a LEFT JOIN ($sql_listLastKeyIds) b
ON a.buyer=b.buyer
WHERE  a.id>=b.last_key_id
GROUP BY a.buyer"""

    def sql_getLatestBalance = '''
SELECT SUM(amount) FROM tally_deal 
WHERE buyer=? 
 AND id>=(SELECT MAX(id) FROM tally_deal WHERE buyer=? AND TYPE='key')'''


    List listLatestBalanceByBuyer() {
        cjt.sql(sql_listLatestBalance).list()
    }

    BigDecimal getLatestBalanceByBuyer(String buyer) {
        cjt.sql(sql_getLatestBalance).arg(buyer, buyer).firstCell(BigDecimal.class)
    }

    Map<String, Object> get3CntsByBuyer(String buyer) {
        def sql = """
SELECT buyer,COUNT(id) deal_cnt,COUNT(DISTINCT bill_id) bill_cnt, ($sql_getLatestBalance) latest_balance
FROM tally_deal d WHERE buyer=?"""
        cjt.sql(sql).arg(buyer, buyer, buyer).first()
    }


}
