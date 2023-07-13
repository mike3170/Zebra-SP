package com.stit.rest;

import com.stit.common.ApiResponse;
import com.stit.common.ApiResponse.Error;
import com.stit.common.ApiResponse.Status;
import com.stit.model.ApcJobOrde;
import com.stit.model.AskCoilScanTemp;
import com.stit.model.AskCoilScanTempStringDate;
import com.stit.model.BasLoc;
import com.stit.model.CodMast;
import com.stit.model.ProcMast;
import com.stit.svc.JhBarcodeService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/jhbarcode")
public class JhBarcodeResource {

    private final Logger log = LogManager.getLogger();
    private static final String WIN_UPLOADED_FOLDER = "C:/Users/user/apps/zebra/upload/";
    private static final String LINUX_UPLOADED_FOLDER = "/home/zebra/upload/";

    private final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private JhBarcodeService jhBarcodeService;

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/codMast/list")
    public ApiResponse getCodMast() {
        List<CodMast> codMastList = this.jhBarcodeService.getCodMastList();
        return new ApiResponse(Status.OK, codMastList, new Error(0, null));
    }

    @GetMapping("/getLocList")
    public ApiResponse getLocList() {
        List<BasLoc> basLocList = this.jhBarcodeService.getLocList();

        return new ApiResponse(Status.OK, basLocList, new Error(0, null));
    }

    @GetMapping("/getProcList")
    public ApiResponse getProcList() {
        List<ProcMast> procMastList = this.jhBarcodeService.getProcList();

        return new ApiResponse(Status.OK, procMastList, new Error(0, null));
    }

    /**
     * login
     *
     * @param depNo requestParam
     * @param locNo
     * @return
     */
    @RequestMapping(path = "chkLocExist", method = RequestMethod.GET)
    public ApiResponse chkLocExist(@RequestParam String depNo, @RequestParam String locNo) {
        // log.info("empNo:" + empNo);
        // log.info("chgPswd:" + chgPswd);

        try {
            boolean chkResult = this.jhBarcodeService.chkLocExist(depNo, locNo);
            if (chkResult) {
                return new ApiResponse(Status.OK);
                //return new ApiResponse(Status.OK, null, new Error(0, "登入成功!"));
            } else {
                return new ApiResponse(Status.ERROR);
                //return new ApiResponse(Status.ERROR, new Error(-1, "登入失敗!"));
            }
        } catch (Exception ex) {
            log.error("err", ex);
            //return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
            return ApiResponse.error(ex.getMessage());
        }
    }

    @RequestMapping(path = "getjobloc", method = RequestMethod.GET)
    public ApiResponse getJobLoc(@RequestParam String jobNo, @RequestParam String procNo) {
        // log.info("empNo:" + empNo);
        // log.info("chgPswd:" + chgPswd);

        try {
            String loc = this.jhBarcodeService.getJobLoc(jobNo, procNo);
            if (loc.isEmpty()) {
                return ApiResponse.error("此批號庫位為空值");
            } else {
                return ApiResponse.ok(loc);
            }
        } catch (Exception ex) {
            log.error("err", ex);
            //return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
            return ApiResponse.error(ex.getMessage());
        }
    }
    
    /**
     * login
     *
     * @param jobNo requestParam
     * @param procNo
     * @return
     */
    @RequestMapping(path = "chkStokExist", method = RequestMethod.GET)
    public ApiResponse chkJobExist(@RequestParam String jobNo, @RequestParam String procNo) {
        // log.info("empNo:" + empNo);
        // log.info("chgPswd:" + chgPswd);

        try {
            boolean login = this.jhBarcodeService.chkStokExist(jobNo, procNo);
            if (login) {
                return ApiResponse.ok("true");
                //return new ApiResponse(Status.OK, null, new Error(0, "登入成功!"));
            } else {
                return ApiResponse.error("false");
                //return new ApiResponse(Status.ERROR, new Error(-1, "登入失敗!"));
            }
        } catch (Exception ex) {
            log.error("err", ex);
            //return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
            return ApiResponse.error(ex.getMessage());
        }
    }

    /**
     * login
     *
     * @param jobNo requestParam
     * @param procNo
     * @return
     */
    @RequestMapping(path = "chkJobExist", method = RequestMethod.GET)
    public ApiResponse chkJobExist(@RequestParam String jobNo) {
        // log.info("empNo:" + empNo);
        // log.info("chgPswd:" + chgPswd);

        try {
            boolean result = this.jhBarcodeService.chkJobExist(jobNo);
            if (result) {
                return ApiResponse.ok("true");
                //return new ApiResponse(Status.OK, null, new Error(0, "登入成功!"));
            } else {
                return ApiResponse.error("false");
                //return new ApiResponse(Status.ERROR, new Error(-1, "登入失敗!"));
            }
        } catch (Exception ex) {
            log.error("err", ex);
            //return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
            return ApiResponse.error(ex.getMessage());
        }
    }

