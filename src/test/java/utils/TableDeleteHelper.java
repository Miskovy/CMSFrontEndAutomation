package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class TableDeleteHelper {

    // Safe XPath literal builder (handles quotes in emails)
    private static String xpathLiteral(String s) {
        if (!s.contains("'")) return "'" + s + "'";
        if (!s.contains("\"")) return "\"" + s + "\"";
        String[] parts = s.split("'");
        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(",\"'\",");
            sb.append("'").append(parts[i]).append("'");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Deletes a user row by email. Returns true if delete flow completed (row removed), false otherwise.
     */
    public static boolean deleteUserByEmail(WebDriver driver, String email, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        String emailLit = xpathLiteral(email);

        try {
            // 1) Find the row by email (span text). This exactly matches the HTML you sent.
            By rowBy = By.xpath("//table[@data-slot='table']//tr[.//span[normalize-space() = " + emailLit + "]]");
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowBy));

            // 2) Hover the row (in case buttons show only on hover) and small pause
            try {
                new Actions(driver).moveToElement(row).perform();
                Thread.sleep(200);
            } catch (InterruptedException ignored) {}

            // 3) Prefer button that contains an <svg> whose class contains 'trash' (case-insensitive).
            //    This will match lucide-trash2 / lucide-trash-2 etc and won't match the edit svg.
            By deleteBtnRel = By.xpath(".//button[.//svg[contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'trash')]]");

            WebElement deleteBtn = null;
            try {
                deleteBtn = row.findElement(deleteBtnRel);
            } catch (NoSuchElementException e) {
                // fallback: if not found by svg class, try second button as last resort
                try {
                    java.util.List<WebElement> buttons = row.findElements(By.xpath(".//button"));
                    if (buttons.size() >= 2) {
                        System.out.println("Fallback: picking second button in row (index 1).");
                        deleteBtn = buttons.get(1);
                    } else if (buttons.size() == 1) {
                        System.out.println("Fallback: only one button found in row; using it.");
                        deleteBtn = buttons.get(0);
                    }
                } catch (Exception ignored2) {}
            }

            if (deleteBtn == null) {
                // debug output so you can copy/paste into a bug report
                System.out.println("Delete button not found for email: " + email);
                try {
                    String outer = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].outerHTML;", row);
                    System.out.println("Row outerHTML:\n" + outer);
                } catch (Exception ignored) {}
                return false;
            }

            // 4) Scroll into view and click (try normal click then JS fallback)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", deleteBtn);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
                deleteBtn.click();
            } catch (ElementClickInterceptedException | TimeoutException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
            }

            // 5) handle native confirm() if any
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                shortWait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
            } catch (Exception ignored) {}

            // 6) optionally click modal confirm button if present
            try {
                WebElement confirmBtn = wait.withTimeout(Duration.ofSeconds(3))
                        .until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'confirm') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'delete')]")
                        ));
                if (confirmBtn != null && confirmBtn.isDisplayed()) confirmBtn.click();
            } catch (Exception ignored) {}

            // 7) wait until row is removed
            try {
                wait.until(ExpectedConditions.stalenessOf(row));
            } catch (TimeoutException te) {
                // alternative: wait for absence by same locator
                try {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(rowBy));
                } catch (TimeoutException t2) {
                    System.out.println("Row still present after delete attempt for: " + email);
                    return false;
                }
            }

            System.out.println("Deleted user row for: " + email);
            return true;

        } catch (TimeoutException toe) {
            System.out.println("Row not found for email: " + email);
            return false;
        } catch (StaleElementReferenceException sere) {
            // retry once if table re-rendered
            System.out.println("Stale while deleting; retrying once for: " + email);
            return deleteUserByEmail(driver, email, timeoutSeconds);
        } catch (Exception ex) {
            System.out.println("Exception in deleteUserByEmail: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    public static boolean clickEditByEmail(WebDriver driver, String email, int timeoutSeconds, By waitForLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        String emailLit = xpathLiteral(email);

        try {
            // 1) find the row by exact span email
            By rowBy = By.xpath("//table[@data-slot='table']//tr[.//span[normalize-space() = " + emailLit + "]]");
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowBy));

            // 2) hover the row (in case buttons show on hover) and small pause
            try {
                new Actions(driver).moveToElement(row).perform();
                Thread.sleep(200);
            } catch (InterruptedException ignored) {}

            // 3) preferred selector: button with svg whose class contains 'pen' / 'square-pen' / 'edit' / 'pencil'
            By editBtnRel = By.xpath(".//button[.//svg[contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pen') or contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'edit') or contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pencil')]]");

            WebElement editBtn = null;
            try {
                editBtn = row.findElement(editBtnRel);
            } catch (NoSuchElementException e) {
                // fallback: pick the first button in the row (your HTML shows edit is the first)
                try {
                    java.util.List<WebElement> buttons = row.findElements(By.xpath(".//button"));
                    if (!buttons.isEmpty()) {
                        System.out.println("Fallback: picking first button in row (index 0) as edit.");
                        editBtn = buttons.get(0);
                    }
                } catch (Exception ignored2) {}
            }

            if (editBtn == null) {
                System.out.println("Edit button not found for email: " + email);
                try {
                    String outer = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].outerHTML;", row);
                    System.out.println("Row outerHTML:\n" + outer);
                } catch (Exception ignored) {}
                return false;
            }

            // 4) scroll into view and click (with fallback to JS click)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editBtn);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(editBtn));
                editBtn.click();
            } catch (ElementClickInterceptedException | TimeoutException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
            }

            // 5) if caller provided a locator to wait for (e.g. edit modal or edit form), wait for it
            if (waitForLocator != null) {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(waitForLocator));
                } catch (TimeoutException te) {
                    System.out.println("Edit clicked but expected locator did not appear: " + waitForLocator);
                    return false;
                }
            }

            System.out.println("Clicked edit for email: " + email);
            return true;

        } catch (TimeoutException toe) {
            System.out.println("Row not found for email: " + email);
            return false;
        } catch (StaleElementReferenceException sere) {
            System.out.println("Stale while clicking edit; retrying once for: " + email);
            return clickEditByEmail(driver, email, timeoutSeconds, waitForLocator);
        } catch (Exception ex) {
            System.out.println("Exception in clickEditByEmail: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    public static boolean clickEditByEmail(WebDriver driver, String email, int timeoutSeconds) {
        return clickEditByEmail(driver, email, timeoutSeconds, null);
    }
}
