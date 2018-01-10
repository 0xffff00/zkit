package party.threebody.zkit.tally.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.zkit.tally.dao.BillDao;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Bill;
import party.threebody.zkit.tally.domain.Deal;

import java.util.List;

@Service
public class BillService {
    @Autowired BillDao billDao;
    @Autowired DealDao dealDao;

    private Bill createOrUpdateBill(Bill bill) {
        if (bill.getId() == null) {
            bill.setId(System.currentTimeMillis());
            billDao.create(bill);
        } else {
            billDao.updateByExample(bill);
        }
        return bill;
    }


    @Transactional
    public Bill createOrUpdateBilllWithItsDeals(Bill bill) {
        bill = createOrUpdateBill(bill);
        Long billId = bill.getId();
        // delete all
        int rnd = dealDao.deleteByBillId(billId);
        long id = billId;
        List<Deal> deals = bill.getDeals();
        // create all
        for (Deal deal : deals) {
            deal.setBuyer(bill.getMainBuyer());
            deal.setSeller(bill.getMainSeller());
            deal.setId(id++);
            deal.setBillId(billId);
            dealDao.create(deal);
        }
        return bill;
    }

    // unnecessary
    Bill getLastBill(String seller, String buyer) {
        return billDao.getLast(seller, buyer);
    }
}
