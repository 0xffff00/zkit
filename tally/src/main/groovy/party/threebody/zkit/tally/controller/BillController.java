package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;
import party.threebody.zkit.tally.dao.BillDao;
import party.threebody.zkit.tally.domain.Bill;
import party.threebody.zkit.tally.service.BillService;

@RestController
@RequestMapping("bills")
public class BillController extends SinglePKUriVarCrudRestController<Bill, Long> {
    @Autowired BillDao billDao;
    @Autowired BillService billService;

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Bill, Long> builder) {
        builder.fromSinglePKCrudDAO(billDao)
                .oneReader(billService::getBillWithItsDeals);
    }

    @PutMapping("{id}")
    @Override
    public ResponseEntity<Object> httpCreateOrUpdate(@PathVariable Long id, @RequestBody Bill entity) {
        int rna = billService.createOrUpdateBilllWithItsDeals(entity, id);
        return respondRowNumAffected(rna);
    }
}
