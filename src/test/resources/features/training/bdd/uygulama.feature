    @bdd
Feature: ParaBank Login Functionality
    @smoke @tag2
Scenario: Successfully login 2
    Given : I am on ParaBank login page
    When : I login with username "valid-user" and password "valid-pass"
    Then : home page should be displayed
    @debug @happy @tag1

Scenario Outline: Invalid login
    Given I am on ParaBank login page
    When I login with username "<username>" and password "<password>"
    Then I should see an authentication error message

    Examples:
    | username       | password       |
    | invalid-user-1 | invalid-pass-1 |
    | invalid-user-2 | invalid-pass-2 |
Feature: Uygulama testleri
    Uygulama testleri için örnek senaryolar.
    @smoke @happy
  Scenario: Geçersiz giriş yapıldığında net bir hata mesajı gösterilmeli
    Given Uygulama ana sayfasını açtım
    When Geçersiz kullanıcı bilgileri ile giriş yapıyorum
    Then Net bir giriş hatası mesajı görmeliyim
