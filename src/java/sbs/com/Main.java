package sbs.com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import sbs.com.dto.NaverNews;
import sbs.com.dto.dcArticle;

public class Main {
	public static void main(String[] args) {
//		getNewsFromNaverNews();
		getSocietyNewsFromDaum();
		}
	private static void getSocietyNewsFromDaum() {
		
		Path path = Paths.get(System.getProperty("user.dir"), "src/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		
		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));

		driver.get("https://news.daum.net/society#1");
		List<WebElement> Elements = driver.findElements(By.cssSelector(".list_timenews li"));
		for (WebElement Element : Elements) {
			WebElement aElement = Element.findElement(By.cssSelector(".tit_timenews > a"));
			String id = aElement.getAttribute("data-tiara-id=").trim();
			

			 
			
			String title = Element.findElements(By.cssSelector(".link_txt")).get(0).getText().trim();
			String RegTime = Element.findElements(By.cssSelector(".txt_time")).get(0).getText().trim();
			
			System.out.println(id);
			System.out.println(title);
			System.out.println(RegTime);
		}
	}
	private static void getNewsFromNaverNews(){
		File downloadsFolder = new File("downloads/naverNewsFlash");

		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdirs();
		}
		Path path = Paths.get(System.getProperty("user.dir"), "src/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		
		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		
		NaverNews news = new NaverNews();
		
		int i = 1;
		driver.get("https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001");
		List<WebElement> Elements = driver.findElements(By.cssSelector(".content li dl"));
		for (WebElement Element : Elements) {
			WebElement aElement = Element.findElement(By.cssSelector("dt:not(.photo) > a"));
			String href = aElement.getAttribute("href").trim();
			href = href.split("aid=")[1];

			news.code = href.split("&")[0];
			
			news.title = Element.findElements(By.cssSelector("dt:not(.photo) > a")).get(0).getText().trim();
			news.body =Element.findElements(By.cssSelector(".lede")).get(0).getText().trim();
			news.writing =Element.findElements(By.cssSelector(".writing")).get(0).getText().trim();
			news.Date =Element.findElements(By.cssSelector(".date")).get(0).getText().trim();
			
					
			
			System.out.println("------------------------");
			System.out.printf("번호 : %d\n",i);
			System.out.printf("기사 코드 : %s\n",news.code);
			System.out.printf("제목: %s\n",news.title);
			System.out.printf("내용: %s\n",news.body);
			System.out.printf("언론사: %s\n",news.writing);
			System.out.printf("등록시간: %s\n",news.Date);
			System.out.println("------------------------");
			
			i++;
			String thumbUrl = "";

			try {
				thumbUrl = Element.findElement(By.cssSelector("dt.photo > a > img")).getAttribute("src");
			} catch (NoSuchElementException e) {

			}

			if (thumbUrl.length() > 0) {
				BufferedImage saveImage = null;

				try {
					saveImage = ImageIO.read(new URL(thumbUrl));
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (saveImage != null) {
					try {
						String fileName = news.code;
						ImageIO.write(saveImage, "jpg", new File("downloads/naverNewsFlash/" + fileName + ".jpg"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	
	
	private static void getArticleInfoDC(){
		Path path = Paths.get(System.getProperty("user.dir"), "src/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		
		dcArticle article = new dcArticle();
		List<dcArticle> articles = new ArrayList<>();
		
		driver.get("https://gall.dcinside.com/board/lists/?id=tree");
		List<WebElement> Elements = driver.findElements(By.cssSelector(".gall_list .us-post"));
		for (WebElement Element : Elements) {
			
			article.title = Element.findElements(By.cssSelector(".gall_tit")).get(0).getText().trim();
			article.writer =Element.findElements(By.cssSelector(".gall_writer")).get(0).getText().trim();
			
					articles.add(article);
			if (article.title == null || article.title.length() == 0) {
				System.out.println("null");
				continue;
			}
			System.out.println(articles);
			
		}
		
	}
	
	
	
	private static void getArticlesFromMPark() {
			
		Path path = Paths.get(System.getProperty("user.dir"), "src/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("http://mlbpark.donga.com/mp/b.php?b=kbotown");

		ArrayList<String> titles = new ArrayList<>();
		ArrayList<String> date = new ArrayList<>();
		List<WebElement> ArticleElements = driver.findElements(By.cssSelector(".tbl_type01>tbody>tr>td>a "));
		for (WebElement ArticleElement : ArticleElements) {
			String src = ArticleElement.getAttribute("title");
			

			if (src == null || src.length() == 0) {
				continue;
			}
			titles.add(src);
			
		}
		List<WebElement> dateElements = driver.findElements(By.cssSelector(".tbl_type01>tbody>tr>td>.date "));
		for (WebElement dateElement : dateElements) {
			String Cdate = dateElement.getAttribute("");
			date.add(Cdate);
		}
		for(int i=1;i <=10;i++) {
		System.out.printf("%d번 / %s / %s\n",i,titles.get(i),date.get(i));
		
	}
	}
	private static void dwnUnsplashNatureImages() {

		Path path = Paths.get(System.getProperty("user.dir"), "src/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://unsplash.com/t/wallpapers");
		File downloadsFolder = new File("downloads");

		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdir();
		}

		List<WebElement> imgElements = driver
				.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img"));

		System.out.println(imgElements.size());
		for (WebElement imgElement : imgElements) {
			String src = imgElement.getAttribute("src");
			if (src.contains("images.unsplash.com/photo-") == false) {
				continue;
			}

			BufferedImage saveImage = null;
			try {
				saveImage = ImageIO.read(new URL(src));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (saveImage != null) {
				try {

					String fileName = src.split("/")[3];
					fileName = fileName.split("\\?")[0];
					ImageIO.write(saveImage, "jpg", new File("downloads/" + fileName + ".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(src);
		}
	}
}
