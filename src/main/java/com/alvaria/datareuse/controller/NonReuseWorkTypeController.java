package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.*;
import com.alvaria.datareuse.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/non_reuse_work_type")
@CrossOrigin
@Api(tags = "Non Reuse Work Type controller")
public class NonReuseWorkTypeController {

    @Autowired
    private NonReuseWorkTypeService nonReuseWorkTypeService;

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private TestBedDataService testBedDataService;

    @Autowired
    private UploadCSVService uploadCSVService;

    @PostMapping("upload")
    @ApiOperation(value = "import users into the database")
    public ResponseResult multipartFileTest(@RequestPart MultipartFile multipartFile) throws Exception {
        List<WorkType> workTypes = uploadCSVService.getWorkTypesFromCSV(multipartFile);
        List<NonReuseWorkType> nonReuseWorkTypeList = new ArrayList<>();
        for (WorkType wt :workTypes) {
            NonReuseWorkType nonReuseWorkType =  new NonReuseWorkType(wt.getWorkTypeName(),wt.getType());
            nonReuseWorkTypeList.add(nonReuseWorkType);
        }
        int a = nonReuseWorkTypeService.insertWorkTypes(nonReuseWorkTypeList);
        return new ResponseResult(0, "Insert " + a + " record(s) successfully", "" );
    }
    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) String keyWord, @RequestParam(required = false) String sort) {
        return new ResponseResult(0, nonReuseWorkTypeService.selectPage(pageNum, pageSize, keyWord, sort),"");
    }

    @GetMapping("/all")
    public ResponseResult getAllWorkTypes() {
        List<NonReuseWorkType> wts = nonReuseWorkTypeService.getAll();
        return new ResponseResult(0, wts, "");
    }

    @PostMapping("/save")
    public ResponseResult SaveOrUpdate(@RequestBody NonReuseWorkType workType) {
        int row = nonReuseWorkTypeService.saveWorkType(workType);
        int code = row > 0 ? 0 : -1;
        String message = row > 0 ? "Save successfully" : "Save failed";
        return new ResponseResult(code, workType, message);
    }

    @PostMapping("/saveWts")
    public ResponseResult SaveWTs(@RequestBody List<NonReuseWorkType> workTypes) {
        int row = nonReuseWorkTypeService.saveWorkTypes(workTypes);
        int code = row > 0 ? 0 : -1;
        String message = row > 0 ? "Save successfully" : "Save failed";
        return new ResponseResult(code, workTypes, message);
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteWorkTypes(@RequestBody String[] ids) {
        return new ResponseResult(0, "Delete " + nonReuseWorkTypeService.deleteWorkTypes(ids) + " worktype(s) successfully", "");
    }

    @GetMapping("/organizations/{org}/workTypes")
    public ResponseResult getOrganizationUsers(@PathVariable String org, @RequestParam String keyWord){
        List<WorkType> testBedWTs = testBedDataService.getOrganizationWorkTypes(org, keyWord);
        List<NonReuseWorkType> wtsInDB  = nonReuseWorkTypeService.getAll();
        List<WorkType> wtsNoReuseInDB  = workTypeService.getAll();
        List<WorkType> wtsNotInDB = testBedWTs.stream().parallel().filter(a -> wtsInDB.stream().noneMatch(b -> a.getWorkTypeName().equals(b.getWorkTypeName())) && wtsNoReuseInDB.stream().noneMatch(c -> a.getWorkTypeName().equals(c.getWorkTypeName()))).collect(Collectors.toList());
        return new ResponseResult(0, wtsNotInDB, "");
    }
}
