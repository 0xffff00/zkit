package party.threebody.zkit.tally.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.skean.data.result.Count;
import party.threebody.skean.data.result.Counts;
import party.threebody.zkit.tally.dao.BillDao;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Bill;
import party.threebody.zkit.tally.domain.Deal;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {
    @Autowired BillDao billDao;
    @Autowired DealDao dealDao;

    @Transactional
    public Bill createOrUpdateDealWithItsDeals(Bill bill) {

        List<Deal> deals = bill.getDeals();
        for (Deal deal : deals) {
            deal.setBuyer(bill.getMainBuyer());
            deal.setSeller(bill.getMainSellor());
        }
        batchCreateOrUpdateDeals(deals);
        if (bill.getId() == null) {
            bill.setId(System.currentTimeMillis());
            billDao.create(bill);
            batchCreateBillItems(bill.getId(), deals, 0);
        } else {
            billDao.updateByExample(bill);
            billDao.deleteBillItems(bill.getId());
            batchCreateBillItems(bill.getId(), deals, 0);
        }
        return bill;
    }

    public int createDeal(Deal deal) {
        if (deal.getId() == null) {
            deal.setId(System.currentTimeMillis());
        }
        if (deal.getMakeDate() == null) {
            deal.setMakeDate(LocalDate.now());
        }
        return dealDao.create(deal);
    }

    public int createBill(Bill bill) {
        if (bill.getId() == null) {
            bill.setId(System.currentTimeMillis());
        }
        if (bill.getCreateDate() == null) {
            bill.setCreateDate(LocalDate.now());
        }
        return billDao.create(bill);
    }


    void batchCreateBillItems(Long billId, List<Deal> deals, int initSeq) {
        int seq = initSeq;
        for (Deal deal : deals) {
            billDao.createBillItem(billId, deal.getId(), seq++);
        }
    }

    @Transactional
    Count batchCreateOrUpdateDeals(List<Deal> deals) {
        long now = System.currentTimeMillis();
        int rnc = 0;
        int rnu = 0;
        for (Deal deal : deals) {
            if (deal.getId() == null) {
                deal.setId(now++);
                rnc += createDeal(deal);
            } else {
                rnu += dealDao.updateByExample(deal);
            }
        }
        return Counts.created(rnc).updated(rnu);
    }
}
