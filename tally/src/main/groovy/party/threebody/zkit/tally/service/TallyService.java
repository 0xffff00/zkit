package party.threebody.zkit.tally.service;

import com.github.promeg.pinyinhelper.Pinyin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.skean.collections.Maps;
import party.threebody.skean.data.result.Count;
import party.threebody.skean.data.result.Counts;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Deal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TallyService {
    @Autowired DealDao dealDao;


    public List<String> listAllBuyers() {
        return dealDao.listAllBuyers();
    }

    @Transactional
    public Count saveDeals(List<Deal> deals, String buyer, String seller, Integer snMin, Integer snMax) {
        int rnd = dealDao.deleteBySnRange(buyer, seller, snMin, snMax);
        int rnc = 0;
        for (Deal deal : deals) {
            deal.setBuyer(buyer);
            rnc += dealDao.create(deal);
        }
        return Counts.created(rnc).deleted(rnd);
    }

    public List<Map> listAllBuyersWithPinyin() {
        List<String> buyers = dealDao.listAllBuyers();
        return buyers.stream()
                .map(buyer -> Maps.of("hanzi", buyer, "pinyin", Pinyin.toPinyin(buyer, ",")))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> list3CntsGroupByBuyer() {
        List<String> buyers = listAllBuyers();
        return buyers.stream().map(buyer -> dealDao.get3CntsByBuyer(buyer)).collect(Collectors.toList());
    }

    public BigDecimal getBalance(String buyer, String seller, Integer snMax) {
        Deal deal = dealDao.getLastKeyDeal(buyer, seller, snMax);
        if (deal == null) {
            return BigDecimal.ZERO;
        } else {
            return deal.getAmount();
        }
    }
}
