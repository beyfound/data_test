package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.User;
import com.alvaria.datareuse.entity.WorkType;
import com.alvaria.datareuse.service.UploadCSVService;
import com.alvaria.datareuse.service.WorkTypeService;
import com.alvaria.datareuse.service.WorkTypeStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/save")
    public ResponseResult SaveOrUpdate(WorkType workType) {
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

    @DeleteMapping("/release/uuids")
    public ResponseResult releaseWorkTypeByList(@RequestBody List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return new ResponseResult(-1, uuids, "parameter error");
        }

        return new ResponseResult(0, "effect " + workTypeStatusService.releaseWorkTypeList(uuids) + " rows", "Release successfully");
    }

    @PostMapping("upload")
    @ApiOperation(value = "import users into the database")
    public String multipartFileTest(@RequestPart MultipartFile multipartFile) throws Exception {
        List<WorkType> userInfoList = uploadCSVService.getWorkTypesFromCSV(multipartFile);
        int a = workTypeService.insertWorkTypes(userInfoList);
        return "insert " + a + " records";
    }
}
