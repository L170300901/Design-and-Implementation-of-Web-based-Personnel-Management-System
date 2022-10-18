package kr.co.yooooon.base.dao;

import kr.co.yooooon.base.to.ReportSalaryTO;
import kr.co.yooooon.base.to.ReportTO;

public interface ReportDAO {
   public ReportTO selectReport(String empCode);

   public ReportSalaryTO selecSalarytReport(String empCode, String applyMonth);
   
}