    @RequestMapping(path = "chkCoilExist", method = RequestMethod.GET)
    public ApiResponse chkCoilExist(@RequestParam String coilNo) {
        // log.info("empNo:" + empNo);
        // log.info("chgPswd:" + chgPswd);

        try {
            boolean result = this.jhBarcodeService.chkCoilExist(coilNo);
            if (result) {
                return ApiResponse.ok("true");
                //return new ApiResponse(Status.OK, null, new Error(0, "登入成功!"));
            } else {
                return ApiResponse.error("false");
                //return new ApiResponse(Status.ERROR, new Error(-1, "登入失敗!"));
            }
        } catch (Exception ex) {
            log.error("err", ex);
            //return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
            return ApiResponse.error(ex.getMessage());
        }
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public ApiResponse singleFileUpload(@RequestParam("file") MultipartFile file) {
        int insertRowCount = 0;

        if (file.isEmpty()) {
            log.error("api-空檔");
            return new ApiResponse(Status.ERROR, new Error(-1, "空檔!"));
        }

        String upload_foler = null;
        if (this.isWindow()) {
            upload_foler = WIN_UPLOADED_FOLDER;
        } else {
            upload_foler = LINUX_UPLOADED_FOLDER;
        }

        //log.trace("aaa");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss-SSS");
        String postFix = dtFmt.format(now);

        String fileName = upload_foler + file.getOriginalFilename() + "-" + postFix;
        //log.trace("bbb");
        log.trace("fileName: " + fileName);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileName);
            Files.write(path, bytes);
            //log.trace("ccc");

            List<String> lines = Files.readAllLines(path);
            if (lines.size() == 0) {
                log.error("api", "空檔!");
                return new ApiResponse(Status.ERROR, new Error(-1, "空檔!"));
            }

            //log.trace("ddd");
            insertRowCount = this.jhBarcodeService.insertTemp(lines);

        } catch (Exception ex) {
            log.error("err", ex);
            return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
        }

