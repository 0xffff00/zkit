package party.threebody.zkit.tally.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.data.result.Count
import party.threebody.skean.data.result.Counts
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO
import party.threebody.zkit.tally.domain.Deal

@Repository
class DealDao extends SinglePKJpaCrudDAO<Deal, Long> {
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

    List<Deal> listByBillId(Long billId){
        cjt.sql('''
SELECT d.* 
FROM tally_deal d LEFT JOIN tally_bill_item bd
ON d.id=bd.`deal_id`
WHERE bd.bill_id=?''')
        .arg(billId).list(Deal.class)
    }


}
