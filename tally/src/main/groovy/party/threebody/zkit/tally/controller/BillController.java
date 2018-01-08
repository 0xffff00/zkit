package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;
import party.threebody.zkit.tally.dao.BillDao;
import party.threebody.zkit.tally.domain.Bill;

@RestController
@RequestMapping("bills")
public class BillController extends SinglePKUriVarCrudRestController<Bill, Long> {
    @Autowired BillDao billDao;

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Bill, Long> builder) {
        builder.fromSinglePKCrudDAO(billDao);
    }
}
