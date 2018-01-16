package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Deal;
import party.threebody.zkit.tally.service.BillService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("deals")
public class DealController extends SinglePKUriVarCrudRestController<Deal, Long> {
    @Autowired DealDao dealDao;
    @Autowired BillService billService;
    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Deal, Long> builder) {
        builder.fromSinglePKCrudDAO(dealDao);
    }

    @GetMapping("buyers")
    public List<Map> listAllBuyers(){
        return billService.listAllBuyersWithPinyin();
    }

    @GetMapping("buyers/3cnts")
    public Object list3CntsGroupByBuyer(){
        return billService.list3CntsGroupByBuyer();
    }
}
