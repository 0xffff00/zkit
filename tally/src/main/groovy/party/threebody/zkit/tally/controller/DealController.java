package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Deal;

public class DealController extends SinglePKUriVarCrudRestController<Deal, Long> {
    @Autowired DealDao dealDao;

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Deal, Long> builder) {
        builder.fromSinglePKCrudDAO(dealDao);
    }
}
