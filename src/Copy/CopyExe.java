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

			for (int i = 0; i < copyList.size(); i++) {
				CopyUtil.setFilterData(copyList.get(i).getFilterData());

				File srcFile = new File(copyList.get(i).getSrcFile());
				if (!srcFile.exists())
					throw new IOException("來源路徑=>" + srcFile + " 不存在!");

				File destFile = new File(copyList.get(i).getDestFile());
				File[] srcFileList = srcFile.listFiles();

				System.out.println("來源路徑=>" + srcFile + " 確定進行複製?(y/n)");
				Scanner sc = new Scanner(System.in);

				switch (sc.next().toLowerCase()) {
				case "y":
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
				case "n":
					continue;
				default:
					System.out.println("請輸入y或n");
					i--;
				}
			}

			logger.info("執行結束!");

			do {
				outer: try {
					Scanner sc = new Scanner(System.in);
					System.out.println("是否離開?(y/n)");

					switch (sc.next().toLowerCase()) {
					case "y":
						System.out.println("離開程式...");
						return;
					case "n":
						break outer;
					default:
						throw new Exception("請輸入y或n");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					break outer;
				}
			} while (true);

		} catch (Exception e) {
			logger.error("出現異常:", e);
			// e.printStackTrace();
		}
	}

}
