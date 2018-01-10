package party.threebody.zkit.tally.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO
import party.threebody.zkit.tally.domain.Bill

@Repository
class BillDao extends SinglePKJpaCrudDAO<Bill, Long> {
    @Autowired ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }

    Bill getLast(String seller,String buyer){
        fromTable().by('seller','buyer').val(seller,buyer).orderBy('-id').first()
    }

}
