package party.threebody.zkit.tally.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.zkit.tally.dao.BillDao;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Bill;
import party.threebody.zkit.tally.domain.Deal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillService {
    @Autowired BillDao billDao;
    @Autowired DealDao dealDao;

    private int createOrUpdateBill(Bill bill, Long billId) {
        Bill existed = billDao.readOne(billId);
        if (existed == null) {
            return billDao.create(bill);
        } else {
            return billDao.update(bill, billId);
        }
    }

    public Bill getBillWithItsDeals(Long billId){
        List<Deal> deals=dealDao.listByBillId(billId);
        Bill bill=billDao.readOne(billId);
        if (bill!=null) {
            bill.setDeals(deals);
        }
        return bill;
    }


    @Transactional
    public int createOrUpdateBilllWithItsDeals(Bill bill, Long billId) {
        int rna = createOrUpdateBill(bill, billId);
        // delete all
        rna += dealDao.deleteByBillId(billId);
        long id = billId;
        List<Deal> deals = bill.getDeals();
        // create all
        for (Deal deal : deals) {
            deal.setBuyer(bill.getMainBuyer());
            deal.setSeller(bill.getMainSeller());
            deal.setId(id++);
            deal.setBillId(billId);
            rna += dealDao.create(deal);
        }
        return rna;
    }

    public List<String> listAllBuyers() {
        return dealDao.listAllBuyers();
    }

    // unnecessary
    Bill getLastBill(String seller, String buyer) {
        return billDao.getLast(seller, buyer);
    }

    public List<Map<String,Object>> list3CntsGroupByBuyer(){
        List<String> buyers=listAllBuyers();
        return buyers.stream().map(buyer->dealDao.get3CntsByBuyer(buyer)).collect(Collectors.toList());
    }
}
