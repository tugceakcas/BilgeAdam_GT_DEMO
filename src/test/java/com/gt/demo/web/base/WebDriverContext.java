package com.gt.demo.web.base;

import org.openqa.selenium.WebDriver;

/**
 * Web testlerinin kullandığı aktif WebDriver'ı saklayan küçük context sınıfı.
 *
 * <p>ThreadLocal kullanımı, paralel çalışan scenario'larda her thread'in kendi browser'ını
 * kullanmasını sağlar.
 */
public final class WebDriverContext {
  // Paralel web testlerinde driver'ların birbirine karışmaması için thread bazlı saklanır.
  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

  private WebDriverContext() {}

  // Page ve step class'ları ihtiyaç duyduklarında mevcut thread'e ait driver'ı buradan alır.
  public static WebDriver get() {
    WebDriver driver = DRIVER.get();
    if (driver == null) {
      throw new IllegalStateException("WebDriver is not initialized for this thread.");
    }
    return driver;
  }

  // Hook tarafında oluşturulan driver, scenario boyunca erişilebilir olması için kaydedilir.
  public static void set(WebDriver driver) {
    DRIVER.set(driver);
  }

  // Scenario sonunda browser kapatılır ve ThreadLocal alanı temizlenir.
  public static void clear() {
    WebDriver driver = DRIVER.get();
    if (driver != null) {
      driver.quit();
    }
    DRIVER.remove();
  }
}