        log.debug("insert row count:" + insertRowCount);
        log.trace("-------------------------------------");
        return new ApiResponse(Status.OK, insertRowCount);
        // return new ApiResponse(Status.OK, new Error(0, String.valueOf(insertRowCount)));
    }

    /**
     * Online post data
     *
     * @param scanTemp as a POJO, date as string, needs to parse to
     * java.util.Date
     * @return ApiResponse<Integer> insert count
     */
    @PostMapping("/insertData") // //new annotation since 4.3
    public ApiResponse insertData(@RequestBody AskCoilScanTempStringDate scanTemp) {
        try {
            System.out.println("insert begin");
            // Thread.sleep(5000);
            AskCoilScanTemp bean = new AskCoilScanTemp();

            bean.setProcEmp(scanTemp.getProcEmp());
            bean.setSheetNo(null);
            bean.setSheetDate(null);

            //------------------------
            Date scanDate = null;
            try {
                scanDate = this.dateFmt.parse(scanTemp.getScanDate());
            } catch (Exception e) {
                log.error("JHBarcodeResource.insertData", "日期格式錯誤!");
                throw new IllegalArgumentException("日期格式錯誤!");
            }

            bean.setScanDate(scanDate);
            //-----------------------
            bean.setKind(scanTemp.getKind());
            bean.setBarCode(scanTemp.getBarCode());
            bean.setLocate(scanTemp.getLocate());
            bean.setScwJobNo(scanTemp.getScwJobNo());
            bean.setItemNo(scanTemp.getItemNo());
            bean.setIsrtType(scanTemp.getIsrtType());
            bean.setReasonCode(scanTemp.getReasonCode());
            bean.setClassNo(scanTemp.getClassNo());
            bean.setPassYn(scanTemp.getPassYn());
            bean.setCoilGw(scanTemp.getCoilGw());
            bean.setOverage("N");
            bean.setRackWt(scanTemp.getRackWt());
            bean.setProcNo(scanTemp.getProcNo());

            System.out.println("loc:"+bean.getLocate());
            Integer insertCount = this.jhBarcodeService.insertData(bean);
            System.out.println("2");
            if (insertCount.intValue() == 1) {
                log.info("online insert ok:" + bean.getKind() + " " + bean.getBarCode());
                System.out.println("online insert ok:" + bean.getKind() + " " + bean.getBarCode());
                return new ApiResponse(Status.OK, 1, null);
            } else {
//                log.info("online insert error:" + bean.getKind() + " " + bean.getBarCode());
                System.out.println("online insert error:" + bean.getKind() + " " + bean.getBarCode());
                throw new IllegalStateException("online insert data error");
            }

        } catch (Exception ex) {
            log.error("err", ex);
            System.out.println("err:" + ex);
            return new ApiResponse(Status.ERROR, -1, new Error(-1, ex.getMessage()));
        }
    }

    @GetMapping("/apcJobOrde/list")
    public ApiResponse getApcJobOrde(@RequestParam String jobNo) {
        try {
            List<ApcJobOrde> list = this.jhBarcodeService.getApcJobOrdeList(jobNo);
            return new ApiResponse(Status.OK, list);
        } catch (Exception ex) {
            log.error("err", ex);
            return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
        }
    }

    /**
     * 查詢
     *
     * @param uploaded	true/flase
     * @param scanDate	yyyy/mm/dd
     * @return
     */
    @GetMapping("/askCoilScanTemp")
    public ApiResponse getAskCoilScanTemp(@RequestParam String kind, @RequestParam String scanDate) {
        try {
            //log.info(kind);
            //log.info(scanDate);

            List<AskCoilScanTemp> list = this.jhBarcodeService.getAskCoilScanTemp(kind, scanDate);
            return new ApiResponse(Status.OK, list);
        } catch (Exception ex) {
            log.error("err", ex);
            return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
        }
    }

    /**
     * 工令領用 細項刪除
     *
     * @param jobNo
     * @param itemNo
     * @param barcode
     * @param scanDate yyyy-mm-dd
     * @return
     */
    @DeleteMapping("/askCoilScanTemp")
    public ApiResponse deleteJobOrder(
            @RequestParam String kind,
            @RequestParam String jobNo,
            @RequestParam int itemNo,
            @RequestParam String barcode,
            @RequestParam String scanDate) {

        log.info("--------------------------------");
        log.info("kind: " + kind);
        log.info("jobNo: " + jobNo);
        log.info("itemNo: " + itemNo);
        log.info("barcode: " + barcode);
        log.info("scanDate: " + scanDate);

        try {
            boolean success = this.jhBarcodeService.deleteJobOder(kind, jobNo, itemNo, barcode, scanDate);
            return new ApiResponse(Status.OK, success);
        } catch (Exception ex) {
            log.error("err", ex);
            return new ApiResponse(Status.ERROR, new Error(-1, ex.getMessage()));
        }

    }

    /**
     * 工令領用線上check
     *
     * @return
     */
    @GetMapping("jobOrder/check")
    public ApiResponse jobOrderCheck(
            @RequestParam String jobNo,
            @RequestParam String coilNo,
            @RequestParam String procDate,
            @RequestParam String procEmp) {

        try {
            Map<String, Object> outMap = this.jhBarcodeService.jobOrderCheck(jobNo, coilNo, procDate, procEmp);
            String failure = (String) outMap.get("pv_failure");
            String errMsg = (String) outMap.get("pv_err_msg");

            log.info("failuer:" + failure);
            log.info("errMsg:" + errMsg);

            if (failure.equalsIgnoreCase("N")) {
                return new ApiResponse(Status.OK, "ok");
            } else {
                return new ApiResponse(Status.ERROR, "err", new Error(-1, errMsg));
            }

        } catch (Exception e) {
            log.error("err", e);
            return new ApiResponse(Status.ERROR, "err", new Error(-1, e.getMessage()));
        }

    }

    /**
     * 線材領用線上check
     *
     * @return
     */
    @GetMapping("coilissu/check")
    public ApiResponse jobissuchek(
            @RequestParam String coilNo,
            @RequestParam String procDate,
            @RequestParam String procEmp) {

        try {
            Map<String, Object> outMap = this.jhBarcodeService.coilissuchek(coilNo, procDate, procEmp);

            String failure = (String) outMap.get("pv_failure");
            String errMsg = (String) outMap.get("pv_err_msg");

            log.info("failuer:" + failure);
            log.info("errMsg:" + errMsg);

            if (failure.equalsIgnoreCase("N")) {
                return new ApiResponse(Status.OK, "ok");
            } else {
                return new ApiResponse(Status.ERROR, "err", new Error(-1, errMsg));
            }

        } catch (Exception e) {
            log.error("err", e);
            return new ApiResponse(Status.ERROR, "err", new Error(-1, e.getMessage()));
        }

    }
    // 

    private boolean isWindow() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains("windows");
    }

} // end class
