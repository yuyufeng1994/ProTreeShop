package controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Yu Yufeng
 *
 */
@Controller
public class CommonController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/thumbnail/{uuidExt}", method = RequestMethod.GET)
	public void thumbnail(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@PathVariable("uuidExt") String uuidExt) {
		String path = "c://upload/" + uuidExt + ".png";
		try {
			InputStream inputStream = new FileInputStream(path);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e) {
			LOG.info("文件缺失："+path);
		}
	}

}
