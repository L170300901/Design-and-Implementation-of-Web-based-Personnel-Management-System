package kr.co.yooooon.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;


public class FileUploadUtil {
	public static String doFileUpload(HttpServletRequest request, FileItem fileitem, String empCode)
			throws FileNotFoundException, IOException {

			InputStream in = fileitem.getInputStream();
			String fileName = fileitem.getName().substring(fileitem.getName().lastIndexOf("\\")+1);
			String fileExt = fileName.substring(fileName.lastIndexOf("."));

			String saveFileName = empCode + fileExt;

			String path1 ="C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\insa\\profile\\"+ saveFileName;
			String path2 ="C:\\dev\\insaWorkspace\\insa\\WebContent\\profile\\"+ saveFileName;

			FileOutputStream fout1 = new FileOutputStream(path1);
			FileOutputStream fout2 = new FileOutputStream(path2);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				fout1.write(buffer, 0, bytesRead);
				fout2.write(buffer, 0, bytesRead);
			}
			in.close();
			fout1.close();
			fout2.close();

			return "C:\\dev\\insaWorkspace\\insa\\WebContent\\profile\\"+ saveFileName;
		}
}
