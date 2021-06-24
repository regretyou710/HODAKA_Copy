package Copy.tw.com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;

import Copy.CopyExe;
import Copy.tw.com.bean.Copy;
import Copy.tw.com.bean.CopyList;

public class CopyUtil {
	private static List<String> filterData;
	private static Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

	private CopyUtil() {
	}

	public static List<String> getFilterData() {
		return filterData;
	}

	public static void setFilterData(List<String> filterData) {
		CopyUtil.filterData = filterData;
	}

	public static List<Copy> getCopyList() throws IOException {
		InputStream is = null;
		File jsonFile = new File("./CopyList.json");

		if (jsonFile.exists()) {
			is = new FileInputStream(jsonFile);
		} else {
			is = CopyExe.class.getClassLoader().getResourceAsStream("Copy/CopyList.json");
		}

		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);

		// 透過gson對json文件讀取
		Gson gson = new Gson();
		CopyList Copy = gson.fromJson(br, CopyList.class);
		br.close();

		return Copy.getCopyList();
	}

	/**
	 * 複製
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param datas
	 * @throws IOException
	 */
	public static void copy(File srcFile, File destFile) throws Exception {
		for (String item : filterData) {
			if (!new File(item).exists()) {
				destFile.delete();
				throw new IOException(String.format("過濾路徑=>%s不存在!", item));
			}
		}

		// 判斷是否為目錄，是則在目標中創建同名目錄然後調用自己來判斷下一層中是否有目錄
		if (srcFile.isDirectory()) {
			if (!accept(filterData, srcFile)) {
				File newFolder = new File(destFile, srcFile.getName());
				newFolder.mkdirs();
				File[] Array = srcFile.listFiles();

				for (File file : Array)
					copy(file, newFolder);
			}
		} else {
			// 判斷是否為要過濾的類型，是則不做任何事，不是就執行複製
			if (!accept(filterData, srcFile)) {
				File newFile = new File(destFile, srcFile.getName());
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));

				byte[] buff = new byte[1024];
				int len = 0;
				while ((len = bis.read(buff)) != -1) {
					bos.write(buff, 0, len);
				}

				bos.close();
				bis.close();
			}
		}
	}

	/**
	 * 過濾
	 * 
	 * @param pathname
	 * @param srcFile
	 * @return
	 * @throws Exception
	 */
	public static boolean accept(List<String> filterData, File srcFile) throws Exception {
		boolean rs = false;

		for (int i = 0; i < filterData.size(); i++) {
			logger.debug(srcFile.getAbsolutePath());
			rs |= srcFile.getAbsolutePath().contains(filterData.get(i));
		}

		return rs;
	}
}
