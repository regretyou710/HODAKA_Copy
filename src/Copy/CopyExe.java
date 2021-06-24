package Copy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Copy.tw.com.bean.Copy;
import Copy.tw.com.util.CopyUtil;

public class CopyExe {
	private static Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

	public static void main(String[] args) {

		try {
			List<Copy> copyList = CopyUtil.getCopyList();

			for (Copy item : copyList) {

				CopyUtil.setFilterData(item.getFilterData());

				File srcFile = new File(item.getSrcFile());
				if (!srcFile.exists())
					throw new IOException("來源路徑=>" + srcFile + " 不存在!");

				File destFile = new File(item.getDestFile());

				File[] srcFileList = srcFile.listFiles();

				System.out.println("來源路徑=>" + srcFile + " 確定進行複製?(y/n)");
				Scanner sc = new Scanner(System.in);

				while (sc.next().toLowerCase().equals("y")) {
					if (!destFile.exists())
						destFile.mkdirs();

					double startTime = System.currentTimeMillis();

					if (srcFile.isDirectory()) {
						for (File f : srcFileList) {
							CopyUtil.copy(f, destFile);
							logger.info("{} 複製檔案成功!", f.getName());
						}
					} else {
						CopyUtil.copy(srcFile, destFile);
						logger.info("{} 複製檔案成功!", srcFile.getName());
					}

					double endTime = System.currentTimeMillis();
					logger.info("花費時間{}秒", (endTime - startTime) / 1000);
					System.out.println("********************************************");
					System.out.println("********************************************");
					break;
				}
			}

			logger.info("執行結束!");

		} catch (Exception e) {
			logger.error("出現異常:", e);
			// e.printStackTrace();
		}
	}

}
