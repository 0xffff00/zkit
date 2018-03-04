package party.threebody.zkit.tally.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO
import party.threebody.zkit.tally.domain.Deal

@Repository
class DealDao extends TriplePKsJpaCrudDAO<Deal, String, String, Integer> {
    final static int SN_2000 = 0, SN_2270 = 98616

    @Autowired ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }

    Deal getLastKeyDeal(String buyer, String seller, Integer snMax) {
        if (snMax == null) snMax = SN_2270
        cjt.sql("SELECT * FROM $table WHERE buyer=? AND seller=? AND sn<=? AND type='KEY' ORDER BY sn DESC")
                .arg(buyer, seller, snMax)
                .first(Deal.class)
    }

    int deleteBySnRange(String buyer, String seller, Integer snMin, Integer snMax) {
        cjt.sql("DELETE FROM tally_deal WHERE buyer=? AND seller=? AND sn>=? AND sn<=?")
                .arg(buyer, seller, snMin, snMax).execute()
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
