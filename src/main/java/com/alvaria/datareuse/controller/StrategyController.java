package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.*;
import com.alvaria.datareuse.service.StrategyService;
import com.alvaria.datareuse.service.StrategyStatusService;
import com.alvaria.datareuse.service.UploadCSVService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/strategy")
@CrossOrigin
@Api(tags = "strategy controller")
public class StrategyController {
    @Autowired
    StrategyService strategyService;
    @Autowired
    StrategyStatusService strategyStatusService;
    @Autowired
    private UploadCSVService uploadCSVService;
    @PostMapping("/save")
    public ResponseResult saveStrategy(@RequestBody Strategy strategy) {
        int code = strategyService.save(strategy);
        return new ResponseResult(code, strategy, code > 0 ? "Save successfully" : "Save failed");
    }
    @PostMapping("/apply")
    public ResponseResult applyStrategy(@RequestBody ConditionModel conditionModel) throws Exception {
        StrategyStatus strategy = strategyService.applyStrategy(conditionModel);
        String message = strategy != null?"Apply strategy successfully!":"Apply strategy failed!";
        return new ResponseResult(0,strategy , message);
    }
    @DeleteMapping("/release")
    public ResponseResult releaseStrategy(@RequestParam String name, @RequestParam String org) throws Exception {
        strategyStatusService.releaseStrategyByName(name, org);
        return new ResponseResult(0, name, "Release strategy successfully!");
    }

    @DeleteMapping("/release/uuids")
    public ResponseResult releaseStrategyList(@RequestBody List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return new ResponseResult(-1, uuids, "parameter error");
        }

        return new ResponseResult(0, "effect " + strategyStatusService.releaseStrategyList(uuids) + " rows", "Release successfully");
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteUsers(@RequestBody Integer[] ids) {
        int row = strategyService.deleteStrategies(ids);
        return new ResponseResult(row > 0 ? row : -1, "", row > 0 ? "Delete successfully" : "Delete failed");
    }
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String keyWord, @RequestParam(required = false) String sort) {
        return new ResponseResult(0,strategyService.selectPage(pageNum, pageSize, keyWord, sort),"");
    }
    @GetMapping("/status/page")
    public ResponseResult findInUsePage(@RequestParam Integer pageNum, @RequestParam() Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<StrategyStatus> data = strategyStatusService.selectInusePage(pageNum, pageSize);
        Integer total = strategyStatusService.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return new ResponseResult(0, res, "");
    }

    @PostMapping("upload")
    @ApiOperation(value = "import strategy into the database")
    public ResponseResult multipartFileTest(@RequestPart MultipartFile multipartFile) throws Exception {
        List<Strategy> strategies = uploadCSVService.getStrategiesFromCSV(multipartFile);
        int a = strategyService.insertStrategis(strategies);
        return new ResponseResult(0, "Insert " + a + " record(s) successfully", "");
    }
}
