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

    int deleteByBillId(Long billId){
        fromTable().by('billId').val(billId).delete()
    }

    List<String> listAllBuyers(){
        cjt.sql("SELECT distinct buyer FROM $table ORDER BY buyer").listOfSingleCol(String.class)
    }

}
