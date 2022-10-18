package kr.co.yooooon.system.to;

import kr.co.yooooon.base.to.*;

public class BranchBasicRegistTO extends BaseTO{
	
	public String getBranchLicense() {
		return branchLicense;
	}
	public void setBranchLicense(String branchLicense) {
		this.branchLicense = branchLicense;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getRepregentName() {
		return repregentName;
	}
	public void setRepregentName(String repregentName) {
		this.repregentName = repregentName;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public String getBranchPost() {
		return branchPost;
	}
	public void setBranchPost(String branchPost) {
		this.branchPost = branchPost;
	}
	public String getBranchTel() {
		return branchTel;
	}
	public void setBranchTel(String branchTel) {
		this.branchTel = branchTel;
	}
	public String getBranchFax() {
		return branchFax;
	}
	public void setBranchFax(String branchFax) {
		this.branchFax = branchFax;
	}
	public String getBusinessCondition() {
		return businessCondition;
	}
	public void setBusinessCondition(String businessCondition) {
		this.businessCondition = businessCondition;
	}
	public String getBranchEvent() {
		return branchEvent;
	}
	public void setBranchEvent(String branchEvent) {
		this.branchEvent = branchEvent;
	}
	public String getCompetentTax() {
		return competentTax;
	}
	public void setCompetentTax(String competentTax) {
		this.competentTax = competentTax;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getIsHead() {
		return isHead;
	}
	public void setIsHead(String isHead) {
		this.isHead = isHead;
	}
	public String branchLicense,taxCode,repregentName, branchCode,branchAddress,branchPost,branchTel,branchFax,businessCondition,branchEvent;       
	public String competentTax,openDate,closeDate,isHead;   
	public String taxNumber,postNumber;
	public String getTaxNumber() {
		return taxNumber;
	}
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	public String getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}

}
