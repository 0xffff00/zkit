package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.data.result.Count;
import party.threebody.skean.web.mvc.controller.CrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.QVarCrudRestController;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Deal;
import party.threebody.zkit.tally.service.TallyService;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("deals")
public class DealController extends QVarCrudRestController<Deal> {
    @Autowired DealDao dealDao;
    @Autowired TallyService tallyService;

    @Override
    public void buildCrudFunctions(CrudFunctionsBuilder<Deal> builder) {
        builder.fillFromCrudDAO(dealDao);
    }

    @PutMapping("batch")
    public Count batchSave(@RequestBody List<Deal> deals,
                           @RequestParam @NotNull String buyer,
                           @RequestParam @NotNull String seller,
                           @RequestParam Integer snMin,
                           @RequestParam Integer snMax) {
        return tallyService.saveDeals(deals, buyer, seller, snMin, snMax);
    }

    @GetMapping("balance")
    public BigDecimal balance(@RequestParam @NotNull String buyer,
                              @RequestParam @NotNull String seller,
                              @RequestParam Integer snMax) {
        return tallyService.getBalance(buyer, seller, snMax);
    }

    @GetMapping("buyers")
    public List<Map> listAllBuyers() {
        return tallyService.listAllBuyersWithPinyin();
    }

    @GetMapping("buyers/3cnts")
    public Object list3CntsGroupByBuyer() {
        return tallyService.list3CntsGroupByBuyer();
    }
}
