package com.stit.model;

import java.util.Date;

// for online post
public class AskCoilScanTempStringDate {

	private String  procEmp;
	private String  sheetNo;
	private String  sheetDate;
	private String  scanDate;
	private String  kind;
	private String  barCode;
        private Integer CoilGw;
	private String  locate;
	private String  scwJobNo;
	private Integer itemNo;
	private String  isrtType;
	private String  reasonCode;
	private String  classNo;
	private String  passYn;
        private String  overage;
        private Integer rackWt;
        private String  procNo;

	public String getProcEmp() {
		return procEmp;
	}

	public void setProcEmp(String procEmp) {
		this.procEmp = procEmp;
	}

	public String getSheetNo() {
		return sheetNo;
	}

	public void setSheetNo(String sheetNo) {
		this.sheetNo = sheetNo;
	}

	public String getSheetDate() {
		return sheetDate;
	}

	public void setSheetDate(String sheetDate) {
		this.sheetDate = sheetDate;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

        public Integer getCoilGw() {
                return CoilGw;
        }
        
        public void setCoilGw(Integer CoilGw) {
                 this.CoilGw = CoilGw;
        }
        
	public String getLocate() {
		return locate;
	}

	public void setLocate(String locate) {
		this.locate = locate;
	}

	public String getScwJobNo() {
		return scwJobNo;
	}

	public void setScwJobNo(String scwJobNo) {
		this.scwJobNo = scwJobNo;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public String getIsrtType() {
		return isrtType;
	}

	public void setIsrtType(String isrtType) {
		this.isrtType = isrtType;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getPassYn() {
		return passYn;
	}

	public void setPassYn(String passYn) {
		this.passYn = passYn;
	}

	public String getOverage() {
		return overage;
	}

	public void setOverage(String overage) {
		this.overage = overage;
	}

	public Integer getRackWt() {
		return rackWt;
	}

	public void setRackWt(Integer rackWt) {
		this.rackWt = rackWt;
	}
	@Override
	public String toString() {
		return "AskCoilScanTempStringDate{" + "procEmp=" + procEmp + ", sheetNo=" + sheetNo + ", sheetDate=" + sheetDate + ", scanDate=" + scanDate + ", kind=" + kind + ", barCode=" + barCode + ", locate=" + locate + ", scwJobNo=" + scwJobNo + ", itemNo=" + itemNo + ", isrtType=" + isrtType + ", reasonCode=" + reasonCode + ", classNo=" + classNo + ", passYn=" + passYn + '}';
	}

    /**
     * @return the procNo
     */
    public String getProcNo() {
        return procNo;
    }

    /**
     * @param procNo the procNo to set
     */
    public void setProcNo(String procNo) {
        this.procNo = procNo;
    }

	



}
