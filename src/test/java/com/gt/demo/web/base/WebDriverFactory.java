package com.gt.demo.web.base;

import com.gt.demo.common.config.FrameworkConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Selenium WebDriver üretiminden sorumlu factory sınıfı.
 *
 * <p>Browser tipi ve headless çalışma bilgisi config üzerinden okunur. Eğitim projesi için sadece
 * temel Chrome/Firefox seçenekleri tutulur.
 */
public final class WebDriverFactory {
  private static final String[] CHROME_BINARY_CANDIDATES = {
    "/usr/bin/google-chrome",
    "/usr/bin/google-chrome-stable",
    "/usr/bin/chromium",
    "/usr/bin/chromium-browser",
    "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
  };

  private WebDriverFactory() {}

  // Config'e göre Chrome veya Firefox driver oluşturur. Browser belirtilmezse Chrome kullanılır.
  public static WebDriver create() {
    String browser = FrameworkConfig.get("webdriver.browser");
    if (browser == null || browser.isBlank()) {
      browser = "chrome";
    }

    boolean headless = resolveHeadlessMode();

    return switch (browser.toLowerCase()) {
      case "firefox" -> createFirefox(headless);
      case "chrome" -> createChrome(headless);
      default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
    };
  }

  // Chrome için eğitim/CI ortamlarında ihtiyaç duyulan temel seçenekler hazırlanır.
  private static WebDriver createChrome(boolean headless) {
    ChromeOptions options = new ChromeOptions();
    String chromeBinary = resolveChromeBinary();
    if (chromeBinary != null) {
      options.setBinary(chromeBinary);
    } else {
      String chromeVersion = FrameworkConfig.get("webdriver.chrome.version");
      if (chromeVersion != null && !chromeVersion.isBlank()) {
        options.setBrowserVersion(chromeVersion);
      }
    }
    if (headless) {
      options.addArguments("--headless=new");
    }
    // Linux containers (Codespaces/CI) often need these flags for stable startup.
    if (isLinux()) {
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");
      options.addArguments("--disable-gpu");
      options.addArguments("--disable-crash-reporter");
      options.addArguments("--disable-crashpad");
      options.addArguments("--disable-breakpad");
    }
    options.addArguments("--window-size=1920,1080");
    return new ChromeDriver(options);
  }

  private static String resolveChromeBinary() {
    String configuredBinary = FrameworkConfig.get("webdriver.chrome.binary");
    if (configuredBinary != null && !configuredBinary.isBlank()) {
      if (Files.exists(Path.of(configuredBinary))) {
        return configuredBinary;
      }
      throw new IllegalStateException("Configured Chrome binary not found: " + configuredBinary);
    }

    for (String candidate : CHROME_BINARY_CANDIDATES) {
      if (Files.exists(Path.of(candidate))) {
        return candidate;
      }
    }

    return null;
  }

  // Firefox için sade bir headless/native çalışma ayarı yeterlidir.
  private static WebDriver createFirefox(boolean headless) {
    FirefoxOptions options = new FirefoxOptions();
    if (headless) {
      options.addArguments("-headless");
    }
    return new FirefoxDriver(options);
  }

  // CI veya Codespaces gibi ekransız ortamlarda browser otomatik headless çalıştırılır.
  private static boolean resolveHeadlessMode() {
    boolean configured = FrameworkConfig.getBoolean("webdriver.headless", true);
    if (configured) {
      return true;
    }
    // If there is no usable display in Linux, force headless even if config is false.
    return isLinux() && isDisplayUnavailable();
  }

  private static boolean isLinux() {
    String osName = System.getProperty("os.name", "");
    return osName.toLowerCase().contains("linux");
  }

  private static boolean isDisplayUnavailable() {
    String display = System.getenv("DISPLAY");
    if (display == null || display.isBlank()) {
      return true;
    }

    // VS Code / Codespaces can pass DISPLAY=:99 even when the X server is not really running.
    // In that case Chrome starts in headed mode and exits immediately.
    if (display.startsWith(":")) {
      String displayNumber = display.substring(1).split("\\.")[0];
      return Files.notExists(Path.of("/tmp/.X11-unix/X" + displayNumber));
    }

    return false;
  }
}
