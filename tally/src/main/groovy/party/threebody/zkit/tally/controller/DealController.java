package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Deal;
import party.threebody.zkit.tally.service.BillService;

@RestController
@RequestMapping("deals")
public class DealController extends SinglePKUriVarCrudRestController<Deal, Long> {
    @Autowired DealDao dealDao;
    @Autowired BillService billService;
    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Deal, Long> builder) {
        builder.fromSinglePKCrudDAO(dealDao);
        builder.oneCreator(billService::createDeal);
    }
}
