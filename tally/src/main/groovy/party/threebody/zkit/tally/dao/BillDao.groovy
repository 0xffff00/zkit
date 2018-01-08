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


    int createBillItem(Long billId, Long dealId, int seq) {
        cjt.sql('INSERT INTO tally_bill_item(bill_id,deal_id,seq) VALUES(?,?,?)')
                .arg(billId, dealId, seq).execute()
    }

    int deleteBillItem(Long billId, int seq) {
        cjt.from('tally_bill_item')
                .by('bill_id', 'seq').val(billId, seq).delete()
    }

    int deleteBillItems(Long billId) {
        cjt.from('tally_bill_item').by('bill_id').val(billId).delete()
    }
}
