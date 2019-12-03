package typosquatting_detector;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;

public class ReportGenerator {
	
	private static String url;
	private static String imgString;
	private static String source;
	
	private String path ="/Users/HyejunJEONG/eclipse-workspace/typosquatting-detector/WebContent/";
	
	File screenshot;

	public ReportGenerator () {	}
	
	public void setPath(String path) {
		this.path = path;
		System.out.println("[RG] setPath: path = " + path);
	}

	private static String readFileToString(File f) {
		String content = "";
		try {
			content = FileUtils.readFileToString(f);
			String[] lines = content.split("\r\n|\r|\n", 3);
			url = lines[0];
			imgString = lines[1];
			source = lines[2];

		} catch (IOException e) {
			System.err.println("ERROR: No such file");
		}
		return content;
	}

	private static String getURL(File f) {
		return url;
	}
	
	private static String getSource(File f) {
		return source;
	}
	
	private static String getImgPath(File img) {
		return img.getPath();
	}
	
	private static File convert2png(File f, String base64, String basePath) {
		String path = basePath + "images/";
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		String url = getURL(f);
		File screenshot = null;
		BufferedImage image = null;
		byte[] imageByte = Base64.decodeBase64(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		try {
			image = ImageIO.read(bis);
			screenshot = new File(path+"/"+url+".png");
			ImageIO.write(image, "png", screenshot);
			bis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return screenshot;
	}
	
	static StringBuilder htmlStringBuilder = new StringBuilder();
	public static int globalElemID = 1;
	static String restJS = "\");\n" + 
			"  if (x.style.display === \"block\") {\n" + 
			"    x.style.display = \"none\";\n" + 
			"  } else {\n" + 
			"    x.style.display = \"block\";\n" + 
			"  }\n}\n</script>\n";

	private static boolean createReportHeader() {
		String txtStyle = "<style>div {display:none;text-align: justify;word-break: break-all;}\n";
		String btnStyle = "button {font-size:13px;}\n";
		String imgStyle = "img {max-width:100%; height:auto;}</style>\n";
		
		htmlStringBuilder.append("<html>\n<head><title>Typosquatting Detector</title></head>\n");
		htmlStringBuilder.append(txtStyle + btnStyle + imgStyle);
		htmlStringBuilder.append("<h2 style=\"text-align: center;\">Server Report</h2><body>");

		return true;
	}
	
	private static boolean createReportMiddle(File f, String path) {
		String imgPath = getImgPath(convert2png(f, imgString, path));
		String sourceCode = StringEscapeUtils.escapeHtml4(getSource(f));

		htmlStringBuilder.append("<script>\n function myFunction"+globalElemID+"() {\n");
		htmlStringBuilder.append("  var x = document.getElementById(\"myDIV"+globalElemID);
		htmlStringBuilder.append(restJS);

		htmlStringBuilder.append("<br><h4>"+ url +"</h4>\n");
		htmlStringBuilder.append("<img src="+ imgPath +"><br><br>\n");
		htmlStringBuilder.append("<button onclick=\"myFunction"+globalElemID+"()\">View Source Code</button>\n");
		htmlStringBuilder.append("<div id=\"myDIV"+globalElemID+"\">"+ sourceCode +"</div>\n");
	
		return true;
	}
	
	
	private static boolean createReportFooter(String path){
		htmlStringBuilder.append("</body></html>");
		try {
			WriteToFile(htmlStringBuilder.toString(), "report.html", path);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void WriteToFile(String fileContent, String fileName, String path) throws IOException {
		String tempFile = path + File.separator + fileName;
		File report = new File(tempFile);
		
		if(report.exists()) {
			File newFileName = new File(tempFile);
			report.renameTo(newFileName);
		}
		FileUtils.writeStringToFile(report, fileContent);
		System.out.println("Report path: " + report.getPath());
	}
	
	public static File[] readFilesFromFolder(File dir) {
		File[] files;
		FilenameFilter receivedFileFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if(name.startsWith("receivedFile_")) {
					System.out.println("true");
					return true;
				}
				else {
					return false;
				}
			}
		};
		
		files = dir.listFiles(receivedFileFilter);
		for(File file : files) {
			System.out.println(file.getName());
		}
		
		return files;
	}
	
	public void createReport() {
		String filePath = path + "reports/";
		
		File dir = new File(filePath);
		
		File[] files = readFilesFromFolder(dir);

		createReportHeader();		
		for(int i = 0; i < files.length; i ++) {
			readFileToString(files[i]);
			createReportMiddle(files[i], path);
			globalElemID ++;
		}
		createReportFooter(path);
	}
}