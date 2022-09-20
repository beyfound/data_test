package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.*;
import com.alvaria.datareuse.service.UploadCSVService;
import com.alvaria.datareuse.service.WorkTypeService;
import com.alvaria.datareuse.service.WorkTypeStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/work_type")
@CrossOrigin
@Api(tags = "Work Type controller")
public class WorkTypeController {

    @Autowired
    private WorkTypeService workTypeService;
    @Autowired
    private WorkTypeStatusService workTypeStatusService;

    @Autowired
    private UploadCSVService uploadCSVService;

    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String keyWord) {
        return new ResponseResult(0,workTypeService.selectPage(pageNum, pageSize, keyWord),"");
    }

    @GetMapping("/status/page")
    public ResponseResult findInUsePage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<WorkTypeStatus> data = workTypeStatusService.selectInUsePage(pageNum, pageSize);
        Integer total = workTypeStatusService.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return new ResponseResult(0,res,"");
    }

    @PostMapping("/save")
    public ResponseResult SaveOrUpdate(@RequestBody WorkType workType) {
        int row = workTypeService.saveWorkType(workType);
        int code = row > 0 ? 0 : -1;
        String message = row > 0 ? "Save successfully" : "Save failed";
        return new ResponseResult(code, workType, message);
    }

//    @PostMapping("/save")
//    public ResponseResult SaveList(List<WorkType> workTypeList) {
//        int row = workTypeService.insertWorkTypes(workTypeList);
//        int code = row > 0 ? 0 : -1;
//        String message = row > 0 ? "Save " + row + "items successfully" : "Save failed";
//        return new ResponseResult(code, row, message);
//    }

    @PostMapping("/apply")
    public ResponseResult applyWorkType(@RequestBody ConditionModel conditionModel) {
        return workTypeService.applyOneWorkType(conditionModel);
    }

    @DeleteMapping("/release/{uuid}")
    public ResponseResult releaseWorkType(@PathVariable String uuid) {
        return new ResponseResult(0, "effect " + workTypeStatusService.releaseWorkType(uuid) + " rows", "Release successfully");
    }

    @DeleteMapping("/release/{testCase}")
    public ResponseResult releaseWorkTypeByTestCase(@PathVariable String testCase) {
        return new ResponseResult(0, "effect " + workTypeStatusService.releaseWorkTypeByTestCase(testCase) + " rows", "Release successfully");
    }

    @DeleteMapping("/release/uuids")
    public ResponseResult releaseWorkTypeByList(@RequestBody List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return new ResponseResult(-1, uuids, "parameter error");
        }

        return new ResponseResult(0, "effect " + workTypeStatusService.releaseWorkTypeList(uuids) + " rows", "Release successfully");
    }

    @PostMapping("upload")
    @ApiOperation(value = "import users into the database")
    public ResponseResult multipartFileTest(@RequestPart MultipartFile multipartFile) throws Exception {
        List<WorkType> workTypes = uploadCSVService.getWorkTypesFromCSV(multipartFile);
        int a = workTypeService.insertWorkTypes(workTypes);
        return new ResponseResult(0, "Insert " + a + " record(s) successfully", "" );
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteWorkTypes(@RequestBody String[] ids) {
        return new ResponseResult(0, "Delete " + workTypeService.deleteWorkTypes(ids) + " worktype(s) successfully", "");
    }

    @DeleteMapping("/release/ids")
    public ResponseResult releaseWorkTypeByIds(@RequestBody List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ResponseResult(-1, ids, "parameter error");
        }

        return new ResponseResult(0, "effect " + workTypeStatusService.releaseWorkTypeByIds(ids) + " rows", "Release successfully");
    }
}
